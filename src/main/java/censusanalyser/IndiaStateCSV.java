package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSV
{
    @CsvBindByName
    private int SrNo;
    @CsvBindByName(column = "State Name",required = true)
    private String StateName;
    @CsvBindByName(column = "StateCode",required = true)
    private String StateCode;
    @CsvBindByName
    private String TIN;

    public int getSrNo() {
        return SrNo;
    }

    public void setSrNo(int srNo) {
        SrNo = srNo;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getStateCode() {
        return StateCode;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }
}
