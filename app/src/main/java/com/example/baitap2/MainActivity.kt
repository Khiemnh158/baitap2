package com.example.baitap2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baitap2.ui.theme.Baitap2Theme
import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Baitap2Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "screen1") {
        composable("screen1") {
            Screen1(navController)
        }
        composable("screen2/{userName}/{mssv}") { backStackEntry ->
            val rawUserName = backStackEntry.arguments?.getString("userName") ?: ""
            val rawMssv = backStackEntry.arguments?.getString("mssv") ?: ""
            val userName = URLDecoder.decode(rawUserName, StandardCharsets.UTF_8.name())
            val mssv = URLDecoder.decode(rawMssv, StandardCharsets.UTF_8.name())
            Screen2(navController, userName, mssv)
        }
    }
}

@Composable
fun Screen1(navController: NavController) {
    var userName by remember { mutableStateOf("") }
    var mssv by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // The Blocks layout
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp) // Adjust height to give blocks more space
                ) {
                    // Block 1
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color(0xFF2C84ED)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "1", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Block 2
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color(0xFFF44445)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "2", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Blocks 3, 4, 5
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.5f)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(Color(0xFFFBCE20)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "3", color = Color.Black, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(Color(0xFF31A463)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "4", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(Color(0xFF8646E3)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "5", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // Block 6
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.5f)
                            .background(Color(0xFFF9791E)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "6", color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                // Inputs
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("UserName") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = mssv,
                    onValueChange = { mssv = it },
                    label = { Text("MSSV") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Click me button at bottom-center
            Button(
                onClick = {
                    val uName = userName.trim()
                    val id = mssv.trim()
                    if (uName.isEmpty() || id.isEmpty()) {
                        Toast.makeText(context, "Vui lòng nhập đầy đủ UserName và MSSV", Toast.LENGTH_SHORT).show()
                    } else {
                        val encodedUName = URLEncoder.encode(uName, StandardCharsets.UTF_8.name())
                        val encodedId = URLEncoder.encode(id, StandardCharsets.UTF_8.name())
                        navController.navigate("screen2/$encodedUName/$encodedId")
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp, top = 8.dp)
            ) {
                Text(text = "Click me")
            }
        }
    }
}

@Composable
fun Screen2(navController: NavController, userName: String, mssv: String) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Thông tin từ Screen 1:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "UserName: $userName", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "MSSV: $mssv", fontSize = 20.sp)
        }
    }
}
