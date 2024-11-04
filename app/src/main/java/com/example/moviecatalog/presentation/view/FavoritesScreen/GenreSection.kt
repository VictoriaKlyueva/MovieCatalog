package com.example.moviecatalog.presentation.view.FavoritesScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.data.model.GenreModel
import com.example.moviecatalog.R

@Composable
fun GenreSection(favoritesGenres: List<GenreModel>) {
    Text(
        modifier = getGradientTextModifier(),
        text = stringResource(id = R.string.favorite_genres),
        style = MaterialTheme.typography.bodyLarge
    )

    Spacer(modifier = Modifier.height(16.dp))

    LazyColumn {
        items(favoritesGenres) { genre ->
            GenreItem(genre.name.replaceFirstChar(Char::uppercaseChar))
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun GenreItem(genre: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(id = R.color.dark_faded)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.padding(start = 16.dp).weight(1f),
            text = genre,
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark)),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                /* Логика удаления из избранного */
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_broken_heart),
                tint = Color.Unspecified,
                contentDescription = "Broken Heart",
                modifier = Modifier.size(24.dp)
            )
        }
    }

}
