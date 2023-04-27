package com.example.sorty2.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
data class GroupList(
    val id: String = "",
    val name: String = "",
    val items: List<ListItem> = emptyList()
)