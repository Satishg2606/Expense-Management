package techverito;

import java.util.Collection;
import java.util.HashMap;

public class User{
    private String username;

    //Here we will map the incoming money
    public HashMap<String,Transaction> transaction = new HashMap<String,Transaction>();

    public User(String name){
        setUsername(name);
    }

    public boolean transactionExits(String name){
        return transaction.containsKey(name);
    }
    public boolean addTransaction(String name,Transaction T){
        if(transaction.containsKey(name)){
            Transaction nt = transaction.get(name);
            nt.amt += T.amt;
            return false;
        }
        else {
            transaction.put(name, T);
            return true;
        }
    }

    public Collection<Transaction> getAllTrasaction(){
        return  transaction.values();
    }
    public Transaction getTransaction(String name){
        return transaction.getOrDefault(name, null);
    }

    public void removeTransaction(String name){
        transaction.remove(name);
        return ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
