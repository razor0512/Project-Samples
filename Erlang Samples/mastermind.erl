-module(mastermind).
-export([main/0]).

main() ->

    Playerscore = 0,
	PCscore = 0,
	Guess = 0,
	{_,Numgames} = io:read("Enter the number of games: "),
	{_, Role} = io:read("Enter 1 to be the codebreaker or 2 to be the codemaker: "),
	if
		Role =:= 1 ->
			io:fwrite("~n~nGuess the hidden code..~n"),
			Hiddencode = {random:uniform(6),random:uniform(6),random:uniform(6),random:uniform(6)},
			io:format("Hidden Code (for debugging purposes):~w~n",[Hiddencode]),
			breaker(10,Guess,Hiddencode,Playerscore,PCscore,Numgames);
		Role =:= 2 ->
			maker(Playerscore,PCscore,Numgames)
	end.
	
		
secondary(Playerscore,PCscore,Numgames) when Numgames =:= 0  ->

if
	Playerscore > PCscore ->
		io:fwrite("Player Score : ~w 	   Computer Score : ~w 		You are the winner!~n",[Playerscore,PCscore]);
	PCscore > Playerscore ->
		io:fwrite("Player Score : ~w 	   Computer Score : ~w 		I am the the winner!~n",[Playerscore,PCscore]);
	Playerscore =:= PCscore ->
		io:fwrite("Player Score : ~w 	   Computer Score : ~w 		Draw!~n",[Playerscore,PCscore])
end;
		
secondary(Playerscore,PCscore,Numgames) ->

	Guess = 0,
	io:fwrite("Player Score : ~w       Computer Score : ~w 		Games Remaining : ~w ~n",[Playerscore,PCscore,Numgames]),
	{_, Role1} = io:read("Enter 1 to be the codebreaker or 2 to be the codemaker: "),
	if
		Role1 =:= 1 ->
			io:fwrite("~n~nGuess the hidden code..~n"),
			Hiddencode = {random:uniform(6),random:uniform(6),random:uniform(6),random:uniform(6)},
			io:format("Hidden Code (for debugging purposes):~w~n",[Hiddencode]),
			breaker(10,Guess,Hiddencode,Playerscore,PCscore,Numgames);
		Role1 =:= 2 ->
			maker(Playerscore,PCscore,Numgames)
	end.
	
	
maker(Playerscore,PCscore,Numgames) ->
	G1 = {1,1,2,2},
	io:fwrite("You are the code maker. This is my first guess: ~n~w~n",[G1]),
	{_, B1} = io:read("BLACKS: "),
	{_, W1} = io:read("WHITES: "),
	A1 = [{W,X,Y,Z}||W<-[1,2,3,4,5,6],X<-[1,2,3,4,5,6],Y<-[1,2,3,4,5,6],Z<-[1,2,3,4,5,6]],
	compG(Playerscore,PCscore,Numgames,G1,A1,B1,W1).				
	
compG(Playerscore,PCscore,Numgames,G,_P,B,_W) when B =:= 4 ->

	io:fwrite("The hidden code is ~w~n",[G]),
	Acc = PCscore + 1,
	Acc1 = Numgames -1,
	secondary(Playerscore,Acc,Acc1);
	
compG(Playerscore,PCscore,Numgames,G,P,B,W) ->

	K = [X||X<- P, blacks(X,G) =:= B, whites(X,G) =:= W],
	Z = length(K),
	io:fwrite("Possible answers left in list: ~w~n",[Z]),
	O = lists:nth(random:uniform(Z), K),
	io:fwrite("~w~n",[O]),
	{_ , BLCK} = io:read("BLACKS: "),
	{_ , WHT} = io: read("WHITES: "),
	compG(Playerscore,PCscore,Numgames,O,K,BLCK,WHT).
	
	
breaker(0,_,Hiddencode,PS,CS,NG) ->

	io:fwrite("~nYou have reached the maximum number of guesses. The correct code is ~w~n~n~n",[Hiddencode]),
	Acc = CS + 1,
	Acc1 = NG - 1,
	secondary(PS,Acc,Acc1);
	
breaker(_N, Guess, Hiddencode,PS,CS,NG) when Guess =:= Hiddencode ->

	io:fwrite("~nYOU GUESSED THE CORRECT CODE!~n~n~n"),
	Acc = PS + 1,
	Acc1 = NG -1,
	secondary(Acc,CS,Acc1);
	
breaker(N, _Guess, Hiddencode,_PS,_CS,_NG) -> 

	io:fwrite("Guesses Left: ~w~n",[N]),
    {_,Acc} = io:read("Enter your guess: "),
	B = blacks(Acc, Hiddencode),
	W = whites(Acc, Hiddencode),
	io:fwrite("Blacks: ~w  Whites: ~w~n",[B,W]),
	breaker(N-1, Acc, Hiddencode,_PS,_CS,_NG).
	
blacks(Guess, Hiddencode) ->

	length([A||{A,B}<-lists:zip(tuple_to_list(Guess), tuple_to_list(Hiddencode)),A==B]).
	 
whites(Guess, Hiddencode) ->

    T = [A||{A,B}<-lists:zip(tuple_to_list(Guess), tuple_to_list(Hiddencode)),A=/=B],
	V = [B||{A,B}<-lists:zip(tuple_to_list(Guess), tuple_to_list(Hiddencode)),A=/=B],
	X = [A||A<-T,lists:member(A, V)],
	length(X).
	