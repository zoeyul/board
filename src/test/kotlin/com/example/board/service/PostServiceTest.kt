package com.example.board.service

import com.example.board.domain.Post
import com.example.board.exception.PostNotDeletableException
import com.example.board.exception.PostNotFoundException
import com.example.board.exception.PostNotUpdatableException
import com.example.board.repository.PostRepository
import com.example.board.service.dto.PostCreateRequestDto
import com.example.board.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
  private val postService: PostService,
  private val postRepository: PostRepository
): BehaviorSpec({
  given("게시글 생성시") {
    `when`("게시글 인풋이 정상적으로 들어오면") {
      val postId = postService.createPost(
        PostCreateRequestDto(
          title = "제목",
          content = "내용",
          createdBy = "harris",
        )
      )
      then("게시글이 정상적으로 생성됨을 확인한다.") {
        postId shouldBeGreaterThan 0L
        val post = postRepository.findByIdOrNull(postId)
        post shouldNotBe null
        post?.title shouldBe "제목"
        post?.content shouldBe "내용"
        post?.createdBy shouldBe "harris"
      }
    }
  }

  given("게시글 수정시"){
    val saved = postRepository.save(Post(title="title", content = "content", createdBy = "harris"))
    `when`("정상 수정시") {
      val updatedId = postService.updatePost(saved.id, PostUpdateRequestDto(
          title = "updated title",
          content = "updated content",
          updatedBy = "harris"
        )
      )
      then("게시글이 정상적으로 수정됨을 확인한다.") {
        saved.id shouldBe updatedId
        val updated = postRepository.findByIdOrNull(updatedId)
        updated shouldNotBe null
        updated?.title shouldBe "updated title"
        updated?.content shouldBe "updated content"
        updated?.updatedBy shouldBe "harris"
      }
    }

    `when`("게시글을 찾을 수 없을 때") {
      then("게시글을 찾을 수 없다라는 예외가 발생한다."){
        shouldThrow<PostNotFoundException>{
          postService.updatePost(9999L, PostUpdateRequestDto(
            title = "updated title",
            content = "updated content",
            updatedBy = "harris"
          ))
        }
      }
    }


    `when`("작성자가 동일하지 않으면") {
      then("수정할 수 없는 게시물입니다 예외가 발생한다."){
        shouldThrow<PostNotUpdatableException>{
          postService.updatePost(1L, PostUpdateRequestDto(
            title = "updated title",
            content = "updated content",
            updatedBy = "updated harris"
          ))
        }
      }
    }
  }

  given("게시글 삭제시") {
    val saved = postRepository.save(Post(title="title", content = "content", createdBy = "harris"))
    `when`("정상 삭제시") {
      val postId = postService.deletePost(saved.id, "harris")
      then("게시글이 정상적으로 삭제됨을 확인한다."){
        postId shouldBe saved.id
        postRepository.findByIdOrNull(saved.id) shouldBe "null"
      }
    }
    `when`("작성자가 동일하지 않으면") {
      val saved2 = postRepository.save(Post(title="title", content = "content", createdBy = "harris"))
      then("삭제할 수 없는 게시물입니다 예외가 발생한다."){
        shouldThrow<PostNotDeletableException>{
          postService.deletePost(
            saved2.id,
            "harris2"
          )
        }
      }
    }
  }
})
