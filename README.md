# Support Bank 

## Problem definition

The full problem definition can be found at https://isolate.menlosecurity.com/https://corndel.atlassian.net/wiki/spaces/AC/pages/22904972/3+-+SupportBank 

Here an extraction:

> ...They're a fun-loving bunch and do lots of social things. They mostly operate on an IOU basis and keep records of who owes money to whom. Over time though, these records have gotten a bit out of hand. Your job is to write a program which reads their records and works out how much money each member of the support team owes.

> Each IOU can be thought of as one person paying another… but you can perform this via an intermediary, like moving money between bank accounts. Instead of Jon A paying Sarah T directly, Jon A pays the money to a central bank, and Sarah T receives money from the central bank.


## Design 

The requirements already present a CSV file of transactions between two parties with a specific format. The records and fields in this file naturally indicate the need for a `Transaction` class that directly map their fields.

### `Transaction`

| **Type**    | **Name**  |
|-------------|-----------|
| LocalDate   | date      |
| String      | from      |
| String      | to        |
| String      | narrative |
| BigDecimal  | amount    |

### `Account`

The need to keep track of each transaction for each user with their balances implies a class `Account` responsible for handling a particular account, allowing new transactions to be added to it, return balances, etc. 

Some fields needed are:

| **Type**          | **Name**     | **Description**                               |
|-------------------|--------------|-----------------------------------------------|
| String            | name         | Account's owner and account name.             |
| BigDecimal        | balance      | Current balance for this account.             |
| List<Transaction> | transactions | Transaction objects belonging to this account |

Alongside getters and setters for the fields above, a constructor is needed for an account to be created with the name of the account holder.

An `add(Transaction t)` method that allows a transaction to be added to this account should also
update the balance. 

### `ReadTransactions`

To better process the transactions, we need to load all transactions from CSV or JSON file into an `ArrayList<Transaction>`. The responsibility of this class is merely read out these files, handling any potential errors, returning a list to the caller.

### Main

The `main` method has the following steps:

1. Read Transaction files from disk and put them into `List<Transaction>`:
   1. File formats can be CSV or Json.
2. Create all empty initial accounts for each user in transactions. 
3. Distribute each transaction to their respective accounts. If a transaction is between Jamie and Alice, that transaction should go to Jamie's account and also to Alice's. This would update their balances and include this transaction on both accounts. 
4. Wait for user input. Command `List All` should list all accounts with their balances whereas `List Alice` should show the balance for user-Account "Alice" with the list of her transactions. It is possible to solve this by removing `"List "` from the command, as it is redundant, which leaves either `All` or an user name. 
   1. If user select `All`: loop over all accounts and present the name followed by balance;
   2. If user selected `<some user>` find that account from list, and present balance followed by each transaction.

Some of the processing above might be delegated to other methods in main, to make code clean. 

## Error handling

Care must be taken when handling dodgy input. If any of the conversions from CSV file into objects fail, the offending CSV file with the line must be presented to the user. The program can't continue under these conditions, so it is valid to stop the program processing.

    