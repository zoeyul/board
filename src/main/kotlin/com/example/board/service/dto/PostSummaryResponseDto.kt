package com.example.board.service.dto

import com.example.board.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponseDto(
  val id: Long,
  val title: String,
  val createdBy: String,
  val createdAt: String,
)

// 페이지에 대한 확장함수
fun Page<Post>.toSummaryResponseDto() = PageImpl(
  // 아래 post를 summaryDto로 만드는 확장 함수를 이용
  content.map { it.toSummaryResponseDto() },
  pageable,
  totalElements
)

// post를 summaryDto로 만드는 확장 함수
fun Post.toSummaryResponseDto() = PostSummaryResponseDto(
  id = id,
  title = title,
  createdBy = createdBy,
  createdAt = createdAt.toString()
)
