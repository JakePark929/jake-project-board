package com.jake.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true) // 안쪽까지 들어가서 call string을 찍어 내겠다(AuditingFields)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
}) // index 는 불가
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment, MySQL은 Identity로 해야함
    private Long id;

    // 일부만 Setter로 지정, 임의로 내용을 수정할 수 없게끔..
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount; // 유저 정보 (ID)

    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @Setter private String hashtag; // 해시태그, Optional

    @ToString.Exclude // 퍼포먼스 메모리 저하 등 문제, 설큘러 레퍼런싱 문제 - Article Comment 까지 찍으면 Article도 찍음(순환참조)
//    @OrderBy("id")
    @OrderBy("createdAt DESC") // 시간순 정렬
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // 실무에서는 양방향 바인딩을 풀기도 함, 원치않는 데이터 손실이 일어날 수 있음.. 운영때 댓글 백업 등..
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>(); // 양방향 설정

    // 자동으로 세팅, JPA Auditing
//    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일자
//    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
//    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
//    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

    // 공통 추출 MappedSuperClass - 상속 또는 Embedded - 필드로 추가
//    @Embedded AAA aa; // - 공통필드로 치환됨
//    class AAA { }

    protected Article() {} // 하이버 네이트 구현체는 기본 생성자를 가지고 있어야함, protected까지 열 수 있음

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 팩토리 메소드로 간단히 생성할 수 있게 만듦
    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false; // pattern variable 방식
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
