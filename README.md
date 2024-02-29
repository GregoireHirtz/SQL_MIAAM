# SQL_MIAAM

## Specification technique
- SGBD : MySQL
- Langage : java 17
- Driver JDBC : mysql-connector-j-8.3.0


## Description conception

L'application est décomposée en plusieur package :

- **activeRecord** : contient les classes qui permettent de faire le lien entre les objets et la base de données.
- **api** : la classe qui a les acces admin sur la base pour connecter l'application avec la base de données selon le compte utiliser
- **app** : les classes principales de l'application
- **bd** : la classe qui permet de communiquer avec la base de données

L'idée génrale  est d'avoir une boucle qui demande à l'api une instance de la classe Bd, 
instance utilisant un compte serveur ou serveur gestionnaire selon le grade 
de l'utilsiateur. Par la suite, on utilisera cette instance pour communiquer avce la base de données.
Ainsi, on peut imaginer deporter l'execution de la classe Api et ainsi ne pas avoir les identifiants 
administrateur dans le code de l'application, mais sur une serveur distant.
Une fois conecté, l'applciation utilisera des methodes dans les classes de l'active record pour
effectuer des operations sur la base de données.

### Acces par compte
- **serveur** : permet de voir et faire des reservations, voir les plats disponibles et commander des plats
  - **serveur gestionnaire** : permet de faire en plus du serveur simple, consulter et affecter des serveurs a des tables, 
et encaisser les paiements des reservations


## Description des fonctionnalités

### Consulter les tables disponibles
Pour faire cela, on demande la date à l'utilisateur, et on l'utilise avec cette commande sql
```sql
SELECT * FROM tabl WHERE numtab NOT IN 
    (SELECT numtab FROM reservation 
    WHERE DATE_SUB(datres, INTERVAL 2 HOUR) < ? 
    AND ((datpaie IS NULL AND DATE_ADD(datres, INTERVAL 2 HOUR) > ?) 
    OR (datpaie IS NOT NULL AND datpaie > ?))
    )
```

avec cette commande, on obtient les tables disponibles à la date donnée par l'utilisateur en garantisant que la table 
n'est pas déjà reserve dans les 2 heures qui suivent la date donnée par l'utilisateur. Pour finir, si une reservation est payée,
alors l'interdiction de reserver la table est levée apres la date de paiement.
Pour faire cela, je ne bloque pas la table car juste une lecture simple qui pose peu de probleme (probleme de mise a jour négligable).

#### Exemple

Si j'ai une reservation à 20h, je ne peux pas reserver une table à 20h, mais je peux reserver la table de 18h à 22h.
En effet, cela permet de garantir 2 heures de libres avant une nouvelle reservation. 


## Réservation d'un table

Pour reserver une table, on demande à l'utilisateur de rentrer les informations suivantes :
- la date de la reservation
- le numéro de la table
- le nombre de personnes

Verification effectue avant l'applciation de la reservation:
- conformiter de la date
- num table fourni existe
- nombre de personnes <= nbmaxpers de la table
- la table est disponible à la date donnée

Une fois la date verifie je bloque les tables tabl et reservation
pour empêcher les autres utilisateurs de reserver la table en même temps que moi.

Si tout est bon, on ajoute la reservation dans la base de données.


## Consulter les plats disponibles

Pour faire cela, je recupère juste les plats en bd et je les affiche à l'utilisateur.
Mais j'affiche uniquement avec une quantité disponible > 0.


## Commander des plats

Pour commander des plats, on demande à l'utilisateur de rentrer les informations suivantes :
- le numéro de la reservation
- le numéro du plat
- la quantité

On vérifie :
- que la reservation existe
- que la reseration n'est pas deja payée
- que le plat existe
- que la quantité est disponible

Si tout est bon, on creer une Objet Commande et on le sauvegarde dans la bd.
Si l existe deja dans la bd, on fait une maj sinon on fait une insertion


# Améliorations possibles
- Utiliser un serveur distant pour la gestion des comptes
- mieux gérer les erreurs
- utiliser mieux le pattern active record


