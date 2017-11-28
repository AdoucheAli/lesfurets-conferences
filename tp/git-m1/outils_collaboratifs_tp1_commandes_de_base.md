### Le working directory (espace de travail, `ls`)

![working dir](https://git-scm.com/book/en/v2/images/areas.png)

### Le dépôt git (base de donnée .git, `git`)

![repository](https://git-scm.com/book/en/v2/images/snapshots.png)

### Le commit (`git commit`)

![commit](https://git-scm.com/book/en/v2/images/lifecycle.png)

### Les branches

![branches](https://git-scm.com/book/en/v2/images/branch-and-history.png)
Les commit dans git contienne votre nom et mail, ce sera important pour le TP2

Pour démarrer un nouveau dépôt, utiliser `git init`, puis `cd` pour entrer dans le répertoire créé par git.

Utiliser `echo` pour créer 3 nouveaux fichiers avec comme contenu une ligne "Ligne 1"

Avec `git status`, git vous montre qu'il y a 3 nouveaux fichier dans votre répertoire de travail qui sont "untracked" ou inconnu.

```
Pour ajouter ces fichiers au "staging", il faut utiliser la commande `git add`. Attention: cela n'ajoute pas le fichier à git, pour se faire il faut faire un "commit".

La commande `git status` vous montre l'état de votre dépôt

```bash
```

Le "staging" est la partie notée par "changes to be commited". Pour créer un nouveau commit, utiliser la commande `git commit`. À partir du moment ou un fichier est dans un commit, il est persisté à jamais dans git.

```bash
git commit
# [master (root-commit) 8e8630d] Mon 1e commit
#  1 file changed, 1 insertion(+)
#  create mode 100644 fichier1
```
Il est aussi possible de passer directement le message de commit avec `-m`, c'est la syntaxe que nous utiliserons à partir de maintenant

```bash
**TODO:** Ajouter fichier2 et fichier3 dans un 2e commit, jusqu'à ce que git affiche "working tree clean", ce qui veut dire que le dépôt git contient tous les fichiers qui sont dans votre répertoire de travail.
Vous avez déjà fait 2 commits et la commande `git log` vous permet de les voir

L'option `--name-status` donne la liste des fichiers modifiés (le A préfixé veut dire "added", vous avez aussi M pour "modified" et D pour "deleted")

La commande `git show` permet de voir le contenu des modifications, les lignes ajoutées sont préfixées d'un +, les lignes supprimées sont préfixées d'un - (ce sont les seules modifications possibles)

Le paramètre HEAD veut dire "commit courant"

La syntaxe `HEAD~1` permet de revenir un commit en arrière (HEAD est le commit courant, HEAD\~1 est le commit précédent, HEAD\~2 est le commit d'avant, etc.)

On ajoute une ligne aux fichier1 et fichier2 avec `echo`

La commande `git diff` permet de voir les modifications qu'on a fait (utile avant le commit), c'est le même affichage que la commande `git show` de l'exercice précédent.

On ajoute le fichier1 dans un nouveau commit pour l'exercice suivant

![commits](https://git-scm.com/book/en/v2/images/branch-and-history.png)


Utiliser `git reset` pour supprimer le commit (attention : cette opération parfois est réversible, mais avec des commandes difficiles à utiliser, éviter l'usage)

# HEAD is now at 3012558 poubelle

# fichier1  fichier2  fichier3  poubelle
```

On est de retour sur le commit avec le fichier "poubelle"

```bash
# HEAD is now at 59c88f8 Mon 3e commit

# commit 59c88f86b6b64c6f016bb6e078d520d89826dfb7
# Author: Alexandre DuBreuil <adu@lesfurets.com>
# Date:   Mon Nov 13 17:41:17 2017 +0100
# 
#     Mon 3e commit
#  ...
On est bien revenu sur le 3e commit

Les branches permettent de développer des nouvelles fonctionnalités de manière indépendante, la création se fait avec la commande `git branch`

Vous pouvez les voir comme des copies de votre espace de travail, entre lesquelles vous pouvez basculer rapidement, avec la commande `git checkout`

git branch develop
#   develop
# * master

git checkout develop
# Switched to branch 'develop'
git branch
# * develop
#   master
```

Pour l'instant, les 2 branches sont identiques, mais les modifications de l'une ne vont pas impacter l'autre.

On ajoute un fichier "fichierdev" dans la branche courante, soit "develop", dans un nouveau commit

```bash
echo "Contenu dev" >> fichierdev
git add fichierdev
# [develop c1fa6e3] Nouveau fichier dev 1
#  1 file changed, 0 insertions(+), 0 deletions(-)
#  create mode 100644 fichierdev
```

On remarque que ce commit est présent dans la branche courante "develop" mais pas dans la branche "master"

```bash
git log --oneline
# c1fa6e3 Nouveau fichier dev
# 59c88f8 Mon 3e commit
# 888f4ec Mon 2e commit
# 8e8630d Mon 1e commit

git log --oneline master
# 59c88f8 Mon 3e commit
# 888f4ec Mon 2e commit
# 8e8630d Mon 1e commit
```

Ce qui veut dire que le nouveau fichier "fichierdev" est présent dans la branche "develop", mais pas dans master

```bash
ls
# fichier1  fichier2  fichier3  fichierdev

# Switched to branch 'master'

# fichier1  fichier2  fichier3
Le fichier "fichierdev" n'est pas présent dans votre répertoire de travail

La branche "develop" a plus de commit que la branche master, comment fait-on pour rassembler l'historique ?

On crée un nouveau commit sur la branche "master", on aura donc 2 branches divergeantes comme dans cet exemple (remplacer "testing" par "develop")

![split](https://git-scm.com/book/en/v2/images/advance-master.png)

# [master 509f5a4] Nouveau fichier master
#  1 file changed, 0 insertions(+), 0 deletions(-)
#  create mode 100644 fichiermaster
```

Pour fusionner l'historique on utilise la commande `git merge`, en passant comme argument l'autre (ou les autres) branche à fusionner

```bash
git merge --no-edit develop
# Merge made by the 'recursive' strategy.
#  fichierdev | 0
#  1 file changed, 0 insertions(+), 0 deletions(-)
#  create mode 100644 fichierdev

git log --oneline --graph --decorate
# *   f5cf4f0 (HEAD -> master) Merge branch 'develop'
# |\  
# | * c1fa6e3 (develop) Nouveau fichier dev
# * | 509f5a4 Nouveau fichier master
# |/  
# * 59c88f8 Mon 3e commit
# * 888f4ec Mon 2e commit
# * 8e8630d Mon 1e commit

On voit que l'historique des branches s'est séparé, puis fusionné en avec un commit de merge (le commit nommé "Merge branch 'develop'")

![merge](https://git-scm.com/book/en/v2/images/basic-merging-2.png)

## Exercice 9 : conflit de merge

Lors de la fusion de branche, les conflits sont possibles si les deux branches contiennent une modification sur le même fichier, et sur la même ligne. Si c'est le cas, git marque les conflits dans le fichier en question, et il faut les corriger, puis compléter la fusion.

Sur la branche "master", on enlève le commit de merge précédemment créé, pour en refaire un nouveau.

```bash
git reset --hard HEAD~1
```

On ajoute du contenu au fichier "fichierdev", pour rappel, un fichier de même nom est dans la branche "develop"

```bash
echo "Contenu master" >> fichierdev
git commit --amend --no-edit fichierdev
# [master 1ebf23b] Nouveau fichier master
#  Date: Mon Nov 13 18:43:50 2017 +0100
#  2 files changed, 2 insertions(+)
#  create mode 100644 fichierdev
#  create mode 100644 fichiermaster
```

On a maintenant :

- Dans la branche "master", le contenu de "fichierdev" est "Contenu master"
- Dans la branche "develop", le contenu de "fichierdev" est "Contenu develop"

Lorsqu'on fait le merge, git ne sait pas quelle version prendre

```bash
git merge develop
# Auto-merging fichierdev
# CONFLICT (add/add): Merge conflict in fichierdev
# Automatic merge failed; fix conflicts and then commit the result.

cat fichierdev
# <<<<<<< HEAD
# Contenu master
# =======
# Contenu dev
# >>>>>>> develop
```

Le contenu de "fichierdev" a été modifié par git, il affiche entre les chevrons <<< et le égal === le contenu de HEAD (donc la branche "master"), et entre le égal et les chevrons >>> le contenu de la branche "develop".

C'est au développeur de choisir quelle version il veut ! Éditez le fichier pour garder que la ligne "Contenu master" ou "Contenu develop"

Puis il faut dire à git que la résolution de conflit est terminée en ajoutant le fichier, puis en faisant un commit

```bash
git add fichierdev
git commit --no-edit
# [master 982ac41] Merge branch 'develop'
```
