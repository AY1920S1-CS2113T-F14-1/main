package moomoo.task;

import java.util.ArrayList;

public class Category {
    private double monthTotal;
    private String categoryName;
    private ArrayList<Expenditure> category;

    public Category() {

    }

    /**
     * Initializes a new category with a name, an empty list of expenditures, and a monthly total.
     * @param name category name
     */
    public Category(String name) {
        this.categoryName = name;
        this.category = new ArrayList<>();
        this.monthTotal = 0.00;
    }


    public int size() {
        return category.size();
    }

    public Expenditure get(int i) {
        return category.get(i);
    }

    public String toString() {
        return categoryName;
    }

    public void add(Expenditure newExpenditure) {
        category.add(newExpenditure);
    }

    public void deleteExpenditure(int expenditureNumber) {
        category.remove(expenditureNumber);
    }

    /**
     * Calculates the total expenditure for every entry in the category.
     * @return totalCost
     */
    public double getCategoryMonthTotal() {
        double totalCost = 0.00;
        for (int i = 0; i < category.size(); i++) {
            totalCost += category.get(i).amount();
        }
        return totalCost;
    }
    
    public double getMonthlyTotal(int month) {
        return monthTotal;
    }

    /**
     * Set the month total (FOR TESTING PURPOSES).
     * @param value The value to be set
     */
    public void setMonthTotal(int value) {
        monthTotal = value;
    }
    
}
