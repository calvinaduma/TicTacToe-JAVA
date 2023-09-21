default: cpsc2150/extendedTicTacToe/*.java cpsc2150/extendedTicTacToe/models/*.java 
	javac cpsc2150/extendedTicTacToe/*.java cpsc2150/extendedTicTacToe/models/*.java

run: cpsc2150/extendedTicTacToe/models/*.class cpsc2150/extendedTicTacToe/*.class
	java cpsc2150.extendedTicTacToe.GameScreen

clean:
	rm -f cpsc2150/extendedTicTacToe/*.class cpsc2150/extendedTicTacToe/models/*.class cpsc2150/extendedTicTacToe/*.interface