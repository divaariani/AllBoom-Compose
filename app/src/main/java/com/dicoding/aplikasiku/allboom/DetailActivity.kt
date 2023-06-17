package com.dicoding.aplikasiku.allboom

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.aplikasiku.allboom.ui.theme.AllBoomTheme

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = getColor(R.color.cream)

        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("name")
        val artist = intent.getStringExtra("artist")
        val photoUrl = intent.getStringExtra("photoUrl")
        val description = intent.getStringExtra("description")

        setContent {
            AllBoomTheme{
                DetailScreen(name = name, artist = artist, photoUrl = photoUrl, description = description)
            }
        }
    }
}

@Composable
fun DetailScreen(name: String?, artist: String?, photoUrl: String?, description: String?) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${artist}",
                    style = TextStyle(fontSize = 25.sp),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Album: ${name}",
                    style = TextStyle(fontSize = 18.sp),
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${description}",
                    style = TextStyle(fontSize = 15.sp),
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
            }
        }
    }
}