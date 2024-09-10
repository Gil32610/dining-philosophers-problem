package main.java.br.unicap.ppd.diningphilosophers.fork;

public class Fork {
    private boolean occupied;
    private String name;

    public Fork(String name){
        this.name = name;
        this.occupied = false;
    }

    public synchronized void changeState(){
        this.occupied = !occupied;
    }

    public boolean isOccupied(){
        return occupied;
    }

}
