package net.skhome.gradle.plugin.database.task
import groovy.sql.Sql
import net.skhome.gradle.plugin.database.Environment
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction

import java.sql.Driver
import java.sql.DriverManager

class DatabaseTask extends DefaultTask {

	Environment environment

	FileCollection scripts

	@TaskAction
	public executeScripts() {
		logger.info("Executing SQL scripts against ${environment.name} environment")

		if (scripts == null || scripts.empty) {
			logger.info("no scripts specified")
			return;
		}

		registerDriver(environment.driver)

		def sql = Sql.newInstance(environment.url, environment.username, environment.password)
		scripts.each { File script ->
			logger.info("Executing: ${script.name}")
			sql.execute(script.text)
		}
	}

	private void registerDriver(final String driverName) {
		URLClassLoader loader = GroovyObject.class.classLoader as URLClassLoader
		project.configurations['driver'].each { File file -> loader.addURL(file.toURL()) }
		Class driverClass = loader.loadClass(driverName)
		DriverManager.registerDriver(driverClass.newInstance() as Driver)
	}

}
