package model;

import com.opencsv.bean.CsvBindByPosition;
import exception.DividingByZeroException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Martyna Szczekocka
 * @version 1.0
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Planet {
    
    /**
     * @param id - auto generated id
     * @param name - name of planet 
     * @param mass - mass of planet [10^24kg]
     * @param rotationPeriod - rotation period of planet [h]
     * @param dayLength - length of day on planet [h]
     * @param distanceToTheSun - distance to the Sun [10^6 km] 
     * @param averageTemperature - planet average temperature [C]
     * @param hasMagneticField - information about planet magnetic field
     */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 2)
    private double mass;
    @CsvBindByPosition(position = 7)
    private double rotationPeriod;
    @CsvBindByPosition(position = 8)
    private double dayLength;
    @CsvBindByPosition(position = 9)
    private double distanceToTheSun;
    @CsvBindByPosition(position = 17)
    private double averageTemperature;
    @CsvBindByPosition(position = 21)
    private boolean hasMagneticField;
    
    /**
     * method giving person correlation coefficient
     * @return person correlation coefficient
     * @throws DividingByZeroException - when rotationPeriod = 0
     */
    public double coutPearsonCorrelationCoefficient() {
        if (this.rotationPeriod == 0) {
            throw new DividingByZeroException();
        }
        return this.dayLength * 1.0 / this.rotationPeriod;
    }

    /**
     * @return String with all information about object
     */
    @Override
    public String toString() {
        return "Planet{" + "name=" + name + ", mass=" + mass + ", rotationPeriod=" + rotationPeriod + ", dayLength=" + dayLength + ", distanceToTheSun=" + distanceToTheSun + ", averageTemperature=" + averageTemperature + ", hasMagneticField=" + hasMagneticField + '}';
    }
   
}
