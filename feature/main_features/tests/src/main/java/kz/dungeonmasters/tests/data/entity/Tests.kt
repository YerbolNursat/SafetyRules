package kz.dungeonmasters.tests.data.entity

data class TestsResponse(
    val id: Int,
    val is_active: Boolean,
    val quizzes: List<Tests>,
    val title: String
)

data class Tests(
    val body: String?,
    val id: String,
    val score: Int?,
    val title: String,
    val is_active: Boolean,
)