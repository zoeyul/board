package com.example.board.service.dto

data class CommentUpdateRequest(
  val content: String,
  val updatedBy: String,
)

fun CommentUpdateRequest.toDto() = CommentUpdateRequestDto(
  content = content,
  updatedBy = updatedBy
)
