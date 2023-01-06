package com.triprint.backend.domain.user.entity;

import com.triprint.backend.domain.bookmark.entity.Bookmark;
import com.triprint.backend.domain.comment.entity.Comment;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.user.status.UserRole;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.entity.PostGroup;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String profileImg;

    @CreatedDate
    private Timestamp createdAt;

    @LastModifiedDate
    private Timestamp updatedAt;

    @Column(name = "kakao_id")
    private String kakaoId;

    @OneToMany(
            mappedBy = "user"
    )
    private List<Post> posts = new ArrayList();

    @OneToMany(
            mappedBy = "user"
    )
    private List<Bookmark> bookmarks = new ArrayList();

    @OneToMany(
            mappedBy = "user"
    )
    private List<PostGroup> postGroup = new ArrayList();

    @OneToMany(
            mappedBy = "user"
    )
    private List<Like> likes = new ArrayList();

    @OneToMany(
            mappedBy = "user"
    )
    private List<Comment> comments = new ArrayList();

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "parent_user_id"
    )
    public User parent;

    @OneToMany(
            mappedBy = "parent",
            cascade = {CascadeType.ALL}
    )
    public List<User> children;

    @Builder
    public User(String kakaoId, String kakaoProfileImg, String kakaoNickname,
                String kakaoEmail, UserRole userRole) {

        this.kakaoId = kakaoId;
        this.profileImg = kakaoProfileImg;
        this.username = kakaoNickname;;
        this.email = kakaoEmail;
        this.role = userRole;
    }

    public void editProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
    public void editUsername(String username) {
        this.username = username;
    }
}

