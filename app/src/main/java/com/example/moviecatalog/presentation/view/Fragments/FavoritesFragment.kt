package com.example.moviecatalog.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.GenreModel
import com.example.moviecatalog.presentation.viewModel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private var favoritesGenres: List<GenreModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SetContent()
            }
        }
    }

    private fun observeGenres() {
        favoritesViewModel.favoritesGenres.observe(viewLifecycleOwner) { result ->
            when {
                result.isNullOrEmpty() -> {
                    println("No genres found")
                    // Показать заглушку
                }
                else -> {
                    favoritesGenres = result
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGenres()
        favoritesViewModel.fetchFavoriteGenres()
    }

    @Composable
    private fun SetContent() {
        val favoritesGenresState = remember { mutableStateOf(emptyList<GenreModel>()) }

        favoritesViewModel.favoritesGenres.observe(viewLifecycleOwner) { result ->
            favoritesGenresState.value = result ?: emptyList()
        }

        return MaterialTheme {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.favorite),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    modifier = Modifier.graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            val brush = Brush.horizontalGradient(
                                listOf(
                                    Color(resources.getColor(R.color.gradient_start)),
                                    Color(resources.getColor(R.color.gradient_end))
                                )
                            )
                            onDrawWithContent {
                                drawContent()
                                drawRect(brush, blendMode = BlendMode.SrcAtop)
                            }
                        },
                    text = "Любимые жанры",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(favoritesGenresState.value) { genre ->
                        GenreItem(genre.name.replaceFirstChar(Char::uppercaseChar))
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    @Composable
    fun GenreItem(genre: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(resources.getColor(R.color.dark_faded))),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                text = genre,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(resources.getColor(R.color.dark))
                ),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    /* Логика удаления из избранного */
                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_broken_heart),
                    contentDescription = "Broken Heart",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
