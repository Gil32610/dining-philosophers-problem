package main.java.br.unicap.ppd.diningphilosophers.philosopher;

import main.java.br.unicap.ppd.diningphilosophers.fork.Fork;
import main.java.br.unicap.ppd.diningphilosophers.philosophertable.PhilosopherTable;
import main.java.br.unicap.ppd.diningphilosophers.spaghetti.Spaghetti;

import java.util.Random;

public class Philosopher implements Runnable {
    private String philosopherName;

    private boolean hasEaten;
    private Fork leftFork;
    private Fork rightFork;

    private Spaghetti spaghetti;
    private PhilosopherTable philosopherTable;

    public Philosopher(String philosopherName, Spaghetti spaghetti, PhilosopherTable philosopherTable) {
        this.philosopherName = philosopherName;
        this.philosopherTable = philosopherTable;
        this.spaghetti = spaghetti;
    }

    @Override
    public void run() {
        Random random = new Random();
        Thread.currentThread().setName(this.philosopherName);
        synchronized (this.rightFork){
            this.rightFork.eat(spaghetti);
        }
    }
}
