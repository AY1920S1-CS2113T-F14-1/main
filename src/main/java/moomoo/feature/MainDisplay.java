package moomoo.feature;

import moomoo.feature.category.CategoryList;

import java.util.ArrayList;
import java.util.HashMap;
import java.text.DecimalFormat;


public class MainDisplay {

    private static DecimalFormat df = new DecimalFormat("0.00");

    // Colours to be used for Linux only
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    // Short cut for Bars used to build table
    private static final String TOP_BORDERLEFT = ".-------------------.";
    private static final String TOP_BORDERRIGHT = "--------------------------";
    private static final String BORDER_LEFT = "|                   |";
    private static final String MONTH_LEFT = "|Month: ";
    private static final String YEAR_LEFT = "|Year: ";
    private static final String BUDGET_LEFT = "|" + ANSI_CYAN + "Budget" + ANSI_RESET + ":            |";
    private static final String SAVINGS_LEFT = "|" + ANSI_YELLOW + "Savings" + ANSI_RESET + ":           |";
    private static final String TOTAL_LEFT = "|" + ANSI_GREEN + "Total" + ANSI_RESET + ":             |";
    private static final String MISC_TITLES = "|" + TOP_BORDERRIGHT + ".\n"
            + BORDER_LEFT + "misc                      |\n"
            + BORDER_LEFT + TOP_BORDERRIGHT + ".\n"
            + BORDER_LEFT + "              |           |\n"
            + TOP_BORDERLEFT + TOP_BORDERRIGHT + ".\n"
            + TOTAL_LEFT + "                          |\n"
            + TOP_BORDERLEFT + TOP_BORDERRIGHT + ".\n"
            + BUDGET_LEFT + "                          |\n"
            + TOP_BORDERLEFT + TOP_BORDERRIGHT + ".\n"
            + SAVINGS_LEFT + "                          |\n"
            + TOP_BORDERLEFT + TOP_BORDERRIGHT + ".\n";

    public MainDisplay() {

    }

    /**
     * This function is called when user specifies a month and year.
     * We want to find out the maximum amount of Expenditures in a valid Category as this will be the number of rows
     * in our table output.
     * @param categoryList  categoryList to be passed into the function.
     * @param month month that the user wants to see a summary of.
     * @param year  year that the user wants to see a summary of.
     * @return an Integer corresponding to the number of rows.
     */
    public int getMonthsExpSize(CategoryList categoryList, int month, int year) throws MooMooException {
        int monthsExpSize = 0;
        int expSize;
        for (int i = 0; i < categoryList.size(); i++) {
            expSize = 0;
            for (int j = 0; j < categoryList.get(i).size(); j++) {
                if (categoryList.get(i).get(j).getDate().getMonthValue() == month
                        && categoryList.get(i).get(j).getDate().getYear() == year) {
                    expSize += 1;
                }
            }
            if (expSize != 0 && expSize >= monthsExpSize) {
                monthsExpSize = expSize;
            }
        }
        return monthsExpSize;
    }

    /**
     * This function is called when user does not specify a month and a year.
     * We want to find out the amount of Categories that are in the CategoryList as this will be the number of columns
     * in our table output.
     * @param categoryList  categoryList to be passed into the function.
     * @return an Integer corresponding to the number of columns.
     */
    public int getCatListSize(CategoryList categoryList) {
        int catNum = categoryList.size();
        return catNum;
    }

    /**
     * Returns the current maximum category size in the categoryList.
     *
     * @param categoryList The CategoryList containing all the categories.
     * @return the maximum category size.
     */
    public int getMaxCatSize(CategoryList categoryList) throws MooMooException {
        int maxCatSize = 0;
        for (int i = 0; i < categoryList.size(); i++) {
            int newCatSize = 0;
            for (int j = 0; j < categoryList.get(i).size(); j++) {
                newCatSize += 1;
            }
            if (newCatSize >= maxCatSize) {
                maxCatSize = newCatSize;
            }
        }
        return maxCatSize;
    }

    private String[] monthsInYear = {
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    };


    private String openCloseLines = "";
    private String blankSpaceMth = "";
    private String blankSpaceYr = "";
    private String blankSpaceCat = "";
    private String blankSpaceExp = "";
    private String blankSpaceBud = "";
    private String blankSpaceSav = "";
    private String blankSpaceCost = "";
    private String blankSpaceTot = "";

