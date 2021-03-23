sealed class LogLevel(val lvl: String)

case object Info extends LogLevel("INFO")

case object Warn extends LogLevel("WARN")

case object Fatal extends LogLevel("FATAL")

object Logger {
  def log(lvl: LogLevel, msg: String): Unit = {
    System.err.println(s"[$lvl] $msg")
  }

  def fatal(msg: String): Unit = {
    log(Fatal, msg)
    System.exit(1)
  }
}
