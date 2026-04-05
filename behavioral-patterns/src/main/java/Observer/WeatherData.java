package Observer;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Observable;

@EqualsAndHashCode(callSuper = true)
@Data
public class WeatherData extends Observable {
    private float temp;

    private float humidity;

    private float pressure;

    public WeatherData() { }

    public void measurementsChanged() {
        float temp = getTemp();
        float humidity = getHumidity();
        float pressure = getPressure();
        setChanged();
        notifyObservers();
    }

    public void setMeasurements(float temp, float humidity, float pressure) {
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}
