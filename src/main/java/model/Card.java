package model;

public class Card {
    public enum CardType { CHANCE, COMMUNITY_CHEST }
    public enum EffectType { MOVE, MONEY, JAIL, GET_OUT_OF_JAIL }

    private String description;
    private CardType type;
    private EffectType effect;
    private int value;

    public Card(String description, CardType type, EffectType effect, int value) {
        this.description = description;
        this.type = type;
        this.effect = effect;
        this.value = value;
    }

    public String getDescription() { return description; }
    public EffectType getEffect() { return effect; }
    public int getValue() { return value; }
}