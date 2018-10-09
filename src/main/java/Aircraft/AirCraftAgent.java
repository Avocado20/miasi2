package Aircraft;

import AirPort.AirPortAgent;
import Params.RunningParams;
import Weather.WeatherAgent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private boolean amILandindNext = false;
    private boolean amIStartingNext = false;
    private int currentProcedureTime = 0;


    private List<AirCraftAgent> otherAirCrafts = new ArrayList<AirCraftAgent>();

    public void run() {
        System.out.println("AirCraft agent initialize: " + String.valueOf(ID));
        while(true) {
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
            if (!this.takeOnRequest) {
                Random random = new Random();
                int var = Math.abs(random.nextInt() % 5);
                if (var == 0) {
                    this.setTakeOnRequest(true);
                }
            } else {
                if (!this.amILandindNext) {
                    if (this.amAirCraftWithLowestFuel() && !this.anyOtherIsNext()) {
                        this.amILandindNext = true;
                    }
                } else {
                    while(!this.getAirPortAgent().askForLandind(this.getID())) {
                        this.sleep();
                    }
                    this.amILandindNext = false;
                    int operationTime = this.weatherAgent.countOperationTime(this.basicLandingTime);
                    this.currentProcedureTime = operationTime;
                    do {
                        this.currentProcedureTime -= 1;
                        this.sleep();
                        System.out.println("AirCraft " + this.getID() + " is landind " + (operationTime - this.currentProcedureTime) + "/" + operationTime);
                    } while (this.currentProcedureTime != 0);
                    System.out.println("AirCraft " + this.getID() + " landed");
                    this.setTakeOnRequest(false);
                    this.setOnTheFlight(false);
                    this.airPortAgent.land();
                }
            }

        }
    }

    /**
     * If is on the airport, try to take of.
     *
     */
    private void tryToTakeOff() {
        if (!this.isOnTheFlight()) {
            if (!this.takeOfRequest) {
                Random random = new Random();
                int var = Math.abs(random.nextInt() % 5);
                if (var == 0) {
                    this.setTakeOfRequest(true);
                }
            } else {
                if (!this.amIStartingNext) {
                    if (this.amAirCraftWithLowestFuel() && !this.anyOtherIsNext()) {
                        this.amIStartingNext = true;
                    }
                } else {
                    do {
                        this.sleep();
                    } while (!this.airPortAgent.askForStarting(this.getID()));

                    int operationTime = this.weatherAgent.countOperationTime(this.basicLandingTime);
                    this.currentProcedureTime = operationTime;

                    do {
                        this.currentProcedureTime-=1;
                        System.out.println("AirCraft " + this.getID() + " is starting " + (operationTime - this.currentProcedureTime) + "/" + operationTime);

                    } while (this.currentProcedureTime != 0);
                    this.amIStartingNext = false;
                    this.airPortAgent.start();
                    this.setOnTheFlight(true);
                    System.out.println(this.getID() + " started");
                }
            }
        }
    }

    public void setTakeOnRequest(boolean isSet) {
        this.takeOnRequest = isSet;
        if (isSet) {
            System.out.println(this.getID() + " wants to Land");
        }
    }

    public boolean amAirCraftWithLowestFuel() {
        boolean result = true;
        for (AirCraftAgent other : otherAirCrafts) {
            if (other.isTakeOnRequest()) {
                if (other.getCurrentFuelAmount() < this.getCurrentFuelAmount()) {
                    result = false;
                }
            }
        }
        return result;
    }

    public boolean anyOtherIsNext() {
        for (AirCraftAgent aircraft : otherAirCrafts) {
            if (aircraft.amILandindNext) {
                return true;
            }
        }
        return false;
    }

    public void addOtherAgent(final AirCraftAgent airCraftAgent) {
        this.otherAirCrafts.add(airCraftAgent);
    }

    private void sleep() {
        try {
            this.setFuel();
//            System.out.println("AirCraft status: " + this.toString());
            Thread.sleep(RunningParams.AIRCRAFT_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setFuel() {
        if (this.isOnTheFlight()) {
            this.setCurrentFuelAmount(this.getCurrentFuelAmount() - 1);
        } else {
            this.setCurrentFuelAmount(this.getMaxFuelAmount());
        }
        if (this.currentFuelAmount < 1 && this.isOnTheFlight()) {
            System.out.println("AirCraft " + this.getID() + " crashed with no survivors");
            this.setOnTheFlight(false);
        }
    }

    @Override
    public String toString() {
        return
                "ID: " + ID + " " + //
                "currentFuel: " + currentFuelAmount + " " + //
                "isOnTheFlight: " + isOnTheFlight + " " + //
                "takeOnRequest: " + takeOnRequest + " " + //
                "amILandindNext: " + amILandindNext;
    }

}
