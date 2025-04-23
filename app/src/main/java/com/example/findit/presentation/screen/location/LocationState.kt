package com.example.findit.presentation.screen.location

import com.example.findit.domain.model.PostDomain

data class LocationState(
    val isLoading: Boolean = false,
    val posts: List<PostDomain> = emptyList(),
    val error: String? = null
)