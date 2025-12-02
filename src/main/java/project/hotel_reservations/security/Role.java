package project.hotel_reservations.security;

public enum Role {
    SUPER_ADMIN("system-admin"),
    HOTEL_ADMIN("hotel-admin"),
    GUEST("guest");

    private final String value;
    Role(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
