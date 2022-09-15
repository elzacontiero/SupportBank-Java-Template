package training.supportbank;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ReadTransactions has the responsibility to read the CSV file and return a list of transactions.
public class ReadTransactions {

    // create a property formatter that is going to be used many times inside the loop to parse dates.
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // This method reads a CSV file parsing each line, creating one Transaction for each line.
    public static List<Transaction> transactionsReader(String fileName) throws Exception {
        System.out.println("Creating scanner for CSV file.");
        Scanner scan = new Scanner(new File(fileName));
        String line = scan.nextLine(); // read the whole line
        System.out.println("Throwing away first line as it has only names: " + line);
        // Creating a list of transactions to store all the transactions.
        List<Transaction> listOfTransaction = new ArrayList<>();

        // Looping over the lines (each transaction)
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            // Array of fields to save everything that is in between commas.
            String[] fields = line.split(",");

            // Magic to convert String date into a LocalDate object.
            LocalDate date = LocalDate.parse(fields[0], formatter);

            // Create object Transaction t that holds all the fields.
            Transaction t = new Transaction(date,
                    fields[1], // From
                    fields[2], // To
                    fields[3], // Narrative
                    new BigDecimal(fields[4])); // amount

            // add current transaction into a listOfTransaction.
            listOfTransaction.add(t);
        }
        return listOfTransaction;
    }
}

