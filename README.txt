A story / mystery game developed in Android Studio to be run on Android devices.

A story of a heroic frog caught in a battle of time. Along the way, he finds answers, as well as himself...

--Documentation found in "documentation" folder

Main Goals--
-Improve draw() algorithm to not lag during processing of larger images
	-Potential Solution: Change sorting algorithm for sorting entities for drawing, or insert them in and odered way. Possibly a min heap that draws lowest y position first?

-Improve tap to move system
	-Potential Solution: Differentiate tap regions using octants, not quadrants

-Fix pause thread issue (multitask crashses game)
	-Potential Solution: (somewhere in gamePanel class) 

-Make draw algorithm to handle different Width x Height tile sizes
	-Potential Solution: Have a potential check for size, and when drawing screen outline subtract a smaller amount of pixels from playerImageX and Y.
				Subtracted amount could be a percentage of 96 x 96 pixels? so subtract (surfaceWidth / 2) * 0.33 if you want to use 32 x 32 pixels.

-Add save states
	-Potential Solution: Each time you change maps, you save current map to appropriate text file, and if you go to a save state or something, save current layout to text file.
		-Issues: Current entitiy loctions, are created during the program, not sure where to save all player positions. (Maybe just do player)?