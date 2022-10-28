package com.jake.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment, MySQL은 Identity로 해야함
    private Long id;

//    @Setter private Long articleId; // 게시글 (ID), 연관관계 맵핑 없이 Join 가능
    @Setter @ManyToOne(optional = false) private Article article; // 게시글 (ID)
    @Setter @ManyToOne(optional = false) private UserAccount userAccount; // 유저 정보 (ID)

    @Setter @Column(nullable = false, length = 500) private String content; // 본문

    // 자동으로 세팅, JPA Auditing
//    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일자
//    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
//    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
//    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

    protected ArticleComment() {}

    private ArticleComment(Article article, UserAccount userAccount, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
    }

    public static ArticleComment of(Article article, UserAccount userAccount, String content) {
        return new ArticleComment(article, userAccount, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
