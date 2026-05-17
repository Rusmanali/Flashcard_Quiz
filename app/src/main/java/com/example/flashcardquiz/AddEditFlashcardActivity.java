package com.example.flashcardquiz;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcardquiz.database.AppDatabase;
import com.example.flashcardquiz.databinding.ActivityAddEditFlashcardBinding;
import com.example.flashcardquiz.model.Flashcard;

public class AddEditFlashcardActivity extends AppCompatActivity {

    private ActivityAddEditFlashcardBinding binding;
    private AppDatabase database;
    private Flashcard existingFlashcard;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditFlashcardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = AppDatabase.getInstance(this);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().hasExtra("flashcard")) {
            existingFlashcard = (Flashcard) getIntent().getSerializableExtra("flashcard");
            if (existingFlashcard != null) {
                isEditMode = true;
                binding.etQuestion.setText(existingFlashcard.getQuestion());
                binding.etAnswer.setText(existingFlashcard.getAnswer());
                binding.toolbar.setTitle(R.string.edit_flashcard);
                binding.btnSave.setText(R.string.update_flashcard);
            }
        }

        binding.btnSave.setOnClickListener(v -> saveFlashcard());
    }

    private void saveFlashcard() {
        if (binding.etQuestion.getText() == null || binding.etAnswer.getText() == null) return;
        
        String question = binding.etQuestion.getText().toString().trim();
        String answer = binding.etAnswer.getText().toString().trim();

        if (question.isEmpty() || answer.isEmpty()) {
            Toast.makeText(this, "Please enter both question and answer", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditMode) {
            existingFlashcard.setQuestion(question);
            existingFlashcard.setAnswer(answer);
            database.flashcardDao().update(existingFlashcard);
            Toast.makeText(this, "Flashcard updated", Toast.LENGTH_SHORT).show();
        } else {
            Flashcard newFlashcard = new Flashcard(question, answer);
            database.flashcardDao().insert(newFlashcard);
            Toast.makeText(this, "Flashcard saved", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}