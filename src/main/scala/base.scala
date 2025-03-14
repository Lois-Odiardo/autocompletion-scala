import scala.collection.immutable.List

object base:
  def transformString(s: String): List[String] = s.split(" ").toList

  //TODO temporaire
  def constructTrie(): Nothing = ???

  //TODO temporaire
  def verifyProbable(): Nothing = ???

object abreTempoObj:
  //TODO refactor
  class Node[A](children: Map[Char, Node[A]] = Map(), value: Option[A] = None):
    def addNode(key: Char, grandChildrens: Node[A]): Node[A] = Node[A](children + (key -> grandChildrens), value)

    def changeOption(newValue: Option[A]): Node[A] = Node[A](children,newValue)

  //TODO on fait en générique on verra le reste après, temporaire
  def constructTrieGeneriqueTempo[A](l: List[String], nodes: A): Map[Char, Node[A]] =
    def constructAux(mapTest: Map[Char, Node[A]], l2: List[String]): Map[Char, Node[A]] =
      def constructAuxAux(mapTest: Map[Char, Node[A]], l3: List[Char]) = 
        l3 match
          case Nil => mapTest
          case x :: xs => if !mapTest.contains(x) then Map() else Map() //TODO

      l2 match
        case Nil => mapTest
        case x :: xs => constructAux(constructAuxAux(mapTest, x.toList), xs)

    constructAux(Map(), l)

  def constructTrieApres(l: List[String]): Map[String, Node[String]] = ???

/*
object Trie :

// La structure du Trie, chaque élément est une liste d'éléments de type TrieNode
def insert(trie: List[TrieNode], prevWord: String, nextWord: String): List[TrieNode] = {
  // Chercher si le mot précédent existe déjà dans la liste des noeuds
  val nodeOpt = trie.find(_.word == prevWord)

  nodeOpt match {
    case Some(node) =>
      // Si le noeud existe, on retourne une nouvelle liste avec un noeud mis à jour
      (new TrieNode(prevWord, node.nextWordCounts :+ (nextWord, 1))) :: trie.filterNot(_.word == prevWord)
    case None =>
      // Si le mot précédent n'existe pas, on crée un nouveau noeud
      new TrieNode(prevWord, List((nextWord, 1))) :: trie
  }
}

// Calcule les probabilités des mots suivants pour un mot donné
def getNextWordProbabilities(trie: List[TrieNode], word: String): List[(String, Double)] = {
  // Trouver le noeud correspondant au mot
  trie.find(_.word == word) match {
    case Some(node) => node.getProbabilities
    case None => List() // Aucun mot suivant trouvé
  }
}


class TrieNode(val word: String, val nextWordCounts: List[(String, Int)]) {

// Calcule les probabilités des mots suivants
def getProbabilities: List[(String, Double)] = {
  val total = nextWordCounts.map(_._2).sum.toDouble
  if (total == 0) List()
  else nextWordCounts.map {
    case (word, count) => (word, count / total)
  }
}
}
 */