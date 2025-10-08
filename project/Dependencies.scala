import sbt._

object Dependencies {

  private val gatlingVersion = "3.14.4"

  val test = Seq(
    "com.typesafe"          % "config"                    % "1.4.5"        % Test,
    "uk.gov.hmrc"          %% "performance-test-runner"   % "6.2.0"        % Test
  )
}
