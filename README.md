## Screenshot (MainGA)
Compile this version using `make` and run using `java MainGa`. Here is a screenshot of our GA algorithm GUI. Each search implementaion runs in its own thread and may be started and stopped independently. Each implemtation is populated with 1000 random circuits (each verified for a solution). The GUI updates every 250ms with resreshed stats on the current running proccesses and graphically shows circuits as they are spliced. All discovered circuit solutions are stored in a unique file in the `src/solutions` directory. The Sum circuit may take some time to find. The pool size never exceeds 1000 circuits and is allways filtered for the best circuits according to fitness.

### Pre-Run
![GA GUI](https://github.tamu.edu/bobtimm/CSCE-315-Project-3/raw/master/docs/images/ga-pre-run.png)

### Post-Run
All circuits with green and white boxes in the center are complete solution circuits. 

![GA GUI](https://github.tamu.edu/bobtimm/CSCE-315-Project-3/raw/master/docs/images/ga-post-run.png)

## Screenshot (MainNonGA)
Compile this version using `javac MainNonGA.java` and run using `java MainNonGa`. Here is a screenshot of our search algorithm GUI. Each search implementaion runs in its own thread and may be started and stopped independently. The GUI updates every 250ms with resreshed stats on the current running proccesses. All discovered circuit solutions are stored in a unique file in the `src/solutions` directory. The Sum circuit may take some time to find.

![Search GUI](https://github.tamu.edu/bobtimm/CSCE-315-Project-3/raw/master/docs/images/search-algorithm-gui.png)

## Run & Compile
* If you are running this application on a remote server, you must enable X11 forwarding.
* Checkout this project using Git, `git clone <repo-name>`
* Change directories into project then source folder, `cd <repo-name>`, `cd src/`
* Compile the application, `make`
* Run the application, `java Main`


## Team #7
* Rafa Moreno - ralphie9224@email.tamu.edu
* Robert (Bob) Timm - bobtimm@email.tamu.edu
* Samuel Cimino - samuelcimino@email.tamu.edu
* Ben Whitley - benwhitley92@email.tamu.edu

[Project #3 Link](http://faculty.cse.tamu.edu/ritchey/courses/csce315/spring15/homework/project3.pdf)
