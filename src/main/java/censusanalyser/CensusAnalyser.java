package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;


public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {
            ICSVBuilder icsvBuilder=CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList=icsvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e)
        {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
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
        }catch (CSVBuilderException e)
        {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    private <E> int getCount(Iterator<E>iterator){
        Iterable<E>csvIterable=()->iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }

   public String getStateWiseSortedCensusData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));)
        {
            ICSVBuilder icsvBuilder=CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList=icsvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);
            Comparator <IndiaCensusCSV>censusComparator= Comparator.comparing(census->census.state);
            this.sort(censusCSVList,censusComparator);
            String sortedStateCensusJson=new Gson().toJson(censusCSVList);
            return sortedStateCensusJson;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (CSVBuilderException e)
        {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }

    }

    private void sort(List<IndiaCensusCSV> censusCSVList,Comparator<IndiaCensusCSV> censusComparator){
        for(int i=0;i<censusCSVList.size()-1;i++){
            for(int j=0;j<censusCSVList.size()-i-1;j++){
                IndiaCensusCSV census1=censusCSVList.get(j);
                IndiaCensusCSV census2=censusCSVList.get(j+1);
                if(censusComparator.compare(census1, census2)>0){
                    censusCSVList.set(j,census2);
                    censusCSVList.set(j+1,census1);
                }
            }
        }
    }
}