package com.example.board.controller.dto

data class PostUpdateRequest(
  val title: String,
  val content: String,
  val updatedBy: String,
)
