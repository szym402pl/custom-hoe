package me.xiaojibazhanshi.customhoe.upgrades.property;

public class IntegerProperty implements Property<Integer>{

    private final int value;

    public IntegerProperty(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
