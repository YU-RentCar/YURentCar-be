package com.yu.yurentcar.security.repository;

import com.yu.yurentcar.security.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<Token, String> {

}
