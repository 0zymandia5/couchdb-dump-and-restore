package libs

import sttp.client4.quick._
import sttp.client4.Response
import sttp.model.StatusCode
import sttp.model.Uri

/**
  * <h1>class httpRequest</h1>
  * Class contains functions to perform http requests such as 
  * GET/POST/PUT/DELETE etc .....
  */
class httpRequest {
    /**
      * getRequestWithAuth function
      * Executes a GEt request with a simple authentication.
      * 
      * @param url String
      * @param username String
      * @param pass String
      * 
      * @return Response[String]
    **/
    def getRequestWithAuth(url: String, username : String, pass : String): Response[String]  = {
        try {
            val response: Response[String] = quickRequest
            .get(uri"$url")
            .auth.basic(user = username, password = pass)
            .send()
            return response;
        } catch {
            case e : Exception => { println("Exception Occurred [httpRequest][getRequestWithAuth]: "+e.printStackTrace()); 
            return Response("", StatusCode.InternalServerError, "", Nil)}
        }// Closure catch
    }// Closure getRequestWithAuth

    /**
      * postRequestWithAuth function
      * Executes a Post request with a simple authentication.
      * 
      * @param url String
      * @param username String
      * @param pass String
      * @param json2String String
      * 
      * @return Response[String]
    **/
    def postRequestWithAuth(url: String, username : String, pass : String, json2String : String): Response[String]  = {
        try {
          val response: Response[String] = quickRequest
            .post(uri"$url")
            .auth.basic(user = username, password = pass)
            .body(json2String)
            .contentType("application/json")
            .send()
            return response;
        } catch {
            case e : Exception => { println("Exception Occurred [httpRequest][postRequestWithAuth]: "+e.printStackTrace()); 
            return Response("", StatusCode.InternalServerError, "", Nil)}
        }// Closure catch
    }// Closure postRequestWithAuth

}// Closure httpRequest