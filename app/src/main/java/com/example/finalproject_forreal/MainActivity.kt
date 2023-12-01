package com.example.finalproject_forreal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter
import com.example.finalproject_forreal.data.repository.AppPreferences
import com.example.finalproject_forreal.ui.theme.FinalProject_forRealTheme
import com.example.finalproject_forreal.utils.EXTRA_IMAGE
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.harbourspace.unsplash.ui.theme.SearchShape

class MainActivity : ComponentActivity() {
    private val CatViewModel: CatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CatViewModel.fetchImages()

        setContent {
            FinalProject_forRealTheme {

                val CatImages = CatViewModel.items.observeAsState(emptyList())
                val appPreferences = AppPreferences(this@MainActivity)
                val favoriteIds = remember { mutableStateOf(appPreferences.getFavorites())}


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Greeting()
                        LazyColumn {
                            items(CatImages.value) { image ->

                                val catViewModel2: CatViewModel2 by viewModels()
                                catViewModel2.fetchImagesDetails(image.id.toString())

                                val catDetails = catViewModel2.items.observeAsState().value


                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .padding(vertical = 8.dp, horizontal = 10.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable {
                                            val intent = Intent(
                                                this@MainActivity,
                                                DetailsActivity::class.java
                                            )
                                            intent.putExtra(EXTRA_IMAGE, image.url)
                                            intent.putExtra("image_id", image.id)
                                            startActivity(intent)
                                        }
                                ) {
                                    val imageUrl = image.url
                                    val painter = rememberImagePainter(data = imageUrl)
                                    val isFav = favoriteIds.value.contains(image.id)

                                    Surface {

                                        Image(
                                            painter = painter,
                                            contentDescription = "",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .width(180.dp)
                                                .background(color = Color.Black)
                                        )

                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 180.dp)
                                                .background(color = Color(0xFFA3CAE9)),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(text = image.id, modifier = Modifier.padding(start = 20.dp), fontWeight = FontWeight.Bold)

                                            Spacer(modifier = Modifier.height(10.dp))

                                            Text(text = image.url, modifier = Modifier.padding(start = 20.dp))
                                        }

                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            Surface(
                                                shape = CircleShape,
                                                modifier = Modifier
                                                    .padding(6.dp)
                                                    .size(32.dp),
//                                                color = Color(0x77000000)
                                            ) {
                                                FavoriteButton(
                                                    isFav = isFav,
                                                    id = image.id,
                                                    favoriteIds = favoriteIds,
                                                    appPreferences = appPreferences
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Greeting() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(0.dp))
    ) {
        Surface {

            Image(
                painter = painterResource(id = R.drawable.sky),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 10.dp),
                    text = "Let's explore",
                    textAlign = TextAlign.Start,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    text = "Your new Buddy!!!",
                    textAlign = TextAlign.Start,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun FavoriteButton(
    isFav: Boolean,
    id: String,
    favoriteIds: MutableState<List<String>>,
    appPreferences: AppPreferences,
    modifier: Modifier = Modifier,
    color: Color = Color(0xffE91E63)
) {


    var isFavorite = isFav

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite;
            if (!isFavorite) {
                appPreferences.removeFavorite(id)
            } else {
                appPreferences.addFavorite(id)
            }
            favoriteIds.value = appPreferences.getFavorites()
            Log.d("Isfavorite", isFavorite.toString())
            Log.d("FavStrings", favoriteIds.value.toString())
        }
    ) {
        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}


