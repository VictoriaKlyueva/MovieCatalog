package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieDetailsModel
import com.example.moviecatalog.data.model.MovieElementModel


@SuppressLint("DefaultLocale")
@Composable
fun RatingSection(movie: MovieDetailsModel) {
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
                String.format("%.1f", movie.reviews.map { it.rating }.average()),
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
                "10",
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
                "10",
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

@Composable
fun MovieServiceRating(icon: Painter, rating: String, modifier: Modifier) {
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

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier
                .padding(
                    start = 6.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                    end = 8.dp
                ),
            text = rating,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}