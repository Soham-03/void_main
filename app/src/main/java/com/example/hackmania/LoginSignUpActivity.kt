package com.example.hackmania

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.hackmania.model.Global
import com.example.hackmania.ui.theme.HackManiaTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginSignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HackManiaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val startForResult =
                        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                            if (result.resultCode == RESULT_OK) {
                                val intent = result.data
                                if (result.data != null) {
                                    lifecycleScope.launch {
                                        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
                                        handleSignInResult(task)
                                    }
                                }
                            }
                        }
                    if(FirebaseAuth.getInstance().currentUser==null){
                        Button(onClick = {
                            startForResult.launch(googleSignInClient.signInIntent)
                        }) {
                            Text(text = "Sign Up with Google")
                        }
                    }
                    else{
                        val intent = Intent(this@LoginSignUpActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        getGoogleLoginAuth()
    }
    private fun getGoogleLoginAuth(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    private suspend fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Now, you can authenticate with Firebase using the GoogleSignInAccount's ID token.
            if (account != null) {
                val idToken = account.idToken
                val credential = GoogleAuthProvider.getCredential(idToken, null)

                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign-in successful. You can access the Firebase user here.
                            val user = FirebaseAuth.getInstance().currentUser
                            if (user != null) {
//                                val doc = FirebaseFirestore.getInstance().collection("users").document(user.uid).get()
//                                val exists = doc.result.exists()
//                                if(!exists){
                                val hashMap = HashMap<String, Any>()
                                hashMap["userName"] = user.displayName.toString()
                                hashMap["userImage"] = user.photoUrl.toString()
                                hashMap["wallet"] = 0
                                hashMap["carbonFoot"] = "0.0"
                                FirebaseFirestore.getInstance().collection("users").document(user.uid)
                                    .set(hashMap).addOnSuccessListener {
                                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                                    }
//                                }
                                println("Current User: ${user.uid}")
                                val intent = Intent(this@LoginSignUpActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            // Sign-in failed.
                            Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                        }
                    }
                    .await()
            } else {
                // Handle the case where the GoogleSignInAccount is null.
            }
        } catch (e: ApiException) {
            // Handle ApiException here.
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
        }
    }
}