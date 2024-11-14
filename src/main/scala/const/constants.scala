package const

/**
  * <h1>object constants</h1>
  * Objext contains constant to avoid harcoding acrosss version changes
*/
object constants {

    val request4CouchDB : Map[String, String] = Map(
        "getAllDocs" ->  "_all_docs?include_docs=true",
        "getAllDesingDocs" -> "_design_docs?include_docs=true"
    );
    val dumpExecution : Map[String, String] = Map(
        "docs" ->  "docs.json",
        "views" -> "views.json"
    );
}//constants closure
