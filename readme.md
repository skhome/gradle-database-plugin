
database {

	createScripts = ['src/main/db/ddl/create-tables.sql', 'src/main/db/ddl/create-sequences.sql']
	initScripts   = ['src/main/db/data/master-data.sql', 'src/test/db/data/test-data.sql']
	resetScripts  = ['src/main/db/ddl/reset-schema.sql']

	environments {

		local {
			driver   = 'oracle.jdbc.OracleDriver'
			url      = 'jdbc:oracle:thin:@[HOST][:PORT]:SID'
			schema   = ''
			username = 'username'
			password = 'password'
		}

		development {
			driver   = 'oracle.jdbc.OracleDriver'
			url      = 'jdbc:oracle:thin:@[HOST][:PORT]:SID'
			schema   = ''
			username = 'username'
			password = 'password'
		}
	}
}


This should create the following tasks:

createLocalDatabase
	This task will execute all create scripts. This should be used to populate the schema with
	schema objects (tables, views, sequences, ...)

initLocalDatabase
	This task will execute all initialize scripts. This should be used to fill data into tables,
	initialize sequences, ...

resetLocalDatabase
	This task will execute the reset script. If none was given, it will do nothing.
	This will allow to have one reset script for the whole schema in a multi-project setup.
