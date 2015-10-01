package com.epam.bdcc.trut
/**
 * Created by sergey_moukavoztchik on 9/25/2015.
 */

import scala.io.{Codec, Source}
import scala.collection.mutable.HashMap

import org.apache.hadoop.conf._
import org.apache.hadoop.fs._

import java.util.Scanner


object FileProcessor extends App {

  private val conf = new Configuration()
  conf.set("fs.defaultFS", "hdfs://127.0.0.1:8020")

  val fileSystem = FileSystem.get(conf)
  fileSystem.setWorkingDirectory(new Path("/input/media/"))
  val ls = fileSystem.listStatus(new Path("."))
  val files = ls.map(_.getPath).filter(_.getName.startsWith("bid.")).filter(_.getName.endsWith("txt"))

  val hash = new HashMap[String, Int]

  implicit val codec = Codec("UTF-8")

  files.foreach { fileName =>
    println(s"Processing file: ${fileName.getName}")


    val stream = fileSystem.open(fileName)
    val scanner = new Scanner(stream, "UTF-8")
    var rowCount: Int = 0


    while (scanner.hasNextLine) {
      val line = scanner.nextLine()
      val key: String = line.split("\t")(2)
      hash(key) = hash.getOrElse(key, 0) + 1
    }
    scanner.close()
    stream.close()
  }

  //TODO: generate some output
  println(hash("null"))

}

