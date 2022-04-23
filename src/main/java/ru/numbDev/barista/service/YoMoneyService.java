package ru.numbDev.barista.service;

import org.springframework.stereotype.Service;
import ru.numbDev.barista.entity.WalletEntity;

@Service
public class YoMoneyService implements WalletService {

    @Override
    public WalletEntity initWallet() {
        return new WalletEntity();
    }
}
