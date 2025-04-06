import scala.annotation.tailrec
import scala.collection.immutable.List

object base:
  def transformString(s: String): List[String] = s.split(" ").toList

  def constructDico(s: String): Node[Probabilite] =
    @tailrec
    def constructDicoAux(l : List[String], dicoAlreadyFormed: Node[Probabilite]): Node[Probabilite] = l match
      case Nil => dicoAlreadyFormed
      case x :: xs => xs match
        case Nil => dicoAlreadyFormed
        case y :: ys => constructDicoAux(xs , dicoAlreadyFormed.addToTrieWithOption(x,Some(y),constructProba))

    constructDicoAux(transformString(s),Node[Probabilite]())

  def constructProba(newValue : Option[String] , oldValue : Option[Probabilite]): Option[Probabilite] = newValue match
    case Some(mot) => oldValue match
      case Some(oldTabProba) => Some(oldTabProba.addProbaToWord(mot))
      case None => Some(Probabilite(Map(mot -> 1)))
    case None => oldValue

  def getNextWordInDico(s: String, dico: Node[Probabilite]): String =
    dico.getValueAtWord(s) match
      case Some(proba) => proba.getBestProba
      case None => ""

class Node[A](protected val children: Map[Char, Node[A]] = Map(),protected val value: Option[A] = None):
  def addNode(key: Char, grandChildrens: Node[A]): Node[A] = Node[A](children + (key -> grandChildrens), value)

  def changeOption(newValue: Option[A]): Node[A] = Node[A](children, newValue)

  def copy(children: Map[Char, Node[A]] = children, value: Option[A] = value): Node[A] =
    Node(children, value)

  def addToTrieWithOption[B](s: String, valueForWord: Option[B], updateValueFonction: (Option[B], Option[A]) => Option[A]): Node[A] =
    def addToTrieWithOptionAux(currentNode: Node[A], word: List[Char], valueForWord: Option[B], updateValueFonction: (Option[B], Option[A]) => Option[A]): Node[A] = word match
      case Nil => Node[A](currentNode.children , updateValueFonction(valueForWord,currentNode.value))
      case x :: xs =>
        currentNode.children.get(x) match
          case Some(letterChildNode) =>
            Node[A](currentNode.children + (x -> addToTrieWithOptionAux(letterChildNode , xs , valueForWord , updateValueFonction)) , currentNode.value)
          case None => Node[A](currentNode.children + (x -> addToTrieWithOptionAux(Node[A]() , xs , valueForWord , updateValueFonction)) , currentNode.value)

    addToTrieWithOptionAux(Node[A](children, value), s.toList, valueForWord, updateValueFonction)

  def getValueAtWord(s: String): Option[A] = ???
  /*
  def constructTrieGenerique(l: List[String]): Map[Char, Node[A]] = {
    def insertWord(root: Map[Char, Node[A]], word: List[Char], nodeValue: Option[A]): Map[Char, Node[A]] = {
      word match {
        case Nil => root
        case x :: xs =>
          insertWordHelper(root, x, xs, nodeValue)
      }
    }

    def insertWordHelper(root: Map[Char, Node[A]], x: Char, xs: List[Char], nodeValue: Option[A]): Map[Char, Node[A]] = {
      root.get(x) match {
        case Some(childNode) =>
          root + (x -> (if (xs.isEmpty) childNode.changeOption(nodeValue) else childNode.addNode(xs.head, childNode.copy(children = insertWord(childNode.children, xs, nodeValue)))))
        case None =>
          root + (x -> (if (xs.isEmpty) Node(Map(), nodeValue) else Node(Map(xs.head -> Node(insertWord(Map(), xs, nodeValue))))))
      }
    }
  }*/

class Probabilite(tabProba: Map[String, Int]):
  def addProbaToWord(s: String): Probabilite =
    tabProba.get(s) match
      case Some(value) =>
        Probabilite(tabProba + (s -> (value + 1)))
      case None =>
        Probabilite(tabProba + (s -> 1))

  def getBestProba: String =
    @tailrec
    def getBestProbaAux(tab: List[(String,Int)], bestString : String, bestInt : Int): String = tab match
      case Nil => bestString
      case (xString , xInt) :: xs =>
        if xInt < bestInt then
          getBestProbaAux(xs,bestString,bestInt)
        else
          getBestProbaAux(xs , xString , xInt)

    getBestProbaAux(tabProba.toList , "" , -1)