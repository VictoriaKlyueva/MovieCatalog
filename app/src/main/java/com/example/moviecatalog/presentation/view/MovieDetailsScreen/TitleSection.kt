package com.example.moviecatalog.presentation.view.MovieDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieElementModel

@Composable
fun TitleSection(movie: MovieElementModel) {
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