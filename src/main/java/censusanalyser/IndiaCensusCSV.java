package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int areaInKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int densityPerKm;

    public IndiaCensusCSV() {
    }

    public IndiaCensusCSV(String state, int population, int populationDensity, int totalArea) {
        this.state = state;
        this.population = population;
        this.areaInKm = totalArea;
        this.densityPerKm = populationDensity;

    }
}
