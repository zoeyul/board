package com.example.board.controller.dto

import com.example.board.service.dto.PostUpdateRequestDto

data class PostUpdateRequest(
  val title: String,
  val content: String,
  val updatedBy: String,
)

fun PostUpdateRequest.toDto() = PostUpdateRequestDto(title, content, updatedBy)
