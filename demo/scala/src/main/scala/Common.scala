sealed class Body

case class InitBody(msgId: Int,
                    nodeId: String,
                    nodeIds: Vector[String]) extends Body

case class InitOkBody(msgId: Int,
                      inReplyTo: Int) extends Body

case class EchoBody(msgId: Int,
                    echo: String) extends Body

case class EchoOkBody(msgId: Int,
                      inReplyTo: Int,
                      echo: String) extends Body

case class Message(src: String, dest: String, body: Body)

case class State(nodeId: String, nxtMsgId: Int)

sealed class Effect

case class SendMessageEffect(msg: Message) extends Effect

sealed class Event

case class ReceivedMessageEvent(msg: Message) extends Event