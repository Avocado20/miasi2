package Weather;

import Params.RunningParams;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Accessors(chain = true)
public class WeatherAgent implements Runnable {

    private static final Double WIND_NORTH =  1.00;
    private static final Double WIND_SOUTH =  2.00;
    private static final Double WIND_EAST =   1.25;
    private static final Double WIND_WEST =   1.25;

    private static final List<Double> list = new ArrayList<Double>() {{
        add(WIND_NORTH);
        add(WIND_SOUTH);
        add(WIND_EAST);
        add(WIND_WEST);
    }};


    public void run() {
        System.out.println("Init weather Agent");
        while(true) {
            this.sleep();
        }
    }

    public int countOperationTime(int airCraftBasicProcedureTime) {
        return Integer.valueOf((int) (airCraftBasicProcedureTime * list.get(new Random().nextInt(list.size()))));
    }

    private void sleep() {
        try {
            Thread.sleep(RunningParams.WEATHER_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
