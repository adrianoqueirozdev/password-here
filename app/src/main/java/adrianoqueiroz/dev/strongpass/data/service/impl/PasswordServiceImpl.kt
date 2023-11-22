package adrianoqueiroz.dev.strongpass.data.service.impl

import adrianoqueiroz.dev.strongpass.data.constants.FirebaseCollections
import adrianoqueiroz.dev.strongpass.data.model.Password
import adrianoqueiroz.dev.strongpass.data.model.fields.PasswordFields
import adrianoqueiroz.dev.strongpass.data.service.PasswordService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PasswordServiceImpl @Inject constructor(
   private val firestore: FirebaseFirestore,
   private val auth: FirebaseAuth,
) : PasswordService {
   override suspend fun getPasswordsByCategory(categoryId: String): List<Password> {
      val userId = auth.currentUser?.uid

      val querySnapshot = firestore.collection(FirebaseCollections.PASSWORDS)
         .whereEqualTo(PasswordFields.REMOVED, false)
         .whereEqualTo(PasswordFields.PASSWORD_CATEGORY_ID, categoryId)
         .whereEqualTo(PasswordFields.CURRENT_USER, userId)
         .orderBy(PasswordFields.CREATED_AT, Query.Direction.DESCENDING)
         .get()
         .await()

      return querySnapshot.toObjects(Password::class.java)
   }

   override suspend fun getPasswordById(passwordId: String): Password {
      return firestore.collection(FirebaseCollections.PASSWORDS).document(passwordId).get().await()
         .toObject(Password::class.java)!!
   }

   override suspend fun createPassword(password: Password) {
      val result = firestore.collection(FirebaseCollections.PASSWORDS).add(password).await()

      if (result != null) {
         updatePasswordId(result.id, FirebaseCollections.PASSWORDS)
      }
   }

   override suspend fun updatePassword(password: Password) {
      firestore.collection(FirebaseCollections.PASSWORDS).document(password.id).set(password)
         .await()
   }

   override suspend fun deletePassword(passwordId: String) {
      firestore.collection(FirebaseCollections.PASSWORDS).document(passwordId).update(
         PasswordFields.REMOVED, true
      ).await()
   }

   private suspend fun updatePasswordId(id: String, collectionName: String) {
      firestore.collection(FirebaseCollections.PASSWORDS).document(id).update(
         PasswordFields.ID, id
      ).await()
   }
}