package me.mocha.planther.common.security.jwt;

public enum JwtType {
    ACCESS("access"),
    REFRESH("refresh");

    String stringValue;

    JwtType(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
