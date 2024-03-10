package lab2;

import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

/**
 * AccountBalance defines the balance of the users at a given time
 * in the ledger model of bitcoins
 */

public class AccountBalance {

    /**
     * The current balance of each user, with each account's name mapped to its
     * current balance.
     */

    private TreeMap<String, Integer> accountBalanceBase;

    /**
     * Constructor for creating empty AccountBalance
     */

    public AccountBalance() {
        accountBalanceBase = new TreeMap<String, Integer>();
    }

    /**
     * Constructor for creating AccountBalance from a map from string to integers
     */

    public AccountBalance(TreeMap<String, Integer> accountBalanceBase) {
        this.accountBalanceBase = accountBalanceBase;
    }

    /**
     * obtain the underlying Treemap from string to integers
     */

    public TreeMap<String, Integer> getAccountBalanceBase() {
        return accountBalanceBase;
    };

    /**
     * obtain the list of users in the tree map
     */

    public Set<String> getUsers() {
        return getAccountBalanceBase().keySet();
    };

    /**
     * Adds an account for user with balance.
     *
     * if there was an entry it is overridden.
     */

    public void addAccount(String user, int balance) {
        accountBalanceBase.put(user, balance);
    }

    /**
     * @return true if the {@code user} exists in AccountBalance
     */

    public boolean hasUser(String user) {
        return accountBalanceBase.containsKey(user);
    }

    /**
     * @return the balance for this account {@code account}
     *
     *         if there was no entry, return zero
     *
     */

    public int getBalance(String user) {
        if (hasUser(user)) {
            return accountBalanceBase.get(user);
        } else {
            return 0;
        }
    }

    /**
     * set the balance for {@code user} to {@code amount}
     * this will override any existing entries
     */

    public void setBalance(String user, int amount) {
        accountBalanceBase.put(user, amount);
    };

    /**
     * Adds amount to balance for {@code user}
     * 
     * if there was no entry for {@code user} add one with
     * {@code balance}
     */

    public void addBalance(String user, int amount) {
        setBalance(user, getBalance(user) + amount);
    }

    /**
     * Subtracts amount from balance for {@code user}
     */

    public void subtractBalance(String user, int amount) {
        setBalance(user, getBalance(user) - amount);
    }

    /**
     * Check balance has at least amount for {@code user}
     */

    public boolean checkBalance(String user, int amount) {
        return (getBalance(user) >= amount);
    }

    /**
     *
     * Task 2.1: Fill in the body of method checkAccountBalanceDeductable()
     * It has been commented out so that the code compiles.
     *
     * Check all items in accountBalance2 can be deducted from the current one
     *
     * accountBalance2 is usually obtained
     * from a list of inputs of a transaction
     *
     * Checking that a TxOutputList can be deducted will be later done
     * by first converting that TxOutputList into an AccountBalance and then using
     * this method
     *
     * A naive check would just check whether each entry of a TxOutputList can be
     * deducted
     *
     * But there could be an output for the same user Alice of say 10 units twice
     * where there are not enough funds to deduct it twice but enough
     * funds to deduct it once
     * The naive check would succeed, but after converting the TxOutputList
     * to AccountBalance we have that for Alice 20 units have to be deducted
     * so the deduction of the accountBalance created fails.
     *
     * One could try for checking that one should actually deduct each entry in
     * squence
     * but then one has to backtrack again.
     * Converting the TxOutputList into a AccountBalance is a better approach since
     * the
     * TxOutputList is usually much smaller than the main AccountBalance.
     * 
     * Note amountToCheckForDeduction is not a 2nd amount but the
     * but is intended to be a TxEntryListList converted into an
     * AccountBalance to check whether it can be deducted.
     */

