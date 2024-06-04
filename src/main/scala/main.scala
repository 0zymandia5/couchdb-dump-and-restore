package com.spark.main

//Local Imports
import libs.couchdb
import libs.files
import const.constants
import envDBs.environment
//Libs Imports
import sttp.client4.Response

object main extends environment {

    val objCouchDB : couchdb = new couchdb();
    val objfiles : files = new files();
    val executionMap : Map[String, String]= constants.dumpExecution;

    def main(args: Array[String]): Unit = {
        try {
            val content : String = os.read(os.pwd / "Ozymandias.txt");
            println(content)
            if (DUMP_ENABLE){
                println("############################");
                println("### Running Dump Process ###");
                println("############################");
                executionMap.keys.foreach(key=>{
                    val response : Response[String] = objCouchDB.getAllDocuments(key);
                    objfiles.writeJsonFile(executionMap(key), response.body);
                });
            }//If Closure
            if (BULK_ENABLE){
                println("#############################");
                println("## Running Restore Process ##");
                println("#############################");
                executionMap.keys.foreach(key=>{
                    objCouchDB.bulkDocuments(key);
                });
            }//If Closure
        } catch {
            case ex: Exception => {println("Exception Occurred [main]: "+ex.printStackTrace());}
        }// catch closure
    }// main closure
} // object main closure
