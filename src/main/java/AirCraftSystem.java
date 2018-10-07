import AirPort.AirPortAgent;
import Aircraft.AirCraftAgent;
import Weather.WeatherAgent;

import java.util.ArrayList;
import java.util.List;

public class AirCraftSystem extends Thread {

    private WeatherAgent weatherAgent;
    private AirPortAgent airportAgent;
    private List<Thread> airCraftAgents = new ArrayList<Thread>();

    public void run() {
        this.initializeWeatherAgent();
        this.initAirportAgent();
        this.initializeAirCraftAgents();
        this.sleep();
    }

    protected void initializeWeatherAgent() {
        this.weatherAgent = new WeatherAgent();
        Thread weatherThread = new Thread(this.weatherAgent);
        weatherThread.start();
    }

    protected void initAirportAgent() {
        this.airportAgent = new AirPortAgent();
        Thread airportThread = new Thread(this.airportAgent);
        airportThread.start();
    }

    protected  void initializeAirCraftAgents() {
        List<AirCraftAgent> airCrafts = new ArrayList<AirCraftAgent>();
        airCrafts.add(new AirCraftAgent().setID(1).setMaxFuelAmount(200).setCurrentFuelAmount(180).setBasicLandingTime(25).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(2).setMaxFuelAmount(300).setCurrentFuelAmount(200).setBasicLandingTime(35).setOnTheFlight(false));
        airCrafts.add(new AirCraftAgent().setID(3).setMaxFuelAmount(400).setCurrentFuelAmount(100).setBasicLandingTime(50).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(4).setMaxFuelAmount(600).setCurrentFuelAmount(200).setBasicLandingTime(5).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(5).setMaxFuelAmount(800).setCurrentFuelAmount(700).setBasicLandingTime(15).setOnTheFlight(true));


       for (AirCraftAgent airCraft : airCrafts) {
           for (AirCraftAgent other : airCrafts) {
               if (other != airCraft) {
                   airCraft.addOtherAgent(other);
               }
           }
           airCraft.setAirPortAgent(this.airportAgent);
           airCraft.setWeatherAgent(this.weatherAgent);
           Thread air = new Thread(airCraft);
           airCraftAgents.add(air);
           air.start();
       }

    }

    protected void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
