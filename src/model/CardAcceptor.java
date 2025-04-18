package model;

public class CardAcceptor {
    private int amount;
    private final int cardBillOnCoins = 100;

    public int getCardBillOnCoins() {
        return cardBillOnCoins;
    }

    public CardAcceptor(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount * cardBillOnCoins;
    }

    public void setAmount(int amount) {
        this.amount = amount / cardBillOnCoins;
    }
}
