package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int totalArea;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int populationDensity;
    @CsvBindByName(column = "getstateCode", required = true)
    public String getstateCode;

    public String getState() {
        return state;
    }

    public int getPopulation() {
        return population;
    }

    public int getTotalArea() {
        return totalArea;
    }

    public int getPopulationDensity() {
        return populationDensity;
    }

    public String getGetstateCode() {
        return getstateCode;
    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + totalArea + '\'' +
                ", DensityPerSqKm='" + populationDensity + '\'' +
                '}';
    }
}