    public boolean checkAccountBalanceDeductable(AccountBalance amountToCheckForDeduction) {
        // you need to replace then next line by the correct statement
        for (String user : amountToCheckForDeduction.getUsers()) {
            int amountToDeduct = amountToCheckForDeduction.getBalance(user);
            if (this.getBalance(user) < amountToDeduct) {
                return false; // Cannot deduct for this user
            }
        }
        return true;
    };

    /**
     *
     * Task 2.2: Fill in the method checkTxELdeductable
     * It has been commented out so that the code compiles.
     *
     * It checks that a list of txEntries (which will be inputs of a transactions)
     * can be deducted from AccountBalance
     *
     * done by first converting the list of txEntries into an accountBalance
     * and then checking that the resulting accountBalance can be deducted.
     * 
     */

    public boolean checkTxELdeductable(TxEntryList txel) {
        // you need to replace then next line by the correct statement
    	AccountBalance txAccountBalance = txel.toAccountBalance();
        return this.checkAccountBalanceDeductable(txAccountBalance);
    };

    /**
     * Task 2.3: Fill in the methods subtractTxEL and addTxEL.
     *
     * Subtract a list of txEntries (txel, usually transaction inputs) from the
     * accountBalance
     *
     * requires that the list to be deducted is deductable.
     * 
     */
    
   

    public void subtractTxEL(TxEntryList txel) {
        for (TxEntry entry : txel.toList()) {
            String user = entry.getUser();
            int amount = entry.getAmount();
            this.subtractBalance(user, amount);
        }
    }

    /**
     * Add a list of txEntries (txel, usually transaction outputs) to the current
     * accountBalance
     *
     */

    public void addTxEL(TxEntryList txel) {
        for (TxEntry entry : txel.toList()) {
            String user = entry.getUser();
            int amount = entry.getAmount();
            this.addBalance(user, amount);
        }
    }

    /**
     *
     * Task 2.4: Fill in the method checkTxValid
     * It has been commented out so that the code compiles.
     *
     * Check a transaction is valid:
     * the sum of outputs is less than or equal the sum of inputs
     * and the inputs can be deducted from the accountBalance.
     *
     */

    public boolean checkTxValid(Tx tx) {
        TxEntryList inputs = tx.toInputs();
        TxEntryList outputs = tx.toOutputs();
        return inputs.toSum() >= outputs.toSum() && this.checkTxELdeductable(inputs);
    }

    /**
     *
     * Task 2.5: Fill in the method processTx
     *
     * Process a transaction
     * by first deducting all the inputs
     * and then adding all the outputs.
     *
     */

    public void processTx(Tx tx) {
        TxEntryList inputs = tx.toInputs();
        TxEntryList outputs = tx.toOutputs();

        // Deduct inputs
        this.subtractTxEL(inputs);

        // Add outputs
        this.addTxEL(outputs);
    }

    /**
     * Prints the current state of the accountBalance.
     */

    public void print() {
        for (String user : getUsers()) {
            Integer value = getBalance(user);
            System.out.println("The balance for " + user + " is " + value);
        }

    }

    /**
     * Task 2.6: Fill in the testcases as described in the labsheet
     * 
     * Testcase
     */

