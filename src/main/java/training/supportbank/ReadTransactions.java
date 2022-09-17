package training.supportbank;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// the responsibility of the class below is to read the CSV file and return a list of transactions.
public class ReadTransactions {

    //  property formatter was created to be use many times inside the loop to parse dates.
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static List<Transaction> transactionsReader(String fileName) throws Exception {
        System.out.println("Creating scanner for CSV file.");
        Scanner scan = new Scanner(new File(fileName));
        String line = scan.nextLine();
        System.out.println("Throwing away first line as it has only names: " + line);

        // list of transactions stores all the transactions.
        List<Transaction> listOfTransaction = new ArrayList<>();

        // Looping over each line, that is each transaction.
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            String[] fields = line.split(",");

            // Magic to convert String date into a LocalDate object.
            LocalDate date = LocalDate.parse(fields[0], formatter);

            // Variable t holds all the fields.
            Transaction t = new Transaction(date,
                    fields[1], // From
                    fields[2], // To
                    fields[3], // Narrative
                    new BigDecimal(fields[4])); // amount

            listOfTransaction.add(t);
        }
        return listOfTransaction;
    }
}
