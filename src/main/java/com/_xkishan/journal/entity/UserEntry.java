package com._xkishan.journal.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
public class UserEntry {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;

    private List<String> roles = new ArrayList<>();
    // a way to link (one user many journal entries)
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
