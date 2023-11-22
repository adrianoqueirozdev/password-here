package adrianoqueiroz.dev.strongpass.data.service.impl

import adrianoqueiroz.dev.strongpass.data.model.User
import adrianoqueiroz.dev.strongpass.data.service.AccountService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {
   override val currentUserId: String
      get() = auth.currentUser?.uid.orEmpty()

   override val hasUser: Boolean
      get() = auth.currentUser != null

   override val currentUser: Flow<User>
      get() = callbackFlow {
         val listener = FirebaseAuth.AuthStateListener { auth ->
            this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
         }
         auth.addAuthStateListener(listener)
         awaitClose { auth.removeAuthStateListener(listener) }
      }

   override suspend fun authenticate(email: String, password: String) {
      auth.signInWithEmailAndPassword(email, password).await()
   }

   override suspend fun sendPasswordResetEmail(email: String) {
      auth.sendPasswordResetEmail(email).await()
   }

   override suspend fun signOut() {
      auth.signOut()
   }
}