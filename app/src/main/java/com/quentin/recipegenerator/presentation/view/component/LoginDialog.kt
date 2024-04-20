package com.quentin.recipegenerator.presentation.view.component

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.quentin.recipegenerator.presentation.ui.theme.Background
import com.quentin.recipegenerator.presentation.ui.theme.ButtonOrHighlight
import com.quentin.recipegenerator.presentation.ui.theme.Headline
import com.quentin.recipegenerator.presentation.ui.theme.Secondary
import com.quentin.recipegenerator.presentation.ui.theme.Stroke
import com.quentin.recipegenerator.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

@Composable
fun LoginDialog(onDismissRequest: ()->Unit, mainViewModel: MainViewModel, context: Context){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        // Dismiss the dialog when the user clicks outside the dialog or on the back
        // button. If you want to disable that functionality, simply use an empty
        // onDismissRequest.
        onDismissRequest = onDismissRequest
    ){
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .background(Secondary, RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {

                //... AlertDialog content
                Text(
                    text = "Login",
                    color = Headline,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 20.dp)
                )

                OutlinedTextField(
                    value = email,
                    textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp), color = Headline),
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp),
                    label = {
                        Text(text = "Email", color = Background)
                    }
                )

                OutlinedTextField(
                    value = password,
                    textStyle = TextStyle(fontSize = TextUnit(20f, TextUnitType.Sp), color = Headline),
                    onValueChange = { password = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    label = {
                        Text(text = "Password", color = Background)
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp),
                ) {
                    OutlinedButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonOrHighlight
                        ),
                        onClick = {
                            mainViewModel.signIn(email, password, context)
                            keyboardController?.hide()
                            onDismissRequest()
                        }
                    ) {
                        Text("Sign in", color = Stroke)
                    }
                }
            }
        }

    }
}