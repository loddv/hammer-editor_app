import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.core.app.ActivityScenario
import com.darkrockstudios.apps.hammer.android.ProjectRootActivity
import com.darkrockstudios.apps.hammer.common.data.ProjectDef
import org.junit.After
import org.junit.Before
import org.junit.Rule

/**
 * Base for editor instrumented tests: seeds a uniquely-named project, launches straight into the
 * editor, and tears down in the safe order. Teardown ordering is load-bearing - racing the
 * project-scope buffer flush crashes the process - so it lives here, once, rather than per class.
 */
abstract class EditorInstrumentedTest(private val seedName: String) {

	@get:Rule
	val composeRule = createEmptyComposeRule()

	protected lateinit var projectDef: ProjectDef
	protected lateinit var scenario: ActivityScenario<ProjectRootActivity>

	@Before
	fun launchEditor() {
		projectDef = EditorTestHarness.seedProject(seedName)
		scenario = EditorTestHarness.launchEditor(projectDef)
	}

	@After
	fun closeEditor() {
		EditorTestHarness.teardown(scenario, projectDef)
	}
}
