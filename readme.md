
database {

	steps {

		drop {
			script file: 'src/main/db/ddl/reset-schema.sql', separator: ';'
		}

		create {
			script file: 'src/main/db/ddl/create-tables.sql', separator: ';'
			script file: 'src/main/db/ddl/create-sequences.sql', separator: ';'
		}

		init {
			script file: 'src/main/db/data/master-data.sql', separator: ';'
			script file: 'src/test/db/data/test-data.sql', separator: ';'
		}

	}

	environments {

		local {
			driver   = 'oracle.jdbc.OracleDriver'
			url      = 'jdbc:oracle:thin:@[HOST][:PORT]:SID'
			username = 'username'
			password = 'password'
		}

		development {
			driver   = 'oracle.jdbc.OracleDriver'
			url      = 'jdbc:oracle:thin:@[HOST][:PORT]:SID'
			username = 'username'
			password = 'password'
		}
	}
}


This should create the following tasks:

dropLocalDatabase
	This task will execute all drop scripts. This should be used to clean the database schema
	from all schema objects.

createLocalDatabase
	This task will execute all create scripts. This should be used to populate the schema with
	schema objects (tables, views, sequences, ...)

initLocalDatabase
	This task will execute all initialize scripts. This should be used to fill data into tables,
	initialize sequences, ...

resetLocalDatabase
	This task will execute the reset script. If none was given, it will do nothing.
	This will allow to have one reset script for the whole schema in a multi-project setup.
