package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE = "/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_FILE_PATH="/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianStateCensus_whenSortedOnState_shouldeReturnResult() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndiaStateCensusData_WhenSortedOnPopulation_shouldeReturnResult() {
       try {
           CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.POPULATION);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Sikkim", censusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

   @Test
    public void givenIndiaStateCensusData_whenSortedOnArea_shouldReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.AREA);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Goa", censusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndiaStateCensusData_whenSortedOnDensity_shouldReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.DENSITY);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndiaStateCensusDataByDesending_whenSortedPopulation_shouldReturnResult()
    {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
           censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.POPULATION);
           IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
           Assert.assertEquals("Uttar Pradesh", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
   }

    @Test
   public void givenIndiaStateCensusDataByDesending_whenSortedDensity_shouldReturnResult()
    {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.DENSITY);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

   @Test
    public void givenIndiaStateCensusDataByDesending_whenSortedArea_shouldReturnResult() {
       CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
       try {
           censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
           String sortedCensusData = censusAnalyser.getSortedByField(EnumField.AREA);
           IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
           Assert.assertEquals("Rajasthan", censusCSV[censusCSV.length - 1].state);
       } catch (CensusAnalyserException e) {
           e.printStackTrace();
       }
   }

    @Test
    public void givenUSStateCensus_whenSortedState_shouldeReturnResult() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.STATE);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulation_shouldeReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Wyoming", censusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulationByDecending_shouldeReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenUSStateCensusData_whenSortedOnArea_shouldReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.AREA);
            USCensusCSV[]censusCSV = new Gson().fromJson(sortedCensusData,  USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenUSStateCensusData_whenSortedOnAreaByDesendingOrder_shouldReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.AREA);
            USCensusCSV[]censusCSV = new Gson().fromJson(sortedCensusData,  USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenUSStateCensusData_whenSortedDensity_shouldReturnResult()
    {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSStateCensusData_whenSortedDensityByDecending_shouldReturnResult()
    {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_FILE_PATH);
            String sortedCensusData = censusAnalyser.getSortedByField(EnumField.DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}