package net.skhome.gradle.plugin.database.model

class Script {
    
    String filename
    String separator

    Script(final String filename, final String separator) {
        this.filename = filename
        this.separator = separator
    }

}
