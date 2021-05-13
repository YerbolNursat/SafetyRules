package kz.dungeonmasters.home.data.entity

data class Theory(
    val articles: List<Article>,
    val code: String,
    val comics: List<Comic>,
    val quizzes: List<Quiz>,
    val title: String,
    val videos: List<Video>

)

data class Comic(
    val code: String,
    val title: String?,
    val url: String,
    val icon: String
)

data class Article(
    val body: String?,
    val code: String,
    val title: String?
)

data class Video(
    val code: String,
    val title: String?,
    val url: String,
    val icon: String
)

data class Quiz(
    val body: String?,
    val id: Int,
    val score: Int,
    val title: String?
)