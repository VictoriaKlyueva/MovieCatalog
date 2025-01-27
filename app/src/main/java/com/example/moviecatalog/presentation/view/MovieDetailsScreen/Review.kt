package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.main.ReviewModel
import com.example.moviecatalog.data.model.main.UserShortModel
import com.example.moviecatalog.domain.utils.DateHelper
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Review(viewModel: MovieDetailsViewModel, review: ReviewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = colorResource(id = R.color.dark),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        User(viewModel, review, review.author)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    bottom = 8.dp,
                    end = 8.dp
                ),
            text = review.reviewText ?: "No review text available",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun User(viewModel: MovieDetailsViewModel, review: ReviewModel, author: UserShortModel?) {

    val dateHelper = remember { DateHelper() }

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
                .background(Color.Transparent)
                .clickable {
                    author?.let { viewModel.addFriend(it) }
                },
            painter = rememberImagePainter(
                data = if (review.isAnonymous)
                    R.drawable.avatar_default
                else
                    author?.avatar ?: R.drawable.avatar_default,
                builder = {
                    error(R.drawable.avatar_default)
                }
            ),
            contentDescription = "User avatar",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column (
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = if (review.isAnonymous || author?.nickName == null)
                    stringResource(id = R.string.nick_name_default)
                else
                    author.nickName,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )

            Text(
                text = dateHelper.convertFromDateTimezonesSeconds(review.createDateTime),
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
        }

        val backgroundColor = when (review.rating) {
            1 -> colorResource(id = R.color.one_star)
            2 -> colorResource(id = R.color.two_stars)
            3 -> colorResource(id = R.color.three_stars)
            4 -> colorResource(id = R.color.four_stars)
            5 -> colorResource(id = R.color.five_stars)
            6 -> colorResource(id = R.color.six_stars)
            7 -> colorResource(id = R.color.seven_stars)
            8 -> colorResource(id = R.color.eight_stars)
            9 -> colorResource(id = R.color.nine_stars)
            10 -> colorResource(id = R.color.nine_stars)
            else -> colorResource(id = R.color.dark_faded)
        }

        Row(
            modifier = Modifier
                .height(32.dp)
                .wrapContentSize()
                .background(
                    color = backgroundColor,
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
                text = review.rating.toString(),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}