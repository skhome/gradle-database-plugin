package net.skhome.gradle.plugin.database.task

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction

class CompositeTask extends DefaultTask {

	List<Task> subTasks = []

	@TaskAction
	public executeSubtasks() {
		if (subTasks.empty) {
			logger.warn("composite task ${path} has no sub tasks.")
		}
		subTasks.each { Task subTask ->
			println subTask.path
			subTask.execute()
		}
	}

}
