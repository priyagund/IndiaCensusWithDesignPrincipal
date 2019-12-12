package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV
{
    @CsvBindByName(column = "State Name",required = true)
   public String state;
    @CsvBindByName(column = "State Id",required = true)
    public String stateId;
    @CsvBindByName(column = "Populattion",required = true)
    public int population;
    @CsvBindByName(column = "totalArea",required = true)
    public double totalArea;
    @CsvBindByName(column = "populationDensity",required = true)
    public int populationDensity;
    @CsvBindByName(column = "stateCode",required = true)
     public String stateCode;
}
