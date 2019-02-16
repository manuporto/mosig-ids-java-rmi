# Java RMI Project Assignment: Chat Application

## Participants 
* Peter BARDAWIL - peter.bardawil@grenoble-inp.org
* Manuel PORTO - manuel-ignacio.porto@grenoble-inp.org

## How To Compile

In the terminal, run the command `./gradlew.build`.

## How To Run
1. To run the server, the command will be `./gradlew -q --console=plain runServer`.
2. To run a client, the command will be `./gradlew -q --console=plain runClient`.

From the client side, there exist different commands to achieve several things. After entering the nickname, the user has the choice between different commands:

1. Send a broadcast message that will be received by all connected clients: `/bmsg <message>`
2. Send a direct message that will be received only by the desired client: `/msg <destName> <message>`
3. Show all connected clients: `/clients`
4. Show the history of the previous chats: `/history`
5. Exit the Chat application: `/exit`

From the server side the only possible interaction if for closing it with the letter **Q**.

## Design

The Chat application is based on two main programs, one server and one or more clients. To communicate between them they were created two Java RMI remote objects. The first one, to access the service and the second one to send messages and receive. 

The `AccessServiceItf` is an interface that helps managing the clients that will join or leave the service. On the other hand, the `MessageServiceItf` is used to send the different type of messages and to get the history of the previous sent messages. 

It was also implemented a class, `MessageStorer`, to read and write messages from and to a file. This way messages can be viewed again after shutting down the server. While the server is running, messages are stored in the program memory and are updated after each message is sent. 
