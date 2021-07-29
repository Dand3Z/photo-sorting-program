# photo-sorting-program
The multithreaded window program for sorting .jpg files by creation date

The program contains two starting points:
- GuiStart - starts the program in window mode (Simple Swing Gui)
- ConsoleVersionStart - starts the program in the console

In both cases you indicate the folder where the .jpg files are located and the destination folder where the sorted files should be copied.
We also determine the number of threads. Each photo is copied in a separate thread.
Folders are named in the format "year-month-day". 
The files in the folders are numbered with consecutive integers starting from 1 (1,2,3, ...).

## Screenshots
![initial-game-img](/screenshots/gui-img.png?raw=true "GUI Image")
