package net.skhome.gradle.plugin.database

import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class DatabasePluginExtensionTest {

	Project project = ProjectBuilder.builder().build()

	@Before
	public void preparePlugin() {
		project.apply plugin: 'database'
	}

	@Test
	public void shouldCreateDomainObjectsForEachEnvironment() {

		// when
		project.database.environments {
			local {
			}

			development {

			}
		}

		// then
		assert project.database.environments.local instanceof Environment
		assert project.database.environments.development instanceof Environment

	}

	@Test
	public void shouldFeedConnectionPropertiesFromEnvironmentClosure() {

		// when
		project.database.environments {
			local {
				driver = "driver"
				url = "url"
				username = "username"
				password = "secret"
			}
		}

		// then
		def environment = project.database.environments.local
		assert environment.driver == "driver"
		assert environment.url == "url"
		assert environment.username == "username"
		assert environment.password == "secret"

	}

	@Test
	public void shouldProvideCreateScriptCollection() {

		// when
		project.database {
			createScripts = project.files("src/main/db/create.sql")
		}

		// then
		assert project.database.createScripts instanceof FileCollection
		assert !project.database.createScripts.empty

	}

	@Test
	public void shouldProvideInitScriptCollection() {

		// when
		project.database {
			initScripts = project.files("src/main/db/master-data.sql")
		}

		// then
		assert project.database.initScripts instanceof FileCollection
		assert !project.database.initScripts.empty

	}

	@Test
	public void shouldProvideDropScriptCollection() {

		// when
		project.database {
			dropScripts = project.files("src/main/db/drop-schema.sql")
		}

		// then
		assert project.database.dropScripts instanceof FileCollection
		assert !project.database.dropScripts.empty

	}


}
