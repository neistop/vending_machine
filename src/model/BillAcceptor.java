package model;

public class BillAcceptor {
    private int amount;
    private final int billOnCoins = 100;

    public int getBillOnCoins() {
        return billOnCoins;
    }

    public BillAcceptor(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount * billOnCoins;
    }

    public void setAmount(int amount) {
        this.amount = amount / billOnCoins;
    }
}
