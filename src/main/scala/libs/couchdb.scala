package libs

//local Imports
import envDBs.environment
import const.constants
//libs Imports
import sttp.client4.Response
import sttp.model.StatusCode
import scala.collection.mutable.ArrayBuffer
/**
  * <h1>class couchdb</h1>
  * Class contains functions to operate basic commands on couchdb bia http request
*/
class couchdb extends environment { 

    val objHttp : httpRequest= new httpRequest();
    val objFiles : files = new files();
    val docsLimit4Bulk : Int = 1000;

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
    def bulkDocuments(flavor: String): Unit = {
        try {
          val requestToBulkAll : String = "_bulk_docs";
          var jsonDocName : String = "";
          flavor match {
            case "docs" => jsonDocName = constants.dumpExecution("docs")
            case "views" => jsonDocName = constants.dumpExecution("views") 
          }
          val json2BulkRaw : String  =  objFiles.readJsonFile(jsonDocName);
          val json2Bulk : ArrayBuffer[ujson.Value]  =  cleanUpRawDocs(json2BulkRaw);
          val arrayOfDocs2Bulk : ArrayBuffer[String]  =  chunkIt(json2Bulk);
          val finalURL = this.buildURL4Request(true, HOSTDB_CLOUDANT, DBNAME_RESTORE_CLOUDANT, requestToBulkAll);
          arrayOfDocs2Bulk.foreach(docs =>{
            val response : Response[String] = objHttp.postRequestWithAuth(finalURL, USER_CLOUDANT, PASS_CLOUDANT, docs);
            println(s"Bulk status : ${response.code}");
          })          
        } catch {
            case ex: Exception => {println("Exception Occurred [couchdb][bulkDocuments]: "+ex.printStackTrace());
          } 
        }// Closure catch 
    }// Closure bulkDocuments

    /**
      * cleanUpRawDocs function
      * Takes the json raw data from the dumps document and performs a cleanup and formatting
      * 
      * @param response Response[String]
      * 
      * @return ArrayBuffer[ujson.Value]
      * 
    **/
    def cleanUpRawDocs(docsRawFormat : String) : ArrayBuffer[ujson.Value] = {
      try {
        val jsonResponse : ujson.Value  = ujson.read(docsRawFormat);
        val docsArray : ArrayBuffer[ujson.Value] = ArrayBuffer.empty;
        jsonResponse.obj("rows").arr.foreach(rawDoc => {                   // Remove "_rev" if needed
            val singleDoc = rawDoc.obj("doc");
            val docUpdated : ujson.Value = singleDoc.obj.filterNot { case (key, _) => key == "_rev" };
            docsArray.append(docUpdated);
        });
        return docsArray;
      } catch {
        case ex: Exception => {println("Exception Occurred [couchdb][cleanUpRawDocs]: "+ex.printStackTrace());return(ArrayBuffer.empty)}
      }// catch closure
    }//Closure cleanUpRawDocs


    /**
      * chunkIt function
      * Takes an Array of Documents and split it into pieces having a maximum length declared on docsLimit4Bulk
      * 
      * @param arrayOfDocs  ArrayBuffer[ujson.Value]
      * 
      * @return ArrayBuffer [String]
      * 
    **/
    def chunkIt( arrayOfDocs :  ArrayBuffer[ujson.Value]): ArrayBuffer [String] = {
      try {
        val arraysToBulk : ArrayBuffer [String] = ArrayBuffer.empty;
        val auxArray : ArrayBuffer[ujson.Value] = ArrayBuffer.empty;
        arrayOfDocs.foreach(doc => {
          if(auxArray.length == this.docsLimit4Bulk){
            arraysToBulk.append("""{"docs":"""+ujson.write(auxArray.toList,-1,true)+"}")
            auxArray.clear();
          }//if closure
          auxArray.append(doc);
        });
      return arraysToBulk;
      } catch {
        case ex: Exception => {println("Exception Occurred [couchdb][chunkIt]: "+ex.printStackTrace()); return ArrayBuffer.empty;} 
      }//catch closure
    }//chunkIt closure

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