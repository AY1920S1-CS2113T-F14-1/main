package moomoo.stubs;

import moomoo.task.Category;
import moomoo.task.Expenditure;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class CategoryStub extends Category {
    private String name;
    private ArrayList<ExpenditureStub> category;

    public CategoryStub() {

    }

    public CategoryStub(String name) {
        this.category = new ArrayList<>();
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public double getCategoryTotalPerMonthYear(int month, int year) {
        double totalCost = 0.00;
        for (int i = 0; i < category.size(); i++) {
            Expenditure currExpenditure = category.get(i);
            if (currExpenditure.getDate().getMonthValue() == month
                    && currExpenditure.getDate().getYear() == year) {
                totalCost += currExpenditure.getCost();
            }
        }
        return totalCost;
    }

    @Override
    public void add(Expenditure newExpenditure) {
        this.category.add(new ExpenditureStub("Value 1",100, LocalDateTime.of(2017, Month.DECEMBER, 20, 19, 30, 50)));
        this.category.add(new ExpenditureStub("Value 2", 100, LocalDateTime.of(2018, Month.DECEMBER, 20, 19, 30, 50)));
        this.category.add(new ExpenditureStub("Value 3", 100, LocalDateTime.of(2019, Month.JANUARY, 20, 19, 30, 50)));

        this.category.add(new ExpenditureStub("Value 4", 200, LocalDateTime.of(2019, Month.AUGUST, 20, 19, 30, 50)));
        this.category.add(new ExpenditureStub("Value 5", 150, LocalDateTime.of(2019, Month.SEPTEMBER, 20, 19, 30, 50)));
        this.category.add(new ExpenditureStub("Value 6", 50, LocalDateTime.of(2019, Month.OCTOBER, 25, 19, 30, 50)));
        this.category.add(new ExpenditureStub("Value 7", 65, LocalDateTime.of(2019, Month.NOVEMBER, 21, 19, 30, 50)));
    }
}
