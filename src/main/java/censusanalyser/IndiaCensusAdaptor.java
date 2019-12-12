package censusanalyser;

import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdaptor extends CensusAdaptor{

    @Override
    public <E> Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String,CensusDAO> censusStateMap=super.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);
        this.loadIndiaStateCode(censusStateMap,csvFilePath[1]);
        return censusStateMap;
    }

    private Map<String, CensusDAO> loadIndiaStateCode(Map<String, CensusDAO> censusStateMap, String csvDataFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvDataFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSV> stateIterator = icsvBuilder.getCSVFileIterator(reader, IndiaStateCSV.class);
            Iterable<IndiaStateCSV> csvIterable = () -> stateIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusStateMap.get(csvState.getStateName()) != null).
                    forEach(csvState -> censusStateMap.get(csvState.getStateName()).stateCode = csvState.getStateCode());
            return censusStateMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE);
        }
    }


}
