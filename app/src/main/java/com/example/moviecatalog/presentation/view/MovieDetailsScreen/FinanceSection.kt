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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieDetailsModel

@Composable
fun FinanceSection(movie: MovieDetailsModel) {
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
                painter = painterResource(id = R.drawable.ic_finance),
                contentDescription = "Icon heart",
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = stringResource(id = R.string.finance),
                color = colorResource(id = R.color.gray),
                style = MaterialTheme.typography.bodyMedium
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
                stringResource(id = R.string.budget),
                formatCurrency(movie.budget),
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = colorResource(id = R.color.dark),
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Spacer(modifier = Modifier.width(8.dp))

            InfoCell(
                stringResource(id = R.string.fees),
                formatCurrency(movie.fees),
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
fun formatCurrency(input: Int?): String {
    return if (input != null) {
        "$ " + String.format("%,d", input).replace(",", " ")
    } else {
        stringResource(id = R.string.unknown)
    }
}