package net.skhome.gradle.plugin.database.task

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction

class CompositeTask extends DefaultTask {

	List<Task> subtasks = []

	@TaskAction
	public executeSubtasks() {
		if (subtasks.empty) {
			logger.warn("composite task ${path} has no subtasks.")
		}
		subtasks.each { Task subtask ->
			println subtask.path
			subtask.execute()
		}
	}

}
