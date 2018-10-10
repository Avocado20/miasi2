import AirPort.AirPortAgent;
import Aircraft.AirCraftAgent;
import Weather.WeatherAgent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class AirCraftSystem extends Thread implements Runnable {

    private WeatherAgent weatherAgent;
    private AirPortAgent airportAgent;
    public List<AirCraftAgent> airCrafts;


    public void runAll() {
        this.initializeWeatherAgent();
        this.initAirportAgent();
        this.initializeAirCraftAgents();
        this.run();
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
        airCrafts = new ArrayList<AirCraftAgent>();
        airCrafts.add(new AirCraftAgent().setID(1).setMaxFuelAmount(380).setCurrentFuelAmount(300).setBasicLandingTime(25).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(2).setMaxFuelAmount(300).setCurrentFuelAmount(200).setBasicLandingTime(35).setOnTheFlight(false));
        airCrafts.add(new AirCraftAgent().setID(3).setMaxFuelAmount(800).setCurrentFuelAmount(600).setBasicLandingTime(50).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(4).setMaxFuelAmount(600).setCurrentFuelAmount(200).setBasicLandingTime(5).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(5).setMaxFuelAmount(800).setCurrentFuelAmount(700).setBasicLandingTime(15).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(6).setMaxFuelAmount(800).setCurrentFuelAmount(700).setBasicLandingTime(15).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(7).setMaxFuelAmount(800).setCurrentFuelAmount(700).setBasicLandingTime(15).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(8).setMaxFuelAmount(800).setCurrentFuelAmount(700).setBasicLandingTime(15).setOnTheFlight(true));
        airCrafts.add(new AirCraftAgent().setID(9).setMaxFuelAmount(800).setCurrentFuelAmount(700).setBasicLandingTime(15).setOnTheFlight(true));
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

    public void run() {
        int width = 1800;
        String[] columns = {"ID", "maxFuelAmount", "currentFuelAmount", "basicLandingTime", "isOnTheFlight",
                "takeOfRequest", "takeOnRequest", "amILandindNext", "amIStartingNext", "currentProcedureTime", "isCrashed"};
        Object[][] data = new Object[airCrafts.size()+1][11];
        JFrame frame = new JFrame("AirCraft system");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JTable table = new JTable(data, columns);
        table.setSize(width, 500);
        panel.add(table);
        frame.add(panel);
        frame.setSize(width, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        while(true) {
            for (int i = 0; i < columns.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(width / 11);
                data[0][i] = columns[i];
            }
            for (AirCraftAgent agent : airCrafts) {
                data[airCrafts.indexOf(agent)+1][0] = agent.getID();
                data[airCrafts.indexOf(agent)+1][1] = agent.getMaxFuelAmount();
                data[airCrafts.indexOf(agent)+1][2] = agent.getCurrentFuelAmount();
                data[airCrafts.indexOf(agent)+1][3] = agent.getBasicLandingTime();
                data[airCrafts.indexOf(agent)+1][4] = agent.isOnTheFlight();
                data[airCrafts.indexOf(agent)+1][5] = agent.isTakeOfRequest();
                data[airCrafts.indexOf(agent)+1][6] = agent.isTakeOnRequest();
                data[airCrafts.indexOf(agent)+1][7] = agent.isAmILandindNext();
                data[airCrafts.indexOf(agent)+1][8] = agent.isAmIStartingNext();
                data[airCrafts.indexOf(agent)+1][9] = agent.getCurrentProcedureTime();
                data[airCrafts.indexOf(agent)+1][10] = agent.isCrashed();
            }
            table.repaint();
       }
   }

}
