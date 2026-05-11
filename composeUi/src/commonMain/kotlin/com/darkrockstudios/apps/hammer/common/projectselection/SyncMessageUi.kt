package com.darkrockstudios.apps.hammer.common.projectselection

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.darkrockstudios.apps.hammer.common.compose.theme.LocalHammerColors
import com.darkrockstudios.apps.hammer.common.data.sync.projectsync.SyncLogLevel

@Composable
@ReadOnlyComposable
fun SyncLogLevel.accentColor(): Color =
	when (this) {
		SyncLogLevel.DEBUG -> MaterialTheme.colorScheme.onSurfaceVariant
		SyncLogLevel.INFO -> MaterialTheme.colorScheme.tertiary
		SyncLogLevel.WARN -> LocalHammerColors.current.warning
		SyncLogLevel.ERROR -> MaterialTheme.colorScheme.error
	}
