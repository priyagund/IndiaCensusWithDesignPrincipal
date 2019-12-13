package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public double totalArea;
    public double populationDensity;
    public int population;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.areaInKm;
        populationDensity = indiaCensusCSV.densityPerKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(USCensusCSV censusCSV) {
        state = censusCSV.state;
        stateCode = censusCSV.stateId;
        population = censusCSV.population;
        totalArea = censusCSV.totalArea;
        populationDensity = censusCSV.populationDensity;
    }


    public Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state, stateCode, population, populationDensity, totalArea);
        return new IndiaCensusCSV(state, population, (int) populationDensity, (int) totalArea);
    }
}