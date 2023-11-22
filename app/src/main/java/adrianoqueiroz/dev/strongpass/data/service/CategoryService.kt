package adrianoqueiroz.dev.strongpass.data.service

import adrianoqueiroz.dev.strongpass.data.model.Category

interface CategoryService {

   suspend fun getCategories(): List<Category>

   suspend fun getCategoryById(categoryId: String): Category

   suspend fun incrementCategoryCountPasswords(categoryId: String)
}