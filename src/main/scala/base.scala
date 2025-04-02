import scala.collection.immutable.List

object base:
  def transformString(s: String): List[String] = s.split(" ").toList

object abreTempoObj:
  class Node[A](children: Map[Char, Node[A]] = Map(), value: Option[A] = None):
    def addNode(key: Char, grandChildrens: Node[A]): Node[A] = Node[A](children + (key -> grandChildrens), value)

    def changeOption(newValue: Option[A]): Node[A] = Node[A](children,newValue)

    def copy(children: Map[Char, Node[A]] = children, value: Option[A] = value): Node[A] =
      Node(children, value)
    
    def constructTrieGeneriqueTempo(l: List[String]): Map[Char, Node[String]] = {

      def insertWord(root: Map[Char, Node[String]], word: List[Char], nextWord: Option[String]): Map[Char, Node[String]] = {
        word match {
          case Nil => root
          case x :: xs =>
            insertWordHelper(root, x, xs, nextWord)
        }
      }

      def insertWordHelper(root: Map[Char, Node[String]], x: Char, xs: List[Char], nextWord: Option[String]): Map[Char, Node[String]] = {
        root.get(x) match {
          case Some(childNode) =>
            root + (x -> (if (xs.isEmpty) childNode.changeOption(nextWord) else childNode.addNode(xs.head, childNode.copy(children = insertWord(childNode.children, xs, nextWord)))))///TODO le .children ne marche pas ? 
          case None =>
            root + (x -> (if (xs.isEmpty) Node(Map(), nextWord) else Node(Map(xs.head -> Node(insertWord(Map(), xs, nextWord))))))
        }
      }

      def constructAux(words: List[String], acc: Map[Char, Node[String]]): Map[Char, Node[String]] = {
        words match {
          case Nil => acc
          case x :: xs =>
            constructAux(xs, insertWord(acc, x.toList, xs.headOption))
        }
      }

      constructAux(l, Map())
    }


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