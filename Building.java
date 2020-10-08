
// Building contains the building number,executed time, total time and tempExecTime

public class Building {
    public int buildingNumber, executedTime, totalTime, tempExecTime;
    public Building(int buildingNumber, int executedTime, int totalTime) {
        this.buildingNumber = buildingNumber;
        this.executedTime = executedTime;
        this.tempExecTime = executedTime;
        this.totalTime = totalTime;
    }
    @Override
    public String toString() {
        //return "(" + buildingNumber + "," + executedTime + "," + tempExecTime + "," + totalTime + ")";
        return "(" + buildingNumber + "," + tempExecTime + "," + totalTime + ")";
    }
}