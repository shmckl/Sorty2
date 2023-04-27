package com.example.sorty2.data


data class GroupList(
    val id: String = "",
    val name: String = "",
    val items: List<ListItem> = emptyList()
)