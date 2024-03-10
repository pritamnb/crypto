package lab3;

import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.util.Hashtable;
import java.util.Set;
import java.util.ArrayList;


/** 
 *   AccountBalance defines an accountBalance in the ledger model of bitcoins
 */

public class AccountBalance {

    /** 
     * The current accountBalance, with each account's public Key mapped to its 
     *    account balance.
     */
    
    private Hashtable<PublicKey, Integer> accountBalanceBase;

    /**
     *  In order to print out the accountBalance in a good order
     *  we maintain a list of public Keys,
     *  which will be the set of public keys maped by it in the order
     *  they were added
     **/

    private ArrayList<PublicKey> publicKeyList;


    /** 
     * Creates a new accountBalance
     */
    public AccountBalance() {
	accountBalanceBase = new Hashtable<PublicKey, Integer>();
	publicKeyList = new ArrayList<PublicKey>();
	
    }

    /** 
     * Creates a new accountBalance from a map from string to integers
     */
    
    public AccountBalance(Hashtable<PublicKey, Integer> accountBalanceBase) {
	this.accountBalanceBase = accountBalanceBase;
	publicKeyList = new ArrayList<PublicKey>();	
	for (PublicKey pbk : accountBalanceBase.keySet()){
	    publicKeyList.add(pbk);
	}
    }

    /** obtain the underlying Hashtable from string to integers
     */   
    
    public Hashtable<PublicKey,Integer> getAccountBalanceBase(){
	return accountBalanceBase;
    };

    /** 
      * obtain the list of publicKeys in the tree map
      */   
    
    public Set<PublicKey> getPublicKeys(){
	return getAccountBalanceBase().keySet();
    };

    /** 
      * obtain the list of publicKeys in the order they were added
      */   

    public ArrayList<PublicKey> getPublicKeysOrdered(){
	return publicKeyList;
    };        

    
    

    /** 
     * Adds a mapping from new account's name {@code publicKey} to its 
     * account balance {@code balance} into the accountBalance. 
     *
     * if there was an entry it is overridden.  
     */

    public void addAccount(PublicKey publicKey, int balance) {
	accountBalanceBase.put(publicKey, balance);
	if (! publicKeyList.contains(publicKey)){
	    publicKeyList.add(publicKey);
	}
    }

    /** 
     * @return true if the {@code publicKey} exists in the accountBalance.
     */
    
    public boolean hasPublicKey(PublicKey publicKey) {
	return accountBalanceBase.containsKey(publicKey);
    }


    /** 
     * @return the balance for this account {@code account}
     *
     *  if there was no entry, return zero
     *
     */
    
    public int getBalance(PublicKey publicKey) {
	if (hasPublicKey(publicKey)){
		return accountBalanceBase.get(publicKey);
	    } else
	    {  return 0;
	    }
    }


    /** 
     * set the balance for {@code publicKey} to {@code amount}
     */

    
    public void setBalance(PublicKey publicKey, int amount){
	accountBalanceBase.put(publicKey,amount);
	if (! publicKeyList.contains(publicKey)){
	    publicKeyList.add(publicKey);
	}	
	    };
	

    /** 
     * Imcrements Adds amount to balance for {@code publicKey}
     * 
     *  if there was no entry for {@code publicKey} add one with 
     *       {@code balance}
     */
    
    public void addToBalance(PublicKey publicKey, int amount) {
	setBalance(publicKey,getBalance(publicKey) + amount);
    }


    /** 
     * Subtracts amount from balance for {@code publicKey}
     */
    
    public void subtractFromBalance(PublicKey publicKey, int amount) {
	setBalance(publicKey,getBalance(publicKey) - amount);
    }


    /** 
     * Check balance has at least amount for {@code publicKey}
     */
    public boolean checkBalance(PublicKey publicKey, int amount) {
	return (getBalance(publicKey) >= amount);
    }


    /* Task 3.4 consists of several subtasks
       This is just a repetition of the tasks done for lab2 adapted to the 
       current setting
 
       Task 3.4.1
       check whether an accountBalance can be deducted 
       this is an auxiliary function used to define checkTxInputListDeductable 
     */

    public boolean checkAccountBalanceDeductable(AccountBalance amountToCheckForDeduction){
	// you need to replace then next line by the correct statement	
	return true;
    };


    /** 
     *  Task 3.4.2
     *
     *  Check that a list of publicKey amounts can be deducted from the 
     *     current accountBalance
     *
     *   done by first converting the list of publicKey amounts into an accountBalance
     *     and then checking that the resulting accountBalance can be deducted.
     *   
     */    


    public boolean checkTxInputListDeductable(TxInputList txInputList){
	// you need to replace then next line by the correct statement	
	return true;	
    };


    /** 
     * Task 3.4.3
     * Subtract a list of TxInput from the accountBalance
     *
     *   requires that the list to be deducted is deductable.
     *   
     */    
    

    public void subtractTxInputList(TxInputList txInputList){
	// fill in Body 	
    }


    
    /** 
     * Task 3.4.4
     * Adds a list of txOutput of a transaction to the current accountBalance
     *
     */    

    public void addTxOutputList(TxOutputList txOutputList){
	// fill in Body 	
    }

    /** 
     * Task 3.4.5
     * Process a transaction
     *    by first deducting all the inputs
     *    and then adding all the outputs.
     *
     */    
    
    public void processTransaction(Transaction tx){
	// fill in Body 		
    };


    /** 
     *
     *  Task 3.5 Check a transaction is valid.
     *           Including that Signatures are valid (change to lab 2!!)
     *
     *  this means that 
     *    the sum of outputs is less than or equal to the sum of inputs
     *    all signatures are valid
     *    and the inputs can be deducted from the accountBalance.

     *    This method has been set to true so that the code compiles - that should
     *    be changed
     */    

    public boolean checkTxValid(Transaction tx){
	return true;
	/* this is not the correct value, only used here so that the code
	   compiles */
    };


    
    /** 
     * Prints the current state of the accountBalance. 
     */

    public void print(PublicKeyMap pubKeyMap) {
	for (PublicKey publicKey : publicKeyList ) {
	    Integer value = getBalance(publicKey);
	    System.out.println("The balance for " +
			       pubKeyMap.getUser(publicKey) + " is " + value); 
	}

    }



    /** 
     * Testcase
     */

    public static void test()
	throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

	Wallet exampleWallet = SampleWallet.generate(new String[]{ "Alice"});
	byte[] exampleMessage = KeyUtils.integer2ByteArray(1);
	// The next line gives a warning that it is unused
	//     but it can be used for solving Task 3.6
	byte[] exampleSignature = exampleWallet.signMessage(exampleMessage,"Alice");


	/***   Task 3.6
               add  to the test case the test as described in the lab sheet
                
               you can use the above exampleSignature, when a sample signature is needed
	       which cannot be computed from the data.

	**/
	
    }

    /** 
     * main function running test cases
     */            

    public static void main(String[] args)
	throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
	AccountBalance.test();
    }
}
