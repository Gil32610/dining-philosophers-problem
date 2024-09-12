package main.java.br.unicap.ppd.diningphilosophers.fork;

import main.java.br.unicap.ppd.diningphilosophers.spaghetti.Spaghetti;

public class Fork {
    private boolean occupied;
    private String name;

    private Object occupiedLock;

    public Fork(String name) {
        this.name = name;
        this.occupied = false;
    }

    public void changeState() {
            this.occupied = !occupied;
            notifyAll();
    }

    public void eat(Spaghetti spaghetti){
        while(occupied){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        spaghetti.setHasEaten();
        if(spaghetti.hasEaten()){
            System.out.printf("%s has finished eaten. Releasing fork to next philosopher.\n", Thread.currentThread().getName());
        }
    }

    public boolean isOccupied() {
        return occupied;
    }


}
