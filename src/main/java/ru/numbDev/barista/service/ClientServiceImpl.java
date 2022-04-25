package ru.numbDev.barista.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.numbDev.barista.emuns.OrderStatus;
import ru.numbDev.barista.entity.*;
import ru.numbDev.barista.pojo.*;
import ru.numbDev.barista.repository.*;
import ru.numbDev.barista.utils.ThrowUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.numbDev.barista.utils.CommonUtils.idNullCheck;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UnitRepository unitRepository;
    private final OrderClientRepository orderClientRepository;
    private final DishRepository dishRepository;
    private final CommentRepository commentRepository;
    private final BucketRepository bucketRepository;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UnitListElement> findUnits(Filter filter) {
        return unitRepository
                .findAll()
                .stream()
                .map(UnitListElement::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Unit getUnit(Long id) {
        idNullCheck(id);
        return new Unit(findById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long createOrder(Order order) {
        idNullCheck(order.getUnitId());

        // Пользователь
        var unitEntity = findById(order.getUnitId());
        var currentUser = currentUserService.getCurrentUserEntity();
        // Собираем заказ
        var orderClientEntity = new OrderClientEntity()
                .setStatus(OrderStatus.CREATED)
                .setClient(currentUser)
                .setUnit(unitEntity)
                .setToDate(order.getToDate())
                .setIsDone(false);

        // Ищем блюда - проверки на фактическое наличие блюда не предполагается
        List<OrderClientDishEntity> dishes = new ArrayList<>();
        bucketRepository
                .findBucketsForUserForUnit(currentUser.getId(), order.getUnitId())
                .forEach(d -> {
                    dishes.add(
                            new OrderClientDishEntity()
                                    .setCount(d.getCount())
                                    .setDish(d.getDish())
                                    .setOrderClient(orderClientEntity)
                    );
                });

        if (dishes.isEmpty()) {
            throw ThrowUtils.apiEx("Dishes not found", 400);
        }

        orderClientEntity.getClientDishes().addAll(dishes);
        bucketRepository.deleteBucketsForUser(currentUser.getId(), order.getUnitId());
        return orderClientRepository.save(orderClientEntity).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientOrder getClientOrder(Long id) {
        idNullCheck(id);

        var orders = orderClientRepository.getById(id);
        if (!orders.getClient().getId().equals(currentUserService.getCurrentUserEntity().getId())) {
            throw ThrowUtils.apiEx("The client is not ordered to this order", 400);
        }
        return new ClientOrder(orders);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ClientOrder> getHistoryOrders() {
        return orderClientRepository
                .findAllOrdersClients(currentUserService.getCurrentUserEntity().getId())
                .stream()
                .map(ClientOrder::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(Comment comment) {
        idNullCheck(comment.getUnitId());
        if (comment.getRating() > 5 || comment.getRating() < 0) {
            throw ThrowUtils.apiEx("Rating must be between 0 to 5", 400);
        }

        var commentEntity = new CommentEntity()
                .setMessage(comment.getComment())
                .setValue(comment.getRating())
                .setUnit(findById(comment.getUnitId()));

        commentRepository.save(commentEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DishToBucket addDishToBucket(NewDishToBucket dishToBucket) {
        idNullCheck(dishToBucket.unitId());
        idNullCheck(dishToBucket.dishId());

        if (dishToBucket.count() < 1 || dishToBucket.count() > 10) {
            throw ThrowUtils.apiEx("Too much count of dish", 400);
        }

        var clientEntity = currentUserService.getCurrentUserEntity();
        var dishFromUserBucket = bucketRepository
                .findDishFromUserBucket(clientEntity.getId(), dishToBucket.dishId())
                .orElseGet(BucketEntity::new);

        dishFromUserBucket
                .setClient(clientEntity)
                .setCount(dishToBucket.count())
                .setDish(
                        dishRepository.findById(dishToBucket.dishId())
                                .orElseThrow(ThrowUtils.throwApiExRequest("Dish does not exists"))
                );

        return new DishToBucket(bucketRepository.save(dishFromUserBucket));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllFromBucket() {
        bucketRepository.deleteAllFromUserBucket(currentUserService.getCurrentUserEntity().getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDishFromBucket(Long dishId) {
        idNullCheck(dishId);

        var currentUserId = currentUserService.getCurrentUserEntity().getId();
        boolean isDishFromBucket = bucketRepository
                .findDishFromUserBucket(currentUserId, dishId)
                .isPresent();

        if (!isDishFromBucket) {
            throw ThrowUtils.apiEx("The dish is not contained in the user bucket", 400);
        }

        bucketRepository.deleteDishFromBucket(currentUserId, dishId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DishToBucket> getBucket() {
        return bucketRepository
                .findBucketsForUser(currentUserService.getCurrentUserEntity().getId())
                .stream()
                .map(DishToBucket::new)
                .collect(Collectors.toList());
    }

    private UnitEntity findById(long id) {
        return unitRepository
                .findById(id)
                .orElseThrow(ThrowUtils.throwApiExRequest("Unit not found with id: " + id));
    }
}
