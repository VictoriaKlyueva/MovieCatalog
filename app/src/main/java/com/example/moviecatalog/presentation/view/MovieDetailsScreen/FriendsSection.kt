package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.main.ReviewModel
import com.example.moviecatalog.presentation.viewModel.FriendsViewModel
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel

@Composable
fun FriendsSection(viewModel: MovieDetailsViewModel, reviews: List<ReviewModel>) {
    val friendsCount = remember(reviews, viewModel.friends.value) {
        viewModel.countFriendsWhoLikedMovie(reviews)
    }

    val friends = viewModel.friends.value ?: emptyList()

    if (friendsCount > 0) {
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
            Row {
                for (i in 0 until minOf(friendsCount, 3)) {
                    Image(
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp)
                            .offset(x = (-8 * i).dp, y = 0.dp)
                            .clip(CircleShape),
                        painter = rememberImagePainter(friends[i].avatar),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = if (friendsCount != 1) {
                    stringResource(id = R.string.friends_like_plural, friendsCount)
                } else {
                    stringResource(id = R.string.friends_like_singular, friendsCount)
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

