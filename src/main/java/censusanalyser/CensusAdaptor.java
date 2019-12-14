package censusanalyser;

import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdaptor
{
    public abstract  <E> Map<String, CensusDAO> loadCensusData(CensusAnalyser.Country country,String... csvFilePath) throws CensusAnalyserException;

    Map<String, CensusDAO> censusStateMap = new HashMap<>();

    protected  <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = icsvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> csvFileIterator;
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false).
                        map(IndiaCensusCSV.class::cast).
                        forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false).
                        map(USCensusCSV.class::cast).
                        forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusStateMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE);
        }
    }


}
