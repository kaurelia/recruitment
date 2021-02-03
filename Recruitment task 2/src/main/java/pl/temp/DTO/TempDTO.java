package pl.temp.DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class TempDTO {

    private int id;
    private Timestamp time;
    private Double measurement;


    public Timestamp getTime() {
        return time;
    }

    public Double getMeasurement() {
        return measurement;
    }

    public TempDTO setTime(Timestamp time) {
        this.time = time;
        return this;
    }

    public TempDTO setMeasurement(Double measurement) {
        this.measurement = measurement;
        return this;
    }

    public TempDTO setId(int id) {
        this.id = id;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempDTO tempDTO = (TempDTO) o;
        return id == tempDTO.id && time.equals(tempDTO.time) && Objects.equals(measurement, tempDTO.measurement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, measurement);
    }

    @Override
    public String toString() {
        return "TempDAO{" +
                "id=" + id +
                ", time=" + time +
                ", measurement=" + measurement +
                '}';
    }
}
