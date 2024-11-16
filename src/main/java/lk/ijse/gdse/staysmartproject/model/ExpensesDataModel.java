package lk.ijse.gdse.staysmartproject.model;

public class ExpensesDataModel {
    private static ExpensesDataModel instance = new ExpensesDataModel();
    private double totalExpenses;

    private ExpensesDataModel() {}

    public static ExpensesDataModel getInstance() {
        return instance;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}