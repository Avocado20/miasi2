package AirPort;

import Params.RunningParams;

public class AirPortAgent implements Runnable {


    private boolean isAirCraftLanding = false;

    public void run() {
        System.out.println("Airport agent initialize");
        while(true) {
            this.sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(RunningParams.AIRPORT_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
