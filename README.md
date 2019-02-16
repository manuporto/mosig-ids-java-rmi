# Universite Grenoble Alpes
## Master of Sciences in Informatics at Grenoble
## Introduction to Distributed Systems
### Java RMI Project Assignment: Chat Application
---

### Participants 
* Peter BARDAWIL - peter.bardawil@grenoble-inp.org
* Manuel PORTO - manuel-ignacio.porto@grenoble-inp.org

### How To Compile

In the terminal, run the command `./gradlew build`.

### How To Run
1. To run the server, the command `./gradlew -q --console=plain runServer` needs to be run.
2. To run a client, the command `./gradlew -q --console=plain runClient` needs to be run.

From the client side, there exist several commands to perform different actions. After selecting it's name, the user has the choice between different commands:

1. Send a broadcast message that will be received by all connected clients: `/bmsg <message>`
2. Send a direct message that will be received only by the desired client: `/msg <destName> <message>`
3. Show all connected clients: `/clients`
4. Show the history of the previous chats: `/history`
5. Exit the Chat application: `/exit`

From the server side the only possible interaction is for closing it with the letter **Q**.

### Design

The Chat application is based on two main programs, one server and one or more clients. 

The client, meant to be used by the chat users, offers an interface for interacting with the service. Besides checking that commands are properly called, it merely connects and consumes the server's services.

On the other side it exists the server which implements and subsequently offers the services needed to execute the chat application.

To communicate between them they were created two Java RMI remote objects. The first one, to access the service and the second one to send messages and receive. 

The `AccessServiceItf` is an interface that helps managing the clients that will join or leave the service. On the other hand, the `MessageServiceItf` is used to send the different type of messages and to get the history of the previous sent messages. 

It was also implemented a class, `MessageStorer`, to read and write messages from and to a file. This way messages can be viewed again after shutting down the server. While the server is running, messages are stored in the program memory and are updated after each message is sent. 

### How to test the application
The testing of the application must be done manually by using it. Each presented command can be used and, in consecuence, tested for expected behavior.

### Not implemented features
The following features are not currently implemented in the present work:
* Persistence of direct messages. Altough they are saved in memory and are accesible by all the users, they do not get persisted after a server shutdown or restart.
* Graphical User Interface
