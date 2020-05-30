# Santorini Board Game
[![License: MIT][license-image]][license]
![latest commit](https://img.shields.io/github/last-commit/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino?color=red)
<img src="https://images-na.ssl-images-amazon.com/images/I/91irtho0CNL._AC_SL1500_.jpg" width=192px height=192 px align="right" />

Santorini Board Game is the final test of **"Software Engineering"**, course of **"Computer Engineering"** held at Politecnico di Milano (2019/2020).

## Project specification
The project consists of a Java version of the board game *Santorini*, made by Roxley Games.

You can find the full game [here](https://roxley.com/products/santorini).

The final version must include:
* initial UML diagram;
* final UML diagram, generated from the code by automated tools;
* working game implementation, which has to be rules compliant;
* source code of the implementation;
* source code of unity tests.
## Find out more

| **[Installation][installation-link]**     | **[Compiling][compiling-link]**     |    **[Running][running-link]**       | **[Javadocs][javadocs]** | **[Troubleshooting][troubleshooting-link]**
|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|-------------------------------------|
| [![i1][installation-image]][installation-link] | [![i2][compiling-image]][compiling-link] | [![i4][running-image]][running-link] | [![i3][javadocs-image]][javadocs] | [![i5][troubleshooting-image]][troubleshooting-link]

## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/model) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/model) |
| Socket |[![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/server) |
| GUI | [![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/client/gui) |
| CLI |[![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/client/cli) |
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/blob/master/src/main/java/it/polimi/ingsw/server/Server.java)|
| 5 Advanced Gods | [![GREEN](https://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/model/player/gods/advancedgods) |
| Persistence | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Undo Function | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

#### Legend
[![RED](https://placehold.it/15/f03c15/f03c15)](#) Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#) Implementing&nbsp;&nbsp;&nbsp;&nbsp;[![GREEN](https://placehold.it/15/44bb44/44bb44)](#) Implemented


<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

## Test cases
All tests in model and controller has a classes coverage at 100%.

**Coverage criteria: code lines.**

| Package |Tested Class | Coverage |
|:-----------------------|:------------------|:------------------------------------:|
| Controller | ActionController | 
| Controller | Controller | 
| Controller | GodSelectionController | 45/51 (88%)
| Controller | TurnControllerTest | 
| Model | Global Package | 641/676 (94%)

## The Team
Our working group is composed by three students:
* [Alice Piemonti](https://github.com/AlicePiemonti)
* [Luca Pirovano](https://github.com/PiroX4256)
* [Nicolò Sonnino](https://github.com/S0NN1)

## Software used
**Adobe XD** - sequence diagrams

**MagicDraw** - UML diagrams

**Intellij IDEA Ultimate** - main IDE 

## Copyright and license

Santorini Board Game is copyright 2020.

Licensed under the **[MIT License](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/blob/master/LICENSE)** ;
you may not use this software except in compliance with the License.


[license]: https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/blob/master/LICENSE
[license-image]: https://img.shields.io/badge/License-MIT-blue.svg
[javadocs-image]: github/Artboard%202.png
[javadocs]: https://s0nn1.github.io/Santorini-javadocs/
[installation-link]: https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/wiki/Installation
[installation-image]: github/Artboard%201.png
[compiling-image]: github/Artboard%203.png
[compiling-link]: https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/wiki/Compiling
[running-image]: github/Artboard%204.png
[running-link]: https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/wiki/Running
[troubleshooting-link]: https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/wiki/Troubleshooting
[troubleshooting-image]: github/Artboard%205.png
