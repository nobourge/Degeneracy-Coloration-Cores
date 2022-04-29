Utilisation des algorithmes d'opérations sur graphes :


Formattage des fichiers de graphe :
Les fichiers des graphes doivent suivre cette logique :
- Chaque ligne est une arête qui rejoint 2 sommets
- Chaque ligne est formatté de la manière suivante:
[sommet 1] [sommet 2]
Entre les deux sommets est un caractère de délimitation. Dans cet exemple, le caractère de délimitation est espace.
Enfin, les fichiers doivent commencer par une ligne comme celle-ci : "-1 [nombre de sommets dans le graphes]".



1. Dégénérescence :


2. Coloration :
	1- compiler le fichier avec la commande "javac WFC.java"
	2- lancer avec la commande "java WFC -f -c "
		-f étant le nom du fichier texte contenant les données du graphe
		-c étant le caractère de délimitation qui sépare les 2 numéros de sommets à chaque ligne. Il est probable qu'il faille l'entourer de "".

	
3. Coeurs :