package censusanalyser;

import java.util.Map;

public class CensusAdaptorFactory {

    public static CensusAdaptor getCensusData(CensusAnalyser.Country country) throws CensusAnalyserException {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdaptor();
        if(country.equals(CensusAnalyser.Country.US))
            return new USCensusAdaptor();
        throw new CensusAnalyserException("unknownCountry",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
