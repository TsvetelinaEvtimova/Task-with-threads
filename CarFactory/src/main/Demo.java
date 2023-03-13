package main;

import main.carComponents.Engine;
import main.carComponents.Frame;
import main.carComponents.Seat;
import main.carComponents.Tire;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExecutorService carFactory = Executors.newFixedThreadPool(3);
        carFactory.execute(() -> {
            try {
                Engine engine = new Engine();
                Thread.sleep(engine.getConstructionTime());
                System.out.println(Thread.currentThread().getName() + " made " + engine.getClass().getName() + " by " + (engine.getConstructionTime() / 1000) + " seconds.");
            } catch (InterruptedException e) {
                System.out.println("There is a problem!");
            }
        });
        carFactory.execute(() -> {
            try {
                Frame frame = new Frame();
                Thread.sleep(frame.getConstructionTime());
                System.out.println(Thread.currentThread().getName() + " made " + frame.getClass().getName() + " by " + (frame.getConstructionTime() / 1000) + " seconds.");
            } catch (InterruptedException e) {
                System.out.println("There is a problem!");
            }
        });
        for (int i = 0; i < 5; i++) {
            carFactory.execute(() -> {
                try {
                    Seat seat = new Seat();
                    Thread.sleep(seat.getConstructionTime());
                    System.out.println(Thread.currentThread().getName() + " made " + seat.getClass().getName() + " by " + (seat.getConstructionTime() / 1000) + " seconds.");
                } catch (InterruptedException e) {
                    System.out.println("There is a problem!");
                }
            });
        }
        for (int i = 0; i < 4; i++) {
            carFactory.execute(() -> {
                try {
                    Tire tire = new Tire();
                    Thread.sleep(tire.getConstructionTime());
                    System.out.println(Thread.currentThread().getName() + " made " + tire.getClass().getName() + " by " + (tire.getConstructionTime() / 1000) + " seconds.");
                } catch (InterruptedException e) {
                    System.out.println("There is a problem!");
                }
            });
        }
        carFactory.shutdown();
        try {
            carFactory.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Oops!");
        }
        System.out.println("\nCar is created by " + (System.currentTimeMillis() - start) + " milliseconds.");
    }
}
