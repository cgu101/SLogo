#Estimation
I think it will take about 20 minutes to complete this feature. I will need to update Turtle, and 3-4 of the parsing classes.

#Review
Edited Turtle.java to have state information: window or wrap, and update position depending on which one is active.
Created WRAP and WINDOW classes that used ITurtle lambda
Edited CommandFactory, SyntaxType, and Parser.java to support new command.
I added WRAP and WINDOW. 

This took 15 minutes.
I got it right on the second try. I forgot to add the new commands to the resources file on the first go. After I did this and ran it a second time, it worked fine.

#Analysis
Our method for respresneting commands is as effective as I remembered. I was able to call turtle commands indirectly from the new command classes just like before. Updating the parser to accept new commands was just as hard as before, and I had to update it in four places and even made a mistake. This could be seriously improved by consolidating the input- by including more info in the resource file and then having the other classes generate the list of possible commands by looking at the file instead of each holding their own list of possible classes. 

If I was not familiar with the code at all, it would have been really hard to add the command to the parser. On the other hand though, it would have been pretty easy to add it to the well-named and simple to use Turtle class, and then by looking at other commands in the command package to manipulate the turtle.
