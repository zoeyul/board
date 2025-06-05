package com.example.board.service

import com.example.board.exception.CommentNotDeletableException
import com.example.board.exception.CommentNotFoundException
import com.example.board.exception.PostNotFoundException
import com.example.board.repository.CommentRepository
import com.example.board.repository.PostRepository
import com.example.board.service.dto.CommentCreateRequestDto
import com.example.board.service.dto.CommentUpdateRequestDto
import com.example.board.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
  private val commentRepository: CommentRepository,
  private val postRepository: PostRepository,
) {
  fun createComment(postId: Long, createRequestDto: CommentCreateRequestDto): Long {
    val post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException()
    return commentRepository.save(createRequestDto.toEntity(post)).id
  }

  @Transactional
  fun updateComment(id: Long, updateRequestDto: CommentUpdateRequestDto): Long {
    val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
    comment.update(updateRequestDto)
    return comment.id
  }

  fun deleteComment(id: Long, deletedBy: String): Long {
    val comment = commentRepository.findByIdOrNull(id) ?: throw CommentNotFoundException()
    if (comment.createdBy != deletedBy) {
      throw CommentNotDeletableException()
    }
    commentRepository.delete(comment)
    return id
  }
}
