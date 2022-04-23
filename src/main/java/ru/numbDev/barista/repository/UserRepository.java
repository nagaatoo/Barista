package ru.numbDev.barista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.numbDev.barista.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT CAST(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS BIT) FROM users WHERE nick_name = :nickName")
    boolean nickIsExist(@Param("nickName") String nickname);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE nick_name = :nickName")
    Optional<UserEntity> findByNick(@Param("nickName") String nickName);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update users set id = :id where nick_name not in (select nick_name from users where nick_name = :nickName)")
    boolean changeNick(@Param("nickName") String newNick, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from users where nick_name = :nickName")
    void delete(@Param("nickName") String nickName);

}
