package org.example
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.viz.lightning._
import scala.io.Source
import org.apache.flink.ml.feature.CountVectorizer
import scala.collection.immutable.ListMap


object CountVectorizerApp {
  def main(args: Array[String]) {

    val env = ExecutionEnvironment.getExecutionEnvironment
    val lgn = Lightning(host="http://localhost:3000")
    lgn.createSession("demo session2")
   // lgn.plot("line", Map("series" -> List(1,1,2,3,9,20)))
    //lgn.createSession("provide an optional session name")
    // val trainingData = Seq(

    //   Source.fromFile("/Users/roshaninagmote/Downloads/data/austen-bronte/Austen_Pride.txt").getLines().mkString,
    //   Source.fromFile("/Users/roshaninagmote/Downloads/data/austen-bronte/Austen_Sense.txt").getLines().mkString   )

       // val trainingData = Seq(
       //    "This This is the first and second  document document document this girl"
       //  )

val trainingData = Seq(
          Source.fromFile("/Users/roshaninagmote/sample2.txt").getLines().mkString   )


    val trainingDataDS = env.fromCollection(trainingData)
   val cv = CountVectorizer().setMinDF(3)
   cv.fit(trainingDataDS)

    //cv.transform(trainingDataDS)
   val result = cv.transform(trainingDataDS).flatMap(x => x).collect().toList
   val counts = result.unzip._2
        println(counts)

         val features = cv.get_feature_names()
    val checktype = features.collect().toList
    val data = ListMap(checktype.head.toSeq.sortBy(_._2):_*)
    val words = data.keys.toList
    
    println(words)

  val viz = lgn.plot("lightning-wordcloud-new", Map("words" -> words, "counts" -> counts))

  val vizURL = viz.getPermalinkURL

println("it worked")
  }
}
