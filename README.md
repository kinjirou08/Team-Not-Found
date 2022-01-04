# Team-Not-Found

## Case Clothes

### About The Project

A shopping application utilizing https://fakestoreapi.com/ as an external API and it’s stored fake products. The shopping application will two main users, An admin, and the customers. The shopping application could also Insert or Add a new product to the current, update an existing product, and delete an existing product. There will also be a Cart system where a customer could put the product they chose, update its quantity, and remove a product if they don’t want the product. And lastly, the application would be able to do a transaction where, after buying all the products, all the quantity of the product would be reduced, and every transaction will persist in a database.

## Technologies Used

* Spring Framework
* Hibernate
* DBeaver version 21.1.5
    * PostgreSQL 10 version 42.2.18
* Angular
* AWS EC2
* SonarCloud.io
    * JaCoCo version 0.8.7 (For Code Coverage)
* Bulma version 0.9.3

#### Fro Testing
* JUnit 5 (Jupiter) API version 5.8.1
* Mockito version 4.0.0
* Selenium version 4.1.0
* Cucumber version 7.1.0

## Features

### Admin Features

* Successfully logging in
* Successfully Loggin out
* Displaying all the the products from the system
* Addding a Product is functional
* Updating a product from the system is functional
    * Works on Postman/Not implemeneted on the frontend
* Deleting  a product from the system is functional
    * Works on Postman/Not implemeneted on the frontend
* Adding a new Admin user is functional
    * Works on Postman/Not implemented on the frontend

#### Improvement/s to be implemented:
* Implementation of other features in frontend (Angular)

### Customer Features

* Successfully logging in
* Successfully Loggin out
* Create a new account
* Adding a new product to the cart is functional
* Updating the quantity of the product in the cart is functional
    * Works on Postman/Not implemeneted on the front-end
* Removing an existing product in the cart is functional

#### Improvement/s to be implemented:
* Purchase items in cart
* Clear cart after purchase

### Signup Feature
#### Improvement/s to be implemented:
* Password Hashing

### Login Feature
* Storing Sessions


## Getting Started

The repository is set as public and anyone could clone it for their production use.

* Use this command, specifically in Git Bash to clone the repository
    * ```git clone git@github.com:kinjirou08/Team-Not-Found.git```

    * this will automatically create a main/master branch into your local machine

* To start up development, either create a new branch or continue working on the main branch
    * It is recommended to make a new branch when working on a feature, this is to comply to CI/CD pipeline.
    * To create a new branch simply use this command:
        * ```git branch <branch-name>``` then ```git checkout <branch-name>``` OR
        * ```git checkout -b <branch-name>``` this is a shortcut for the two commands above.

* To save changes in the development on the current branch:
    * ```git status``` to check all the changes
    * ```git add . ``` to add/save all the changes OR
    * ```git add <file>``` to add/save a specific file
    * ```git status``` again to check if all the changes have been saved
    * ```git commit -m "summary of all the changes you did" ```
    * ``` git push origin <branch-name>``` to push all the changes onto the remote branch

* To merge a branch into the main:
    * first check if you're on a working branch by using ```git branch```
    ![This is an image](assets/images/git-branch-example.PNG)
    * the green text indicates that you're on that branch currently.
    * Another indicator to know which branch you're on is the cyan-colored text at the upper-right corner of your Git Bash.
    * So if you're not on the main branch, do ```git checkout main```, then do ```git merge <branch-name>``` to merge that branch to the main branch
        * if a merge conflict occurs, either open up your IDE to fix those conflicts, or open up Visual Studio Code and open Source Control to see the conflicts.
        * After fixing the conflicts, do ```git add .``` then do ```git commit``` then do ```git push```

## Contributors
* Jymm Enriquez
* Tanveer Signh
* Allexa Hernandez
* Cory Hall
* Alemu Robele
