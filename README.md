# Software Engineering Project

This project has been developed as part of the BSc thesis and represents the final exam of the course "Ingegneria del Software" ("Software Engineering") attended during my Bachelor's Degree (A.Y. 2020/21) at the Polytechnic University of Milan. The highest possible final grade has been achieved: 30 cum Laude.

## Description

Aim of the project was to design, develop, test and deploy a digital version of the board game "Master of Renaissance" by Cranio Creations in Java using the MVC pattern.\
The final result completely covers the rules defined by the game and it can be used with both a command line interface (CLI) or a graphical user interface (GUI), the network has been managed with the traditional socket approach.

### Group
The project has been developed by me and two other students: Alessandro Curti and Vittorio Antigio Dall'Oglio.

## Documentation
The following documentation includes documents made for the design of the problem.

### UML
The following class diagrams represent, respectively, the initial model according to which the game should have been implemented, and the final implementation diagrams.
- [Initial UML](deliveries/UML/Initial_UML)
- [Final UML](deliveries/UML/Final_UML)

### Communication design
The following file represents the design of the communication architecture. It shows and describes all the interactions between Server and Client.
It can be consulted here [Communication design](/deliveries/Communication_design).

### Libraries and Plugins
|Library/Plugin|Description|
|---------------|-----------|
|__maven__|management tool for Java based software and build automation|
|__junit__|framework dedicated to Java for unit testing|
|__dom__|library to support parsing of files in xml format|
|__JavaFx__|graphic library of Java|

### Test Coverage

Here there is an overview of the test coverage of the model.\
Test Coverage files can be found in [Test Coverage](/deliveries/Coverage) folder.


|__Class__|__Method__|__Line__|__Branch__|
|---------|---------|---------|---------|
|100%|89%|94%|81%|

### Jars
The following jars allow the launch of the game according to the features described in the introduction. The features built according to the project specification are listed in the next section while the details on how to launch them will be defined in the section called __How to run JAR__.\
 The folder containing the client and server Jars is located at the following address: [Jars](/deliveries/JAR).


## Project functionalities
### Developed functionalities
- Basic rules
- Complete rules
- Socket
- CLI
- GUI

### Additional Developed functionalities
- Multiple games
- Resilience to disconnections
- Local game

### Exam demo cheats
If one of these nicknames is chosen: `admin1`, `admin2`, `admin3`, `admin4` the user will start with an amount of fifty for every resources type in the strongbox.



<!--
RED -> [![#f03c15](https://via.placeholder.com/15/f03c15/000000?text=+)](#)
GREEN -> [![#c5f015](https://via.placeholder.com/15/c5f015/000000?text=+)](#)
-->

## How to run JAR

### [Server.jar](/deliveries/JAR/Server.jar)
Run the file from the terminal by typing:
```
java -jar Server.jar
```
By default the program will use the port `4000`.
You can specify the port on which open the socket by typing it after the file path, like this:
```
java -jar Server.jar PORT_NUMBER
```
e.g. `java -jar Server.jar 2432`.

If the selected port number is less than 1024, the default port number will be used.



### [Client.jar](/deliveries/JAR/Client.jar)
Run the file from the terminal by typing
```
java -jar Client.jar
```
By default this will open the GUI and will use localhost (`127.0.0.1`) and default port (`4000`).

If you want to specify the interface type `cli` or `gui` after the file path, like this:
```
java -jar Client.jar UI_TYPE
```
e.g. `java -jar Client.jar cli`.

If you want to play a local game, without launching the server, type `local` after the file path, like this:
```
java -jar Client.jar local
```

You can obviously type both the user interface parameter and the local parameter, in any order you want.

e.g. `java -jar Client.jar cli local`.

Be aware that the program is NOT case-sensitive, so `cli`, `CLI`, `cLi`, `local`, `LOCAL`, `loCAL`... are all accepted.

If you want to specify the socket parameters you can do it by typing them after the file path, like this:
```
java -jar Client.jar IP PORT
```
e.g. `java -jar Client.jar 192.168.10.83 2432`.

You can obviously type both the socket parameters and the interface mark.

e.g. `java -jar Client.jar 192.168.10.83 2432 cli`.

You can type the parameters in any order you want and they will be accepted as long as they meet the specifications.
If you specify the local parameter, the eventual socket parameters will be ignored and a local game will be launched.
