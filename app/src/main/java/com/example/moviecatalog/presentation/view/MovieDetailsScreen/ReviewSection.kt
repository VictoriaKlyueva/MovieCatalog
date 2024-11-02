package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.moviecatalog.R

@Composable
fun ReviewSection() {
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
                painter = painterResource(id = R.drawable.ic_review),
                contentDescription = "Icon heart",
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = stringResource(id = R.string.reviews),
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Review()
    }
}

@Composable
fun Review() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .background(
                color = colorResource(id = R.color.dark),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        User()

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    bottom = 8.dp,
                    end = 8.dp
                ),
            text = "Смотрите этот фильм он имба",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun User() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp
            ),
    ) {
        Image(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Transparent),
            painter = painterResource(id = R.drawable.avatar_default),
            contentDescription = "User avatar",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column (
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Елена Летучая",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )

            Text(
                text = "17 октября 2024",
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
        }

        Row(
            modifier = Modifier
                .height(32.dp)
                .wrapContentSize()
                .background(
                    color = colorResource(id = R.color.nine_stars),
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .height(32.dp)
                    .padding(start = 8.dp, top = 6.dp, bottom = 6.dp)
                    .fillMaxHeight(),
                painter = painterResource(id = R.drawable.ic_star),
                tint = Color.White,
                contentDescription = "Icon star",
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                modifier = Modifier
                    .padding(
                        start = 4.dp,
                        top = 6.dp,
                        bottom = 6.dp,
                        end = 8.dp
                    ),
                text = "9.9",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
