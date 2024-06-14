package budget_tracker;

import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Budget_Tracker {
    private static final int maxEntries = 100000;

    private static String[] incomeCategories = {"Salary", "Allowance", "Others"};
    private static String[] expenseCategories = {"Electric Bill", "Water Bill", "Food", "Groceries", "Travel", "Entertainment", "Others"};

    private static String[] incomes = new String[maxEntries];
    private static double[] incomeAmounts = new double[maxEntries];
    private static String[] incomeNotes = new String[maxEntries];
    private static LocalDate[] incomeDates = new LocalDate[maxEntries];

    private static String[] expenses = new String[maxEntries];
    private static double[] expenseAmounts = new double[maxEntries];
    private static String[] expenseNotes = new String[maxEntries];
    private static String[] expenseCategoriesArray = new String[maxEntries];
    private static LocalDate[] expenseDates = new LocalDate[maxEntries];

    private static double balance = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-----Budget Tracking App-----");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Entries");
            System.out.println("4. Delete Entry");
            System.out.println("5. Edit Entry");
            System.out.println("6. Search Entry");
            System.out.println("7. View Balance");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addExpense();
                    break;
                case 3:
                    viewEntries();
                    break;
                case 4:
                    deleteEntry();
                    break;
                case 5:
                    editEntry();
                    break;
                case 6:
                    searchEntries();
                    break;
                case 7:
                    viewBalance();
                    break;
                case 0:
                    System.out.println("Thank you for using Budget Tracker App!");
                    break;
                default:
                    System.out.println("ERROR: Invalid Choice!");
                    break;
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void addIncome() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n-----Add Income-----");
        System.out.println("Category List:");
        System.out.println("1. Salary");
        System.out.println("2. Allowance");
        System.out.println("3. Others");
        System.out.print("Enter Category: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        if (categoryChoice >= 1 && categoryChoice <= incomeCategories.length) {
            System.out.print("Enter the amount: $");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter notes (Optional): ");
            String notes = scanner.nextLine();

            System.out.print("Enter the date (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateString);

            int index = findEmptyIndex(incomes);

            if (index != -1) {
                incomes[index] = incomeCategories[categoryChoice - 1];
                incomeAmounts[index] = amount;
                incomeNotes[index] = notes;
                incomeDates[index] = date;
                balance += amount;
                System.out.println("Income added successfully!");
            } else {
                System.out.println("ERROR: Maximum number of entries reached!");
            }
        } else {
            System.out.println("ERROR: Invalid Choice!");
        }
    }

    private static void addExpense() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n-----Add Expense-----");
        System.out.println("Category List:");
        System.out.println("1. Electric Bill");
        System.out.println("2. Water Bill");
        System.out.println("3. Food");
        System.out.println("4. Groceries");
        System.out.println("5. Travel");
        System.out.println("6. Entertainment");
        System.out.println("7. Others");
        System.out.print("Enter Category: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        if (categoryChoice >= 1 && categoryChoice <= expenseCategories.length) {
            System.out.print("Enter the amount: $");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter notes (Optional): ");
            String notes = scanner.nextLine();

            System.out.print("Enter the date (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateString);

            int index = findEmptyIndex(expenses);

            if (index != -1) {
                expenses[index] = expenseCategories[categoryChoice - 1];
                expenseAmounts[index] = amount;
                expenseNotes[index] = notes;
                expenseCategoriesArray[index] = expenseCategories[categoryChoice - 1];
                expenseDates[index] = date;
                balance -= amount;
                System.out.println("Expense added successfully!");
            } else {
                System.out.println("ERROR: Maximum number of entries reached!");
            }
        } else {
            System.out.println("ERROR: Invalid Choice!");
        }
    }

    private static void viewEntries() {
        System.out.println("\n-----View Entries-----");

        System.out.println("\n---Incomes---");
        displayEntries(incomes, incomeAmounts, incomeNotes, incomeDates);

        System.out.println("\n---Expenses---");
        displayEntries(expenses, expenseAmounts, expenseNotes, expenseDates);
    }

    private static void displayEntries(String[] categories, double[] amounts, String[] notes, LocalDate[] dates) {
        System.out.printf("%-10s%-20s%-16s%-15s%s\n", "Index", "Category", "Amount", "Date", "Notes");
        System.out.println("----------------------------------------------------------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < categories.length; i++) {
            if (categories[i] != null) {
                System.out.printf("%-10d%-20s%s%-15.2f%-15s%s\n", i, categories[i], "$", amounts[i], formatter.format(dates[i]), notes[i]);
            }
        }
    }

    private static void deleteEntry() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n-----Delete Entry-----");
        System.out.println("Choose Entry:");
        System.out.println("1. Incomes");
        System.out.println("2. Expenses");
        System.out.print("Enter Choice: ");
        int entryTypeChoice = scanner.nextInt();

        if (entryTypeChoice == 1) {
            System.out.println("\n---Incomes---");
            displayEntries(incomes, incomeAmounts, incomeNotes, incomeDates);   
            deleteIncome();
        } else if (entryTypeChoice == 2) {
            System.out.println("\n---Expenses---");
            displayEntries(expenses, expenseAmounts, expenseNotes, expenseDates);
            deleteExpense();
        } else {
            System.out.println("ERROR: Invalid Choice!");
        }
    }

    private static void deleteIncome() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Index Number: ");
        int indexToDelete = scanner.nextInt();

        if (indexToDelete >= 0 && indexToDelete < maxEntries && incomes[indexToDelete] != null) {
            balance -= incomeAmounts[indexToDelete];
            System.out.println("Income deleted successfully!");
            incomes[indexToDelete] = null;
            incomeAmounts[indexToDelete] = 0;
            incomeNotes[indexToDelete] = null;
            incomeDates[indexToDelete] = null;
        } else {
            System.out.println("ERROR: Invalid Index!");
        }
    }

    private static void deleteExpense() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Index Number: ");
        int indexToDelete = scanner.nextInt();

        if (indexToDelete >= 0 && indexToDelete < maxEntries && expenses[indexToDelete] != null) {
            balance += expenseAmounts[indexToDelete];
            System.out.println("Expense deleted successfully!");
            expenses[indexToDelete] = null;
            expenseAmounts[indexToDelete] = 0;
            expenseNotes[indexToDelete] = null;
            expenseCategoriesArray[indexToDelete] = null;
            expenseDates[indexToDelete] = null;
        } else {
            System.out.println("ERROR: Invalid Index!");
        }
    }

    private static void editEntry() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n-----Edit Entry-----");
        System.out.println("Choose Entry:");
        System.out.println("1. Incomes");
        System.out.println("2. Expenses");
        System.out.print("Enter Choice: ");
        int entryTypeChoice = scanner.nextInt();

        if (entryTypeChoice == 1) {
            System.out.println("\n---Incomes---");
            displayEntries(incomes, incomeAmounts, incomeNotes, incomeDates);
            editIncome();
        } else if (entryTypeChoice == 2) {
            System.out.println("\n--- Expenses ---");
            displayEntries(expenses, expenseAmounts, expenseNotes, expenseDates);
            editExpense();
        } else {
            System.out.println("ERROR: Invalid Choice!");
        }
    }

    private static void editIncome() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Index: ");
        int indexToEdit = scanner.nextInt();

        if (indexToEdit >= 0 && indexToEdit < maxEntries && incomes[indexToEdit] != null) {
            System.out.print("Enter New Amount: $");
            double newAmount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter the new date (YYYY-MM-DD): ");
            String newDateString = scanner.nextLine();
            LocalDate newDate = LocalDate.parse(newDateString);

            System.out.print("Enter New Notes (Optional): ");
            String newNotes = scanner.nextLine();

            balance -= incomeAmounts[indexToEdit];
            incomeAmounts[indexToEdit] = newAmount;
            incomeDates[indexToEdit] = newDate;
            incomeNotes[indexToEdit] = newNotes;
            balance += newAmount;

            System.out.println("Income edited successfully!");
        } else {
            System.out.println("ERROR: Invalid Index!");
        }
    }

    private static void editExpense() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Index: ");
        int indexToEdit = scanner.nextInt();

        if (indexToEdit >= 0 && indexToEdit < maxEntries && expenses[indexToEdit] != null) {
            System.out.print("Enter New Amount: $");
            double newAmount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter the new date (YYYY-MM-DD): ");
            String newDateString = scanner.nextLine();
            LocalDate newDate = LocalDate.parse(newDateString);

            System.out.print("Enter New Notes (Optional): ");
            String newNotes = scanner.nextLine();

            balance += expenseAmounts[indexToEdit];
            expenseAmounts[indexToEdit] = newAmount;
            expenseDates[indexToEdit] = newDate;
            expenseNotes[indexToEdit] = newNotes;
            balance -= newAmount;

            System.out.println("Expense edited successfully!");
        } else {
            System.out.println("ERROR: Invalid Index!");
        }
    }

    private static void searchEntries() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n-----Search Entries-----");
        System.out.println("1. Search Incomes");
        System.out.println("2. Search Expenses");
        System.out.print("Enter Choice: ");
        int searchTypeChoice = scanner.nextInt();

        System.out.print("Enter Search Term: ");
        String searchTerm = scanner.next();

        switch (searchTypeChoice) {
            case 1:
                System.out.println("\n---Search Results for Incomes---");
                searchAndDisplayEntries(incomes, incomeAmounts, incomeNotes, incomeDates, searchTerm);
                break;
            case 2:
                System.out.println("\n---Search Results for Expenses---");
                searchAndDisplayEntries(expenses, expenseAmounts, expenseNotes, expenseDates, searchTerm);
                break;
            default:
                System.out.println("ERROR: Invalid Choice.");
        }
    }

    private static void searchAndDisplayEntries(
        String[] categories, double[] amounts, String[] notes, LocalDate[] dates, String searchTerm) {
        System.out.printf("%-10s%-20s%-15s%-15s%s\n", "Index", "Category", "Amount", "Date", "Notes");
        System.out.println("----------------------------------------------------------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < categories.length; i++) {
            if (categories[i] != null &&
                (categories[i].contains(searchTerm) || notes[i].contains(searchTerm) || formatter.format(dates[i]).contains(searchTerm))) {
                System.out.printf("%-10d%-20s%-15.2f%-15s%s\n", i, categories[i], amounts[i], formatter.format(dates[i]), notes[i]);
            }
        }
    }

    private static void viewBalance() {
        System.out.println("\n-----View Balance-----");
        System.out.printf("Current balance: $%.2f\n", balance);
    }

    private static int findEmptyIndex(String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
