package ru.numbDev.barista.pojo;

public enum Role {
    CLIENT(1),
    MANAGER(2),
    OWNER(3),
    ADMIN(4);

    private final long id;
    Role(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
