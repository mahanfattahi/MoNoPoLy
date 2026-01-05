package model;

import ds.list.LinkedList;

public class Player {
    private int id;
    private String name;
    private int money;
    private int position;
    private boolean isInJail;
    private boolean isBankrupt;
    private int jailTurns;
    private LinkedList ownedProperties;

    public Player(int id, String name, int startingMoney) {
        this.id = id;
        this.name = name;
        this.money = startingMoney;
        this.position = 0;
        this.isInJail = false;
        this.isBankrupt = false;
        this.jailTurns = 0;
        this.ownedProperties = new LinkedList();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getMoney() { return money; }
    public void setMoney(int money) { this.money = money; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public boolean isInJail() { return isInJail; }
    public void setInJail(boolean inJail) { this.isInJail = inJail; }
    public boolean isBankrupt() { return isBankrupt; }
    public void setBankrupt(boolean bankrupt) { isBankrupt = bankrupt; }

    public void addProperty(Property property) {
        ownedProperties.add(property);
    }

    public LinkedList getOwnedProperties() {
        return ownedProperties;
    }
}