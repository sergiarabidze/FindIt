package com.example.findit.domain.model

enum class PostType {
    FOUND{
        override fun toString(): String {
            return "found"
        }
    },
    LOST{
        override fun toString(): String {
            return "lost"
        }
    }
}