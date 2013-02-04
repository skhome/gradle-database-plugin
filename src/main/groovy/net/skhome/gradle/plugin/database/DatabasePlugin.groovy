package net.skhome.gradle.plugin.database

import org.gradle.api.Plugin
import org.gradle.api.Project;

class DatabasePlugin implements Plugin<Project> {

	Project project
	DatabasePluginExtension extension

	@Override
	void apply(final Project project) {
		this.project = project
		createExtension()
		createConfiguration()
	}

	private createExtension() {
		extension = project.extensions.create("database", DatabasePluginExtension, project)
	}

	private createConfiguration() {
		project.configurations.create("driver")
	}

}
