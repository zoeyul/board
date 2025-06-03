package com.example.board.service.dto

data class CommentUpdateRequest(
  val content: String,
  val updatedBy: String,
)
