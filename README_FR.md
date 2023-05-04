[Version anglaise](README.md)

# Java Spring Test

Le test de développement Java Spring Anywr a pour but d'évaluer vos compétences en résolution de problèmes, vos connaissances générales en Java, Spring et les principes de développement d'applications, ainsi que votre approche globale du développement. Le test portera sur différents sujets tels que la théorie, l'application pratique, le dépannage, la conception de bases de données, les tests et la pensée critique.

# Nom du projet

Veuillez créer un dépôt GitHub avec un nom qui doit être comme suit: firstName_LastName_GeneratedRandom :
- FirstName : prénom du développeur
- LastName : nom de famille du développeur
- GeneratedRandom comme asd3sf123d

## Resources

N'hésitez pas à utiliser toutes les ressources dont vous avez besoin pour réussir ce test. Que ce soit la recherche sur Google, GitHub, GitLab, ou même ChatGPT, allez-y. Il s'agit d'un test de "ressources ouvertes", et nous comprenons que les vrais programmeurs cherchent des informations quand cela est nécessaire. Nous pensons que l'utilisation de ressources est une bonne chose et nous ne considérons pas cela comme une faiblesse. Bien sûr, si vous n'avez pas besoin de consulter de ressources, c'est très bien aussi. Nous vous demandons simplement de répertorier toutes les ressources consultées dans le fichier README.

## Exercice

### Créez une structure de PostgreSQL base de données en fonction des exigences commerciales suivantes.

- Gestion scolaire qui contient la structure des étudiants
- Les étudiants sont liés à une seule classe
- Un seul enseignant par classe
- L'étudiant doit contenir les informations suivantes: ID, Prénom, Nom de famille
- L'enseignant doit contenir les informations suivantes: ID, Prénom, Nom de famille
- La classe doit contenir les informations suivantes: ID, nom (unique)

### De plus, créez une API RESTful en utilisant Java Spring Boot avec les éléments suivants :

- Sécurisez l'API après la connexion à l'aide du jeton généré par JWT à l'aide de l'intercepteur et pour vérifier dans la table DB application_user qui contient les balises suivantes: "username" et "password".
- Obtenir la liste des étudiants avec les éléments suivants :
  - Filtres : Nom de la classe et/ou Nom complet de l'enseignant
  - Tous les étudiants de la liste seront retournés en cas d'absence de valeur de filtres
  - Paginé
- Tests unitaires (facultatif si vous avez le temps)

## Livraison du code
- Assurez-vous d'utiliser GIT, et de commiter vos modifications en suivant la convention de commit git Angular https://www.conventionalcommits.org/en/v1.0.0-beta.4/
- Push sur votre compte sur github
- Une fois terminé, accordez l'accès développeur pour le nom d'utilisateur anywr-group-recruitement
- Mettez à jour l'équipe de recrutement avec le lien de votre projet.
