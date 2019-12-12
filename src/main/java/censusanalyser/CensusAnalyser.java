package censusanalyser;

import com.google.gson.Gson;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class CensusAnalyser {

    Map<String, CensusDAO> censusStateMap=null;
    HashMap<EnumField, Comparator<CensusDAO>> censusStateEnumMap=null;

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
        this.censusStateEnumMap=new HashMap<>();
        this.censusStateEnumMap.put(EnumField.STATE, Comparator.comparing(census ->census.state));
        this.censusStateEnumMap.put(EnumField.POPULATION, Comparator.comparing(census ->census.population));
        this.censusStateEnumMap.put(EnumField.AREA, Comparator.comparing(census ->census.totalArea));
        this.censusStateEnumMap.put(EnumField.DENSITY, Comparator.comparing(census ->census.populationDensity));
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator=icsvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV>csvIterable=()->csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(censusCSV->censusStateMap.put(censusCSV.state,new CensusDAO(censusCSV)));
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE);
        }

    }

    public int loadIndiaStateCode(String csvDataFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvDataFilePath));)
        {
            ICSVBuilder icsvBuilder=CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSV> stateIterator=icsvBuilder.getCSVFileIterator(reader,IndiaStateCSV.class);
           Iterable<IndiaStateCSV> csvIterable = () -> stateIterator;
           StreamSupport.stream(csvIterable.spliterator(),false)
                   .filter(csvState-> censusStateMap.get(csvState.getStateName()) != null).
                           forEach(csvState->censusStateMap.get(csvState.getStateName()).stateCode = csvState.getStateCode());
            return censusStateMap.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE);
        }
    }

   public String getSortedByField(EnumField fieldName) throws CensusAnalyserException {
       if(censusStateMap == null || censusStateMap.size()==0){
           throw new CensusAnalyserException("No such Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
       }
       Comparator<CensusDAO>censusComparator=Comparator.comparing(census->census.stateCode);
       List<CensusDAO>censusDAOS=censusStateMap.values().stream().collect(Collectors.toList());
       this.sort(censusDAOS,censusStateEnumMap.get(fieldName));
       String sortedStateCensusJson=new Gson().toJson(censusDAOS);
       return sortedStateCensusJson;
    }

    private void sort(List<CensusDAO>censusDAOS, Comparator<CensusDAO> censusComparator){
        for(int i=0;i<censusDAOS.size()-1;i++){
            for(int j=0;j<censusDAOS.size()-i-1;j++){
                CensusDAO census1=censusDAOS.get(j);
                CensusDAO census2=censusDAOS.get(j+1);
                if(censusComparator.compare(census1, census2)>0)
                {
                    censusDAOS.set(j,census2);
                    censusDAOS.set(j+1,census1);
                }
            }
        }
    }


    public int loadUScensusData(String usCensusDataFile) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(usCensusDataFile));)
        {
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusCSV> csvFileIterator=icsvBuilder.getCSVFileIterator(reader,USCensusCSV.class);
            Iterable<USCensusCSV>csvIterable=()->csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(censusCSV->censusStateMap.put(censusCSV.state,new CensusDAO(censusCSV)));
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }catch (RuntimeException e){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE);
        }
    }

}