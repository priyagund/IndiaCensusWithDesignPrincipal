package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Map;

public class USCensusAdaptorTest
{
    private String US_FILE_PATH="/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/USCensusData.csv";
    private String WRONG_FILE_PATH="/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/IPL2019FactsheetMostRuns.csv";

    @Test
    public void givenLoadUSCensusData_ifLoded_shouldReturnRecord()
    {
        USCensusAdaptor usCensusAdaptor=new USCensusAdaptor();
        Map<String, CensusDAO> noOfRecord = null;
        try {
            noOfRecord = usCensusAdaptor.loadCensusData(CensusAnalyser.Country.US,US_FILE_PATH);
            Assert.assertEquals(51,noOfRecord.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaStateCensusData_withWrongFileType_shouldThrowException() {
        try {
            USCensusAdaptor usCensusAdaptor=new USCensusAdaptor();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            usCensusAdaptor.loadCensusData(CensusAnalyser.Country.US,WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_withEmptyFile_shouldReturnException() {
        try {
            USCensusAdaptor usCensusAdaptor=new USCensusAdaptor();
            usCensusAdaptor.loadCensusData(CensusAnalyser.Country.US,US_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_withIncorrectHeader_shouldThrowException() {
        try {
            USCensusAdaptor usCensusAdaptor=new USCensusAdaptor();
            usCensusAdaptor.loadCensusData(CensusAnalyser.Country.US,US_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_withIncorrectDelimiter_shouldThrowException() {
        try {
            USCensusAdaptor usCensusAdaptor = new USCensusAdaptor();
            usCensusAdaptor.loadCensusData(CensusAnalyser.Country.US,US_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE, e.type);
        }
    }
}
