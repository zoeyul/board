package com.example.board.domain

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

// base entity를 상속하는 아이들이 내부 속성을 갖을 수 있다.
@MappedSuperclass
abstract class BaseEntity(
  createdBy: String,
) {
  val createdBy: String = createdBy
  val createdAt: LocalDateTime = LocalDateTime.now()
  var updatedBy: String? = null
    // 외부에서 변경할 수 없게
    protected set
  var updatedAt: LocalDateTime? = null
    protected set

  fun updatedBy(updatedBy: String) {
    this.updatedBy = updatedBy
    this.updatedAt = LocalDateTime.now()
  }
}
