package net.skhome.gradle.plugin.database.model

class Step {

    String name
    List<net.skhome.gradle.plugin.database.model.Script> scripts = []

    Step(final String name) {
		this.name = name
	}

	def script(final Map params) {
		scripts.add(new net.skhome.gradle.plugin.database.model.Script(params['file'], params['separator']))
	}

}
