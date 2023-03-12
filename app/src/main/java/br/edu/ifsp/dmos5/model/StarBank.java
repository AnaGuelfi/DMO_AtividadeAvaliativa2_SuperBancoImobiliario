package br.edu.ifsp.dmos5.model;

public class StarBank {
    private static StarBank instance;

    private StarBank(){}

    public static StarBank getInstance(){
        if(instance == null){
            instance = new StarBank();
        }
        return instance;
    }
    /* Operação inicializa os 6 cartões possíveis do jogo*/
    public void startCreditCards(){
        // TO DO
    }

    /* A cada rodada, o cartão recebe uma recompensa*/
    public void roundCompleted(CreditCard card, double value){
        card.setBalance(card.getBalance() + 500); // 500 é um valor provisório para recompensa de final da rodada
    }

    /*
    * Responsável pela transferência de valores de um CreditCard para outro.
    * O cartão payer terá o valor debitado.
    * O cartão receiver terá o valor creditado.
    * Esse método deve tratar a possibilidade de exceção gerada no método debitValue() da classe CreditCard.
    * O retorno será TRUE caso a transferência seja realizada e FALSE caso a transferência não seja realizada. */
    public boolean transfer(CreditCard payer, CreditCard receiver, double value){
        try{
            receive(receiver, value);
            pay(payer, value);
            //receiver.creditValue(value);
            //payer.debitValue(value);
        }catch (InsufficientBalance e){
            return false;
        }
        return true;
    }

    public void receive(CreditCard card, double value){
        card.creditValue(value);
    }

    public void pay(CreditCard card, double value) throws InsufficientBalance{
        card.debitValue(value);
    }
}
