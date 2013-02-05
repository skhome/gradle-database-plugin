package net.skhome.gradle.plugin.database.model

class Environment {

	String name
	String driver
	String url
	String username
	String password

	Environment(final String name) {
		this.name = name
	}

}
