package com.example.finalproject_forreal

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.finalproject_forreal.data.model.CatItem
import com.example.finalproject_forreal.data.repository.AppPreferences
import com.example.finalproject_forreal.ui.theme.FinalProject_forRealTheme
import com.example.finalproject_forreal.utils.EXTRA_IMAGE

class DetailsActivity : ComponentActivity() {

    private val catViewModel2: CatViewModel2 by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = if (intent.hasExtra(EXTRA_IMAGE)) {
            intent.extras?.get(EXTRA_IMAGE)
        } else {
            ""
        }

        val id = if (intent.hasExtra("image_id")) {
            intent.extras?.get("image_id")
        } else {
            ""
        }

        catViewModel2.fetchImagesDetails(id.toString())

        setContent {
            FinalProject_forRealTheme {
                val catDetails = catViewModel2.items.observeAsState()
                val image = catDetails.value
                val appPreferences = AppPreferences(this@DetailsActivity)
                val favoriteIds = remember { mutableStateOf(appPreferences.getFavorites()) }
                val isFav = favoriteIds.value.contains(id)

                LazyColumn {
                    item {

                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(url)
                                    .build()
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = ""
                        )

                        Row (
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.Start
                        ){
                            Icon(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable {
                                        favoriteIds.value = appPreferences.getFavorites()
                                        super.onBackPressed()
//                                        val intent = Intent(
//                                            this@DetailsActivity,
//                                            MainActivity::class.java
//                                        )
//                                        startActivity(intent)
                                    },
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,)
                            FavoriteButton2(isFav = isFav, id = id.toString() , favoriteIds = favoriteIds, appPreferences = appPreferences)
                            Icon(imageVector = Icons.Default.Share, contentDescription = "Share", modifier = Modifier.padding(10.dp).clickable {
                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "This cat is so cute check out ${url.toString()}")
                                    type = "text/plain"
                                }

                                val shareIntent: Intent = Intent.createChooser(sendIntent, null)
                                startActivity(shareIntent)
                            })
                        }

                    }


                    item {
                        Column {

                            Spacer(modifier = Modifier.height(10.dp))

                            InformationCard(topic = "name", detail = id.toString())

                            Spacer(modifier = Modifier.height(10.dp))

                            InformationCard(topic = "origin", detail = url.toString())

                            Spacer(modifier = Modifier.height(10.dp))

                            InformationCard(topic = "Contact", detail = "(+66)89-546-1234")

                            Spacer(modifier = Modifier.height(10.dp))

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InformationCard(
        topic: String,
        detail: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFA3CAE9)),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = topic,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )

            Text(
                text = ":",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = detail,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun FavoriteButton2(
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
