package com.toki.backend.auth.repository;

import com.toki.backend.auth.entity.RefreshToekn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToekn, String> {
    Optional<RefreshToekn> findByUserPk(String userPk);

    int countByUserPk(String userTag);

    Optional<RefreshToekn> findOneByUserPkAndRefreshToken(String userPk, String refreshToken);

    int countByRefreshToken(String userPk);
}
