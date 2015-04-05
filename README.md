## Project Files
There are three important elements of the project: LogicBase, Circuit, and CircuitTree. The following sections discuss our design process of these elements.

### LogicBase
This class simulates the Logic Gates that are used in the Circuits. All gates are wrapped up into this class for simplification in the Circuit class. Each gate is distiguished by a String: GATE_AND, GATE_OR, GATE_NOT, and GATE_NONE. There can be either one or two inputs (depending on gate), one output, and a Color that is kept so the user can distinguish the origins of the gate. This class also contains an evaluation function which will produce the correct output given all of the inputs for a gate.

### Circuit
This class is designed using a stack as the container of Logic Gates. It took a good amount of care to make sure that every Logic Gate was accessed in the correct order. There are counters for each type of gate that a Circuit contains because this is where the Fitness is determined. A Circuit's Fitness depends on correctness, number of NOT gates, and the number of AND and OR gates. The NOT gate lead to a much higher Fitness score than other gates because it's ideal to have very few of these gates. There are also useful functions such as Trim, Splice, Load, Save, Evaluate, and ShuffleInputs. The Trim function will traverse the Circuit and determine what gates aren't useful so it can eliminate them. The Splice function splices the top half of one Circuit and the bottom half of another into a child Circuit without altering the two parent Circuits. The Load and Save function will write and read a Circuit from file. Evaluate will call LogicBase's evaluate function for every gate to find the output of the entire Circuit. Finally, the ShuffleInputs randomly generates valid inputs for every Logic Gate.

### CircuitTree
The CircuitTree class is used to create Circuits that produce the desired outputs. Every Node of the tree has three branches which represent the different Logic Gates that can be added to a Circuit. The Fitness of a Circuit can be determined at every Node of the Tree so the program can decide whether or not it likes that path that it's on. Instead of testing every single combinations of inputs at every node, this implementation saves time by using Circuit's ShuffleInputs function to randomly shuffle the inputs of every Node. This finds the desired Circuit a lot faster in the long run because it's not wasting time changing inputs at every Node.

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
