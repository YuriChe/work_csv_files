package appCSV.entity;

public class DataRecord {

    public Long getCounterRowsFile() {
        return counterRowsFile;
    }

    public void setCounterRowsFile(Long counterRowsFile) {
        this.counterRowsFile = counterRowsFile;
    }

    public Long getCounterDBRecords() {
        return counterDBRecords;
    }

    public void setCounterDBRecords(Long counterDBRecords) {
        this.counterDBRecords = counterDBRecords;
    }

    private Long counterRowsFile;
    private Long counterDBRecords;

    public String getMethodReading() {
        return methodReading;
    }

    public void setMethodReading(String methodReading) {
        this.methodReading = methodReading;
    }

    private String methodReading;

}
