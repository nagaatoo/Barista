package ru.numbDev.barista.pojo;

import ru.numbDev.barista.entity.WalletEntity;

public record Wallet() {
    public Wallet(WalletEntity wallet) {
        this();
    }
}
