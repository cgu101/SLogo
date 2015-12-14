** Estimation: ** I believe it will take me approximately an hour to implement this new feature. Looking back at my previous analysis I mapped out the steps on how to add a command. From my previous analysis... 

New Command : The steps are as follow... 
- Create the new Command (with the proper Lambda expression and manipulateController call). 
- Add the Command's name to the proper section of the SyntaxType.java class for the parser. 
- Add the Command to the CommandFactory. Done!

**Review** This feature took me a little longer to implement than expected (~1.5 hours). I had almost everything hooked up correctly on the first try, but I had forgotten to mention in the previous analysis that in order to add a new command I would also need to add the information about this new Node to the actual Parser file, as well as the english.properties file. In order to create these new methods I made 3 new parser nodes (named FENCE, WINDOW, WRAP), which called our ManipulateController's updateMapType() method. Since I didn't want to change around the previous commands, I made it so that the turtles and the map had their own MapType state. Whenever a turtle's setPosition() is called, it will call the current MapType enums .getNextPosition(). This allowed me to leave all other commands in tact as well as experiment with the Enum class.

My new functionality works, however the frontEnd's map placement math declares the screen's middle as (0,0) which screwed up some of my math within the enum. I was considering (0,0) as the top left of the screen, but if you consider (0,0) to be the middle, the functionality works perfectly.

Analysis: I believe our design for SLogo was very solid, I could have documented the extra location where you needed to add the commands to for my own Analysis, but it was easy for me to determine the mistake because our console through a specific error "Undefined Command" from the Parser. We definitely should have added some javaDoc's to our code to make it easier. That being said, while it obviously would have taken longer if I had not been familiar the code, it would have still been reasonably easy to implement.