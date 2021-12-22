# Vie Artificielle

## Présentation

Dans le cadre de l’unité d’enseignement Projet de Vie Artificielle et Jeux Systémiques
(2I013-P4) de la faculté Pierre et Marie Curie (Sorbonne Université), nous avons été amené à
développer la simulation d’un monde artificiel abritant une forme de vie adaptative devant faire face
à des défis environnementaux dynamiques. En outre, nous avons dû concevoir un écosystème
équilibré et durable. Dans le document **Rapport-ROSE_BAKA.pdf**, nous vous présenterons dans un premier temps les fonctionnalités relatives au
terrain que nous avons décidé d’implémenter ainsi que la manière dont nous nous y sommes pris.
Ensuite, nous ferons de même pour les fonctionnalités relatives aux agents peuplant notre
écosystème.

## Installation

Pour lancer notre monde artificiel, vous aurez besoin de Eclipse. La version que nous
utilisons est : Oxygen.2 Release (4.7.2).
Téléchargez l'ensemble du répertoire github, et placez l’archive où bon vous semble.
Décompressez-la.
Supprimez les dossiers suivants :
- /projet-2I013-master/Images,
- /projet-2I013-master/javaImages,
- /projet-2I013-master/applications,
- /projet-2I013-master/cellularautomata,
- /projet-2I013-master/graphics,
- /projet-2I013-master/interfaces,
- /projet-2I013-master/landscapegenerator,
- et enfin /projet-2I013-master/objects.
- 
Téléchargez le backup local de JOGL : http://pages.isir.upmc.fr/~bredeche/Teaching/2i013/2018-
2019/JOGL.zip

Décompressez cette archive à la racine du projet, dans un dossier que vous créerez, nommez-le
javalib.

Ouvrez eclipse. Suivez le tutoriel Tutorial-javaOpenGL.pdf fourni, mais ignorez la partie nommée
"Ajouter JOGL à votre projet".

Suivez plutôt les instructions suivantes :

Importez dans Eclipse le dossier /projet-2I013-master/3D/WorldOfCells en tant que projet. (file-
>import->projects from folder)
>
Dans Eclipse, cliquez droit sur le projet (le dossier bleu avec la lettre J) dans le panneau à gauche, et
sélectionnez Build Path -> Add Library... . Dans la fenêtre, sélectionnez ‘User Library’ puis cochez
‘jogl-2.0’. Cliquez sur Finish.

JOGL est maintenant lié à votre projet.
**TRES IMPORTANT : dans eclipse, supprimez du projet le package
landscapegenerator.ca_landscape**
Des avertissements s'affichent ça et là : ignorez-les.
Ouvrez le fichier applications.simpleworld.MyEcosystem
et lancez-le.
