-module(qoip).
-export([start/0, connect/1, recv_loop/3]).

-define(LISTEN_PORT, 8888).
-define(TCP_OPTS, [list, {packet, raw}, {nodelay, true}, {reuseaddr, true}, {active, true}]).

%% IMPORTANT NOTE: BACKSPACE DOES NOT WORK. NOT TESTED ON LINUX"
start() ->
    case gen_tcp:listen(?LISTEN_PORT, ?TCP_OPTS) of
        {ok, Listen} -> spawn(?MODULE, connect, [Listen]),
            io:format("~p QoIP Server online...~n", [erlang:localtime()]);
        Error ->
            io:format("Error: ~p~n", [Error])
    end.

connect(Listen) ->
    {ok, Socket} = gen_tcp:accept(Listen),
    inet:setopts(Socket, ?TCP_OPTS),
    spawn(fun() -> connect(Listen) end),
	gen_tcp:send(Socket, "Welcome to QoIP. \r\n\r\n(IMPORTANT NOTE: BACKSPASCE DOES NOT WORK!)\r\n"),
	WBank = [],
	QBank = [],
    recv_loop(Socket, WBank, QBank),
    gen_tcp:close(Socket).
	
store(Socket, Wbank, QBank) ->
	inet:setopts(Socket, [{active, once}]),
	receive
			{tcp, Socket, "\r\n"} ->
			    io:format("~p~n",[Wbank]),
				gen_tcp:send(Socket, "Quote Stored!\r\n"),
				O = QBank ++ [Wbank],
				A = [],
				recv_loop(Socket,A,O);
			{tcp, Socket, Quote} ->
				Z = Wbank ++ Quote,
				store(Socket, Z, QBank)
			
	end.

recv_loop(Socket, WBank, QBank) ->
	inet:setopts(Socket, [{active, once}]),
    receive
		{tcp, Socket, " "} ->
			if
				WBank == "PUT" ->
					io:format("PUT", []),
					store(Socket,[],QBank);
				true ->
					gen_tcp:send(Socket, "\r\nERROR Unimplemented command\r\n"),
					V = [],
					recv_loop(Socket, V, QBank)
			end;
					
      	{tcp, Socket, "\r\n"} ->
			if
				WBank == "GET" ->
					if 
						QBank == [] ->
							io:format("GET~n",[]),
							gen_tcp:send(Socket, "ERROR NO QUOTES STORED!\r\n"),
							recv_loop(Socket,[],QBank);
						true ->
							io:format("~p~n", [WBank]),
							Q = lists:nth(random:uniform(length(QBank)),QBank),
							gen_tcp:send(Socket, Q),
							gen_tcp:send(Socket, "\r\n"),
							B = [],
							recv_loop(Socket, B, QBank)
					end;
				WBank == "QUIT" ->	
					gen_tcp:send(Socket,"\r\nOK BYE!");
				true ->
					gen_tcp:send(Socket, "ERROR Unimplemented command\r\n"),
					C = [],
					recv_loop(Socket, C, QBank)
			end;
        {tcp, Socket, Data} ->
          	A = WBank ++ Data,
            recv_loop(Socket,A,QBank);
        {tcp_closed, Socket} ->
            io:format("~p Client Disconnected.~n", [erlang:localtime()])
    end. 
	