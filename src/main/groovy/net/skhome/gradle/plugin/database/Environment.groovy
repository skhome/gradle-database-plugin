package net.skhome.gradle.plugin.database

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
