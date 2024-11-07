package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.main.ReviewModel
import com.example.moviecatalog.presentation.viewModel.FriendsViewModel
import com.example.moviecatalog.presentation.viewModel.MovieDetailsViewModel

@Composable
fun FriendsSection(viewModel: MovieDetailsViewModel, reviews: List<ReviewModel>) {
    val friendsCount = remember(reviews, viewModel.friends.value) {
        viewModel.countFriendsWhoLikedMovie(reviews)
    }

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
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(32.dp),
                painter = painterResource(id = R.drawable.friends),
                contentDescription = "Icon heart"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text =
                if (friendsCount != 1)
                    "Нравится $friendsCount вашим друзьям"
                else
                    "Нравится $friendsCount вашему другу",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

