package net.skhome.gradle.plugin.database

import net.skhome.gradle.plugin.database.model.Environment
import org.gradle.api.Project
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
			steps {
				create {
					script file: 'src/main/db/create.sql', separator: ';'
				}
			}
		}

		// then
		assert project.database.steps.create.scripts

	}

	@Test
	public void shouldProvideInitScriptCollection() {

		// when
		project.database {
			steps {
				init {
					script file: 'src/main/db/master-data.sql', separator: ';'
				}
			}
		}

		// then
		assert project.database.steps.init.scripts

	}

	@Test
	public void shouldProvideDropScriptCollection() {

		// when
		project.database {
			steps {
				drop {
					script file: 'src/main/db/drop-schema.sql'
				}
			}
		}

		// then
		assert project.database.steps.drop.scripts

	}


}
