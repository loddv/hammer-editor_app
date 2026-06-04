import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darkrockstudios.apps.hammer.common.projecthome.PROJECT_STATS_TAG
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_HOME_TAG
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Project-home happy path: the editor opens on the Home destination, which must render the project
 * stats dashboard. Re-navigating Home keeps it stable.
 */
@RunWith(AndroidJUnit4::class)
class ProjectHomeTest : EditorInstrumentedTest("E2E Home") {

	@Test
	fun homeShowsProjectStats() {
		composeRule.waitForTag(PROJECT_STATS_TAG)
		composeRule.onNodeWithTag(PROJECT_STATS_TAG).assertIsDisplayed()

		// Re-selecting Home keeps the stats dashboard stable.
		composeRule.navigateTo(NAV_HOME_TAG)
		composeRule.onNodeWithTag(PROJECT_STATS_TAG).assertIsDisplayed()
	}
}
