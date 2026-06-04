package com.darkrockstudios.apps.hammer.common.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

/** Apply a [testTag] only when non-null; otherwise leave the chain unchanged. */
fun Modifier.testTagOrNull(tag: String?): Modifier =
	if (tag != null) testTag(tag) else this
