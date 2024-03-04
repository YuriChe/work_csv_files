package appCSV.entity;

public class DataRecord {

    private Long counterRowsFile;

    private Long counterDBRecords;
    private String methodReading;

    public void setCounterRowsFile(Long counterRowsFile) {
        this.counterRowsFile = counterRowsFile;
    }

    public Long getCounterRowsFile() {
        return counterRowsFile;
    }

    public void setCounterDBRecords(Long counterDBRecords) {
        this.counterDBRecords = counterDBRecords;
    }

    public Long getCounterDBRecords() {
        return counterDBRecords;
    }

    public void setMethodReading(String methodReading) {
        this.methodReading = methodReading;
    }

    public String getMethodReading() {
        return methodReading;
    }

}
