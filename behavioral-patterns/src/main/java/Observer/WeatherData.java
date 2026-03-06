package Observer;

import lombok.Data;

@Data
public class WeatherData {
    private float temp;

    private float humidity;

    private float pressure;

    public void measurementsChanged() {
        float temp = getTemp();
        float humidity = getHumidity();
        float pressure = getPressure();


    }
}