    public static void test() {
        // Create an empty AccountBalance and add users Alice, Bob, Carol, David, initialized with the amount 0 for each user
        AccountBalance ledger = new AccountBalance();
        ledger.addAccount("Alice", 0);
        ledger.addAccount("Bob", 0);
        ledger.addAccount("Carol", 0);
        ledger.addAccount("David", 0);

        // Print initial state
        System.out.println("Initial State:");
        ledger.print();
        System.out.println();

        // Set the balance for Alice to 20
        ledger.setBalance("Alice", 20);
        System.out.println("Set Alice's balance to 20:");
        ledger.print();
        System.out.println();

        // Set the balance for Bob to 15
        ledger.setBalance("Bob", 15);
        System.out.println("Set Bob's balance to 15:");
        ledger.print();
        System.out.println();

        // Subtract 5 from the balance of Bob
        ledger.subtractBalance("Bob", 5);
        System.out.println("Subtract 5 from Bob's balance:");
        ledger.print();
        System.out.println();

        // Check whether the TxEntryList txel1 giving Alice 15 units, and Bob 5 units can be deducted
        TxEntryList txel1 = new TxEntryList("Alice", 15, "Bob", 5);
        System.out.println("Check if txel1 can be deducted:");
        boolean txel1Deductable = ledger.checkTxELdeductable(txel1);
        System.out.println("Txel1 Deductable: " + txel1Deductable);
        System.out.println();

        // Check whether the TxEntryList txel2 giving Alice 15 units, and giving Alice again 15 units can be deducted
        TxEntryList txel2 = new TxEntryList("Alice", 15, "Alice", 15);
        System.out.println("Check if txel2 can be deducted:");
        boolean txel2Deductable = ledger.checkTxELdeductable(txel2);
        System.out.println("Txel2 Deductable: " + txel2Deductable);
        System.out.println();

        // Deduct txel1 from the ledger
        ledger.subtractTxEL(txel1);
        System.out.println("After deducting txel1:");
        ledger.print();
        System.out.println();

        // Add txel2 to the ledger
        ledger.addTxEL(txel2);
        System.out.println("After adding txel2:");
        ledger.print();
        System.out.println();

        // Create a transaction tx1 which takes as input for Alice 40 units and gives Bob 5 and Carol 20 units
        TxEntryList tx1Inputs = new TxEntryList("Alice", 40);
        TxEntryList tx1Outputs = new TxEntryList("Bob", 5, "Carol", 20);
        Tx tx1 = new Tx(tx1Inputs, tx1Outputs);

        // Check whether tx1 is valid
        System.out.println("Check if tx1 is valid:");
        boolean tx1Valid = ledger.checkTxValid(tx1);
        System.out.println("Tx1 Valid: " + tx1Valid);
        System.out.println();

        // Create a transaction tx2 which takes as input for Alice 20 units and gives Bob 5 and Carol 20 units
        TxEntryList tx2Inputs = new TxEntryList("Alice", 20);
        TxEntryList tx2Outputs = new TxEntryList("Bob", 5, "Carol", 20);
        Tx tx2 = new Tx(tx2Inputs, tx2Outputs);

        // Check whether tx2 is valid
        System.out.println("Check if tx2 is valid:");
        boolean tx2Valid = ledger.checkTxValid(tx2);
        System.out.println("Tx2 Valid: " + tx2Valid);
        System.out.println();

        // Create a transaction tx3 which takes as input for Alice 25 units and gives Bob 5 and Carol 20 units
        TxEntryList tx3Inputs = new TxEntryList("Alice", 25);
        TxEntryList tx3Outputs = new TxEntryList("Bob", 5, "Carol", 20);
        Tx tx3 = new Tx(tx3Inputs, tx3Outputs);

        // Check whether tx3 is valid
        System.out.println("Check if tx3 is valid:");
        boolean tx3Valid = ledger.checkTxValid(tx3);
        System.out.println("Tx3 Valid: " + tx3Valid);
        System.out.println();

        // Update AccountBalance by processing tx3
        System.out.println("Processing tx3:");
        ledger.processTx(tx3);
        ledger.print();
        System.out.println();

        // Create a transaction tx4 which takes as inputs for Alice twice 5 units, and as output to Bob 10 units
        TxEntryList tx4Inputs = new TxEntryList("Alice", 5, "Alice", 5);
        TxEntryList tx4Outputs = new TxEntryList("Bob", 10);
        Tx tx4 = new Tx(tx4Inputs, tx4Outputs);

        // Update AccountBalance by processing tx4
        System.out.println("Processing tx4:");
        ledger.processTx(tx4);
        ledger.print();
    }


    /**
     * main function running test cases
     */

    public static void main(String[] args) {
        AccountBalance.test();
    }
}
