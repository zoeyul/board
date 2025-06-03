package com.example.board.service.dto

data class CommentCreateRequest(
  val content: String,
  val createdBy: String,
)
