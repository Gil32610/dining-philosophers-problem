package main.java.br.unicap.ppd.diningphilosophers.spaghetti;

public class Spaghetti {
    private boolean hasEaten;



    public void setHasEaten(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.hasEaten = true;
    }

}
