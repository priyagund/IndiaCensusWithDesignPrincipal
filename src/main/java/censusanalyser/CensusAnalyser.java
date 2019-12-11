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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;


public class CensusAnalyser {

    List<IndiaCensusDAO> censusList=null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator=icsvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV>csvIterable=()->csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).forEach(censusCSV->censusList.add(new IndiaCensusDAO(censusCSV)));
            return censusList.size();
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
            List<IndiaStateCSV> stateList=icsvBuilder.getCSVFileList(reader,IndiaStateCSV.class);
            return stateList.size();
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
       if(censusList == null || censusList.size()==0){
           throw new CensusAnalyserException("No such Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
       }

       this.sort(this.sortByField(field));
       String sortedStateCensusJson=new Gson().toJson(censusList);
       return sortedStateCensusJson;
    }


    private void sort(Comparator<IndiaCensusDAO> censusComparator){
        for(int i=0;i<censusList.size()-1;i++){
            for(int j=0;j<censusList.size()-i-1;j++){
                IndiaCensusDAO census1=censusList.get(j);
                IndiaCensusDAO census2=censusList.get(j+1);
                if(censusComparator.compare(census1, census2)>0){
                    censusList.set(j,census2);
                    censusList.set(j+1,census1);
                }
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