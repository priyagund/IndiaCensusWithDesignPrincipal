package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class IndiaCensusAdaptorTest
{
    private String INDIA_FILE_PATH="/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/IndiaStateCensusData.csv";
    private String INDIA_STATE_CODE_FILE="/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/IndiaStateCode.json";
    private String WRONG_FILE_PATH="/home/admin165/Downloads/CensusAnalyser(2)/CensusAnalyser/src/test/resources/IPL2019FactsheetMostWkts.csv";

    @Test
    public void givenLoadCensusData_ifLoaded_shouldReturnRecord() {
        try {
            IndiaCensusAdaptor indiaCensusAdaptor=new IndiaCensusAdaptor();
            Map<String, CensusDAO> numOfRecords = indiaCensusAdaptor.loadCensusData(INDIA_FILE_PATH);
            Assert.assertEquals(27, numOfRecords.size());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenPassingFile_ifInsufficientFilePass_throwException()
    {
        try {
            IndiaCensusAdaptor indiaCensusAdaptor=new IndiaCensusAdaptor();
            indiaCensusAdaptor.loadCensusData(INDIA_FILE_PATH,INDIA_STATE_CODE_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_COUNT,e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_withWrongFileType_shouldThrowException() {
        IndiaCensusAdaptor indiaCensusAdaptor=new IndiaCensusAdaptor();
        ExpectedException exceptionRule = ExpectedException.none();
        exceptionRule.expect(CensusAnalyserException.class);
        try {
            indiaCensusAdaptor.loadCensusData(WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_withEmptyFile_shouldReturnException() {
      IndiaCensusAdaptor indiaCensusAdaptor=new IndiaCensusAdaptor();
        try {
            indiaCensusAdaptor.loadCensusData("");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_withIncorrectHeader_shouldThrowException() {
        try {
            IndiaCensusAdaptor indiaCensusAdaptor = new IndiaCensusAdaptor();
            indiaCensusAdaptor.loadCensusData(INDIA_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE, e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusData_withIncorrectDelimiter_shouldThrowException() {
        try {
            IndiaCensusAdaptor indiaCensusAdaptor = new IndiaCensusAdaptor();
            indiaCensusAdaptor.loadCensusData(INDIA_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_ERROR_INFILE, e.type);
        }
    }
}
