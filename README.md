# README for ClientServer Game

This project assumes that Java and Maven are installed and set up on the target environment. 

When the project has been pulled to a local repository, the shell scripts should be able to spawn both server and clients. 

## Process to run Server and Client

1. To run the server, simply open the server directory in a CLI, and type `./compile_start_server.sh` - this will compile and launch the server. 

2. Next, you can spin up a client by typing `./compile_start_client.sh` - this will compile and launch a client. The client represents a player in the game, and so for a two player game you should launch a client using this script in at least two new terminal windows, 

Note:
- The server will need to be run before a client can connect, and thus should be run first.
- It may be required to set the permissions for the shell scripts to allow for file execution. To do this, simply type 
`chmod +x <FILE_NAME.sh>` in the current directory containing the script in question.
- That any number of clients can be created, however, when the number of clients connected to the server exceeds 2 (the 2 required for the 
game), then the surplus clients are informed that too many players have joined and will be kicked off the server/not allowed to play.

The first two clients who successfully connected to the server, will battle to the death... or a tie...

## Instructions for the player
- When the client has been spawned, it will be asked to register with the server by providing a user name. The user name is read from standard input. 

- If two players are still to join, the client will print the game state as  `"WAITING FOR BOTH PLAYERS TO JOIN"`. 
When 1 player has joined, the game state displayed by the client will be `"WAITING FOR PLAYER 2 TO JOIN"`.
When the game has begun, the client will print the game state as `"GAME HAS BEGUN"`. 

- If 3 or more clients try to join the server, the surplus clients will display `"TOO MANY PLAYERS HAVE REQUESTED TO PLAY - NO MORE PLAYERS CAN JOIN!"` to the user, and end program execution. 

- After, this, the next steps are for the players to take turns selecting a column until one player wins or a tie occurs. The users have the option to leave during the game by typing `"EXIT"`.

- The board is displayed at each turn for both users to see the moves. If an invalid input is received by the client, (i.e. it's not a value between 0 and 8 or `"EXIT"`), the user who entered the invalid request is requested to re-enter their input.

- The above steps are re-iterated until there is a winner or a tie. 

