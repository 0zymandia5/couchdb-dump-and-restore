package libs
//local Imports
import envDBs.environment

/**
  * <h1>class couchdb</h1>
  * Class contains functions to operate basic commands on couchdb bia http request
*/
class couchdb extends environment{
    /**
      * getAllDocuments function
      * Executes a GEt request to bring all documents from a couchdb 
      * excluding the views
      * 
      * @return Response[String]
    */
    def getAllDocuments() : Unit = {
        try {
        } catch {
            case ex: Exception => {println("Exception Occurred [couchdb][getAllDocuments]: "+ex)} 
        }// Closure catch 
    }// Closure getAllDocuments
}// Closure couchdb