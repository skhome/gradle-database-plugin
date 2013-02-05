package net.skhome.gradle.plugin.database

import net.skhome.gradle.plugin.database.model.Environment
import net.skhome.gradle.plugin.database.model.Step
import net.skhome.gradle.plugin.database.task.CompositeTask
import net.skhome.gradle.plugin.database.task.DatabaseTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.Task

class DatabasePluginExtension {

	final Project project

	final NamedDomainObjectContainer<Environment> environments

	final NamedDomainObjectContainer<Step> steps

	DatabasePluginExtension(final Project project) {
		this.project = project
		this.environments = project.container(Environment)
		this.steps = project.container(Step)
	}

	def steps(Closure closure) {
		steps.configure(closure)
	}

	def environments(Closure closure) {
		environments.configure(closure)
		environments.each { environment ->

			// TODO add some checks for all mandatory fields
			def createDatabaseTask = constructCreateDatabaseTask(environment)
			def initDatabaseTask = constructInitDatabaseTask(environment)
			def dropDatabaseTask = constructDropDatabaseTask(environment)

			def resetDatabaseTask = constructResetDatabaseTask(environment)
			resetDatabaseTask.subTasks = [ dropDatabaseTask, createDatabaseTask, initDatabaseTask ]

		}
	}

	protected Task constructCreateDatabaseTask(final Environment environment) {
		return project.task(type: DatabaseTask, "create${environment.name.capitalize()}Database") { task ->
			task.group = "database"
			task.description = "Creates all schema objects within the ${environment.name} database."
			task.environment = environment
			task.scripts = steps.findByName('create')?.scripts
		}
	}

	protected Task constructInitDatabaseTask(final Environment environment) {
		return project.task(type: DatabaseTask, "init${environment.name.capitalize()}Database") { task ->
			task.group = "database"
			task.description = "Initializes schema objects within the ${environment.name} database."
			task.environment = environment
			task.scripts = steps.findByName('init')?.scripts
		}
	}

	protected Task constructDropDatabaseTask(final Environment environment) {
		return project.task(type: DatabaseTask, "drop${environment.name.capitalize()}Database") { task ->
			task.group = "database"
			task.description = "Drop schema objects within the ${environment.name} database."
			task.environment = environment
			task.scripts = steps.findByName('drop')?.scripts
		}
	}

	protected CompositeTask constructResetDatabaseTask(final Environment environment) {
		return project.task(type: CompositeTask, "reset${environment.name.capitalize()}Database") { task ->
			task.group = "database"
			task.description = "Resets the ${environment.name} database."
		} as CompositeTask
	}

}
