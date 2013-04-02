Some info on these samples:

mastermind.erl - this is a simple AI implementation of the Mastermind game. The computer can play as either the code maker or the codebreaker. It uses Donald Knuth's five-guess algorithm to guess the player's code in five guesses or less.

To compile: c(mastermind).
To run: mastermind:main().

qoip.erl - Short for Quotes Over IP. This program uses Erlang's socket libraries to communicate between a client and a server. The program is to be run on a server and the client uses telnet to connect to said server (port 8888).  The program receives text strings from the client and saves them in a list. 

To compile: c(qoip).
To run: qoip:start().
To connect to server: telnet serveripaddress 8888 (if used on same machine, use localhost instead)

Client to server commands: 

PUT anystringhere - Sends a string to the server and the server stores it in a list.
GET - Retrieves a random string from the server's list of stored strings.
QUIT - Disconnect from the server.
