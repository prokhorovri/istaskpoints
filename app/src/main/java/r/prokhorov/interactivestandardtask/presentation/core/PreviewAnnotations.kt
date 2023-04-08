package r.prokhorov.interactivestandardtask.presentation.core

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "PortraitLight", group = "Light Mode", widthDp = 390, heightDp = 850)
@Preview(
    name = "PortraitDark",
    group = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 390,
    heightDp = 850
)
annotation class PortraitPreview

@Preview(name = "LandscapeLight", group = "Light Mode", heightDp = 390, widthDp = 850)
@Preview(
    name = "LandscapeDark",
    group = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    heightDp = 390,
    widthDp = 850
)
annotation class LandscapePreview