import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darkrockstudios.apps.hammer.common.notes.NOTES_CREATE_BODY_TAG
import com.darkrockstudios.apps.hammer.common.notes.NOTES_CREATE_CONFIRM_TAG
import com.darkrockstudios.apps.hammer.common.notes.NOTES_CREATE_FAB_TAG
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_NOTES_TAG
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Notes happy path: navigate to notes, create a note (body required) via the FAB + create screen,
 * which returns to the browse list, then confirm the new note card appears and open it.
 */
@RunWith(AndroidJUnit4::class)
class NotesWorkflowTest : EditorInstrumentedTest("E2E Notes") {

	@Test
	fun createNoteThenOpenIt() {
		composeRule.navigateTo(NAV_NOTES_TAG)

		composeRule.waitForTag(NOTES_CREATE_FAB_TAG)
		composeRule.onNodeWithTag(NOTES_CREATE_FAB_TAG).performClick()

		composeRule.waitForTag(NOTES_CREATE_BODY_TAG)
		composeRule.typeIntoEditor(NOTES_CREATE_BODY_TAG, "E2E smoke note body")
		composeRule.onNodeWithTag(NOTES_CREATE_CONFIRM_TAG).performClick()

		// Creating returns to the browse grid; the new note card appears (id unknown, match by prefix).
		composeRule.waitForTagPrefix("note-card-")
		composeRule.onAllNodes(hasTestTagPrefix("note-card-")).onFirst().assertIsDisplayed()

		// Opening it leaves the browse list - the create FAB only shows there.
		composeRule.onAllNodes(hasTestTagPrefix("note-card-")).onFirst().performClick()
		composeRule.waitForTagGone(NOTES_CREATE_FAB_TAG)
	}
}
