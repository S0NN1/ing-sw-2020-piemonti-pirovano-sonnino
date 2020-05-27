# Santorini Board Game
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/blob/master/LICENSE)
![latest commit](https://img.shields.io/github/last-commit/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino?color=red)
<img src="https://images-na.ssl-images-amazon.com/images/I/91irtho0CNL._AC_SL1500_.jpg" width=192px height=192 px align="right" />

Santorini Board Game is the final test of **"Software Engineering"**, course of **"Computer Science Engineering"** held at Politecnico di Milano (2019/2020).

## Project specification
The project consists of a Java version of the gameBoard game *Santorini*, made by Cranio Creations.
You can find the full game [here](https://bit.ly/2Qi7gs9).

The final version must include:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* source code of the implementation;
* source code of unity tests.

## Implemented Functionalities
| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/model) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/model) |
| Socket |[![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/server) |
| GUI | [![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/client/gui) |
| CLI |[![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/client/cli) |
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/blob/master/src/main/java/it/polimi/ingsw/server/Server.java)|
| 5 Advanced Gods | [![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/model/player/gods/advancedgods) |
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

## System Requirements
- Linux, MacOS or Windows OS with an active terminal
- Java SE JDK 14 (OracleJDK or OpenJDK) with enviroment variables set
- Maven framework version 3.0 (or newer)

**WARNING:** For the best CLI experience, we suggest using a native linux terminal with a font size
set to 12 (or below), a line spacing set to 0.9 (or below) and a screen resolution of 1920x1080.
The terminal should run in fullscreen mode or maximized window mode.
If these requirements are not met we cannot ensure a high quality CLI experience.

## Compile sources
In order to compile the Java classes and resources and include the full 
dependency list, you need to have the maven framework installed.

You don't have it? 
- Download it here: https://maven.apache.org/download.cgi
- [WIN] Add the enviromental variables following this guide: https://www.tutorialspoint.com/maven/maven_environment_setup.htm


Once you have maven installed, open a terminal and navigate to the project home
directory (where the POM.xml file is located); execute the following commands:
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
In order to run the application you need to have the Java SE JDK 14 installed.

You don't have it? Follow this guide: https://docs.oracle.com/en/java/javase/14/install/index.html

Once you met all the requirements, open a terminal and go to the project target
directory (which has to be previously built with maven). Once there, execute these
commands:
```
- java -jar GC01-<version>-jar-with-dependencies.jar
```
You'll have to choose if you want to launch server, CLI client or GUI client by
typing the option number on your keyboard.

In order to play, you'll have to launch at least one server and two clients 
(either CLI or GUI).

### Troubleshooting
There are common problems that you could encounter while launching the jar, like:

- "java is not recognized as an internal or external command"

    - java is not set in the enviromental variables. Please follow this guide
    in order to fix the issue: https://www.java.com/en/download/help/path.xml

- "unable to access jarfile <file-name>"
    
    - the entered file path is not correct
    - you have not build the project before launching it      

## Test cases
All tests in model and controller has a classes coverage at 100%.

**Coverage criteria: code lines.**

| Package |Tested Class | Coverage |
|:-----------------------|:------------------|:------------------------------------:|
| Controller | ActionController | 
| Controller | Controller | 
| Controller | GodSelectionController | 45/51 (88%)
| Controller | TurnControllerTest | 
| Model | Global Package | 

## The Team
Our working group is composed by three students:
* Alice Piemonti
* Luca Pirovano
* Nicolò Sonnino

## Copyright and license

Santorini Board Game is copyright 2020.

Licensed under the **[MIT License](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/blob/master/LICENSE)** ;
you may not use this software except in compliance with the License.


[license]: (https://opensource.org/licenses/MIT)
