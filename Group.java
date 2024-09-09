package techverito;

import java.util.ArrayList;

public class Group {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Transaction> register = new ArrayList<>();

    public Group(String A,String B,String C){
        User X = new User(A);
        User Y = new User(B);
        User Z = new User(C);

        users.add(X);
        users.add(Y);
        users.add(Z);
    }

    public void addMember(String name){
        User usr = new User(name);
        users.add(usr);
        return ;
    }

    public User findUser(String u){
        for(User usr : users){
            if(usr.getUsername().equals(u)){
                return usr;
            }
        }
        return null;
    }

    public void addTransaction(Transaction T){


        register.add(T);
        return;
    }


    public void removeTransaction(Transaction T){
        if(T!=null){
            register.remove(T);
        }
    }

    public ArrayList<Transaction> getAllTransactionsByUser(String name){
        ArrayList<Transaction> allTransactions = new ArrayList<>();

        for(Transaction T: register){
            if(T.from.equals(name)){
                allTransactions.add(T);
            }
        }
        return allTransactions;
    }

//    public void spentOnGroup(String spender,Double Amount){
//        User spentBy = null;
//        for(User u:users){
//            if(u.getUsername().equals(spender)){
//                spentBy=u;
//                break;
//            }
//        }
//        double amt = Amount / users.size();
//        for(User u:users){
//            if(u!=spentBy){
//                Transaction T = new Transaction(u.getUsername(),spentBy,amt);
//                register.add(T);
//                u.addTransaction(u.getUsername(),T);
//                if (spentBy != null) {
//                    spentBy.addTransaction(u.getUsername(),T);
//                }
//            }
//        }
//        return;
//    }

    public void spentWithPerson(String spender,String spentWith,Double Amount){
        User spentBy = null;
        User spentOn = null;
        if(spender.equals(spentWith))return;

        for(User u:users){
            if(u.getUsername().equals(spender)){
                spentBy=u;
            }
            if(u.getUsername().equals(spentWith)){
                spentOn = u;
            }
        }
//        Transaction T =
    }
}
