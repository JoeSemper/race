package race;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    public static CountDownLatch cdl2 = new CountDownLatch(MainClass.CARS_COUNT);
    private static boolean winner = false;


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CountDownLatch cdl, CyclicBarrier cb) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;

        new Thread(() -> {
            try {
                System.out.println(this.name + " готовится");
                Thread.sleep(500 + (int) (Math.random() * 800));
                System.out.println(this.name + " готов");
                cdl.countDown();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    @Override
    public void run() {


        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        if (!winner){
            winner = true;
            System.out.println(name + " WIN");
        }

        cdl2.countDown();

    }
}

