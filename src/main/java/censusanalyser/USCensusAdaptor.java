package censusanalyser;

import java.util.Map;

public class USCensusAdaptor extends CensusAdaptor
{
    public <E> Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException{
            Map<String, CensusDAO> censusStateMap = super.loadCensusData(USCensusCSV.class,csvFilePath[0]);
            return censusStateMap;
        }
    }


