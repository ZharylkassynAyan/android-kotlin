package com.example.gamevault.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gamevault.ui.theme.GameVaultTheme
import com.example.gamevault.ui.theme.Spacing

@Composable
fun SectionHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SectionHeaderPreview() {
    GameVaultTheme {
        SectionHeader(text = "All games", modifier = Modifier.padding(Spacing.md))
    }
}