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