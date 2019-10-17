package moomoo.task;

import moomoo.command.BudgetCommand;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    @Test
    public void testFileLoad() throws MooMooException, IOException {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File transactionsFile = File.createTempFile("transactions", ".txt");
        transactionsFile.deleteOnExit();

        File categoriesFile = File.createTempFile("categories", ".txt");
        categoriesFile.deleteOnExit();

        CategoryList newCatList = new CategoryList();
        newCatList.getCategoryList().add(new Category("sweets"));
        newCatList.getCategoryList().add(new Category("laptop"));
        newCatList.getCategoryList().add(new Category("desktop"));

        Budget newBudget = new Budget();
        Ui newUi = new Ui();
        TransactionList newTransList = new TransactionList();
        Storage newStorage = new Storage(budgetFile.getPath(), transactionsFile.getPath(), categoriesFile.getPath());

        BudgetCommand budgetCommand = new BudgetCommand(false, "budget set c/sweets b/500 c/laptop b/1500");
        budgetCommand.execute(newBudget, newCatList, newTransList, newUi, newStorage);

        HashMap<String, Double> newHashMap = newStorage.loadBudget(newCatList);
        assertEquals(500, newHashMap.get("sweets"));
        assertEquals(1500, newHashMap.get("laptop"));

        budgetCommand = new BudgetCommand(false, "budget edit c/sweets b/700 c/laptop b/1500");
        budgetCommand.execute(newBudget, newCatList, newTransList, newUi, newStorage);

        newHashMap = newStorage.loadBudget(newCatList);
        assertEquals(700, newHashMap.get("sweets"));
        assertEquals(1500, newHashMap.get("laptop"));
    }
}