package br.edu.ifsp.dmos5.model;

public class CreditCard {
    private int id;
    private double balance;
    private static int LAST_CARD_ID = 0;

    /*
    * Ao instanciar um CreditCard, é atribuido o próximo ID de forma automática, incrementando o valor de LAST_CARD_ID da classe.
    * O valor inicial do saldo de cada CreditCard é de $15000*/
    public CreditCard(){
        this.id = LAST_CARD_ID + 1;
        LAST_CARD_ID++;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public void creditValue(double newValue){
        this.setBalance(this.getBalance() + newValue);
    }

    public void debitValue(double newValue) throws InsufficientBalance {
        if(newValue < this.getBalance()){
            this.setBalance(this.getBalance() - newValue);
        }else{
            throw new InsufficientBalance();
        }
    }

}
