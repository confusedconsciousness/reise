package com._xkishan.journal.repository;

import com._xkishan.journal.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntry, ObjectId> {
    UserEntry findUserEntryByUserName(String username);
}
