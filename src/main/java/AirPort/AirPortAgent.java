package AirPort;

import Params.RunningParams;

public class AirPortAgent implements Runnable {


    private int airCraftLandingID = 0;
    private int airCraftStartingID = 0;

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

    /* Only for security reasons - No aircraft should ask for landing when another is landing, because aircraft should ask other aircrafts, if can land.

     */
    public boolean askForLandind(int airCraftID) {
        if (this.airCraftLandingID == 0) {
            this.airCraftLandingID = airCraftID;
            return true;
        } else {
            return false;
        }
    }

    public boolean askForStarting(int airCraftID) {
        if (this.airCraftStartingID == 0) {
            this.airCraftStartingID = airCraftID;
            return true;
        } else {
            return false;
        }
    }

    public void land() {
        this.airCraftLandingID = 0;
    }

    public void start() {
        this.airCraftStartingID = 0;
    }
}
