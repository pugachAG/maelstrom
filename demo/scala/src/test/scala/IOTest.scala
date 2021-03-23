import org.scalatest.FunSuite

class IOTest extends FunSuite {
  test("Deserialize init") {
    val json = "{\"dest\":\"n1\",\"body\":{\"type\":\"init\",\"node_id\":\"n1\",\"node_ids\":[\"n1\"],\"msg_id\":1},\"src\":\"c1\"}\n"
    val res: Message = IO.deserMessage(json)
    assert(res == Message(
      src = "c1",
      dest = "n1",
      body = InitBody(
        msgId = 1,
        nodeId = "n1",
        nodeIds = Vector("n1")
      )
    ))
  }
}
