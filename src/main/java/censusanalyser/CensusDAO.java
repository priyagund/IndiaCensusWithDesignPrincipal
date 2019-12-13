package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public double totalArea;
    public double populationDensity;
    public int population;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        stateCode=indiaCensusCSV.getstateCode;
        totalArea = indiaCensusCSV.totalArea;
        populationDensity = indiaCensusCSV.populationDensity;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(USCensusCSV censusCSV) {
        state = censusCSV.state;
        stateCode = censusCSV.stateId;
        population = censusCSV.population;
        totalArea = censusCSV.totalArea;
        populationDensity = censusCSV.populationDensity;
    }



}