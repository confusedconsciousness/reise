package com._xkishan.journal.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// Called POJOs Plain object Java objects
@Data
@Document(collection = "journals")
public class JournalEntry {
    // map it as primary
    @Id
    private ObjectId id;
    private String title;
    private String content;

    private LocalDateTime date;

}
