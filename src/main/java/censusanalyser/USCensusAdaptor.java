package censusanalyser;
import java.util.Map;

public class USCensusAdaptor extends CensusAdaptor
{
    public <E> Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country,String... csvFilePath) throws CensusAnalyserException{
            Map<String, CensusDAO> censusStateMap = super.loadCensusData(USCensusCSV.class,csvFilePath);
            return censusStateMap;
        }
    }


