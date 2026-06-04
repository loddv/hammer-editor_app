import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darkrockstudios.apps.hammer.common.data.encyclopediarepository.entry.EntryType
import com.darkrockstudios.apps.hammer.common.encyclopedia.ENCYCLOPEDIA_CREATE_CONFIRM_TAG
import com.darkrockstudios.apps.hammer.common.encyclopedia.ENCYCLOPEDIA_CREATE_FAB_TAG
import com.darkrockstudios.apps.hammer.common.encyclopedia.ENCYCLOPEDIA_CREATE_NAME_TAG
import com.darkrockstudios.apps.hammer.common.encyclopedia.ENCYCLOPEDIA_CREATE_TAGS_TAG
import com.darkrockstudios.apps.hammer.common.encyclopedia.encyclopediaTypeCellTag
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_ENCYCLOPEDIA_TAG
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Encyclopedia happy path: navigate to encyclopedia, create an entry (name + type + tag; description
 * optional), see it land in the browse grid, and open it.
 */
@RunWith(AndroidJUnit4::class)
class EncyclopediaWorkflowTest : EditorInstrumentedTest("E2E Encyclopedia") {

	@Test
	fun createEntryThenOpenIt() {
		composeRule.navigateTo(NAV_ENCYCLOPEDIA_TAG)

		composeRule.waitForTag(ENCYCLOPEDIA_CREATE_FAB_TAG)
		composeRule.onNodeWithTag(ENCYCLOPEDIA_CREATE_FAB_TAG).performClick()

		// Name is required; type defaults to PERSON, pick PLACE. Trailing comma commits the tag.
		composeRule.waitForTag(ENCYCLOPEDIA_CREATE_NAME_TAG)
		composeRule.onNodeWithTag(ENCYCLOPEDIA_CREATE_NAME_TAG).performTextInput("Smoke Entry")
		composeRule.onNodeWithTag(encyclopediaTypeCellTag(EntryType.PLACE)).performClick()
		composeRule.onNodeWithTag(ENCYCLOPEDIA_CREATE_TAGS_TAG).performTextInput("hero,")
		composeRule.onNodeWithTag(ENCYCLOPEDIA_CREATE_CONFIRM_TAG).performClick()

		// Back on the browse grid, the new entry card appears (id unknown, match by tag prefix).
		composeRule.waitForTagPrefix("encyclopedia-entry-")
		composeRule.onAllNodes(hasTestTagPrefix("encyclopedia-entry-")).onFirst().assertIsDisplayed()

		// Opening it leaves the browse list - the create FAB only shows there.
		composeRule.onAllNodes(hasTestTagPrefix("encyclopedia-entry-")).onFirst().performClick()
		composeRule.waitForTagGone(ENCYCLOPEDIA_CREATE_FAB_TAG)
	}
}
