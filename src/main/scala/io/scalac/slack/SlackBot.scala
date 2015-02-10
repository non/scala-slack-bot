package io.scalac.slack

import akka.actor.{ActorSystem, Props}
import akka.event.Logging
import io.scalac.slack.api.Start

/**
 * Created on 20.01.15 21:51
 */
object SlackBot {

  val system = ActorSystem("SlackBotSystem")
  val eventBus = new MessageEventBus

  sys.addShutdownHook(shutdown())

  def main(args: Array[String]) {
    val logger = Logging(system, getClass)

    logger.info("SlackBot started")
    logger.debug("With api key: " + Config.apiKey)

    try {

      system.actorOf(Props[SlackBotActor], "slack-bot") ! Start

      system.awaitTermination()
      logger.info("Shutdown successful...")

    } catch {
      case e: Exception =>
        logger.error("An unhandled exception occured...", e)
        system.shutdown()
        system.awaitTermination()
    }

  }

  def shutdown(): Unit = {
    system.shutdown()
    system.awaitTermination()
  }

}
