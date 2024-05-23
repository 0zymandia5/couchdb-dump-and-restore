package libs

import sttp.client4.Response
/**
  * <h1>class files</h1>
  * Class Contains functions to write/read files
  * 
*/
class files {

  /**
    * writeJsonFile function
    * Writes json data into a json file.
    * 
    * @param nameFile String
    * @param response Response[String]
    * 
    * @return Unit
  **/
  def writeJsonFile (nameFile : String, jsonDocs : String) : Unit = {
    try {
      val json2BeInserted = ujson.read(jsonDocs);
      if(os.exists(os.pwd / "jsons_from_dump" / nameFile)){
        os.remove(os.pwd / "jsons_from_dump" / nameFile)
        println(s"$nameFile file found... Deleting it ......")
      }
      os.write(os.pwd / "jsons_from_dump" / nameFile, ujson.write(json2BeInserted));
      println(s"$nameFile file created")
    } catch {
      case e : Exception => { println("Exception Occurred [files][writeJsonFile]: "+e.printStackTrace());}
    }//catch closure
  }// writeJsonFile function

  /**
    * readJsonFile function
    * Read json file .
    * 
    * @param nameFile String
    * 
    * @return String
  **/
  def readJsonFile(nameFile : String) : String = {
    try {
      val rawJsoncontent : String = os.read(os.pwd / "jsons_from_dump" / nameFile);
      return rawJsoncontent;
    } catch {
      case e : Exception => { println("Exception Occurred [files][readJsonFile]: "+e.printStackTrace()); return "";}
    }//catch closure
  }// readJsonFile function

}// Closure files class
