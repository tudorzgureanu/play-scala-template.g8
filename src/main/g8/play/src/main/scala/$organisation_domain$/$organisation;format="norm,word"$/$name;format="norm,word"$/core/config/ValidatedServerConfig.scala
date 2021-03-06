package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.config

import cats._
import cats.data.{NonEmptyList => NEL, Validated}
import com.typesafe.config.Config
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric._
import net.cakesolutions.config._

/**
  * Validated server configuration settings helper and utilities.
  */
object ValidatedServerConfig {
  type PositiveInt = Int Refined Positive

  /**
    * Validated configuration settings for the server.
    *
    * @param host hostname the Play server will listen on
    * @param port port the Play server will listen on
    */
  sealed abstract case class ServerConfig(host: String, port: PositiveInt)

  /**
    * Factory method for creating validated instances of ServerConfig.
    *
    * @return validated configuration object or the validation failures
    */
  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  def apply(
    implicit config: Config
  ): Validated[NEL[ValueFailure], ServerConfig] = {
    via[ServerConfig]("services.app") { implicit config =>
      Applicative[ValidationFailure].map2(
        unchecked[String](required("host", "NOT_SET")),
        unchecked[PositiveInt](required("port", "NOT_SET"))
      )(new ServerConfig(_, _) {})
    }
  }
}
