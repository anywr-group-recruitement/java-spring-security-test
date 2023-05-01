[French version](README_FR.md)

# Java Spring Test

The Anywr Java Spring development test is intended to evaluate your problem-solving skills, general knowledge of Java, Spring and application development principles, and overall approach to development. The test will cover a range of topics including theory, practical application, troubleshooting, database design, testing, and critical thinking.

## Resources

Feel free to use any resources you need while completing this test. Whether it's searching Google, GitHub, GitLab, or even ChatGPT, go ahead. This is an "open resource" test, and we understand that real programmers look things up when necessary. We believe that using resources is a good thing, and we don't consider it a weakness. Of course, if you don't need to consult any resources, that's great too. we just ask that you list any resources you consulted in the README file.

## Exercice

The main idea is to focus on authentication and authorization using Java Spring Boot and Postgresql DB as per the below:

Project name on github or gitlab repository should be as following firstName_LastName_GeneratedRandom:
- FirstName: developer first name
- LastName: developer last name
- GeneratedRandom: provided by this link: Markup : [Generate Random](https://www.gigacalculator.com/randomizers/random-alphanumeric-generator.php) with following criteria:
![image](https://user-images.githubusercontent.com/92299902/234573178-87e00eed-22d6-492c-8ebb-ebc08463bde3.png)

Create a RESTful API using Java Spring Boot that implements a user authentication and authorization system. The API should have the following features:

- Allow users to register by providing a unique username and password.
- Allow users to log in by providing their username and password.
- Allow users to retrieve their own profile information, such as their username and email address. (Optional if you have time)
- Use JSON Web Tokens (JWT) to authenticate and authorize API requests.
- User log in should be reading from application_user table

Create a database structure on the following business demands:

-	School management that contains students' structure
-	Students are related to one class only
-	Only one teacher per class
-	The student should contain the following information: ID, First Name, Last Name
-	The teacher should contain the following information: ID, First Name, Last Name
-	The class should contain the following information: ID, name


In addition, create a RESTful API with the following:

-	Secure API after login using JWT generated token.
-	Get the list of students with the following:
    - Filters: Class Name and/or Teacher Full Name
    - All Students list will be returned in case of no filters value
    - Paginated
-	Unit testing (Optional if you have time)

## Code delivery
- Make sure to use GIT, and to commit your changes following Angular git commit conection https://www.conventionalcommits.org/en/v1.0.0-beta.4/
- Push to your account on github
- Once finished, allow developer access for username anywr-group-recruitement
- Update the recruitement team with the link of your project.
