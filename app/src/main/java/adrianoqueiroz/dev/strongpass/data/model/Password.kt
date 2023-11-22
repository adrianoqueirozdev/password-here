package adrianoqueiroz.dev.strongpass.data.model

data class Password(
   val id: String = "",
   var name: String = "",
   var userId: String = "",
   val currentUser: String = "",
   var password: String = "",
   val passwordCategoryId: String = "",
   var createdAt: String = "",
   val removed: Boolean = false,
)
