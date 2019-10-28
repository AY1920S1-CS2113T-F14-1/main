package moomoo.task;

import java.time.LocalDate;
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
        monthTotal += newExpenditure.getCost();
        category.add(newExpenditure);
    }

    /**
     * Calculates the total expenditure for every entry in the category.
     * @return totalCost
     */
    double getCategoryMonthTotal() {
        double totalCost = 0.00;
        for (Expenditure expenditure : category) {
            LocalDate date = expenditure.date;
            LocalDate now = LocalDate.now(); // Now see if the month and year match.
            if (date.getMonth() == now.getMonth() && date.getYear() == now.getYear()) {
                // You have a hit.
                totalCost += expenditure.getCost();
            }
        }
        return totalCost;
    }
    
    public void setCategoryMonthTotal() {
        monthTotal = getCategoryMonthTotal();
    }
    
    /**
     * Return the expenditure with the largest value.
     * @return expenditure The value of the largest expenditure
     */
    public double getLargestExpenditure() {
        double expenditure = 0;
        for (Expenditure exp : category) {
            if (exp.getCost() > expenditure) {
                expenditure = exp.getCost();
            }
        }
        return expenditure;
    }
    
    /**
     * The expenditure with the longest name.
     * @return The name of the longest expenditure
     */
    public int getLongestExpenditure() {
        int longestName = 0;
        for (Expenditure exp : category) {
            if (exp.toString().length() > longestName) {
                longestName = exp.toString().length();
            }
            if (longestName >= 14) {
                longestName = 14;
                break;
            }
        }
        return longestName;
    }

    /**
     * Returns the total expenditure for the given month and year.
     * @param month integer value representing the month
     * @param year integer value representing the value.
     * @return total expenditure spent for corresponding month and year
     */
    public double getCategoryTotalPerMonthYear(int month, int year) {
        double totalCost = 0.00;
        for (int i = 0; i < category.size(); i++) {
            Expenditure currExpenditure = category.get(i);
            if (currExpenditure.getDateTime().getMonthValue() == month
                    && currExpenditure.getDateTime().getYear() == year) {
                totalCost += currExpenditure.getCost();
            }
        }
        return totalCost;
    }

    public double getMonthlyTotal(int month) {
        return monthTotal;
    }

    public void addExpenditure() {

    }

    public void editExpenditure() {

    }

    public void deleteExpenditure() {

    }
    
    /**
     * Set the month total (FOR TESTING PURPOSES).
     * @param value The value to be set
     */
    public void setMonthTotal(int value) {
        monthTotal = value;
    }
    
    /**
     * Populate the categoryList array with dummy variables. FOR TESTING PURPOSES.
     */
    public void testPopulate() {
        ArrayList<String> population = new ArrayList<String>();
        population.add("SanicTheHodgepodge");
        population.add("MetalGearLiquid");
        population.add("GTB");
        population.add("Far:Automata");
        population.add("League of Mobile Legends");
        for (int i = 0; i < 5; i += 1) {
            Expenditure newExp = new Expenditure(population.get(i), i * 100 / (i + 3), null);
            category.add(newExp);
        }
        monthTotal = 75;
    }
}
