import android.os.SystemClock
import android.view.KeyEvent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darkrockstudios.apps.hammer.common.globalsearch.GLOBAL_SEARCH_INPUT_TAG
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_HOME_TAG
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Global-search happy path: on Android the search modal is opened with the Ctrl+Shift+F chord
 * (no on-screen trigger), so inject that key event, then confirm the field renders and accepts a query.
 */
@RunWith(AndroidJUnit4::class)
class GlobalSearchTest : EditorInstrumentedTest("E2E Search") {

	@Test
	fun openGlobalSearchAndType() {
		// Wait for the editor chrome before sending the shortcut.
		composeRule.waitForTag(NAV_HOME_TAG)

		sendGlobalSearchShortcut()

		composeRule.waitForTag(GLOBAL_SEARCH_INPUT_TAG)
		composeRule.onNodeWithTag(GLOBAL_SEARCH_INPUT_TAG).performTextInput("smoke")
		composeRule.onNodeWithTag(GLOBAL_SEARCH_INPUT_TAG).assertIsDisplayed()
	}

	// ProjectRootActivity.dispatchKeyEvent opens search on Ctrl+Shift+F (key down). Dispatch it
	// directly on the activity so it doesn't depend on the emulator's hardware-keyboard config.
	private fun sendGlobalSearchShortcut() {
		val now = SystemClock.uptimeMillis()
		val event = KeyEvent(
			now, now, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_F, 0,
			KeyEvent.META_CTRL_ON or KeyEvent.META_SHIFT_ON,
		)
		scenario.onActivity { it.dispatchKeyEvent(event) }
	}
}
