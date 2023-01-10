package com.jonatbergn.core.cars.android.booking

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jonatbergn.cars.state.BookingState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@Composable
fun BookingGrossUi(
    state: BookingState,
    onClicked: () -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable(
                role = Role.Button,
                onClick = onClicked,
            )
            .padding(8.dp)
    ) {
        Column(
            Modifier
                .height(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = state.car?.name.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Column(
            Modifier
                .height(40.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = state.car?.shortDescription.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                val dateTime = state.start?.toLocalDateTime(TimeZone.currentSystemDefault())?.toString().orEmpty()
                val duration = state.duration?.toString().orEmpty()
                Text(
                    text = "$dateTime ($duration)",
                    fontStyle = FontStyle.Italic,
                    fontSize = 8.sp,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}
