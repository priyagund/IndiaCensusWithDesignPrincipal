package censusanalyser;

import com.google.gson.Gson;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class CensusAnalyser {

    Map<String,IndiaCensusDAO> censusStateMap=null;

    public CensusAnalyser() {
        censusStateMap = new HashMap<String,IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator=icsvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV>csvIterable=()->csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(censusCSV->censusStateMap.put(censusCSV.state,new IndiaCensusDAO(censusCSV)));
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
             while(stateIterator.hasNext()) {
                 IndiaStateCSV stateCSV = stateIterator.next();
                 IndiaCensusDAO censusDAO = censusStateMap.get(stateCSV.getStateName());
                 if (censusDAO == null) continue;
                 censusDAO.stateCode = stateCSV.getStateCode();
             }

            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
    }


    private <E> int getCount(Iterator<E>iterator){
        Iterable<E>csvIterable=()->iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }


   public String getStateWiseSortedCensusData(String field) throws CensusAnalyserException {
       if(censusStateMap == null || censusStateMap.size()==0){
           throw new CensusAnalyserException("No such Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
       }
       Comparator<IndiaCensusDAO>censusComparator=Comparator.comparing(census->census.stateCode);
       List<IndiaCensusDAO>censusDAOS=censusStateMap.values().stream().collect(Collectors.toList());
       this.sort(censusDAOS,censusComparator);
       String sortedStateCensusJson=new Gson().toJson(censusStateMap);
       return sortedStateCensusJson;
    }


    private void sort(List<IndiaCensusDAO>censusDAOS, Comparator<IndiaCensusDAO> censusComparator){
        for(int i=0;i<censusDAOS.size()-1;i++){
            for(int j=0;j<censusDAOS.size()-i-1;j++){
                IndiaCensusDAO census1=censusDAOS.get(j);
                IndiaCensusDAO census2=censusDAOS.get(j+1);
                if(censusComparator.compare(census1, census2)>0){
                    censusDAOS.set(j,census2);
                    censusDAOS.set(j+1,census1);                }
            }
        }
    }


public Comparator<IndiaCensusDAO> sortByField(String field) throws CensusAnalyserException {
    Comparator<IndiaCensusDAO>censusComparator;

  switch(field.toLowerCase()) {
      case "state":
          censusComparator = Comparator.comparing(census -> census.state);
          break;

      case "population":
          censusComparator = Comparator.comparing(census -> census.population);
          break;

      case "areaInSqKm":
          censusComparator = Comparator.comparing(census -> census.areaInSqKm);
          break;

      case "densityPerSqKm":
          censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
          break;

      default:
          throw new IllegalStateException("enter proper field" +field.toLowerCase());
  }
return censusComparator;
}

}