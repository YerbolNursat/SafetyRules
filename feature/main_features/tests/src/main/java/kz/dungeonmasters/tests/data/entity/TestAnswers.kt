package kz.dungeonmasters.tests.data.entity

data class TestAnswersResponse(
    val quiz: Int,
    val success: Boolean,
    val title: String,
    val body: String,
    val accepted_answers: List<TestAnswer>
)

data class TestAnswer(
    val is_correct: Boolean
)
