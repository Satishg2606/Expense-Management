package techverito;

public class Transaction {
    String from ;
    User to;
    public Double amt;

    public Transaction(String from,User to,Double amt){
        this.from = from;
        this.to = to;
        this.amt  = amt;
    }

    public void changeAmt(Double amt){
        this.amt=amt;
    }


}


//        for(int i=0;i<G.register.size();i++){
//            Transaction T = G.register.get(i);
//            if(T.to.transactionExits(T.from)){
//                Transaction nt = T.to.getTransaction(T.from);
//                if(nt.amt<T.amt){
//                    T.amt -= nt.amt;
//                    G.removeTransaction(nt);
//                    T.to.removeTransaction(nt.from);
//                    i=G.register.indexOf(T);
//                }else if(nt.amt>T.amt){
//                    nt.amt -= T.amt;
//                    G.removeTransaction(T);
//                    T.to.removeTransaction(T.from);
//                    i--;
//                }else{
//                    G.removeTransaction(nt);
//                    T.to.removeTransaction(nt.from);
//                    i=G.register.indexOf(T);
//                    G.removeTransaction(T);
//                    T.to.removeTransaction(T.from);
//                    i--;
//                    if(i<0)i=0;
//                }
//            }
//        }