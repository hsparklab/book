package com.hsparklab.boot.springboot.domain.posts;

import com.hsparklab.book.springboot.Application;
import com.hsparklab.book.springboot.domain.posts.Posts;
import com.hsparklab.book.springboot.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("contents")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();


        //then
        Posts posts = postsList.get(0);
        System.out.println("CreateDate : "+ posts.getCreatedDate());
        System.out.println("ModifiedDate : "+ posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title="테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("test@test.com")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();


        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }
}
