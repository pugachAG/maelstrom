object Handler {
  type HandleRes = (State, List[Effect])

  def handle(state: State, event: Event): HandleRes = {
    event match {
      case msgEvent: ReceivedMessageEvent => handleMessage(state, msgEvent)
    }
  }

  def handleMessage(state: State, event: ReceivedMessageEvent): HandleRes = {
    val msg = event.msg
    msg.body match {
      case _: InitBody => handleInit(msg)
      case _: EchoBody => handleEcho(state, msg)
    }
  }

  def handleInit(msg: Message): HandleRes = {
    val body = msg.body.asInstanceOf[InitBody]
    val nodeId = body.nodeId
    val initOk = Message(
      dest = msg.src,
      src = nodeId,
      body = InitOkBody(msgId = 1, inReplyTo = body.msgId))
    (State(nodeId, 2), List(SendMessageEffect(initOk)))
  }

  def handleEcho(state: State, msg: Message): HandleRes = {
    val body = msg.body.asInstanceOf[EchoBody]
    val echoOK = Message(
      dest = msg.src,
      src = state.nodeId,
      body = EchoOkBody(
        msgId = state.nxtMsgId,
        inReplyTo = body.msgId,
        echo = body.echo
      ))
    (state.copy(nxtMsgId = state.nxtMsgId + 1), List(SendMessageEffect(echoOK)))
  }
}
