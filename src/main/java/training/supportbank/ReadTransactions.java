package training.supportbank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
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
    static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // method below reads a file name containing Json and returns a List of Transactions.
    public static List<Transaction> transactionReaderJson(String fileName) {
        List<Transaction> results = new ArrayList<>();
        // Create gsonBuilder and gson objects that can handle LocalDate.
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) ->
                LocalDate.parse(jsonElement.getAsString(), formatter2)
        );
        Gson gson = gsonBuilder.create();
        // Create a file reader to feed gson objects with.
        FileReader reader = null;
        try {
            reader = new FileReader(new File(fileName));
        } catch (FileNotFoundException e) {
            LOGGER.error(" error when trying to open file " + fileName + ":" + e.getMessage());
            throw new RuntimeException(e);
        }
        // parsing Json to Transactions array.
        Transaction[] transactions = gson.fromJson(reader, Transaction[].class);
        for(Transaction t: transactions) { // for each element t of transactions add to results
            results.add(t);
        }
        return results;
    }

    public static List<Transaction> transactionsReaderCsv(String fileName)
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
