import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

import base.*
import scala.collection.immutable.List

class Tests extends AnyFlatSpec {

  "transformString" should "be defined" in {
    base.transformString("hello world") shouldBe List("hello", "world")
    base.transformString("a b c") shouldBe List("a", "b", "c")
    base.transformString("") shouldBe List("")
  }

  "constructDico" should "be defined" in {
    val dico = base.constructDico("hello world hello scala hello scala")
    val valueAtHello = dico.getValueAtWord("hello")
    valueAtHello.isDefined shouldBe true
    valueAtHello.get.tabProba("world") shouldBe 1
    valueAtHello.get.tabProba("scala") shouldBe 2
    valueAtHello.get.tabProba.contains("hello") shouldBe false
  }

  "getNextWordInDico" should "be defined" in {
    val dico = base.constructDico("hello world hello scala hello scala test")
    base.getNextWordInDico("hello",dico) shouldBe "scala"
    base.getNextWordInDico("world",dico) shouldBe "hello"
    base.getNextWordInDico("test", dico) shouldBe ""
  }

  "Trie navigation" should "be defined" in {
    val dico = base.constructDico("a b a c a b")
    val aNode = dico.getValueAtWord("a")
    aNode.get.tabProba("b") shouldBe 2
    aNode.get.tabProba("c") shouldBe 1
  }


}
