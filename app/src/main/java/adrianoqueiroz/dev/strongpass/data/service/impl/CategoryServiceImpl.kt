package adrianoqueiroz.dev.strongpass.data.service.impl

import adrianoqueiroz.dev.strongpass.data.constants.FirebaseCollections
import adrianoqueiroz.dev.strongpass.data.model.Category
import adrianoqueiroz.dev.strongpass.data.model.fields.CategoryFields
import adrianoqueiroz.dev.strongpass.data.service.CategoryService
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryServiceImpl @Inject constructor(private val firestore: FirebaseFirestore) :
   CategoryService {

   override suspend fun getCategories(): List<Category> {
      return firestore.collection(FirebaseCollections.CATEGORIES).orderBy(CategoryFields.NAME).get()
         .await().toObjects(Category::class.java)
   }

   override suspend fun getCategoryById(categoryId: String): Category {
      return firestore.collection(FirebaseCollections.CATEGORIES).document(categoryId).get().await()
         .toObject(Category::class.java)!!
   }

   override suspend fun incrementCategoryCountPasswords(categoryId: String) {
      firestore.collection(FirebaseCollections.CATEGORIES).document(categoryId)
         .update(CategoryFields.TOTAL_PASSWORDS, FieldValue.increment(1)).await()
   }
}