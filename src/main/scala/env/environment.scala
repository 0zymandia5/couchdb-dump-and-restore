package envDBs

import io.github.cdimascio.dotenv.Dotenv

trait environment{

  val dotenv = Dotenv.configure().directory(System.getProperty("user.dir")).load();
  // Cloudant/Couch DB Variables
  val HOSTDB_CLOUDANT : String = dotenv.get("HOSTDB_CLOUDANT");
  val USER_CLOUDANT : String = dotenv.get("USER_CLOUDANT");;
  val PASS_CLOUDANT : String = dotenv.get("PASS_CLOUDANT");
  val DBNAME_CLOUDANT : String = dotenv.get("DBNAME_CLOUDANT");
  val MASTER : String = "local";
  val DBNAME_RESTORE_CLOUDANT : String = dotenv.get("DBNAME_RESTORE_CLOUDANT");
  // Execution Variables
  val DUMP_ENABLE : Boolean = dotenv.get("DUMPS").toBoolean
  val BULK_ENABLE : Boolean = dotenv.get("RESTORE").toBoolean

}//Closure trait environment 