    /**
     * This function takes in several parameters in order to append together a final string to be printed out as a
     * table as final output.
     * @param month month that the user wants to view
     * @param year year that the user wants to view
     * @param rows rows corresponds to the max number of expenditures under a category
     * @param cols cols corresponds to the total number of categories in categoryList
     * @param categoryList categoryList is an array list that stores all the categories
     * @param budget budget stores all the individual budgets of each categories
     * @return returns a string to be printed out as the main display
     */
    public String newToPrint(int month, int year, int rows, int cols, CategoryList categoryList, Budget budget)
            throws MooMooException {
        //System.out.println(DEFAULT);
        String output = "";

        for (int i = 0; i < ((27 * cols - 12) / 2); i++) {          // Condition for Category Space Set
            blankSpaceCat += " ";
        }
        for (int i = 0; i < (27 * cols - 1); i++) {                     // Condition for Top Header Set
            openCloseLines += "-";
        }
        openCloseLines += ".";

        if (cols == 0) {        // Scenario where there is no Category in the specified periods
            output = TOP_BORDERLEFT + TOP_BORDERRIGHT  + ".\n";
            blankSpaceCat = "       ";

            if (month == 0 && year == 0) {          //  Case 1: View All -> Default Misc
                for (int i = 0; i <= 8; i++) {
                    blankSpaceMth += " ";
                }
                for (int i = 0; i <= 9; i++) {
                    blankSpaceYr += " ";
                }
                output += MONTH_LEFT + "All" + blankSpaceMth + "|" + blankSpaceCat
                        + "<" + ANSI_BLUE + "Categories" + ANSI_RESET + ">" + blankSpaceCat + "|\n" + YEAR_LEFT
                        + "All" + blankSpaceYr + MISC_TITLES;
            } else {                                //  Case 2: View Specific Month -> Default Misc
                for (int i = 0; i <= 11 - monthsInYear[month - 1].length(); i++) {
                    blankSpaceMth += " ";
                }
                for (int i = 0; i <= 8; i++) {
                    blankSpaceYr += " ";
                }
                output += MONTH_LEFT + monthsInYear[month - 1] + blankSpaceMth + "|" + blankSpaceCat
                        + "<" + ANSI_BLUE + "Categories" + ANSI_RESET + ">" + blankSpaceCat + "|\n" + YEAR_LEFT
                        + year + blankSpaceYr + MISC_TITLES;
            }
        } else {            // Scenario where there are Categories in the specified periods
            output += TOP_BORDERLEFT + openCloseLines + "\n";

            if (month == 0 && year == 0) {          //  Case 1: View All
                for (int i = 0; i <= 8; i++) {
                    blankSpaceMth += " ";
                }
                for (int i = 0; i <= 9; i++) {
                    blankSpaceYr += " ";
                }
                output += MONTH_LEFT + "All" + blankSpaceMth + "|" + blankSpaceCat
                        + "<" + ANSI_BLUE + "Categories" + ANSI_RESET + ">";

                if (cols % 2 == 0) {
                    String blankSpaceTemp = blankSpaceCat;
                    blankSpaceTemp = blankSpaceTemp.substring(0, blankSpaceTemp.length() - 1);
                    output += blankSpaceTemp;
                } else {
                    output += blankSpaceCat;
                }

                output += "|\n" + YEAR_LEFT + "All" + blankSpaceYr + "|";
                for (int i = 0; i < cols; i++) {
                    output += TOP_BORDERRIGHT + ".";
                }

                output += "\n" + BORDER_LEFT;

                // Printing out the line with all the categories
                for (int i = 0; i < cols; i++) {
                    blankSpaceCat = "";
                    String catName = categoryList.get(i).name();
                    if (catName.length() > 26) {
                        catName = catName.substring(0, 23) + "...";
                    } else {
                        for (int j = 0; j < (26 - categoryList.get(i).name().length()); j++) {
                            blankSpaceCat += " ";
                        }
                    }
                    output += ANSI_PURPLE + catName + ANSI_RESET + blankSpaceCat + "|";
                }
                output += "\n" + BORDER_LEFT + openCloseLines + "\n";

                // Printing out the lines corresponding to number of rows with both expenditureName and Amount
                for (int i = 0; i < rows; i++) {
                    output += BORDER_LEFT;
                    for (int j = 0; j < cols; j++) {
                        if (i < categoryList.get(j).size()) {
                            String expenditureName = categoryList.get(j).get(i).getName();
                            String amountString = df.format(categoryList.get(j).get(i).getCost());
                            // prints out each individual expenditureName if it exists
                            if (expenditureName.length() > 14) {
                                expenditureName = expenditureName.substring(0, 11) + "...";
                            } else {
                                blankSpaceExp = "";
                                for (int h = 0; h < (14 - categoryList.get(j).get(i).getName().length()); h++) {
                                    blankSpaceExp += " ";
                                }
                            }
                            if (amountString.length() > 10) {
                                amountString = amountString.substring(0, 7) + "...";
                            } else {
                                blankSpaceCost = "";
                                // prints out each individual expenditure Cost if it exists
                                for (int k = 0; k < (10 - amountString.length()); k++) {
                                    blankSpaceCost += " ";
                                }
                            }
                            output += expenditureName + blankSpaceExp + "|"
                                    //+ ANSI_GREEN + "$" + ANSI_RESET
                                    + "$"
                                    + amountString + blankSpaceCost + "|";
                        } else {
                            // if expenditure dosen't exist, print out the filler space
                            output += "              " + "|" + "           |";
                        }
                    }
                    output += "\n";
                }
                output += TOP_BORDERLEFT + openCloseLines + "\n";
                output += TOTAL_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceTot = "";
                    String totString = df.format(categoryList.get(i).getOverallAmount());
                    if (totString.length() > 25) {
                        totString = totString.substring(0, 22) + "...";
                    } else {
                        for (int j = 0; j < (25 - totString.length()); j++) {
                            blankSpaceTot += " ";
                        }
                    }
                    output += "$" + totString + blankSpaceTot + "|";
                }

                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
                output += BUDGET_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceBud = "";
                    String budName = df.format(budget.getBudgetFromCategory(categoryList.get(i).name()));
                    int budLen = 25 - budName.length();
                    if (budName.length() > 25) {
                        budName = budName.substring(0, 22) + "...";
                    } else {
                        for (int j = 0; j < budLen; j++) {
                            blankSpaceBud += " ";
                        }
                    }
                    output += "$" + budName + blankSpaceBud + "|";
                }

                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
                output += SAVINGS_LEFT;

                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceSav = "";
                    double tot = categoryList.get(i).getOverallAmount();
                    double bud = budget.getBudgetFromCategory(categoryList.get(i).name());
                    double sav = bud - tot;
                    String savString = df.format(sav);
                    if (savString.length() > 25) {
                        savString = savString.substring(0, 22) + "...";
                    } else {
                        for (int j = 0; j < (25 - savString.length()); j++) {
                            blankSpaceSav += " ";
                        }
                    }
                    if (sav < 0) {
                        output += "$" + ANSI_RED + savString + ANSI_RESET + blankSpaceSav + "|";
                    } else {
                        output += "$" + savString + blankSpaceSav + "|";
                    }
                }
                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";


            } else {                                //  Case 2: View Specific Month
                for (int i = 0; i <= 11 - monthsInYear[month - 1].length(); i++) {
                    blankSpaceMth += " ";
                }
                for (int i = 0; i <= 8; i++) {
                    blankSpaceYr += " ";
                }

                // Every time the main display command is called user specifies month/year create new temp hash map
                HashMap<String,ArrayList<String>> newCategoryList = new HashMap<>();

                // Initializes the HashMap for use later as newCategoryList
                for (int i = 0; i < categoryList.size(); i++) {
                    // If array list does not exist, create it
                    ArrayList<String> stringList = new ArrayList<>();
                    for (int j = 0; j < categoryList.get(i).size(); j++) {
                        // Add to array list only if it matches the month and year user is looking for
                        if (categoryList.get(i).get(j).getDate().getMonthValue() == month
                                && categoryList.get(i).get(j).getDate().getYear() == year) {
                            stringList.add(categoryList.get(i).get(j).getName());
                            stringList.add(df.format(categoryList.get(i).get(j).getCost()));
                        }
                    }
                    newCategoryList.put(categoryList.get(i).name(), stringList);
                }

                output += MONTH_LEFT + monthsInYear[month - 1] + blankSpaceMth + "|" + blankSpaceCat
                        + "<" + ANSI_BLUE + "Categories" + ANSI_RESET + ">";

                if (cols % 2 == 0) {
                    String blankSpaceTemp = blankSpaceCat;
                    blankSpaceTemp = blankSpaceTemp.substring(0, blankSpaceTemp.length() - 1);
                    output += blankSpaceTemp;
                } else {
                    output += blankSpaceCat;
                }

                output += "|\n" + YEAR_LEFT + year + blankSpaceYr + "|";
                for (int i = 0; i < cols; i++) {
                    output += TOP_BORDERRIGHT + ".";
                }
                output += "\n" + BORDER_LEFT;

                // Printing out the line with all the categories
                for (int i = 0; i < cols; i++) {
                    String catName = categoryList.get(i).name();
                    blankSpaceCat = "";
                    if (catName.length() > 26) {
                        catName = catName.substring(0, 23) + "...";
                    } else {
                        for (int j = 0; j < (26 - categoryList.get(i).name().length()); j++) {
                            blankSpaceCat += " ";
                        }
                    }
                    output += ANSI_PURPLE + catName + ANSI_RESET + blankSpaceCat + "|";
                }
                output += "\n" + BORDER_LEFT + openCloseLines + "\n";

                // Printing out the lines corresponding to number of rows with both expenditureName and Amount
                for (int i = 0; i < rows; i++) {
                    output += BORDER_LEFT;
                    for (int j = 0; j < cols; j++) {
                        String categoryName = categoryList.get(j).name();
                        if (i < categoryList.get(j).size()) {
                            String expenditureName = "";
                            String amountString = "";
                            if (!newCategoryList.get(categoryName).isEmpty()) {
                                // for nth row, nth category, extract the expenditure name (odd no in array list)
                                expenditureName = (i + 1) + ". " + newCategoryList.get(categoryName).get(0);
                                // for nth row, nth category, extract the expenditure cost (even no in array list)
                                amountString = newCategoryList.get(categoryName).get(1);
                            }
                            for (int k = 0; k < 2; k++) {
                                if (!newCategoryList.get(categoryName).isEmpty()) {
                                    newCategoryList.get(categoryName).remove(0);
                                }
                            }
                            if (expenditureName.length() > 14) {
                                expenditureName = expenditureName.substring(0, 11) + "...";
                            } else {
                                blankSpaceExp = "";
                                // prints out each individual expenditure Name if it exists
                                for (int h = 0; h < (14 - expenditureName.length()); h++) {
                                    blankSpaceExp += " ";
                                }
                            }
                            if (amountString.length() > 10) {
                                amountString = amountString.substring(0, 7) + "...";
                            } else {
                                blankSpaceCost = "";
                                // prints out each individual expenditure Cost if it exists
                                for (int g = 0; g < (10 - amountString.length()); g++) {
                                    blankSpaceCost += " ";
                                }
                            }

                            output += expenditureName + blankSpaceExp + "|"
                                    //+ ANSI_GREEN + "$" + ANSI_RESET
                                    + "$"
                                    + amountString + blankSpaceCost + "|";
                        } else {
                            // if expenditure dosen't exist, print out the filler space
                            output += "              " + "|" + "           |";
                        }
                    }
                    output += "\n";
                }

                // Prints out the line that contains all the total cost for each category
                output += TOP_BORDERLEFT + openCloseLines + "\n";
                output += TOTAL_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceTot = "";
                    int totalLen = df.format(categoryList.get(i).getTotal(month, year)).length();
                    String totalString = df.format(categoryList.get(i).getTotal(month, year));
                    if (totalString.length() > 25) {
                        totalString = totalString.substring(0, 22) + "...";
                    } else {
                        for (int j = 0; j < (25 - totalLen); j++) {
                            blankSpaceTot += " ";
                        }
                    }
                    output += "$" + totalString + blankSpaceTot + "|";
                }

                // Prints out the line that contains all the budgets for each category
                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
                output += BUDGET_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceBud = "";
                    String budName = df.format(budget.getBudgetFromCategory(categoryList.get(i).name()));
                    int budLen = 25 - budName.length();
                    if (budName.length() > 25) {
                        budName = budName.substring(0, 22) + "...";
                    } else {
                        for (int j = 0; j < budLen; j++) {
                            blankSpaceBud += " ";
                        }
                    }
                    output += "$" + budName + blankSpaceBud + "|";
                }

                // Prints out the line that contains all the savings for each category
                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
                output += SAVINGS_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceSav = "";
                    double tot = categoryList.get(i).getTotal(month, year);
                    double bud = budget.getBudgetFromCategory(categoryList.get(i).name());
                    double sav = bud - tot;
                    String savString = df.format(sav);
                    if (savString.length() > 25) {
                        savString = savString.substring(0, 22) + "...";
                    } else {
                        for (int j = 0; j < (25 - df.format(sav).length()); j++) {
                            blankSpaceSav += " ";
                        }
                    }
                    if (sav < 0) {
                        output += "$" + ANSI_RED + savString + ANSI_RESET + blankSpaceSav + "|";
                    } else {
                        output += "$" + savString + blankSpaceSav + "|";
                    }
                }
                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
            }
        }
        return output;
    }
}
