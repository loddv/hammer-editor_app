import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_EDITOR_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.scenelist.CREATE_ITEM_NAME_FIELD_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.scenelist.SCENE_LIST_ADD_BUTTON_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.scenelist.SCENE_LIST_ADD_GROUP_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.scenelist.SCENE_LIST_ADD_SCENE_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.sceneeditor.SCENE_EDITOR_TEXT_TAG
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Scene-list happy path: create a group (stays in the list) and a scene (auto-opens the editor),
 * exercising the add menu + create dialog for both.
 */
@RunWith(AndroidJUnit4::class)
class SceneListWorkflowTest : EditorInstrumentedTest("E2E SceneList") {

	@Test
	fun createGroupAndSceneFromAddMenu() {
		composeRule.navigateTo(NAV_EDITOR_TAG)

		// A group has no editor, so it lands in the tree and the list stays put.
		addItem(SCENE_LIST_ADD_GROUP_TAG, "Act One")
		composeRule.waitForTagPrefix("scene-group-")
		composeRule.onAllNodes(hasTestTagPrefix("scene-group-")).onFirst().assertIsDisplayed()

		// Creating a scene auto-selects it and opens the editor.
		addItem(SCENE_LIST_ADD_SCENE_TAG, "Chapter One")
		composeRule.waitForTag(SCENE_EDITOR_TEXT_TAG)
		composeRule.onNodeWithTag(SCENE_EDITOR_TEXT_TAG).assertIsDisplayed()
	}

	// Open the add menu, pick scene/group, type a name, submit via the IME action.
	private fun addItem(menuItemTag: String, name: String) {
		composeRule.waitForTag(SCENE_LIST_ADD_BUTTON_TAG)
		composeRule.onNodeWithTag(SCENE_LIST_ADD_BUTTON_TAG).performClick()
		composeRule.onNodeWithTag(menuItemTag).performClick()
		composeRule.waitForTag(CREATE_ITEM_NAME_FIELD_TAG)
		composeRule.onNodeWithTag(CREATE_ITEM_NAME_FIELD_TAG).performTextInput(name)
		composeRule.onNodeWithTag(CREATE_ITEM_NAME_FIELD_TAG).performImeAction()
		composeRule.waitForTagGone(CREATE_ITEM_NAME_FIELD_TAG)
	}
}
