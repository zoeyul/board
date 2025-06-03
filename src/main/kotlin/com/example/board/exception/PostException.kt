package com.example.board.exception

//open은 상속을 가능하게 해줌
open class PostException(message: String) : RuntimeException(message)

class PostNotFoundException() : PostException("게시글을 찾을 수 없습니다.")

class PostNotUpdatableException : PostException("게시글을 수정할 수 없습니다.")

class PostNotDeletableException() : PostException("게시글을 삭제할 수 없습니다.")
