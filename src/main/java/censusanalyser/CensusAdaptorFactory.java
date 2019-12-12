package censusanalyser;

import java.util.Map;

public class CensusAdaptorFactory {

    public static Map<String, CensusDAO> getCensusData(CensusAnalyser.Country country, String[] csvFilePath) throws CensusAnalyserException {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdaptor().loadCensusData(csvFilePath);
        if(country.equals(CensusAnalyser.Country.US))
            return new USCensusAdaptor().loadCensusData(csvFilePath);
        throw new CensusAnalyserException("unknownCountry",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
