import AirPort.AirPortAgent;
import Aircraft.AirCraftAgent;
import Weather.WeatherAgent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class AirCraftSystem extends Thread {

    private WeatherAgent weatherAgent;
    private AirPortAgent airportAgent;

    public void run() {
        this.initializeWeatherAgent();
        this.initAirportAgent();
        this.initializeAirCraftAgents();
    }

    protected void initializeWeatherAgent() {
        this.weatherAgent = new WeatherAgent();
        new Thread(this.getWeatherAgent()).start();
    }

    protected void initAirportAgent() {
        this.airportAgent = new AirPortAgent();
        new Thread(this.getAirportAgent()).start();
    }

    protected void initializeAirCraftAgents() {
        List<AirCraftAgent> airCrafts = new ArrayList<AirCraftAgent>();
        airCrafts.add(new AirCraftAgent().setID(1).setMaxFuelAmount(200).setCurrentFuelAmount(180).setBasicLandingTime(25).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(2).setMaxFuelAmount(300).setCurrentFuelAmount(200).setBasicLandingTime(35).setOnTheFlight(false));
        airCrafts.add(new AirCraftAgent().setID(3).setMaxFuelAmount(800).setCurrentFuelAmount(600).setBasicLandingTime(50).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(4).setMaxFuelAmount(600).setCurrentFuelAmount(200).setBasicLandingTime(5).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(5).setMaxFuelAmount(800).setCurrentFuelAmount(700).setBasicLandingTime(15).setOnTheFlight(true));
        this.initAirCraftThread(airCrafts);
    }

    protected void initAirCraftThread(final List<AirCraftAgent> airCrafts) {
        for (AirCraftAgent airCraft : airCrafts) {
            for (AirCraftAgent other : airCrafts) {
                if (other != airCraft) {
                    airCraft.addOtherAgent(other);
                }
            }
            airCraft.setAirPortAgent(this.airportAgent);
            airCraft.setWeatherAgent(this.weatherAgent);
            new Thread(airCraft).start();
        }
    }

}
