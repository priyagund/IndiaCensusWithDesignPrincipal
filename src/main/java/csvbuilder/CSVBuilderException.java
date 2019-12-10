package csvbuilder;

public class CSVBuilderException extends Exception
{
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE;

    }
   ExceptionType type;

//    public CSVBuilderException(ExceptionType type,String message) {
//        super(message);
//        this.type = type;
//    }
   public CSVBuilderException(String message,String name) {
      super(message);
       this.type = CSVBuilderException.ExceptionType.valueOf(name);
    }
}
