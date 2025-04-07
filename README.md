# Projet 3 scala - Autocomplétion de texte (Loïs Odiardo - Nyx Gonzalvez)

## Lancer le projet

- Pour créer un dictionnaire de texte il suffit d'appeler la méthode de l'objet base :

```SCALA
def constructDico(s: String): Node[Probabilite]
```

- Pour ensuite récupérer le prochain mot le plus probable il faut mettre en entrée le mot précédent dans la méthode de
  l'objet base :

```SCALA
def getNextWordInDico(s: String, dico: Node[Probabilite]): String
```

## Choix de conception

Pour la node générique, décision a été prise de ne pouvoir rajouter des mots que si associés à une valeur (on peut
rentrer une valeur None mais il faut qu'à l'utilisation de la méthode on spécifie toujours une option à Some ou None).
Quand on cherche à récupérer 
