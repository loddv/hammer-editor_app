import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_ENCYCLOPEDIA_TAG
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_EDITOR_TAG
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_HOME_TAG
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_NOTES_TAG
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_TIMELINE_TAG
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Smoke-tests every top-level editor destination by seeding a project, launching straight into
 * the editor, and clicking through each nav item. Composing a screen that throws fails the test.
 */
@RunWith(AndroidJUnit4::class)
class EditorNavigationTest : EditorInstrumentedTest("E2E Nav") {

	@Test
	fun visitsEveryTopLevelDestination() {
		// Editor opens on Home; the bottom bar must show all five destinations.
		composeRule.waitForTag(NAV_HOME_TAG)

		listOf(NAV_EDITOR_TAG, NAV_NOTES_TAG, NAV_ENCYCLOPEDIA_TAG, NAV_TIMELINE_TAG, NAV_HOME_TAG)
			.forEach { tag ->
				composeRule.navigateTo(tag)
				composeRule.onNodeWithTag(tag).assertIsDisplayed()
			}
	}
}
