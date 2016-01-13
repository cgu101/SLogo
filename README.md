# SLogo
Third project for CompSci 308 Fall 2015

Name: Connor Usry

Due Date: October 28, 2015

Hours worked: ~50

> This is the link to the Analysis Description: [Analysis - Slogo](http://www.cs.duke.edu/courses/cps108/current/assign/03_slogo/part4.php)

##Specifications
My team of 5 sought to provide an integrated development environment that supports users to write SLogo programs. Unlike IDEs such as Eclipse, which are designed around files that represent a complete program, SLogo users should be able to command the turtle interactively on a per expression basis. This is the read-eval-print loop used in environments for interpreted languages such as Python, Lisp, and Smalltalk (even the command-line shell could be considered such an environment). Thus this IDE focuses on helping users to experiment with and manage expressions, building up complex expressions from previously entered ones and keeping the visual representation of the turtle appropriately updated.

At its heart, the "back end" consists of a basic turtle graphics package and support for understanding a set of commands to control the turtle and its pen using basic programming constructs such as conditionals, loops, and procedures. The "front end", written using JavaFX, should, at the very least, include a command window and a turtle graphics window as well as other tools for supporting experimenting with commands. These two sides combine to receive, parse, and execute commands from the user, reporting any errors it encounters along the way (and not crashing!) or displaying the results of the commands in the graphics window.
