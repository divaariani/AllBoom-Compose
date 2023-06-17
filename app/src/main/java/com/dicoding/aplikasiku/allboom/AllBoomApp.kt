package com.dicoding.aplikasiku.allboom.ui

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.aplikasiku.allboom.*
import com.dicoding.aplikasiku.allboom.R
import com.dicoding.aplikasiku.allboom.data.AlbumRepository
import com.dicoding.aplikasiku.allboom.ui.theme.AllBoomTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.dicoding.aplikasiku.allboom.model.Profile

@Composable
fun AllBoomApp(
    title: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AllBoomViewModel = viewModel(factory = ViewModelFactory(AlbumRepository()))
) {
    val groupedAlbums by viewModel.groupedAlbums.collectAsState()
    val query by viewModel.query
    val showSettingsScreen = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                TopAppBar(
                    title = { Text(text = title) },
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(top = 0.dp),
                    actions = {
                        IconButton(
                            onClick = {
                                showSettingsScreen.value = !showSettingsScreen.value
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "about_page",
                                tint = Color.White
                            )
                        }
                    }
                )
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colors.primary)
                )
            }
            groupedAlbums.forEach { (initial, albums) ->
                items(albums, key = { it.id }) { album ->
                    AlbumListItem(
                        artist = album.artist,
                        name = album.name,
                        photoUrl = album.photoUrl,
                        description = album.description,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        if (showSettingsScreen.value) {
            SettingsScreen(onBackPressed = { showSettingsScreen.value = false })
        } else {
            // Regular content
            // ...
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }

}

@Composable
fun AlbumListItem(
    name: String,
    artist: String,
    photoUrl: String,
    description: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("artist", artist)
            intent.putExtra("photoUrl", photoUrl)
            intent.putExtra("description", description)
            context.startActivity(intent)
        }
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Column {
            Text(
                text = artist,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
            Text(
                text = name,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumListItemPreview() {
    AllBoomTheme {
        AlbumListItem(
            artist = "WINNER",
            name = "Fate Number For",
            photoUrl = "",
            description = ""
        )
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(elevation = 10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = null,
        )
    }
}

@Composable
fun SettingsScreen(onBackPressed: () -> Unit) {
    BackHandler {
        onBackPressed()
    }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ){
        val userProfile = Profile(
            photoUrl = "https://example.com/profile_photo.jpg",
            email = "divaarifinb@gmail.com",
            name = "Diva Ariani"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.divaariani),
                    contentDescription = "about_page",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = userProfile.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = userProfile.email,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text(stringResource(R.string.search_artist))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
                    .heightIn(min = 48.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}