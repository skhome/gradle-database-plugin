package net.skhome.gradle.plugin.database

import net.skhome.gradle.plugin.database.task.CompositeTask
import net.skhome.gradle.plugin.database.task.DatabaseTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileCollection

class DatabasePluginExtension {

	final Project project

	final NamedDomainObjectContainer<Environment> environments

	FileCollection createScripts

	FileCollection initScripts

	FileCollection dropScripts

	DatabasePluginExtension(final Project project) {
		this.project = project
		this.environments = project.container(Environment)
	}

	def environments(Closure closure) {

		environments.configure(closure)

		environments.each { environment ->

			// TODO add some checks for all mandatory fields

			def createDatabaseTask = constructCreateDatabaseTask(environment)
			def initDatabaseTask = constructInitDatabaseTask(environment)
			def dropDatabaseTask = constructDropDatabaseTask(environment)

			def resetDatabaseTask = constructResetDatabaseTask(environment)
			resetDatabaseTask.subtasks = [ dropDatabaseTask, createDatabaseTask, initDatabaseTask ]

		}

	}

	protected Task constructCreateDatabaseTask(final Environment environment) {
		return project.task(type: DatabaseTask, "create${environment.name.capitalize()}Database") { task ->
			task.group = "database"
			task.description = "Creates all schema objects within the ${environment.name} database."
			task.environment = environment
			task.scripts = createScripts
		}
	}

	protected Task constructInitDatabaseTask(final Environment environment) {
		return project.task(type: DatabaseTask, "init${environment.name.capitalize()}Database") { task ->
			task.group = "database"
			task.description = "Initializes schema objects within the ${environment.name} database."
			task.environment = environment
			task.scripts = initScripts
		}
	}

	protected Task constructDropDatabaseTask(final Environment environment) {
		return project.task(type: DatabaseTask, "drop${environment.name.capitalize()}Database") { task ->
			task.group = "database"
			task.description = "Drop schema objects within the ${environment.name} database."
			task.environment = environment
			task.scripts = dropScripts
		}
	}

	protected CompositeTask constructResetDatabaseTask(final Environment environment) {
		return project.task(type: CompositeTask, "reset${environment.name.capitalize()}Database") { task ->
			task.group = "database"
			task.description = "Resets the ${environment.name} database."
		} as CompositeTask
	}

}
