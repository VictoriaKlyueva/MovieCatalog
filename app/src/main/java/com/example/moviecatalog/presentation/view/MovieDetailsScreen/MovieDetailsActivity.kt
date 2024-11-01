package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.presentation.ui.Theme
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel

class MovieDetailsActivity : ComponentActivity() {

    private val movieDetailsViewModel = MovieDetailsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieDetailsViewModel.randomMovie.observe(this) { randomMovie ->
            if (randomMovie != null) {
                setContent {
                    Theme {
                        MovieDetailsScreen(randomMovie)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailsScreen(movie: MovieElementModel) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.dark))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(movie.poster),
                contentDescription = "Background image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                painter = painterResource(id = R.drawable.shadow_bottom_png),
                contentDescription = "Bottom Shadow",
                contentScale = ContentScale.FillHeight
            )

            // Иконка назад
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .size(40.dp)
                    .background(
                        color = colorResource(id = R.color.dark_faded),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        /* Обработчик нажатия */
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Icon back",
                    tint = Color.White,
                )
            }

            // Плашка с сердечком
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .size(40.dp)
                    .background(
                        color = colorResource(id = R.color.dark_faded),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "Icon heart",
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 36.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(474.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    colorResource(id = R.color.gradient_start),
                                    colorResource(id = R.color.gradient_end)
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = movie.name ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 36.sp
                    )

                    Text(
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                        text = "Аче тут писать то",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 20.sp
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.dark_faded),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .width(80.dp)
                            .height(32.dp),
                        painter = painterResource(id = R.drawable.friends),
                        contentDescription = "Icon heart"
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Нравится n вашим друзьям",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.dark_faded),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.fake_description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.dark_faded),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            tint = colorResource(id = R.color.gray),
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "Icon heart",
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = stringResource(id = R.string.rating),
                            color = colorResource(id = R.color.gray),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MovieServiceRating(
                            painterResource(id = R.drawable.ic_logo_simple),
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = colorResource(id = R.color.dark),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        MovieServiceRating(
                            painterResource(id = R.drawable.ic_kinopoisk),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .background(
                                    color = colorResource(id = R.color.dark),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        MovieServiceRating(
                            painterResource(id = R.drawable.ic_imdb),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .background(
                                    color = colorResource(id = R.color.dark),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.dark_faded),
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            tint = colorResource(id = R.color.gray),
                            painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = "Icon heart",
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = stringResource(id = R.string.information),
                            color = colorResource(id = R.color.gray),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MovieInfoCell(
                            "Страны",
                            "Германия, США",
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = colorResource(id = R.color.dark),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        MovieInfoCell(
                            "Возраст",
                            "16+",
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .background(
                                    color = colorResource(id = R.color.dark),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MovieInfoCell(
                            "Время",
                            "1 ч 30 мин",
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = colorResource(id = R.color.dark),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        MovieInfoCell(
                            "Год выхода",
                            "2022",
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    color = colorResource(id = R.color.dark),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieInfoCell(name: String, value: String, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp
                ),
            text = name,
            color = colorResource(id = R.color.gray),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight(400),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    bottom = 8.dp,
                    end = 8.dp
                ),
            text = value,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun MovieServiceRating(icon: Painter, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .height(40.dp)
                .padding(start = 6.dp, top = 8.dp, bottom = 8.dp)
                .fillMaxHeight(),
            painter = icon,
            tint = Color.Unspecified,
            contentDescription = "Icon heart",
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier
                .padding(
                    start = 6.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 8.dp
                ),
            text = "9.9",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
