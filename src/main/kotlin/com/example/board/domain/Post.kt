package com.example.board.domain

import com.example.board.exception.PostNotUpdatableException
import com.example.board.service.dto.PostUpdateRequestDto
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

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
  var content: String = content
    protected set

  @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = [CascadeType.ALL])
  var comments: MutableList<Comment> = mutableListOf()
    protected set

  fun update(postUpdateRequestDto: PostUpdateRequestDto) {
    if (postUpdateRequestDto.updatedBy != this.createdBy) {
      throw PostNotUpdatableException()
    }
    this.title = postUpdateRequestDto.title
    this.content = postUpdateRequestDto.content
    super.updatedBy(postUpdateRequestDto.updatedBy)
  }
}
