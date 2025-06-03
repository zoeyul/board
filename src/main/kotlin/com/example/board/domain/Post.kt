package com.example.board.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Post(
  createdBy: String,
  title: String,
  content: String,
) : BaseEntity(createdBy) {
  @Id
  // db에서 아이디 생성해서 넣어줌
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0

  var title: String = title
    protected set
  var Content: String = content
    protected set
}
