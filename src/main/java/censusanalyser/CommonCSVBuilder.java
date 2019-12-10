package censusanalyser;

import com.opencsv.CSVParser;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import csvbuilder.CSVBuilderException;
import csvbuilder.ICSVBuilder;
import org.apache.commons.csv.CSVFormat;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/*
public class CommonCSVBuilder<E>implements ICSVBuilder
{
    public<E> Iterator<E> getCSVFileIterator(Reader reader, Class<E>csvClass ) throws CSVBuilderException {
        return this.getCSVBean(reader,csvClass).iterator();
    }

    @Override
    public <E> List<E> getCSVFileList(Reader reader, Class<E> csvClass) throws CSVBuilderException
    {
        return this.getCSVBean(reader,csvClass).parse();
    }

    private CsvToBean getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            try (
                    Reader reader = Files.newBufferedReader(Paths.get());
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                            .withHeader("State", "Email", "Phone", "Country")
                            .withIgnoreHeaderCase()
                            .withTrim());
            ) {
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(CSVBuilderException.ExceptionType.UNABLE_TO_PARSE.name(),e.getMessage());

        }
    }

}
*/
