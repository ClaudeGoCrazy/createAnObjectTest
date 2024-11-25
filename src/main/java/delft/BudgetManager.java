package delft;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BudgetManager {

	// Inner class to represent an Expense
	static class Expense {
		String category;
		String description;
		double amount;

		public Expense(String category, String description, double amount) {
			this.category = category;
			this.description = description;
			this.amount = amount;
		}

		@Override
		public String toString() {
			return "Category: " + category + ", Description: " + description + ", Amount: $" + amount;
		}
	}

	// Inner class to represent a Report
	static class Report {
		HashMap<String, Double> categoryTotals;
		double totalSpent;

		public Report(HashMap<String, Double> categoryTotals, double totalSpent) {
			this.categoryTotals = categoryTotals;
			this.totalSpent = totalSpent;
		}

		@Override
		public String toString() {
			StringBuilder reportBuilder = new StringBuilder("Expense Report:\n");
			for (String category : categoryTotals.keySet()) {
				reportBuilder.append(String.format("Category: %s, Total: $%.2f%n", category, categoryTotals.get(category)));
			}
			reportBuilder.append(String.format("Total Spent: $%.2f%n", totalSpent));
			return reportBuilder.toString();
		}
	}

	private List<Expense> expenses;
	private HashMap<String, Double> plannedMonthlyBudget;

	public BudgetManager() {
		this.expenses = new ArrayList<>();
		this.plannedMonthlyBudget = new HashMap<>();
	}

	// Add a new expense
	public void addExpense(String category, String description, double amount) {
		expenses.add(new Expense(category, description, amount));
	}

	// Plan a monthly budget for a category
	public void planMonthlyBudget(String category, double amount) {
		plannedMonthlyBudget.put(category, amount);
	}

	// Generate a summary report
	public Report generateReport() {
		HashMap<String, Double> categoryTotals = new HashMap<>();
		double totalSpent = 0;

		for (Expense expense : expenses) {
			categoryTotals.put(
					expense.category,
					categoryTotals.getOrDefault(expense.category, 0.0) + expense.amount
			);
			totalSpent += expense.amount;
		}

		return new Report(categoryTotals, totalSpent);
	}

	// Generate a 6-month plan report
	public String generateSixMonthPlan() {
		StringBuilder planBuilder = new StringBuilder("6-Month Budget Plan:\n");
		double totalPlanned = 0;

		for (String category : plannedMonthlyBudget.keySet()) {
			double monthlyAmount = plannedMonthlyBudget.get(category);
			double sixMonthAmount = monthlyAmount * 6;
			planBuilder.append(String.format("Category: %s, Monthly Budget: $%.2f, 6-Month Budget: $%.2f%n",
					category, monthlyAmount, sixMonthAmount));
			totalPlanned += sixMonthAmount;
		}

		planBuilder.append(String.format("Total Planned Budget for 6 Months: $%.2f", totalPlanned));
		return planBuilder.toString();
	}

	// Get all expenses as a list
	public List<Expense> getExpenses() {
		return new ArrayList<>(expenses); // Return a copy to prevent external modification
	}
}
