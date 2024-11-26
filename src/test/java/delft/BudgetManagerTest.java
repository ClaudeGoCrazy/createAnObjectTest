package delft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BudgetManagerTest {

    private BudgetManager budgetManager;

    @BeforeEach
    void setUp() {
        budgetManager = new BudgetManager();
    }

    @Test
    void testAddExpense() {
        budgetManager.addExpense("Food", "Groceries", 50.0);

        List<BudgetManager.Expense> expenses = budgetManager.getExpenses();
        assertEquals(1, expenses.size(), "There should be one expense added.");
        BudgetManager.Expense expense = expenses.get(0);

        assertEquals("Food", expense.category, "Category should match the added expense.");
        assertEquals("Groceries", expense.description, "Description should match the added expense.");
        assertEquals(50.0, expense.amount, 0.001, "Amount should match the added expense.");
    }

    @Test
    void testPlanMonthlyBudget() {
        budgetManager.planMonthlyBudget("Rent", 1200.0);

        String sixMonthPlan = budgetManager.generateSixMonthPlan();
        assertTrue(sixMonthPlan.contains("Category: Rent"), "The plan should include the category 'Rent'.");
        assertTrue(sixMonthPlan.contains("Monthly Budget: $1200.00"), "The monthly budget should match the planned amount.");
        assertTrue(sixMonthPlan.contains("6-Month Budget: $7200.00"), "The 6-month budget should be calculated correctly.");
    }

    @Test
    void testGenerateReportWithMultipleExpenses() {
        budgetManager.addExpense("Food", "Groceries", 50.0);
        budgetManager.addExpense("Food", "Dining Out", 25.0);
        budgetManager.addExpense("Transport", "Gas", 40.0);

        BudgetManager.Report report = budgetManager.generateReport();

        assertNotNull(report, "The generated report should not be null.");
        assertEquals(115.0, report.totalSpent, 0.001, "Total spent should match the sum of all expenses.");

        HashMap<String, Double> categoryTotals = report.categoryTotals;
        assertEquals(2, categoryTotals.size(), "There should be two categories in the report.");
        assertEquals(75.0, categoryTotals.get("Food"), 0.001, "Food category total should match the sum of its expenses.");
        assertEquals(40.0, categoryTotals.get("Transport"), 0.001, "Transport category total should match the sum of its expenses.");
    }

    @Test
    void testGenerateSixMonthPlanWithMultipleCategories() {
        budgetManager.planMonthlyBudget("Food", 300.0);
        budgetManager.planMonthlyBudget("Transport", 150.0);

        String sixMonthPlan = budgetManager.generateSixMonthPlan();

        assertTrue(sixMonthPlan.contains("Category: Food"), "The plan should include the category 'Food'.");
        assertTrue(sixMonthPlan.contains("Monthly Budget: $300.00"), "Food monthly budget should match the planned amount.");
        assertTrue(sixMonthPlan.contains("6-Month Budget: $1800.00"), "Food 6-month budget should be calculated correctly.");

        assertTrue(sixMonthPlan.contains("Category: Transport"), "The plan should include the category 'Transport'.");
        assertTrue(sixMonthPlan.contains("Monthly Budget: $150.00"), "Transport monthly budget should match the planned amount.");
        assertTrue(sixMonthPlan.contains("6-Month Budget: $900.00"), "Transport 6-month budget should be calculated correctly.");

        assertTrue(sixMonthPlan.contains("Total Planned Budget for 6 Months: $2700.00"),
                "The total planned budget should sum up all categories for 6 months.");
    }


    @Test
    void testToStringForReport() {
        budgetManager.addExpense("Food", "Groceries", 50.0);
        budgetManager.addExpense("Food", "Dining Out", 25.0);
        budgetManager.addExpense("Transport", "Gas", 40.0);

        BudgetManager.Report report = budgetManager.generateReport();

        String reportString = report.toString();
        assertTrue(reportString.contains("Category: Food, Total: $75.00"), "Report string should include correct totals for Food.");
        assertTrue(reportString.contains("Category: Transport, Total: $40.00"), "Report string should include correct totals for Transport.");
        assertTrue(reportString.contains("Total Spent: $115.00"), "Report string should include the total spent.");
    }

    @Test
    void testToStringForExpense() {
        BudgetManager.Expense expense = new BudgetManager.Expense("Food", "Groceries", 50.0);

        String expenseString = expense.toString();
        assertEquals("Category: Food, Description: Groceries, Amount: $50.0", expenseString,
                "Expense toString() should return the correct format.");
    }

    @Test
    void testEmptyReport() {
        BudgetManager.Report report = budgetManager.generateReport();

        assertNotNull(report, "Report should not be null for an empty list of expenses.");
        assertEquals(0.0, report.totalSpent, 0.001, "Total spent should be 0 for an empty list of expenses.");
        assertTrue(report.categoryTotals.isEmpty(), "Category totals should be empty for no expenses.");
    }
}
