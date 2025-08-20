import com.timushev.sbt.updates.UpdatesKeys.dependencyUpdates
import com.timushev.sbt.updates.UpdatesPlugin.autoImport.{dependencyUpdatesFailBuild, dependencyUpdatesFilter, moduleFilterRemoveValue}
import sbt.Keys._
import sbt._

object SbtUpdatesSettings {

  lazy val sbtUpdatesSettings = Seq(
    dependencyUpdatesFailBuild := false,
    (Compile / compile) := ((Compile / compile) dependsOn dependencyUpdates).value,
    dependencyUpdatesFilter -= moduleFilter("org.scala-lang"),
    // gatling version determined by hmrc performance-test-runner library
    dependencyUpdatesFilter -= moduleFilter("io.gatling"),
    dependencyUpdatesFilter -= moduleFilter("io.gatling.highcharts")
  )

}
