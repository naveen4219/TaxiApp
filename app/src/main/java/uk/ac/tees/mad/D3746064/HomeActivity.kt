package uk.ac.tees.mad.D3746064

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.zIndex

class HomeActivity : ComponentActivity() {
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()
        setContent {
            HomeScreen()
        }
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var fromLocation by remember { mutableStateOf("") }
    var toLocation by remember { mutableStateOf("") }
    var selectedCar by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Map
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LatLng(40.7128, -74.0060), 12f)
            }
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            TopAppBar(
                title = { Text("BetterCommute", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* TODO: Open profile */ }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                }
            )

            // Location inputs
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.8f))
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = fromLocation,
                    onValueChange = { fromLocation = it },
                    label = { Text("From") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = toLocation,
                    onValueChange = { toLocation = it },
                    label = { Text("To") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    trailingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Car selection
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth().background(Color.Yellow)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                val cars = listOf("Toyota Premio G", "Toyota Prius", "Tesla Model 3")
                items(cars) { car ->
                    CarCard(
                        car = car,
                        isSelected = car == selectedCar,
                        onSelect = { selectedCar = car }
                    )
                }
            }

            // Book Now button
            Button(
                onClick = { /* TODO: Implement booking logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("BOOK NOW")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarCard(car: String, isSelected: Boolean, onSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        onClick = onSelect
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(car, fontSize = 14.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Text("$105", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}