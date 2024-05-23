package libs

//local Imports
import envDBs.environment
import const.constants
//libs Imports
import sttp.client4.Response
import sttp.model.StatusCode

/**
  * <h1>class couchdb</h1>
  * Class contains functions to operate basic commands on couchdb bia http request
*/
class couchdb extends environment { 

    val objHttp : httpRequest= new httpRequest();
    val objFiles : files = new files();

    /**
      * getAllDocuments function
      * Executes a GET request to bring all documents from a couchdb 
      * excluding the views
      * 
      * @param flavor String
      * 
      * @return Response[String]
    **/
    def getAllDocuments(flavor: String): Response[String] = {
        try {
          var requestToPullAll : String = "";
          flavor match {
            case "docs" => requestToPullAll = constants.request4CouchDB("getAllDocs")
            case "views" => requestToPullAll = constants.request4CouchDB("getAllDesingDocs") 
          }
          val finalURL = this.buildURL4Request(true, HOSTDB_CLOUDANT, DBNAME_CLOUDANT, requestToPullAll);
          val response : Response[String] = objHttp.getRequestWithAuth(finalURL, USER_CLOUDANT, PASS_CLOUDANT);
          if(response.code.toString() == "200") return response else return Response("", StatusCode.InternalServerError, "", Nil);

        } catch {
            case ex: Exception => {println("Exception Occurred [couchdb][getAllDocuments]: "+ex.printStackTrace());
            return Response("", StatusCode.InternalServerError, "", Nil)
          } 
        }// Closure catch 
    }// Closure getAllDocuments

    /**
      * bulkDocuments function
      * Executes a GET request to bring all documents from a couchdb 
      * excluding the views
      * 
      * @param flavor String
      * 
      * @return Response[String]
    **/
    def bulkDocuments(flavor: String): Response[String] = {
        try {
          val requestToBulkAll : String = "_bulk_docs";
          var jsonDocName : String = "";
          flavor match {
            case "docs" => jsonDocName = constants.dumpExecution("docs")
            case "views" => jsonDocName = constants.dumpExecution("views") 
          }
          val json2BulkRaw : String  =  objFiles.readJsonFile(jsonDocName);
          val json2Bulk : String  =  cleanUpRawDocs(json2BulkRaw);
          val finalURL = this.buildURL4Request(true, HOSTDB_CLOUDANT, DBNAME_RESTORE_CLOUDANT, requestToBulkAll);
          val response : Response[String] = objHttp.postRequestWithAuth(finalURL, USER_CLOUDANT, PASS_CLOUDANT, json2Bulk);
          
          return response;
        } catch {
            case ex: Exception => {println("Exception Occurred [couchdb][getAllDocuments]: "+ex.printStackTrace());
            return Response("", StatusCode.InternalServerError, "", Nil)
          } 
        }// Closure catch 
    }// Closure bulkDocuments

    /**
      * cleanUpRawDocs function
      * Takes the json raw data from the dumps document and performs a cleanup and formatting
      * 
      * @param response Response[String]
      * 
      * @return ujson.Value
      * 
    **/
    def cleanUpRawDocs(docsRawFormat : String) : String = {
      try {
        val jsonResponse : ujson.Value  = ujson.read(docsRawFormat);
        val docsArray : scala.collection.mutable.ArrayBuffer[ujson.Value] = scala.collection.mutable.ArrayBuffer.empty;
        jsonResponse.obj("rows").arr.foreach(rawDoc => {                   // Remove "_rev" if needed
            val singleDoc = rawDoc.obj("doc");
            val docUpdated : ujson.Value = singleDoc.obj.filterNot { case (key, _) => key == "_rev" };
            docsArray.append(docUpdated);
        });
        return ("""{"docs":"""+ujson.write(docsArray.toList,-1,true)+"}");
      } catch {
        case ex: Exception => {println("Exception Occurred [couchdb][cleanUpViewsRaw]: "+ex.printStackTrace());return("{}")}
      }// catch closure
    }//Closure cleanUpRawDocs

    /**
      * buildURL4Request function
      * build the final request to couchdb
      * 
      * @param secure Boolean
      * @param host String
      * @param dbName String
      * @param request String
      * 
      * @return URL String
      * 
    **/
    def buildURL4Request(secure : Boolean, host : String, dbName : String, request : String) : String = {
        try {
            val httpProtocol = if (secure) "https://" else "http://";
            return (httpProtocol+host+"/"+dbName+"/"+request);
        } catch {
           case ex: Exception => {println("Exception Occurred [couchdb][buildURL4Request]: "+ex.printStackTrace()); return "";} 
        }// catch closure
    }//Closure buildURL4Request

}// Closure couchdb