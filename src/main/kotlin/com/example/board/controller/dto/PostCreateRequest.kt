package com.example.board.controller.dto

import com.example.board.service.dto.PostCreateRequestDto

data class PostCreateRequest(
  val title: String,
  val content: String,
  val createdBy: String,
)

fun PostCreateRequest.toDto() = PostCreateRequestDto(title, content, createdBy)
