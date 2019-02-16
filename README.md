# mosig-ids-java-rmi
Java RMI project assignment code. Chatbot application

Participants : Peter BARDAWIL (peter.bardawil@grenoble-inp.org), Manuel PORTO (manuel-ignacio.porto@grenoble-inp.org)

How To Compile : 
In the terminal, run the command : ./gradlew.build

How To Run :
1- to run the server, the command will be ./gradlew -q --console=plain runServer
2- to run the clients , the command will be ./gradlew -q --console=plain runClient

From the client side , different possible commands to achieve different things:
After entering the nickname, the user has the choice between different commands:
1-Send a broadcast message that will be received by all connected clients : 
Command : /bmsg [MESSAGE]
2-Send a direct message that will be received only by the desired client:
Command: /msg [RECIPIENT] [MESSAGE]
3-Show all connected clients :
Command: /clients
4-Show the history of the previous chats:
Command: /history
5-Exit the Chat application :
Command: /exit

Our Chatbot application was based on 2 classes , the server class and the client class. To communicate between these classes we created 2 remote objects , the first one to access the service and the second one to send messages.The AccessServiceItf is an interface that will help us manage the clients that will join or leave the service . Also, the MessageServiceItf is used to send the different type of messages and to get the history of the previous sent messages. We also implemented a class (MessageStorer) to Read/write messages from/to a file so messages could be viewed again after shutting down the server. Objects of type message (a class we created that stores the message , the source and timestamp) are stored into a linked list that we store into a file and update after each message sent. 


