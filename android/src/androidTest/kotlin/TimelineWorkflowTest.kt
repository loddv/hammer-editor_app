import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_TIMELINE_TAG
import com.darkrockstudios.apps.hammer.common.timeline.EVENT_CARD_TAG
import com.darkrockstudios.apps.hammer.common.timeline.TIME_LINE_CREATE_CONFIRM_TAG
import com.darkrockstudios.apps.hammer.common.timeline.TIME_LINE_CREATE_DATE_TAG
import com.darkrockstudios.apps.hammer.common.timeline.TIME_LINE_CREATE_TAG
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Timeline happy path: navigate to the timeline, create an event via the FAB + create screen,
 * see it land in the overview list, and open it. Reference template for the other feature tests.
 */
@RunWith(AndroidJUnit4::class)
class TimelineWorkflowTest : EditorInstrumentedTest("E2E Timeline") {

	@Test
	fun createEventThenOpenIt() {
		composeRule.navigateTo(NAV_TIMELINE_TAG)

		// Open the create screen via the FAB.
		composeRule.waitForTag(TIME_LINE_CREATE_TAG)
		composeRule.onNodeWithTag(TIME_LINE_CREATE_TAG).performClick()

		// Content is optional, so a date alone persists an event.
		composeRule.waitForTag(TIME_LINE_CREATE_DATE_TAG)
		composeRule.onNodeWithTag(TIME_LINE_CREATE_DATE_TAG).performTextInput("Year One")
		composeRule.onNodeWithTag(TIME_LINE_CREATE_CONFIRM_TAG).performClick()

		// Back on the overview, the new event card appears.
		composeRule.waitForTag(EVENT_CARD_TAG)
		composeRule.onAllNodesWithTag(EVENT_CARD_TAG)[0].assertIsDisplayed()

		// Opening the event leaves the overview - the create FAB only shows there.
		composeRule.onAllNodesWithTag(EVENT_CARD_TAG)[0].performClick()
		composeRule.waitForTagGone(TIME_LINE_CREATE_TAG)
	}
}
