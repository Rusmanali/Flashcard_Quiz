package com.example.flashcardquiz.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flashcardquiz.model.Flashcard;

import java.util.List;

@Dao
public interface FlashcardDao {

    @Query("SELECT * FROM flashcards ORDER BY id DESC")
    List<Flashcard> getAllFlashcards();

    @Insert
    void insert(Flashcard flashcard);

    @Update
    void update(Flashcard flashcard);

    @Delete
    void delete(Flashcard flashcard);

    @Query("SELECT * FROM flashcards WHERE question LIKE :searchQuery OR answer LIKE :searchQuery")
    List<Flashcard> searchFlashcards(String searchQuery);
}