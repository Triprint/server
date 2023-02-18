package com.triprint.backend.domain.auth.security.oauth2;

import com.triprint.backend.domain.auth.security.AuthToken;
import com.triprint.backend.domain.auth.security.TokenProvider;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.core.config.AppProperties;
import com.triprint.backend.domain.auth.security.oauth2.user.AuthProvider;
import com.triprint.backend.domain.auth.security.service.AuthService;
import com.triprint.backend.domain.auth.security.service.RefreshTokenService;
import com.triprint.backend.domain.user.entity.UserRefreshToken;
import com.triprint.backend.domain.user.repository.UserRefreshTokenRepository;
import com.triprint.backend.domain.user.status.UserRole;
import com.triprint.backend.domain.user.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import static com.triprint.backend.domain.auth.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.triprint.backend.domain.auth.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REFRESH_TOKEN;



@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final RefreshTokenService refreshTokenService;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = getTargetUrl(request);
        String accessToken = this.createTokenAndCookie(request, response, authentication).getToken();

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, this.determineTargetUrl(targetUrl, accessToken));
    }

    protected String determineTargetUrl(String targetUrl, String accessToken) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken)
                .build().toUriString();
    }

    private String getTargetUrl(HttpServletRequest request){
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        return redirectUri.orElse(getDefaultTargetUrl());
    }

    private AuthToken createTokenAndCookie(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserRole roleType = hasAuthority(userPrincipal.getAuthorities(), UserRole.ADMIN.getCode()) ? UserRole.ADMIN : UserRole.USER;

        Long userId = userPrincipal.getId();
        AuthToken accessToken = tokenProvider.createAccessToken(
                userId,
                roleType.getCode()
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiration();
        AuthToken refreshToken = tokenProvider.createRefreshToken(
                userId
        );

        refreshTokenService.findOrCreate(userId, refreshToken.getToken());

        int cookieMaxAge = (int) refreshTokenExpiry / 1000;
        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtils.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return accessToken;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
        if (authorities == null) {
            return false;
        }

        for (GrantedAuthority grantedAuthority : authorities) {
            if (authority.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}