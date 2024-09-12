package main.java.br.unicap.ppd.diningphilosophers.spaghetti;

import java.util.Random;

public class Spaghetti {
    private boolean eaten;

    private int steps;

    public Spaghetti(){
        this.steps = 0;
    }

    public void setHasEaten(){
        this.steps++;
        if(steps<2){
            System.out.printf("%s still needs a fork to eat!.\n", Thread.currentThread().getName());
        }else{
            Random random = new Random();
            try {
                System.out.printf("Philosopher %s is currently tasting a good spaghetti \n", Thread.currentThread().getName());
                Thread.sleep(random.nextInt(2000,5000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.eaten = true;
        }
    }
    public boolean hasEaten(){
        return eaten;
    }

}
