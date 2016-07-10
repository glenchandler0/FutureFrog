Crash Course to Code

--BEGGINING OF PROGRAM AND GAME SETUP
Program starts at MainActivity
	first method called is onCreate() //this is like main
	creates GamePanel instance.

GamePanel
	-Constructor:
		sets the context of mapManipulator so it can be accessed there
		thread is initialized and now will handle drawing to the screen every frame.
		Control is passed to the new thread, however GamePanel still has touch listeners to handle events like screen touching etc.

--This completes the necessary multithreading for a game. The thread draws, game panel handles inputs.
	every other event is a method being called and a chain effect. Changing entity coordinates, writing dialogue to the screen, changing map, etc.


----PROCESSES
Changing Maps.
	When Door entity is interacted with, or potentially any entity, it may call
	MapManipulator.loadSpecificMap(mapTransportNum), where mapTransportNum is either hard coded in,
	or passed into the door's constructor.

	loadSpecificMap(int mapNum)
		clears all entities from entity array
		immediately after adds player to list, which was initialized in MainThread when the game started. (MapManipulator.player and MapManipulator.entities.get(0) are interchangable, since player is always the first element of MapManipulator.entities)
		depending on which mapNum is passed into function, a different method is called. Technically all the method details could just happen here, but that would be pretty messy
		loadMapX, X being a number, is called

	loadMapX()
		a temporary mapBitmapNum is stored, since this is the current map number before it is changed, this is used to know what map player was at before changing
		loadMapFromFile(X) is called
	
			loadMapFromFile(X)
			does what it sounds like it does, will determine the map's dimensions and load the text file into a character array

		entities.get(0) has already been initialized to player, so you just need to set his x and y coords
		all other entities on the map are reinitialized, since they were previously cleared.
		the entity constructors will place themselves on MapManipulator.map //like map.get(y).set(x, 'character'), 'character' being their symbol

	--The map 2d array has now been loaded from file, all entities have been loaded and placed on map, and the player has been placed on the map. The map image itself doesn't matter, it will be referenced correcetly from the GamePanel draw() method as long as mapBitmapNum variable has changed.

Moving.
	When the screen is tapped, onTouchEvent() is called int eh GamePanel class.
	