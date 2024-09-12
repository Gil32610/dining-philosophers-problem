package main.java.br.unicap.ppd.diningphilosophers;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ThreadLocalRandom;

class Philosopher implements Runnable {
    private final int id;
    private final ReentrantLock leftFork;
    private final ReentrantLock rightFork;

    public Philosopher(int id, ReentrantLock leftFork, ReentrantLock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think() throws InterruptedException {
        int thinkTime = ThreadLocalRandom.current().nextInt(1000, 3000); // Random thinking time
        System.out.println("Philosopher " + id + " is thinking for " + thinkTime + " ms.");
        Thread.sleep(thinkTime);
    }

    private void eat() throws InterruptedException {
        int eatTime = ThreadLocalRandom.current().nextInt(1000, 2000); // Random eating time
        System.out.println("Philosopher " + id + " is eating for " + eatTime + " ms.");
        Thread.sleep(eatTime);
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();

                // Try to pick up left fork
                if (leftFork.tryLock()) {
                    try {
                        System.out.println("Philosopher " + id + " picked up left fork.");

                        // Try to pick up right fork
                        if (rightFork.tryLock()) {
                            try {
                                System.out.println("Philosopher " + id + " picked up right fork.");
                                eat();
                            } finally {
                                rightFork.unlock(); // Release right fork after eating
                                System.out.println("Philosopher " + id + " put down right fork.");
                            }
                        } else {
                            // Failed to pick up right fork, retry after a random wait
                            System.out.println("Philosopher " + id + " failed to pick up right fork, retrying...");
                        }
                    } finally {
                        leftFork.unlock(); // Release left fork if not both forks were acquired
                        System.out.println("Philosopher " + id + " put down left fork.");
                    }
                }

                // Wait a random time before retrying if forks were not acquired
                int waitTime = ThreadLocalRandom.current().nextInt(1000, 2000);
                System.out.println("Philosopher " + id + " waits " + waitTime + " ms before retrying.");
                Thread.sleep(waitTime);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Philosopher " + id + " was interrupted.");
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

        // Create and start philosopher threads
        Thread[] philosophers = new Thread[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosophers[i] = new Thread(new Philosopher(i, forks[i], forks[(i + 1) % numberOfPhilosophers]));
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

