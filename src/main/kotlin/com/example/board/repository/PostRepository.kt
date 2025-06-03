package com.example.board.repository

import com.example.board.domain.Post
import com.example.board.domain.QPost.post
import com.example.board.service.dto.PostSearchRequestDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository

// 기본 jpa로는 검색하기 쉽지 않아 querydsl을 사용
interface CustomPostRepository {
  fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post>
}

class CustomPostRepositoryImpl : CustomPostRepository, QuerydslRepositorySupport(Post::class.java) {
  override fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post> {
    val result = from(post)
      .where(
        postSearchRequestDto.title?.let { post.title.contains(it) },
        postSearchRequestDto.createdBy?.let { post.createdBy.eq(it) }
      )
      .orderBy(post.createdAt.desc())
      .offset(pageRequest.offset)
      .limit(pageRequest.pageSize.toLong())
      .fetchResults()

    return PageImpl(result.results, pageRequest, result.total)
  }
}
