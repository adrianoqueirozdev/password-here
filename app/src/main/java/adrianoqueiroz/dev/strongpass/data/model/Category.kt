package adrianoqueiroz.dev.strongpass.data.model

data class Category(
   val id: String = "",
   val name: String = "",
   val type: String = "",
   var totalPasswords: Int = 0,
)
