# README for ClientServer Game

This project assumes that Java and Maven are installed and set up on the device. 

When the project has been pulled to a local repository, the shell scripts should be able to spawn both server and clients. 

To run the server, simply open the server directory in a CLI, and type 
./compile_start_server.sh - this should build and run the server. 

Next, in at ;east two new terminal windows, you can spin up a client by typing
./compile_start_client.sh - this will spin up a client. 

Note 1. The server will need to be run before a client can connect.

Note 2. It may be required to set the permissions for the shell scripts to allow for file execution. To do this, simply type 
chmod +x <FILE_NAME.sh> in the current directory containing the script in question.

Note 3. That any number of clients can be created, however, when the number of clients exceeds 2 (the 2 required for the 
game), then the surplus clients are informed that too many players have joined and will be kicked off the server/not allowed to play.

The first two clients who successfully connected to the server, will battle to the death... or a tie...

## Process for the client
When the client has been spawned, it will be asked to register with the server by providing a user name. 
The user name is read from standard input. If two players are still to join, the client will print the game state as  "AITING FOR BOTH PLAYERS TO JOIN" 
When 1 player has joined, the game state displayed by the client will be "WAITING FOR PLAYER 2 TO JOIN".
When the game ahs begun, the client will print the game state as "GAME HAS BEGUN". 

If 3 or more clients try to join the server, the client will display "TOO MANY PLAYERS HAVE REQUESTED TO PLAY - NO MORE PLAYERS CAN JOIN!"
Rto the user, and end. 

After, this, the next steps are for the players to take turns selecting a column until one player wins or a tie occurs. 

The board is displayed at each turn for both users to see the moves, and the users have the option to leave during the game by typing "EXIT".




