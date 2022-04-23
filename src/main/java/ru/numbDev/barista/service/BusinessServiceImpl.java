package ru.numbDev.barista.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.numbDev.barista.OrderStatus;
import ru.numbDev.barista.entity.*;
import ru.numbDev.barista.pojo.*;
import ru.numbDev.barista.repository.*;
import ru.numbDev.barista.utils.ThrowUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.numbDev.barista.utils.CommonUtils.idNullCheck;

@Service
@AllArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final UserService userService;
    private final CurrentUserService currentUserService;
    private final AddressService addressService;
    private final WalletService walletService;
    private final UnitRepository unitRepository;
    private final OrderClientRepository orderClientRepository;
    private final NewsRepository newsRepository;
    private final TableRepository tableRepository;
    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessUnit createUnit(NewUnit unit) {
        idNullCheck(unit.ownerId());

        var address = addressService.parseAddress(unit.fullAddres());
        var wallet = walletService.initWallet();
        var owner = userService.findUserById(unit.ownerId());
        var coordinate = new CoordinateEntity()
                .setFullAddress(address.address())
                .setLatitude(address.latitude().toString())
                .setLongitude(address.longitude().toString());
        var metadata = new MetadataEntity()
                .setDescription(unit.description());
        var unitEntity = new UnitEntity()
                .setName(unit.unitName())
                .setOwnerUnit(unit.owner())
                .setOgrn(unit.ogrn())
                .setMetadata(metadata)
                .setOwner(owner)
                .setWallet(wallet)
                .setCoordinate(coordinate);

        return new BusinessUnit(unitRepository.save(unitEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessUnit getUnit(long unitId) {
        idNullCheck(unitId);

        var unit = findUnitById(unitId);
        var orders = orderClientRepository
                .findActualClientsOrdersToUnit(unitId)
                .stream()
                .map(BusinessOrder::new)
                .collect(Collectors.toList());

        return new BusinessUnit(unit, orders);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessOrder getOrder(long orderId) {
        idNullCheck(orderId);

        var orders = orderClientRepository.getById(orderId);
        return new BusinessOrder(orders);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<BusinessOrder> getHistoryOfOrders(HistoryOrderFilter filter) {
        idNullCheck(filter.unitId());
        return orderClientRepository
                .findAllClientsOrdersToUnit(filter.unitId())
                .stream()
                .map(BusinessOrder::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setStatusClientOrder(BusinessStatusOrder statusOrder) {
        idNullCheck(statusOrder.orderId());
        if (statusOrder.status() == null) {
            throw ThrowUtils.apiEx("Status cannot be null", 400);
        }

        var isOrderManager = orderClientRepository
                .isOrderFromManagerUnit(
                        statusOrder.orderId(),
                        currentUserService.getCurrentUserEntity().getId()
                )
                .isPresent();

        if (!isOrderManager) {
            throw ThrowUtils.apiEx("This order is not from unit manager", 403);
        }

        var orderClient = orderClientRepository
                .findById(statusOrder.orderId())
                .orElseThrow(ThrowUtils.throwApiExRequest("Order not found"));

        orderClient.setStatus(statusOrder.status());

        if (statusOrder.status() == OrderStatus.DONE) {
            orderClient.setIsDone(true);
        }

        orderClientRepository.save(orderClient);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public News addNews(NewNews news) {
        idNullCheck(news.unitId());

        if (!userService.isManagerOrUnitForUnit(news.unitId())) {
            throw ThrowUtils.apiEx("This manager does not work on the unit", 403);
        }

        var newsEntity = new UnitNewsEntity()
                .setDescription(news.description())
                .setUnit(findUnitById(news.unitId()));

        return new News(newsRepository.save(newsEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeNews(News news) {
        idNullCheck(news.id());

        var newsEntity = newsRepository
                .findById(news.id())
                .orElseThrow(ThrowUtils.throwApiExRequest("News does not exists"));

        boolean isManagerForUnit = userService.isManagerOrUnitForUnit(newsEntity.getUnit().getId());
        if (!isManagerForUnit) {
            throw ThrowUtils.apiEx("This manager does not work on the unit", 403);
        }

        newsRepository.save(
                newsEntity.setDescription(news.description())
        );
    }

    @Override
    public void deleteNews(Long newsId) {
        idNullCheck(newsId);

        boolean isManagerOrOwner = newsRepository
                .isManagerForUnitForNews(newsId, currentUserService.getCurrentUserEntity().getId())
                .isPresent();

        if (!isManagerOrOwner) {
            throw ThrowUtils.apiEx("This manager or owner does not work on the unit", 403);
        }

        newsRepository.deleteNews(newsId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllNews(Long unitId) {
        idNullCheck(unitId);

        boolean isManager = userService.isManagerOrUnitForUnit(unitId);
        if (!isManager) {
            throw ThrowUtils.apiEx("This manager does not work on the unit", 403);
        }

        newsRepository.deleteAllNews(unitId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Table> setTables(List<NewTable> tables, long unitId) {
        idNullCheck(unitId);
        List<Table> tablesWithId = new ArrayList<>();

        tableRepository.deleteTablesFromUnit(unitId);
        var unitEntity = findUnitById(unitId);
        for (NewTable table : tables) {
            var tableEntity = new TableEntity()
                    .setUnit(unitEntity)
                    .setDescription(table.description())
                    .setCount(table.count());

            tablesWithId.add(new Table(tableRepository.save(tableEntity)));
        }

        return tablesWithId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu createMenu(NewMenu menu) {
        idNullCheck(menu.unitId());

        if (StringUtils.isBlank(menu.name())) {
            throw ThrowUtils.apiEx("Menu name is empty", 400);
        }

        var unitEntity = findUnitById(menu.unitId());
        var menuEntity = new MenuEntity()
                .setName(menu.name())
                .setDescription(menu.description())
                .setUnit(unitEntity);

        return new Menu(menuRepository.save(menuEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeMenu(Menu menu) {
        idNullCheck(menu.id());

        dishRepository.deleteAllDishesFromMenu(menu.id());
        var menuEntity = menuRepository
                .findById(menu.id())
                .orElseThrow(ThrowUtils.throwApiExRequest("Menu not found"))
                .setName(menu.name())
                .setDescription(menu.description());

        for (Dish dish : menu.dishes()) {
            var dishEntity = new DishEntity()
                    .setMenu(menuEntity)
                    .setName(dish.name())
                    .setDescription(dish.description())
                    .setCategory(dish.category())
                    .setCost(dish.cost());

            dishRepository.save(dishEntity);
        }

        menuRepository.save(menuEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        idNullCheck(menuId);

        boolean isManager = menuRepository
                .isMenuFromUnit(menuId, currentUserService.getCurrentUserEntity().getId())
                .isPresent();
        if (!isManager) {
            throw ThrowUtils.apiEx("This manager does not work on the unit", 403);
        }
        dishRepository.deleteAllDishesFromMenu(menuId);
        menuRepository.deleteMenu(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dish addDish(NewDish dish) {
        idNullCheck(dish.menuId());

        boolean isManager = menuRepository
                .isMenuFromUnit(dish.menuId(), currentUserService.getCurrentUserEntity().getId())
                .isPresent();
        if (!isManager) {
            throw ThrowUtils.apiEx("This manager does not work on the unit", 403);
        }

        var menuEntity = menuRepository
                .findById(dish.menuId())
                .orElseThrow(ThrowUtils.throwApiExRequest("Menu does not exists"));

        var dishEntity = new DishEntity()
                .setName(dish.name())
                .setDescription(dish.description())
                .setCategory(dish.category())
                .setCost(dish.cost())
                .setMenu(menuEntity);

        return new Dish(dishRepository.save(dishEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDish(long dishId) {
        idNullCheck(dishId);

        var isManager = dishRepository.isManagerFromUnitForDish(dishId, currentUserService.getCurrentUserEntity().getId());
        if (!isManager) {
            throw ThrowUtils.apiEx("This manager does not work on the unit", 403);
        }

        dishRepository.deleteDish(dishId);
    }

    @Override
    public String getTipForAddress(String proto) {
        if (StringUtils.isBlank(proto) || proto.length() < 3) {
            return "";
        }

        return addressService.tipAddress(proto);
    }

    private UnitEntity findUnitById(long id) {
        return unitRepository
                .findById(id)
                .orElseThrow(ThrowUtils.throwApiExRequest("Unit not found with id: " + id));
    }
}
