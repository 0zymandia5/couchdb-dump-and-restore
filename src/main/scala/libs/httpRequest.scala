package libs
import sttp.client4.BackendOptions
import sttp.client4.DefaultSyncBackend 
import sttp.client4.Response
import sttp.client4.quick._
import sttp.model.StatusCode
import scala.concurrent.duration._
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
    val timeOutRead : Duration = 5.minutes;
    val timeOutConn : FiniteDuration = 5.minutes;
    def getRequestWithAuth(url: String, username : String, pass : String): Response[String]  = {
      try{
            val backend = DefaultSyncBackend(options = BackendOptions.connectionTimeout(this.timeOutConn));
            val response: Response[String] = quickRequest
            .get(uri"$url")
            .auth.basic(user = username, password = pass)
            .readTimeout(this.timeOutRead)
            .send(backend);
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
          val backend = DefaultSyncBackend(options = BackendOptions.connectionTimeout(this.timeOutConn));
          val response: Response[String] = quickRequest
            .post(uri"$url")
            .auth.basic(user = username, password = pass)
            .body(json2String)
            .contentType("application/json")
            .readTimeout(this.timeOutRead)
            .send(backend)
            return response;
        } catch {
            case e : Exception => { println("Exception Occurred [httpRequest][postRequestWithAuth]: "+e.printStackTrace()); 
            return Response("", StatusCode.InternalServerError, "", Nil)}
        }// Closure catch
    }// Closure postRequestWithAuth

}// Closure httpRequest