package ru.netology.moneytransferservice.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.model.*;
import ru.netology.moneytransferservice.model.exceptions.ConfirmationException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MoneyTransferRepository {
    private final AtomicInteger operationId = new AtomicInteger();
    private Map<String, Card> cards = new ConcurrentHashMap<>();
    private Map<Integer, String> codes = new ConcurrentHashMap<>();

    public MoneyTransferRepository() {
    }

    @PostConstruct
    public void initData(){
        Card c1 = new Card("1111111111111111", "111", "11/33", new Amount(new BigDecimal(111_111_123), "RUR"));
        Card c2 = new Card("2222222222222222", "222", "11/33", new Amount(new BigDecimal(1_111_123), "RUR"));
        Card c3 = new Card("3333333333333333", "333", "11/33", new Amount(new BigDecimal(11_111_223), "RUR"));
        cards.put(c1.getNumber(), c1);
        cards.put(c2.getNumber(), c2);
        cards.put(c3.getNumber(), c3);
    }

    public void doTransfer(Card from, Card to, BigDecimal value) {
        from.getAmount().setValue(from.getAmount().getValue().subtract(value));
        to.getAmount().setValue(to.getAmount().getValue().add(value));
    }

    public int getIncrementedOpId(){
        return operationId.incrementAndGet();
    }

    public int getOpId(){
        return operationId.get();
    }

    public void addCode(int id, String code){
        codes.put(id, code);
    }

    public String removeCode(int id) {
        return codes.remove(id);
    }

    public Card getCardByNumber(String number){
        return cards.get(number);
    }

    public boolean cardsContainsNumber(String number){
        return cards.containsKey(number);
    }
}
