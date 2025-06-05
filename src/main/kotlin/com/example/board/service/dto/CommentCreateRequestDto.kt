package com.example.board.service.dto

import com.example.board.domain.Comment
import com.example.board.domain.Post

data class CommentCreateRequestDto(
  val content: String,
  val createdBy: String,
)

fun CommentCreateRequestDto.toEntity(post: Post) = Comment(
  content = content,
  createdBy = createdBy,
  post = post
)
