package kz.dungeonmasters.tests.data.entity

data class TestAnswersResponse (
    val quiz:Int,
    val accepted_answers:List<TestAnswer>
)
data class TestAnswer (
    val is_correct:Boolean
)
