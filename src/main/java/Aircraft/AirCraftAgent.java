package Aircraft;

import AirPort.AirPortAgent;
import Params.RunningParams;
import Weather.WeatherAgent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class AirCraftAgent implements Runnable, Cloneable {

    private int ID;
    private int maxFuelAmount;
    private int currentFuelAmount;
    private int basicLandingTime;
    private boolean isOnTheFlight;
    private boolean takeOfRequest = false;
    private boolean takeOnRequest = false;
    private AirPortAgent airPortAgent;
    private WeatherAgent weatherAgent;


    private List<AirCraftAgent> otherAirCrafts = new ArrayList<AirCraftAgent>();

    public void run() {
        System.out.println("AirCraft agent initialize: " + String.valueOf(ID));
        while(true) {
            System.out.println("AirCraft status: " + this.toString());
            this.tryToTakeOn();
            this.tryToTakeOff();
            this.getInformationFromOtherAgents();
            this.sleep();
        }
    }

    private void getInformationFromOtherAgents() {
        for(AirCraftAgent agent : this.getOtherAirCrafts()) {
            //simulates getting info about other agents;
        }
    }

    /**
     * If is on the flight, can make a decision, that he want to land.
     * 
     */
    private void tryToTakeOn() {
        if(this.isOnTheFlight()) {

        }
    }

    /**
     * If is on the airport, try to take of.
     *
     */
    private void tryToTakeOff() {
        if (!this.isOnTheFlight()) {

        }
    }

    public void addOtherAgent(final AirCraftAgent airCraftAgent) {
        this.otherAirCrafts.add(airCraftAgent);
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
                "ID: " + ID + " " + //
                "currentFuel: " + currentFuelAmount + " " + //
                "isOnTheFlight: " + isOnTheFlight;
    }

}
