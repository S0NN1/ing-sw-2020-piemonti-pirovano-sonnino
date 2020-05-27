# A Java implementation of Santorini
### Software Engineering Final Test
### AA 2019/2020
## Description
Final test of "Software Engineering", course of "Computer Science Engineering" held at "Politecnico di Milano".
## The Team
Our working group is composed by three students:
* Alice Piemonti
   * Person Code: 10572806
   * Number: 888585
* Luca Pirovano
    * Person Code: 10569272
    * Number: 888838
* Nicolò Sonnino
    * Person Code: 10569180 
    * Number: 890940
## Project specification
The project consists of a Java version of the gameBoard game *Santorini*, made by Cranio Creations.
You can find the full game here: https://bit.ly/2Qi7gs9

The final version must include:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* source code of the implementation;
* source code of unity tests.

## Implemented Functionalities
| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket |[![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/server) |
| GUI | [![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#) |
| CLI |[![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#)|
| 5 Advanced Gods | [![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#) |
| Persistence | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Undo Function | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

#### Legend
[![RED](https://placehold.it/15/f03c15/f03c15)](#) Not Implemented 

[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#) Implementing 

[![GREEN](https://placehold.it/15/44bb44/44bb44)](#) Implemented

<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

## Compile sources
In order to compile the Java classes and resources and include the full 
dependency list, you need to have the maven framework installed.
You don't have it? 
- Download it here: https://maven.apache.org/download.cgi
- [WIN] Add the enviromental variables following this guide: https://www.tutorialspoint.com/maven/maven_environment_setup.htm
Once you have maven installed, navigate to the project home directory (whereas
the POM.xml file is located) and execute the following commands:
```
- mvn clean     (to clean previously compilation leftovers)
- mvn package   (to build the new version of the project)
```
You can also run a one line command, just like that:
```
- mvn clean package (to clean and then build the package)
```
A new folder called "**target**" will be created in the project home directory,
inside it you will find the jar file, which already includes the project dependencies.

## Run the application
In order to run the application you need

## Test cases
