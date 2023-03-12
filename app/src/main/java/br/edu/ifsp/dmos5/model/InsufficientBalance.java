package br.edu.ifsp.dmos5.model;

public class InsufficientBalance extends Exception{
    @Override
    public String getMessage(){
        return "Não há saldo suficiente para realizar a operação.";
    }
}
