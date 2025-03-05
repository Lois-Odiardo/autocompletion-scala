import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

import base.*
import scala.collection.immutable.List

class Tests extends AnyFlatSpec {

  //TODO décider ce qu'on fait du point
  "transformString" should "be defined" in {
    val string = "Ceci est un texte à transformer."
    transformString(string) shouldBe List("Ceci","est","un","texte","à","transformer.")
  }

  "constructTrie" should "be defined" in {
    //TODO une fois qu'on a la structure de base, modifier
  }

  "verifyProbable" should "be defined" in {
    //TODO une fois qu'on a la structure de base, modifier
  }

}
