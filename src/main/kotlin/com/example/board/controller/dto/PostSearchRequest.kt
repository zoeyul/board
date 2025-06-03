package com.example.board.controller.dto

import com.example.board.service.dto.PostSearchRequestDto

data class PostSearchRequest(
  val title: String?,
  val createdBy: String?,
)

fun PostSearchRequest.toDto() = PostSearchRequestDto(
  title = title,
  createdBy = createdBy
)
