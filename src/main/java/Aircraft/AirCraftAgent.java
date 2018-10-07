package Aircraft;

import Params.RunningParams;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class AirCraftAgent implements Runnable {

    private int ID;
    private int maxFuelAmount;
    private int currentFuelAmount;
    private int basicLandingTime;
    private boolean isOnTheFlight;


    public void run() {
        System.out.println("AirCraft agent initialize: " + String.valueOf(ID));
        while(true) {
            System.out.println("AirCraft status: " + this.toString());
            this.sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(RunningParams.AIRCRAFT_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return
                "ID: " + ID + //
                "currentFuel: " + currentFuelAmount + //
                "isOnTheFlight: " + isOnTheFlight;

    }

}
