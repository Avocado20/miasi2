package Weather;

import Params.RunningParams;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class WeatherAgent implements Runnable {

    private static final Double WIND_NORTH =  1.00;
    private static final Double WIND_SOUTH =  2.00;
    private static final Double WIND_EAST =   1.25;
    private static final Double WIND_WEST =   1.25;


    public void run() {
        System.out.println("Init weather Agent");
        while(true) {
            this.sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(RunningParams.WEATHER_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
