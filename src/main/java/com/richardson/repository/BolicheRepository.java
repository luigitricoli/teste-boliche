package com.richardson.repository;

import com.richardson.model.Placar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BolicheRepository extends MongoRepository<Placar, String> {
    
    @Query("{'alley': ?0}")
    Placar findByAlleyNumber(String alley);
    
}
