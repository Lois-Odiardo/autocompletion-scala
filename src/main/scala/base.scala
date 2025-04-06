import scala.collection.immutable.List

object base:
  def transformString(s: String): List[String] = s.split(" ").toList

  def constructDico(s: String): Node[Probabilite] =
    def constructDicoAux(l : List[String] , dicoAlreadyFormed: Node[Probabilite]): Node[Probabilite] = l match
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

class Node[A](val children: Map[Char, Node[A]] = Map(),val value: Option[A] = None):
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

//TODO : si j'obtiens pas la réponse sur le parcours de map il faudra faire par liste, ou sinon passer par keySet
class Probabilite(tabProba: Map[String, Int]):
  def addProbaToWord(s: String): Probabilite =
    tabProba.get(s) match
      case Some(value) =>
        Probabilite(tabProba + (s -> (value + 1)))
      case None =>
        Probabilite(tabProba + (s -> 1))
/*
  def getBestProba(): String =
    def getBestProbaAux(tab: Map[String,Int]): String = tab match
      case (x -> y) :: z => "s"*/