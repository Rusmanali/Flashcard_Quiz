package com.example.flashcardquiz;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcardquiz.database.AppDatabase;
import com.example.flashcardquiz.databinding.ActivityStudyBinding;
import com.example.flashcardquiz.model.Flashcard;

import java.util.Collections;
import java.util.List;

public class StudyActivity extends AppCompatActivity {

    private ActivityStudyBinding binding;
    private List<Flashcard> flashcards;
    private int currentIndex = 0;
    private boolean isShowingAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        flashcards = AppDatabase.getInstance(this).flashcardDao().getAllFlashcards();

        if (flashcards == null || flashcards.isEmpty()) {
            Toast.makeText(this, "No flashcards to study", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Shuffle cards for a better learning experience
        Collections.shuffle(flashcards);

        showCard();

        binding.btnShowAnswer.setOnClickListener(v -> toggleAnswer());
        binding.btnNext.setOnClickListener(v -> nextCard());
        binding.btnPrevious.setOnClickListener(v -> previousCard());
    }

    private void showCard() {
        Flashcard current = flashcards.get(currentIndex);
        binding.tvCardContent.setText(current.getQuestion());
        binding.btnShowAnswer.setText(R.string.show_answer);
        isShowingAnswer = false;

        binding.tvProgress.setText(getString(R.string.card_progress, currentIndex + 1, flashcards.size()));
        binding.progressBar.setProgress((int) (((float) (currentIndex + 1) / flashcards.size()) * 100));
    }

    private void toggleAnswer() {
        Flashcard current = flashcards.get(currentIndex);
        
        // Flip animation
        binding.tvCardContent.animate()
                .rotationY(90)
                .setDuration(150)
                .withEndAction(() -> {
                    if (isShowingAnswer) {
                        binding.tvCardContent.setText(current.getQuestion());
                        binding.btnShowAnswer.setText(R.string.show_answer);
                    } else {
                        binding.tvCardContent.setText(current.getAnswer());
                        binding.btnShowAnswer.setText(R.string.show_question);
                    }
                    isShowingAnswer = !isShowingAnswer;
                    binding.tvCardContent.setRotationY(-90);
                    binding.tvCardContent.animate()
                            .rotationY(0)
                            .setDuration(150)
                            .start();
                }).start();
    }

    private void nextCard() {
        if (currentIndex < flashcards.size() - 1) {
            currentIndex++;
            showCard();
            // Entrance animation for new card
            binding.tvCardContent.setAlpha(0f);
            binding.tvCardContent.animate().alpha(1f).setDuration(300).start();
        } else {
            showFinishedDialog();
        }
    }

    private void showFinishedDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Study Session Finished")
                .setMessage("Great job! You've gone through all the flashcards. Would you like to study again?")
                .setPositiveButton("Restart", (dialog, which) -> {
                    currentIndex = 0;
                    Collections.shuffle(flashcards);
                    showCard();
                })
                .setNegativeButton("Finish", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void previousCard() {
        if (currentIndex > 0) {
            currentIndex--;
            showCard();
        }
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