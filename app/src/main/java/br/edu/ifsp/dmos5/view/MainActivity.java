package br.edu.ifsp.dmos5.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifsp.dmos5.R;
import br.edu.ifsp.dmos5.model.CreditCard;
import br.edu.ifsp.dmos5.model.InsufficientBalance;
import br.edu.ifsp.dmos5.model.StarBank;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static ArrayList<CreditCard> cards = new ArrayList<CreditCard>();
    private CreditCard card1 = new CreditCard();
    private CreditCard card2 = new CreditCard();
    private CreditCard card3 = new CreditCard();
    private CreditCard card4 = new CreditCard();
    private CreditCard card5 = new CreditCard();
    private CreditCard card6 = new CreditCard();

    private TextView resultadoTextView;
    private EditText cartaoBonusEditText;
    private EditText cartaoPayerEditText;
    private EditText cartaoReceiverEditText;
    private EditText valorTransferenciaEditText;
    private Button receberBonusButton;
    private Button transferenciaCartoesButton;
    private Button pagarBancoButton;
    private Button receberBancoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciar jogo
        startSuperBancoImobiliario();

        resultadoTextView = findViewById(R.id.textview_label3);

        cartaoBonusEditText = findViewById(R.id.edittext_cartaoBonus);
        cartaoPayerEditText = findViewById(R.id.edittext_cartao_payer);
        cartaoReceiverEditText = findViewById(R.id.edittext_cartao_receiver);
        valorTransferenciaEditText = findViewById(R.id.edittext_valor);

        receberBonusButton = findViewById(R.id.button_receber_bonus);
        receberBonusButton.setOnClickListener(this);
        transferenciaCartoesButton = findViewById(R.id.button_realizar_transferencia_cartoes);
        transferenciaCartoesButton.setOnClickListener(this);
        pagarBancoButton = findViewById(R.id.button_pagar_ao_banco);
        pagarBancoButton.setOnClickListener(this);
        receberBancoButton = findViewById(R.id.button_receber_do_banco);
        receberBancoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_receber_bonus:
                sendBonus();
            break;

            case R.id.button_realizar_transferencia_cartoes:
                transferCards();
            break;

            case R.id.button_pagar_ao_banco:
                try {
                    payBank();
                } catch (InsufficientBalance e) {
                    Toast.makeText(this, "Saldo insuficiente.", Toast.LENGTH_SHORT).show();
                }
            break;

            case R.id.button_receber_do_banco:
                receiveBank();
            break;
        }
    }

    public void startSuperBancoImobiliario() {
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        initCards();
    }

    private void initCards(){
        for(CreditCard c : cards){
            StarBank.getInstance().startCreditCards(c);
        }
    }

    public CreditCard getCard(int cardCalculadora){
        for(CreditCard c : cards){
            if(c.getId() == cardCalculadora){
                return c;
            }
        }
        return null;
    }

    public int getCardId(EditText editCard){
        int cardId;
        String cardText;

        cardText = editCard.getText().toString();

        try{
            cardId = Integer.valueOf(cardText);
        }catch (NumberFormatException e){
            Toast.makeText(this, "Informe um ID válido.", Toast.LENGTH_SHORT).show();
            cardId = 0;
        }
        return cardId;
    }

    private double getValue() {
        double valorDigitado;
        String valorString;

        valorString = valorTransferenciaEditText.getText().toString();

        try{
            valorDigitado = Double.valueOf(valorString);
        }catch(NumberFormatException e){
            Toast.makeText(this, "Informe um valor válido.", Toast.LENGTH_SHORT).show();
            valorDigitado = 0;
        }
        return valorDigitado;
    }

    public void sendBonus(){
        int cardBonus = getCardId(cartaoBonusEditText);
        double bonus = getValue();
        StarBank.getInstance().roundCompleted(getCard(cardBonus), bonus);
        resultadoTextView.setText(String.format("Cartão: %d - Saldo final: %.2f", getCard(cardBonus).getId(), getCard(cardBonus).getBalance()));
    }

    public void payBank() throws InsufficientBalance {
        int cardPayer = getCardId(cartaoPayerEditText);
        double valuePay = getValue();
        StarBank.getInstance().pay(getCard(cardPayer), valuePay);
        resultadoTextView.setText(String.format("Cartão: %d - Saldo final: %.2f", getCard(cardPayer).getId(), getCard(cardPayer).getBalance()));
    }

    public void receiveBank(){
        int cardReceiver = getCardId(cartaoReceiverEditText);
        double valueReceive = getValue();
        StarBank.getInstance().receive(getCard(cardReceiver), valueReceive);
        resultadoTextView.setText(String.format("Cartão: %d - Saldo final: %.2f", getCard(cardReceiver).getId(), getCard(cardReceiver).getBalance()));
    }

    public void transferCards(){
        int cardPayer = getCardId(cartaoPayerEditText);
        int cardReceiver = getCardId(cartaoReceiverEditText);
        double valueTransfer = getValue();
        boolean status;
        status = StarBank.getInstance().transfer(getCard(cardPayer), getCard(cardReceiver), valueTransfer);
        if(status){
            resultadoTextView.setText(String.format("Cartão Payer: %d - Saldo final: %.2f \n Cartão Receiver: %d - Saldo final: %.2f", getCard(cardPayer).getId(), getCard(cardPayer).getBalance(), getCard(cardReceiver).getId(), getCard(cardReceiver).getBalance()));
        } else {
            resultadoTextView.setText(String.format("Transferência cancelada."));
        }
    }

}