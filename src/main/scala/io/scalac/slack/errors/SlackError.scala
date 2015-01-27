package io.scalac.slack.errors

/**
 * Created on 21.01.15 16:23
 */
sealed trait SlackError

object ApiTestError extends SlackError

//no authentication token provided
object NotAuthenticated extends SlackError

//invalid auth token
object InvalidAuth extends SlackError

//token is for deleted user or team
object AccountInactive extends SlackError

case class UnknownError(msg: String) extends SlackError


object SlackError {
  def apply(errorName: String) = {
    errorName match {
      case "not_authed" => NotAuthenticated
      case "invalid_auth" => InvalidAuth
      case "account_inactive" => AccountInactive
      case err => new UnknownError(err)
    }
  }
}