import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darkrockstudios.apps.hammer.common.projectroot.NAV_EDITOR_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.scenelist.CREATE_ITEM_NAME_FIELD_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.scenelist.SCENE_LIST_ADD_BUTTON_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.scenelist.SCENE_LIST_ADD_SCENE_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.sceneeditor.SCENE_EDITOR_SAVE_TAG
import com.darkrockstudios.apps.hammer.common.storyeditor.sceneeditor.SCENE_EDITOR_TEXT_TAG
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Scene-editor happy path: create+select a scene, type into the editor, and save. The save action
 * only appears when the buffer is dirty, so its appearance/disappearance confirms the edit + save.
 */
@RunWith(AndroidJUnit4::class)
class SceneEditorWorkflowTest : EditorInstrumentedTest("E2E SceneEditor") {

	@Test
	fun editSceneTextThenSave() {
		composeRule.navigateTo(NAV_EDITOR_TAG)

		// Create a scene; this auto-opens it in the editor.
		composeRule.waitForTag(SCENE_LIST_ADD_BUTTON_TAG)
		composeRule.onNodeWithTag(SCENE_LIST_ADD_BUTTON_TAG).performClick()
		composeRule.onNodeWithTag(SCENE_LIST_ADD_SCENE_TAG).performClick()
		composeRule.waitForTag(CREATE_ITEM_NAME_FIELD_TAG)
		composeRule.onNodeWithTag(CREATE_ITEM_NAME_FIELD_TAG).performTextInput("Opening")
		composeRule.onNodeWithTag(CREATE_ITEM_NAME_FIELD_TAG).performImeAction()

		// Type into the editor; the save action appears once the buffer is dirty.
		composeRule.waitForTag(SCENE_EDITOR_TEXT_TAG)
		composeRule.typeIntoEditor(SCENE_EDITOR_TEXT_TAG, "Once upon a time")
		composeRule.waitForTag(SCENE_EDITOR_SAVE_TAG)

		// Saving clears the dirty buffer, so the save action disappears.
		composeRule.onNodeWithTag(SCENE_EDITOR_SAVE_TAG).performClick()
		composeRule.waitForTagGone(SCENE_EDITOR_SAVE_TAG)
	}
}
