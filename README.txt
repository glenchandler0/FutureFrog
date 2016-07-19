A story / mystery game developed in Android Studio to be run on Android devices.

A story of a heroic frog caught in a battle of time. Along the way, he finds answers, as well as himself...

--Documentation found in "documentation" folder

Main Goals----------
-Improve draw() algorithm to not lag during processing of larger images
	-Potential Solution: This will likely be because of the bitmap operations, maybe openGL can fix this?

-Improve tap to move system
	-Potential Solution: Differentiate tap regions using octants, not quadrants

-Fix pause thread issue (multitask crashes game) --(IMPORTANT)
	-Potential Solution: (somewhere in gamePanel class) 

-Make draw algorithm to handle different Width x Height tile sizes
	-Potential Solution: Implement system of "magnification coefficients" to change the 'zoom' size of a map for different tile sizes. (This has been more or less completed)
		-Need to implement a variable that is set when maps are loaded that can be retrieved by draw() instead of using if functions.

-Add save states (IMPORTANT)
	-Potential Solution: Each time you change maps, you save current map to appropriate text file, and if you go to a save state or something, save current layout to text file.
		-Issues: Current entitiy loctions, are created during the program, not sure where to save all player positions. (Maybe just do player)?