package training.supportbank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// the responsibility of the class below is to read the CSV file and return a list of transactions.
public class ReadTransactions {

    private static final Logger LOGGER = LogManager.getLogger();

    //  property formatter was created to be use many times inside the loop to parse dates.
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static List<Transaction> transactionsReader(String fileName)
            throws DateTimeParseException, NumberFormatException, FileNotFoundException {
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

            LocalDate date;
            try {
                // Magic to convert String date into a LocalDate object.
                date = LocalDate.parse(fields[0], formatter);
            }
            catch(DateTimeParseException e) {
                LOGGER.error("Failed to convert " + fields[0] + ". Please verify contents of file " + fileName);
                LOGGER.error("ERROR in CVS line: " + line);
                throw e; // We refuse to continue: throw the error
            }

            BigDecimal amount;
            try {
                amount = new BigDecimal(fields[4]);
            } catch(NumberFormatException e) {
                LOGGER.error("ERROR: failed to convert " + fields[4] + ". Please verify contents of file " + fileName);
                LOGGER.error("ERROR in CSV line: " + line);
                throw e; // Refuse to continue: throw the error.
            }

            // Variable t holds all the fields.
            Transaction t = new Transaction(date,
                    fields[1], // From
                    fields[2], // To
                    fields[3], // Narrative
                    amount); // amount

            listOfTransaction.add(t);
        }
        return listOfTransaction;
    }
}
