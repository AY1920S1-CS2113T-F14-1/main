package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.storage.Storage;
import moomoo.feature.MooMooException;
import moomoo.feature.category.Category;
import moomoo.feature.category.CategoryList;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * View the savings for respective categories.
 */
public class SavingsBudgetCommand extends Command {
    private ArrayList<String> categories;
    private LocalDate start;
    private LocalDate end;
    private DecimalFormat df;

    /**
     * Initializes the Savings Command.
     * @param isExit Boolean variable to determine if program should exit.
     * @param categories List of categories to view savings for.
     * @param startPeriod Start period consisting of month and year.
     * @param endPeriod End period consisting of month and year.
     */
    public SavingsBudgetCommand(boolean isExit, ArrayList<String> categories,
                                LocalDate startPeriod, LocalDate endPeriod) {
        super(isExit, "");
        this.categories = categories;
        this.start = startPeriod;
        this.end = endPeriod;
        this.df = new DecimalFormat("#.00");

    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {
        ArrayList<String> array = new ArrayList<>();
        String[] outputArray;
        String outputValue = "";
        double totalSavings = 0;
        if (categories.size() == 0) {
            for (int i = 0; i < categoryList.getCategoryList().size(); ++i) {
                categories.add(categoryList.getCategoryList().get(i).name());
            }
        }

        for (String iteratorCategory : categories) {
            iteratorCategory = iteratorCategory.toLowerCase();
            Category currentCategory = categoryList.get(iteratorCategory);

            if (currentCategory == null) {
                outputValue = iteratorCategory + " category does not exist."
                        + " Please create it first.";
                array.add(outputValue);
                continue;
            }
            if (budget.getBudgetFromCategory(iteratorCategory.toLowerCase()) == 0) {
                outputValue = "The budget for " + iteratorCategory + " does not exist."
                        + " Please set it using budget set.";
                array.add(outputValue);
                continue;
            }

            if (end == null) {
                outputArray = viewSingleMonthSavings(budget, iteratorCategory, currentCategory, outputValue);
                //outputValue = outputArray[0];
                array.add(outputArray[0]);
            } else {
                outputArray = viewMultiMonthSaving(budget, iteratorCategory, currentCategory, outputValue);
                //outputValue = outputArray[0];
                array.add(outputArray[0]);
            }

            totalSavings += Double.parseDouble(outputArray[1]);
        }
        if (totalSavings > 0) {
            outputValue = "Your total savings: $" + df.format(totalSavings);
            array.add(outputValue);
            outputValue = Ui.showInCowBox(array);
        } else if (totalSavings == 0) {
            outputValue = "Your total savings : $0";
            array.add(outputValue);
            outputValue = Ui.showInCowBox(array);
        } else {
            totalSavings = Math.abs(totalSavings);
            if (totalSavings < 1) {
                outputValue = "You have overspent your total budget by: $0" + df.format(totalSavings);
            } else {
                outputValue = "You have overspent your total budget by: $" + df.format(totalSavings);
            }
            array.add(outputValue);
            outputValue = Ui.showInCowBox(array);
        }

        Ui.setOutput(outputValue);
    }

    private String[] viewSingleMonthSavings(Budget budget, String iteratorCategory,
                                            Category currentCategory, String outputVal) {
        double totalSavings = 0;
        String output = outputVal;
        double savings = budget.getBudgetFromCategory(iteratorCategory)
                - currentCategory.getTotal(start.getMonthValue(), start.getYear());

        totalSavings += savings;
        if (savings > 0) {
            output = "Your savings for " + iteratorCategory + " for " + start.getMonth()
                    + " " + start.getYear() + " is: $"
                    + df.format(savings);
        } else if (savings == 0) {
            output = "Your savings for " + iteratorCategory + " for " + start.getMonth()
                    + " " + start.getYear() + " is: $0";
        } else {
            savings = Math.abs(savings);
            if (savings < 1) {
                output = "You have overspent your budget for " + iteratorCategory + " for " + start.getMonth()
                        + " " + start.getYear() + " by $0" + df.format(savings);
            } else {
                output = "You have overspent your budget for " + iteratorCategory + " for " + start.getMonth()
                        + " " + start.getYear() + " by $" + df.format((savings));
            }

        }

        return new String[]{output, Double.toString(totalSavings)};
    }

    private String[] viewMultiMonthSaving(Budget budget, String iteratorCategory,
                                          Category currentCategory, String outputVal) {
        int numberOfMonths = 0;
        int numberOfYears = end.getYear() - start.getYear();
        double totalExpenditure = 0;
        double totalSavings = 0;
        String output = outputVal;

        if (numberOfYears > 0) {
            int startMonthValue = start.getMonthValue();
            int endMonthValue = 12;
            for (int currentYear = start.getYear(); currentYear <= end.getYear(); ++currentYear) {
                for (int currentMonth = startMonthValue; currentMonth <= endMonthValue; ++currentMonth) {
                    ++numberOfMonths;
                    totalExpenditure += currentCategory.getTotal(currentMonth, currentYear);

                }
                startMonthValue = 1;
                if (currentYear == end.getYear() - 1) {
                    endMonthValue = end.getMonthValue();
                }
            }
        } else {
            numberOfMonths = end.getMonthValue() - start.getMonthValue() + 1;
            for (int i = start.getMonthValue(); i < end.getMonthValue() + 1; ++i) {
                totalExpenditure += currentCategory.getTotal(i, start.getYear());
            }
        }
        double savings = (numberOfMonths * budget.getBudgetFromCategory(iteratorCategory))
                - totalExpenditure;
        totalSavings += savings;

        if (savings > 0) {
            output = "Your savings for " + iteratorCategory + " from " + start.getMonth() + " "
                    + start.getYear() + " to "
                    + end.getMonth() + " " + end.getYear() + " is: $"
                    + df.format(savings);
        } else if (savings == 0) {
            output = "Your savings for " + iteratorCategory + " from " + start.getMonth() + " "
                    + start.getYear() + " to "
                    + end.getMonth() + " " + end.getYear() + " is: $0";
        }  else {
            savings = Math.abs(savings);
            if (savings < 1) {
                output = "You have overspent for your budget for " + iteratorCategory + " from "
                        + start.getMonth() + " " + start.getYear() + " to "
                        + end.getMonth() + " " + end.getYear() + " by: $0" + df.format(savings);
            } else {
                output = "You have overspent for your budget for " + iteratorCategory + " from "
                        + start.getMonth() + " " + start.getYear() + " to "
                        + end.getMonth() + " " + end.getYear() + " by: $" + df.format(savings);
            }

        }
        return new String[]{output, Double.toString(totalSavings)};

    }
}
