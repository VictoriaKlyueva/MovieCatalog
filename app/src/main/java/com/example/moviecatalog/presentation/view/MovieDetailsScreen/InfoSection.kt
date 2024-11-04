package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieDetailsModel
import com.example.moviecatalog.domain.utils.DateHelper

@Composable
fun InfoSection(movie: MovieDetailsModel) {
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
            InfoCell(
                stringResource(id = R.string.countries),
                movie.country ?: stringResource(id = R.string.unknown),
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = colorResource(id = R.color.dark),
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Spacer(modifier = Modifier.width(8.dp))

            InfoCell(
                stringResource(id = R.string.age),
                movie.ageLimit.toString() + "+",
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
            InfoCell(
                stringResource(id = R.string.time),
                minutesToHours(movie.time.toString().toInt()),
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = colorResource(id = R.color.dark),
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Spacer(modifier = Modifier.width(8.dp))

            InfoCell(
                stringResource(id = R.string.year_of_release),
                movie.year.toString(),
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

@Composable
fun InfoCell(name: String, value: String, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = 10.dp,
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
                    start = 10.dp,
                    bottom = 8.dp,
                    end = 8.dp
                ),
            text = value,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun minutesToHours(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60

    return "$hours ч $remainingMinutes мин"
}