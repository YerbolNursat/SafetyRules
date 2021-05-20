package kz.dungeonmasters.tests.data.entity

data class TestQuestionsResponse(
    val id: Int,
    val title: String?,
    val body: String?,
    val questions: List<TestQuestions>
)

data class TestQuestions(
    val id: Int,
    val title: String?,
    val body: String?,
    val options: List<TestAnswers>
)

data class TestAnswers(
    val id: Int,
    val title: String?,
    var clicked:Boolean = false,
    val body: String?
)