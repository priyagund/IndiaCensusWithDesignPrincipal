package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;


public class CensusAnalyser {
    public enum Country {
        INDIA, US
    }

    Map<String, CensusDAO> censusStateMap = new HashMap<>();
    HashMap<EnumField, Comparator<CensusDAO>> censusStateEnumMap = new HashMap<>();
    private Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
        this.censusStateMap = new HashMap<>();
        this.censusStateEnumMap = new HashMap<>();
        this.censusStateEnumMap.put(EnumField.STATE, Comparator.comparing(census -> census.state));
        this.censusStateEnumMap.put(EnumField.POPULATION, Comparator.comparing(census -> census.population));
        this.censusStateEnumMap.put(EnumField.AREA, Comparator.comparing(census -> census.totalArea));
        this.censusStateEnumMap.put(EnumField.DENSITY, Comparator.comparing(census -> census.populationDensity));
    }

    public int loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        CensusAdaptor censusAdaptor = CensusAdaptorFactory.getCensusData(country);
        censusStateMap = censusAdaptor.loadCensusData(country,csvFilePath);
        return censusStateMap.size();
    }

    public String getSortedByField(EnumField fieldName) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No such Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList censusDTOS = censusStateMap.values().stream()
                .sorted(this.censusStateEnumMap.get(fieldName))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson;
    }

}