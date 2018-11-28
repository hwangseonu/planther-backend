package me.mocha.calendar.security.jwt;

public enum JwtType {
    ACCESS("access"),
    REFRESH("refresh");

    private String stringValue;

    JwtType(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return this.stringValue;
    }
}
