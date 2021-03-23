import scala.io.StdIn.readLine

object IO {
  def readMessage(): Message = {
    val line = readLine()
    if (line == null) {
      Logger.fatal("Received null line")
    }
    deserMessage(line)
  }

  def writeMessage(msg: Message): Unit = {
    println(serMessage(msg))
  }

  def serMessage(msg: Message): String = {
    ujson.write(ujson.Obj(
      "src" -> msg.src,
      "dest" -> msg.dest,
      "body" -> convertBody(msg.body)))
  }

  def deserMessage(json: String): Message = {
    val msg = ujson.read(json)
    Message(
      src = msg("src").str,
      dest = msg("dest").str,
      body = extractBody(msg("body"))
    )
  }

  def convertBody(body: Body): ujson.Obj = body match {
    case initOk: InitOkBody => ujson.Obj(
      "type" -> "init_ok",
      "in_reply_to" -> initOk.inReplyTo,
      "msg_id" -> initOk.msgId)
    case echoOk: EchoOkBody => ujson.Obj(
      "type" -> "echo_ok",
      "echo" -> echoOk.echo,
      "in_reply_to" -> echoOk.inReplyTo,
      "msg_id" -> echoOk.msgId)
    case x => throw new Exception(s"can't ser body of type $x")
  }

  def extractBody(body: ujson.Value): Body = body("type").str match {
    case "init" => InitBody(
      msgId = body("msg_id").num.toInt,
      nodeId = body("node_id").str,
      nodeIds = body("node_ids").arr.map(_.str).toVector)
    case "echo" => EchoBody(
      msgId = body("msg_id").num.toInt,
      echo = body("echo").str)
    case x => throw new Exception(s"can't deser body of type $x")
  }
}




