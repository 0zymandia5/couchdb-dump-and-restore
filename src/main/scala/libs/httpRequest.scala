package libs

import sttp.client4.quick._
import sttp.client4.Response

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
      * @param url sttp.model.Uri
      * @param username String
      * @param pass String
      * 
      * @return Response[String]
    */
    def getRequestWithAuth(url: sttp.model.Uri, username : String, pass : String): Unit = {
        try {    
            val response: Response[String] = quickRequest
            .get(url)
            .auth.basic(user = username, password = pass)
            .send()
            //return response;
        } catch {
            case e : Exception => { println("Exception Occurred [httpRequest][getRequestWithAuth]: "+e); }
        }// Closure catch
    }// Closure getRequestWithAuth
}// Closure httpRequest