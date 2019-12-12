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

 public String getState() {
  return state;
 }

 public void setState(String state) {
  this.state = state;
 }

 public String getStateId() {
  return stateId;
 }

 public void setStateId(String stateId) {
  this.stateId = stateId;
 }

 public int getPopulation() {
  return population;
 }

 public void setPopulation(int population) {
  this.population = population;
 }

 public double getTotalArea() {
  return totalArea;
 }

 public void setTotalArea(double totalArea) {
  this.totalArea = totalArea;
 }

 public int getPopulationDensity() {
  return populationDensity;
 }

 public void setPopulationDensity(int populationDensity) {
  this.populationDensity = populationDensity;
 }

 public String getStateCode() {
  return stateCode;
 }

 public void setStateCode(String stateCode) {
  this.stateCode = stateCode;
 }
}
