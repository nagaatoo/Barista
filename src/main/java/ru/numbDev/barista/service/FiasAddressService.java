package ru.numbDev.barista.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.numbDev.barista.pojo.FiasAddress;
import ru.numbDev.barista.utils.RestUtils;
import ru.numbDev.barista.utils.ThrowUtils;
import ru.redcom.lib.integration.api.client.dadata.DaDataClient;
import ru.redcom.lib.integration.api.client.dadata.DaDataClientFactory;

import java.util.Map;

@Service
@AllArgsConstructor
public class FiasAddressService implements AddressService {

    private final String API_KEY = "528f60f4ed8d8c17457532c667193256a3d87ee4";
    private final String SECRET_KEY = "6b35d2b729e48edc3c271413c5856b9b83ff7bac";
    private final DaDataClient dadata = DaDataClientFactory.getInstance(API_KEY, SECRET_KEY);

    // URL для подсказок - ФИАС
    private final String FIAS_URI = "https://fias.nalog.ru/";

    @Override
    public FiasAddress parseAddress(String fullAddress) {

        if (StringUtils.isBlank(fullAddress)) {
            throw ThrowUtils.apiEx("The address cannot be blank", 400);
        }

        var address = dadata.cleanAddress(fullAddress);
        Map<String, Object> body = Map.of(
                "query", fullAddress
        );

        return new FiasAddress(
                fullAddress,
                address.getGeoLon(),
                address.getGeoLat()
        );
    }

    @Override
    public String tipAddress(String protoAddress) {
        String fullUri = FIAS_URI + "Search/Searching?text=" + protoAddress;

        return RestUtils.doGet(fullUri, String.class);
    }
}
