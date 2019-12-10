package censusanalyser;

public class CensusAnalyserException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,NO_CENSUS_DATA,CENSUS_FILETYPE_PROBLEM,SOME_OTHER_ERROR_INFILE,EMPTY_FILE_ERROR;

    }
    ExceptionType type;

    public CensusAnalyserException(String message,String name) {
        super(message);
        this.type =ExceptionType.valueOf(name);
    }

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public CensusAnalyserException(ExceptionType type) {
        this.type = type;
    }
}
