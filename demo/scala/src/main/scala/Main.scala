object Main extends App {
  try {
    run()
  } catch {
    case ex: Exception =>
      ex.printStackTrace(System.err)
      System.exit(1)
  }

  def run(): Unit = {
    var state = State(nodeId = "", nxtMsgId = 0)
    while (true) {
      val event = ReceivedMessageEvent(IO.readMessage())
      val (nxtState, effects) = Handler.handle(state, event)
      for (effect <- effects) {
        effect match {
          case SendMessageEffect(msg) => IO.writeMessage(msg)
        }
      }
      state = nxtState
    }
  }
}
