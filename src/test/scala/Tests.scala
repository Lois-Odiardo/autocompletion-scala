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
    val dico = base.constructDico("hello world hello scala")
    val valueAtHello = dico.getValueAtWord("hello")
    valueAtHello.isDefined shouldBe true
    valueAtHello.get.tabProba("world") shouldBe 1
    valueAtHello.get.tabProba("scala") shouldBe 1
    valueAtHello.get.tabProba.contains("hello") shouldBe false
  }

  "verifyProbable" should "be defined" in {
    //TODO une fois qu'on a la structure de base, modifier
  }

}
