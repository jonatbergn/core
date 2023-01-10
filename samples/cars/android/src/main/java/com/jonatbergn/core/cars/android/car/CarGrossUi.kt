package com.jonatbergn.core.cars.android.car

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jonatbergn.cars.state.CarGrossState
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

@Composable
fun CarGrossUi(
    state: CarGrossState,
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
                text = state.name.toString(),
                style = typography.bodyLarge,
            )
        }
        Column(
            Modifier
                .height(40.dp),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = state.shortDescription.orEmpty(),
                style = typography.bodyMedium,
                maxLines = 1,
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = state.car.url,
                    fontStyle = FontStyle.Italic,
                    fontSize = 8.sp,
                    style = typography.labelSmall,
                )
            }
        }
    }
}

@Preview
@Composable
fun CarGrossUiPreview() {
    CarGrossUi(
        CarGrossState(
            car = Pointer("http://job-applicants-dummy-api.kupferwerk.net.s3.amazonaws.com/api/cars/6.json"),
            id = 6,
            name = "Skateboard",
            shortDescription = "With nice stickers on the skateboard to make it fancy",
        )
    )
}
