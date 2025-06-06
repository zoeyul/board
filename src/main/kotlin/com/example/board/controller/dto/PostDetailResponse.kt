package com.example.board.controller.dto

import com.example.board.service.dto.CommentResponse
import com.example.board.service.dto.PostDetailResponseDto
import com.example.board.service.dto.toResponse

data class PostDetailResponse(
  val id: Long,
  val title: String,
  val content: String,
  val createdBy: String,
  val createdAt: String,
  val comments: List<CommentResponse> = emptyList(),
)

fun PostDetailResponseDto.toResponse() = PostDetailResponse(
  id = id,
  title = title,
  content = content,
  createdBy = createdBy,
  createdAt = createdAt,
  comments = comments.map { it.toResponse() }
)
