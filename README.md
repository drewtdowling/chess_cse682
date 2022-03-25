#Simple Chess

By: Parker Wagner, Drew Dowling, and Harrison Chen.

##Introduction
Simple Chess is a desktop chess application that allows a game of chess to be played between two players sharing a desktop system.  It is written in Java using the JavaFX application framework, and follows a model view controller design pattern.

##Building the Application
The Simple Chess codebase compiles with Maven, and the repository features a pom.xml file describing all compilation dependencies and their respective versions.  To build the application, import the source repository into your editor environment of choice and build using maven.

##Running the Application
The main method of execution (and the launch point of the application) is the ChessClientDriver.main() method.  This method invokes the static JavaFX Application.launch() method, and the application starts up.

##Deploying as an All-Inclusive Deliverable
To compile the application to an executable Java ARchive (.jar) file, the code must be compiled into a module.  The steps to perform this are to:

1. Create a module-info.java file describing the module.
2. Compile the application with a module path instead of a classpath.
3. Create a .jar from the classes.
4. Convert the .jar file to a .jmod file using JDK (Java Development Kit) jmod tool.
5. Link the .jmod file (and the modules upon which it depends), into an image.  This image is the runnable deliverable and is usually distributed in some compressed format to customers.


##Source Code Repository
https://github.com/drewtdowling/chess_cse682

##Instructions
When Simple Chess is launched, the first window presented will be the login window for the player using the white pieces.  Logging in is optional, so if the player wishes to play anonymously, they may simply close the login window and the application will proceed.  If they opt to login, they may do so using their previously registered credentials.  If they have not yet registered, a register button is displayed within the window and the user may register by following the prompts.  After the player using the white pieces finishes their login workflow, the same workflow occurs for the player using the black pieces.

After the login workflow concludes, the primary application window will be shown.  This window will feature the chess board, along with all of the game pieces in their starting positions.  From this state, players simply follow the game prompts at the bottom of the window to play the game.
