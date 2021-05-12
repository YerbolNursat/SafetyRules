package kz.dungeonmasters.home.data.entity

data class CategoryResponse(
    val count: Int,
    val next: Int,
    val previous: Int,
    val results: List<Category>
)

data class Category(
    val icon: String,
    val size: Int,
    val is_active: Boolean,
    val title: String,
    val code: String
)