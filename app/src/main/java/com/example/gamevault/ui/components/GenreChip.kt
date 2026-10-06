package com.example.gamevault.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.gamevault.ui.theme.GameVaultTheme
import com.example.gamevault.ui.theme.Spacing

@Composable
fun GenreChip(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val backgroundColor = if (selected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.primaryContainer
    val textColor = if (selected) MaterialTheme.colorScheme.onPrimary
    else MaterialTheme.colorScheme.onPrimaryContainer

    val chipModifier = modifier
        .clip(CircleShape)
        .background(backgroundColor)
        .then(
            if (onClick != null) {
                Modifier.clickable { onClick() }
            } else {
                Modifier
            }
        )

    Box(
        modifier = chipModifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.xs)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GenreChipPreview() {
    GameVaultTheme {
        Row(modifier = Modifier.padding(Spacing.md)) {
            GenreChip(text = "Action RPG", selected = true, onClick = {})
            GenreChip(text = "Puzzle", onClick = {})
        }
    }
}
