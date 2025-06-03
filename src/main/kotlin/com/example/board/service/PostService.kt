package com.example.board.service

import com.example.board.exception.PostNotDeletableException
import com.example.board.exception.PostNotFoundException
import com.example.board.repository.PostRepository
import com.example.board.service.dto.PostCreateRequestDto
import com.example.board.service.dto.PostDetailResponseDto
import com.example.board.service.dto.PostSearchRequestDto
import com.example.board.service.dto.PostSummaryResponseDto
import com.example.board.service.dto.PostUpdateRequestDto
import com.example.board.service.dto.toDetailResponseDto
import com.example.board.service.dto.toEntity
import com.example.board.service.dto.toSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostService(
  private val postRepository: PostRepository,
) {
  // 함수단위에서는 readeonly 제거, 여기가 더 구체적이라 여기걸로 적용됨
  @Transactional
  fun createPost(requestDto: PostCreateRequestDto): Long {
    return postRepository.save(requestDto.toEntity()).id
  }

  @Transactional
  fun updatePost(id: Long, requestDto: PostUpdateRequestDto): Long {
    val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
    post.update(requestDto)
    return id
  }

  @Transactional
  fun deletePost(id: Long, deletedBy: String): Long {
    val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
    if (post.createdBy != deletedBy) throw PostNotDeletableException()
    postRepository.delete(post)
    return id
  }

  fun getPost(id: Long): PostDetailResponseDto {
    return postRepository.findByIdOrNull(id)?.toDetailResponseDto() ?: throw PostNotFoundException()
  }

  fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<PostSummaryResponseDto> {
    return postRepository.findPageBy(pageRequest, postSearchRequestDto).toSummaryResponseDto()
  }
}
