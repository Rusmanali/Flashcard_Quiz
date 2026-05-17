package com.example.flashcardquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcardquiz.adapter.FlashcardAdapter;
import com.example.flashcardquiz.database.AppDatabase;
import com.example.flashcardquiz.databinding.ActivityMainBinding;
import com.example.flashcardquiz.model.Flashcard;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FlashcardAdapter adapter;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = AppDatabase.getInstance(this);
        setupRecyclerView();
        loadFlashcards();

        binding.fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditFlashcardActivity.class);
            startActivity(intent);
        });

        binding.btnStudy.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudyActivity.class);
            startActivity(intent);
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFlashcards(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFlashcards(newText);
                return true;
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new FlashcardAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FlashcardAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Flashcard flashcard) {
                Intent intent = new Intent(MainActivity.this, AddEditFlashcardActivity.class);
                intent.putExtra("flashcard", flashcard);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Flashcard flashcard) {
                showDeleteConfirmation(flashcard);
            }
        });

        // Add Swipe-to-Delete functionality
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Flashcard flashcard = adapter.getFlashcardAt(viewHolder.getAdapterPosition());
                showDeleteConfirmation(flashcard);
            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    private void loadFlashcards() {
        List<Flashcard> flashcards = database.flashcardDao().getAllFlashcards();
        updateUI(flashcards);
    }

    private void searchFlashcards(String query) {
        List<Flashcard> flashcards = database.flashcardDao().searchFlashcards("%" + query + "%");
        updateUI(flashcards);
    }

    private void updateUI(List<Flashcard> flashcards) {
        adapter.submitList(flashcards);
        if (flashcards.isEmpty()) {
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.btnStudy.setVisibility(View.GONE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);
            binding.btnStudy.setVisibility(View.VISIBLE);
        }
    }

    private void showDeleteConfirmation(Flashcard flashcard) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_title)
                .setMessage(R.string.delete_message)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    database.flashcardDao().delete(flashcard);
                    loadFlashcards();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFlashcards();
    }
}