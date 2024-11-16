package lk.ijse.gdse.staysmartproject.model;

public class SharedDataModel {
    private static SharedDataModel instance = new SharedDataModel();
    private double totalRentAmount;

    private SharedDataModel() {}

    public static SharedDataModel getInstance() {
        return instance;
    }

    public double getTotalRentAmount() {
        return totalRentAmount;
    }

    public void setTotalRentAmount(double totalRentAmount) {
        this.totalRentAmount = totalRentAmount;
    }

}
