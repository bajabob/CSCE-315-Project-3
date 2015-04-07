JAVAC = javac
JVM = java
JAVADOC = javadoc
MKBIN = mkdir -p bin

JAVAC_FLAGS = -g

MAINSRC = *.java

.SUFFIXES : .class .java

all: 
	$(JAVAC) $(JAVAC_FLAGS) $(MAINSRC)

clean:
	rm *.class
	rm solutions/*.txt

.PHONY: all test clean run run_test