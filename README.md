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
On considère que la base de l'arbre est une node classique (qui ne possède probablement pas de valeur mais il pourrait
tout de même exister le cas où l'on souhaite associer la clé "" à une valeur)

Quand on cherche à récupérer une valeur, on donne le mot clé de la valeur qui nous intéresse en entier puis on utilise
une méthode privée de la node une fois décomposé en liste de Char ; le but étant que dans un usage classique on ait
juste à passer une clé sans transformation préalable. La méthode privée permet d'appeler récursivement sur les nodes
enfants jusqu'à atteindre la valeur qui nous intéresse. On récupère forcément une option en cas de non présence de la
valeur pour permettre un comportement adaptatif.

Dans la méthode d'ajout dans le tri le choix a été fait de permettre l'insertion d'une fonction pour adapter la manière
dont les valeurs doivent être stockées selon l'usage. On a donc la liberté avec le même objet d'interdire de remplacer
un objet Some() , ou la liberté d'utiliser une méthode d'ajout custom. (On pourrait comme ça imaginer un usage du tri
avec un objet Personne où une seule suite de lettre correspond à une personne, et donc si une personne existe déjà on ne
fait rien, ou nous pourrions imaginer un tri d'int avec chaque suite de lettre correspondant à un id unique de compte
client-e, et chaque insertion de node faisant une simple addition de la valeur si Some de ces int pour stocker les
points de fidélité.) Dans l'objet base correspondant à notre implémentation de dico avec prochain mot le plus probable,
la méthode constructProba sert ce rôle.

La méthode getNextWordInDico ou getBestProba partent du principe que si il n'y a pas de valeur, on renvoit une chaîne de
caractères vide.

La méthode transformString est à part pour permettre une compléxification de la regex / de la méthode de transformation
en liste au besoin. Dans notre usage du dico, on considère que seuls les espaces séparent les mots, et tout autre
caractère fait partie ou est un mot à part entière. Si on a par exmple à la fin d'une phrase rentrée dans notre dico "
levait. Il levait" nous aurons donc dans notre dico "levait." et "levait" comme deux mots différents (l'un étant suivi
d'un mot avec majuscule, l'autre non ; nous ne sommes pas sur les mêmes probabilités de mots)

La fonction de génération de texte nécessite tout de même un préfixe, on ne peut pas générer à partir de rien ; un
texte nécessite de vrais mots pour être considéré comme du texte : "" n'est pas considéré comme un mot en langue
française. Si on atteint une chaîne de caractères vide on s'arrête. Si on atteint le maximum de n-mots défini en
paramètres on s'arrête aussi.

Pour les probas, on utilise un objet à part entière au lieu d'une map pour y intégrer des méthodes directement, et ne
pas les intégrer en déhors du contexte. On peut donc aussi réutiliser cet objet probabilité comme un objet en dehors du
contexte du dico. On considère que la probabilité la plus élevée est celle avec le plus grand int ; on ne calcule pas de
pourcentage par non nécessité et simplicité.