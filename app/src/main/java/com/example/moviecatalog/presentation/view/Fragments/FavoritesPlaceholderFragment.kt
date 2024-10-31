package com.example.moviecatalog.presentation.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.moviecatalog.R
import com.example.moviecatalog.presentation.ui.*

class FavoritesPlaceholderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Theme {
                    this@FavoritesPlaceholderFragment.Content()
                }
            }
        }
    }

    @Composable
    private fun Content() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background_placeholder),
                    contentDescription = "Background image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    painter = painterResource(id = R.drawable.shadow_top_png),
                    contentDescription = "Top Shadow",
                    contentScale = ContentScale.FillHeight
                )

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    painter = painterResource(id = R.drawable.shadow_bottom_png),
                    contentDescription = "Bottom Shadow",
                    contentScale = ContentScale.FillHeight
                )

                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, top = 24.dp)
                        .align(Alignment.TopStart),
                    text = stringResource(id = R.string.favorite),
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.padding(start = 24.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Здесь пока пусто",
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Добавьте любимые жанры и фильмы, чтобы вернуться к ним позже",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(modifier = Modifier.height(4.dp))
            }

            Button(
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 100.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(),
                onClick = { /* Если на кнопочку нажали */ }
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    GradientStart,
                                    GradientEnd
                                )
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Найти фильм для себя",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
