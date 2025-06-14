package com.example.board.service.dto

import com.example.board.domain.Post

data class PostDetailResponseDto(
  val id: Long,
  val title: String,
  val content: String,
  val createdBy: String,
  val createdAt: String,
  val comments: List<CommentResponseDto>,
)

fun Post.toDetailResponseDto() = PostDetailResponseDto(
  id = id,
  title = title,
  content = content,
  createdBy = createdBy,
  createdAt = createdAt.toString(),
  comments = comments.map { it.toResponseDto() }
)
