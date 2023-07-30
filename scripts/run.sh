#!/bin/bash

java -jar ./app.jar \
  --spring.datasource.url="$DB_URL" \
  --spring.datasource.username="$DB_NAME" \
  --spring.datasource.password="$DB_PASSWORD" \
  --spring.security.oauth2.registration.kakaoclientId="$CLIENT_ID" \
  --spring.security.oauth2.registration.kakaoclientSecret="$CLIENT_SECRET" \
  --spring.security.oauth2.registration.kakaoredirectUri="$REDIRECT_URI" \
  --cloud.aws.credentials.accessKey="$ACCESS_KEY" \
  --cloud.aws.credentials.secretKey="$SECRET_KEY" \
  --cloud.aws.s3.bucket="$BUCKET_NAME" \
  --cors.allowd-origins="$FRONT_URL" \
  --app.auth.tokenSecret="$TOKEN_SECRET" \
  --app.auth.refreshTokenSecret="$REFRESH_TOKEN_SECRET" \
  --app.auth.tokenExpiration="$EXPIRATION_TIME" \
  --app.auth.refreshTokenExpiration="$REFRESH_EXPIRATION_TIME" \
  --app.cors.allowedOrigins="$FRONT_URL" \
  --app.oauth2.authorizedRedirectUris="$FRONT_URL" \
  --app.kakaoAccount.serviceAppAdminKey = "$SERVICE_APP_ADMIN_KEY"
