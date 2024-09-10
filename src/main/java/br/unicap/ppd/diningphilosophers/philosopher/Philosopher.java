package main.java.br.unicap.ppd.diningphilosophers.philosopher;

import main.java.br.unicap.ppd.diningphilosophers.fork.Fork;
import main.java.br.unicap.ppd.diningphilosophers.philosophertable.PhilosopherTable;
import main.java.br.unicap.ppd.diningphilosophers.spaghetti.Spaghetti;

public class Philosopher implements Runnable {
    private String philosopherName;

    private boolean hasEaten;
    private Fork leftFork;
    private Fork rightFork;
    private PhilosopherTable philosopherTable;

    public Philosopher(String philosopherName, Spaghetti spaghetti, PhilosopherTable philosopherTable) {
        this.philosopherName = philosopherName;
        this.philosopherTable = philosopherTable;
    }

    @Override
    public void run() {

    }
}
