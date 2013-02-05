package net.skhome.gradle.plugin.database
import net.skhome.gradle.plugin.database.task.CompositeTask
import net.skhome.gradle.plugin.database.task.DatabaseTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

public class DatabasePluginTasksTest {

	private Project project = ProjectBuilder.builder().build()

	@Before
	public void preparePlugin() {
		project.apply plugin: 'database'

		project.database {
			steps {
				create {
					script file: 'src/main/db/create.sql'
				}
				init {
					script file: 'src/main/db/data.sql'
				}
			}
			environments {
				local {
					driver = "driver"
					url = "url"
					username = "username"
					password = "secret"
				}
			}
		}
	}

	@Test
	public void shouldAddCreateDatabaseTask() {
		assert project.tasks.createLocalDatabase instanceof DatabaseTask
	}

	@Test
	public void shouldAddInitDatabaseTask() {
		assert project.tasks.initLocalDatabase instanceof DatabaseTask
	}

	@Test
	public void shouldAddDropDatabaseTask() {
		assert project.tasks.dropLocalDatabase instanceof DatabaseTask
	}

	@Test
	public void shouldAddResetDatabaseTask() {
		assert project.tasks.resetLocalDatabase instanceof CompositeTask
	}

}
