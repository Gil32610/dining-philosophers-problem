package main.java.br.unicap.ppd.diningphilosophers;

import main.java.br.unicap.ppd.diningphilosophers.threadcolor.ThreadColor;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ThreadLocalRandom;

class Philosopher implements Runnable {
    private final String philosopherName;
    private final ReentrantLock leftFork;
    private final ReentrantLock rightFork;
    private final ThreadColor color;

    public Philosopher(String philosopherName, ReentrantLock leftFork, ReentrantLock rightFork, ThreadColor color) {
        this.philosopherName = philosopherName;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.color = color;
    }

    private void think() throws InterruptedException {

        int thinkTime = ThreadLocalRandom.current().nextInt(4000, 8000); // Random thinking time
        System.out.printf("%s%s is thinking for %d ms.\n",color.color(), philosopherName, thinkTime);
        Thread.sleep(thinkTime);
    }

    private void eat() throws InterruptedException {

        int eatTime = ThreadLocalRandom.current().nextInt(5000, 9000); // Random eating time
        System.out.printf("%s%s is eating for %d ms.\n",color.color(), philosopherName, eatTime);
        Thread.sleep(eatTime);
    }


    @Override
    public void run() {
        try {
            while (true) {
                think();


                if (leftFork.tryLock()) {
                    try {
                        System.out.printf("%s%s picked up left fork.%s\n", color.color(), philosopherName, ThreadColor.ANSI_RESET.color());


                        if (rightFork.tryLock()) {
                            try {
                                System.out.printf("%s%s picked up right fork.%s\n", color.color(), philosopherName, ThreadColor.ANSI_RESET.color());
                                eat();
                            } finally {
                                rightFork.unlock(); // Release right fork after eating
                                System.out.printf("%s%s put down right fork.%s\n", color.color(), philosopherName, ThreadColor.ANSI_RESET.color());
                            }
                        } else {
                            System.out.printf("%s%s failed to pick up right fork, retrying...%s\n", color.color(), philosopherName, ThreadColor.ANSI_RESET.color());
                        }
                    } finally {
                        leftFork.unlock(); // Release left fork if not both forks were acquired
                        System.out.printf("%s%s put down left fork.%s\n", color.color(), philosopherName, ThreadColor.ANSI_RESET.color());
                    }
                }

                int waitTime = ThreadLocalRandom.current().nextInt(5000, 9000);
                System.out.printf("%s%s waits %d ms before retrying.%s\n", color.color(), philosopherName, waitTime, ThreadColor.ANSI_RESET.color());
                Thread.sleep(waitTime);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf("%s%s was interrupted.%s\n", color.color(), philosopherName, ThreadColor.ANSI_RESET.color());
        }
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        int numberOfPhilosophers = 5;
        ReentrantLock[] forks = new ReentrantLock[numberOfPhilosophers];

        // Initialize forks as ReentrantLocks
        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i] = new ReentrantLock();
        }
        ThreadColor[] colors = {ThreadColor.ANSI_BLUE, ThreadColor.ANSI_CYAN, ThreadColor.ANSI_GREEN, ThreadColor.ANSI_PURPLE, ThreadColor.ANSI_RED};

        String[] names = {"Duda", "Rodrigo", "Caio", "Gil", "Arquimedes"};
        // Create and start philosopher threads
        Thread[] philosophers = new Thread[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosophers[i] = new Thread(new Philosopher(names[i], forks[i], forks[(i + 1) % numberOfPhilosophers], colors[i]));
            philosophers[i].start();
        }

        // Let the philosophers dine indefinitely
        for (Thread philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                philosopher.interrupt();
            }
        }
    }
}

