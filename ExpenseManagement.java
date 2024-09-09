package techverito;

import java.util.ArrayList;
import java.util.HashMap;
import  java.util.*;
public class ExpenseManagement {
    public static Group G = new Group("john", "mark", "carla");

    public static void main(String[] args) {
        ArrayList<User> users = G.users;
        Scanner in = new Scanner(System.in);
        HashMap<String, User> names = new HashMap<>();
        for (User user : users) {
            names.put(user.getUsername(), user);
        }
        while (true) {
            int option = -1;

            System.out.println("1 : Initiate Transaction ");
            System.out.println("2: Calculate Debt  ");
            System.out.println("3 : Exit ");
            System.out.print("Enter Choice : ");
            option = in.nextInt();
            switch (option) {
                case 1:
                    trans(names);
                    break;
                case 2:
                    calculatedebt();
                    return;
                case 3:
                    break;
            }
            if (option == 3)
                return;
        }
    }

    static void calculatedebt() {

        // Check & Settle reverse Debts.
        handleReverseExpenses();

        // Check & Settle circular transactions .
        handleCircularTransactions();

        //check & Settle Multi level Transactions.
        handleMultiLevelTransactions();

        //Print the settlements
        printSettlements();


    }


    public static void handleCircularTransactions() {
        ArrayList<Transaction> Trans = G.register;
        for (Transaction T : Trans) {
            String fromStr = T.from;
            User to = T.to;
            User from = G.findUser(fromStr);
            User third = null;
            for (User u : G.users) {
                if (u != from && u != to) third = u;
            }
            if (third == null) continue;
            if (from.transactionExits(third.getUsername()) && third.transactionExits(to.getUsername())) {
                double minimum = T.amt;
                for (Transaction t : new Transaction[]{T, from.getTransaction(third.getUsername()), third.getTransaction(to.getUsername())}) {
                    minimum = Math.min(minimum, t.amt);
                }
                for (Transaction t : new Transaction[]{T, from.getTransaction(third.getUsername()), third.getTransaction(to.getUsername())}) {
                    if (minimum == t.amt) {
                        G.register.remove(t);
                        t.to.removeTransaction(t.from);
                    } else {
                        t.amt -= minimum;
                    }
                }
                return;
            }
        }
    }

    public static void printSettlements() {
        System.out.print("After Settlement, ");
        for (Transaction T : G.register) {
            System.out.println(T.from + " owes " +  T.amt + " to "+T.to.getUsername());
        }
    }

    public static void handleReverseExpenses() {
        for (User user : G.users) {
            HashMap<String, Transaction> register = user.transaction;
            String[] str = register.keySet().toArray(new String[0]);
            for (int i = 0; i < str.length; i++) {
                Transaction T = register.get(str[i]);
                if (T == null) continue;
                User sec = G.findUser(T.from);
                if (sec.transactionExits(user.getUsername())) {
                    Transaction nt = sec.getTransaction(user.getUsername());
                    if (T.amt == nt.amt) {
                        sec.removeTransaction(nt.from);
                        user.removeTransaction(T.from);
                        i--;
                        G.removeTransaction(T);
                        G.removeTransaction(nt);
                    } else if (T.amt > nt.amt) {
                        T.amt -= nt.amt;
                        sec.removeTransaction(nt.from);
                        G.removeTransaction(nt);
                    } else {
                        nt.amt -= T.amt;
                        user.removeTransaction(T.from);
                        i--;
                        G.removeTransaction(T);
                    }
                }

            }

        }

    }

    static void trans(HashMap<String, User> names) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter Spender name : ");
        String spender = in.next().trim().toLowerCase();
        System.out.print("Enter Receiver names : ");
        String str = in.next().trim().toLowerCase();
        String[] receivers = str.split(",");
        System.out.print("Enter Amount : ");
        double amt = in.nextInt();
        int mems = receivers.length;

        for (String r : receivers) {
            if (r.trim().equals(spender.trim())) continue;
            Transaction T = new Transaction(r, names.get(spender), (amt / mems));
            //add the money in user register.
            if (names.get(spender).addTransaction(r, T)) {
                //add the Transaction in Group register if doesn't existed...
                G.addTransaction(T);
            }
        }
        System.out.println("Transactions added successfully.");

    }


    public static Transaction compareTransactions(Transaction A, Transaction B) {
        if (A.amt > B.amt) {
            return A;
        } else if (A.amt < B.amt) {
            return B;
        } else {
            return null;
        }
    }


    public static void handleMultiLevelTransactions() {
        ArrayList<Transaction> Trans = G.register;
        for(int i=0;i<Trans.size();i++){
            Transaction T = Trans.get(i);
            Transaction t=null;
            if( !G.register.contains(T) ) continue;
            for(Transaction nt:G.register){
                if(T.to==G.findUser(nt.from)){
                    t=nt;
                    break;
                }
            }
            if(t==null)continue;
            Transaction nt = compareTransactions(T,t);
            if(nt==null){
                T.to=t.to;
                t.to.removeTransaction(t.from);
                G.register.remove(t);
            }
            else if(nt == T ){
                t.to=T.to;
//                T.from = t.from;
                T.amt-=t.amt;
            }
            else{
                // nt == t
                T.to = t.to;
//                t.from = T.from;
                t.amt -= T.amt;
            }
            i = Trans.indexOf(T);

        }

    }
}
