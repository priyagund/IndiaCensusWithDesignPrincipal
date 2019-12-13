package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class USCensusAdaptorTest
{
    private String US_FILE_PATH="/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/USCensusData.csv";
    private String WRONG_FILE_PATH="/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/IPL2019FactsheetMostRuns.csv";

    @Test
    public void givenIndiaStateCensusData_withWrongFileType_shouldThrowException() {
        USCensusAdaptor usCensusAdaptor=new USCensusAdaptor();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CensusAnalyserException.class);
        try {
            usCensusAdaptor.loadCensusData(CensusAnalyser.Country.US,WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_withEmptyFile_shouldReturnException() {
        USCensusAdaptor usCensusAdaptor=new USCensusAdaptor();
        try {
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
