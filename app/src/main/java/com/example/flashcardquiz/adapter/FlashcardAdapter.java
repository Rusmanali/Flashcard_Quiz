package com.example.flashcardquiz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcardquiz.R;
import com.example.flashcardquiz.model.Flashcard;

import java.util.List;

public class FlashcardAdapter extends ListAdapter<Flashcard, FlashcardAdapter.FlashcardViewHolder> {

    private OnItemClickListener listener;

    public FlashcardAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Flashcard> DIFF_CALLBACK = new DiffUtil.ItemCallback<Flashcard>() {
        @Override
        public boolean areItemsTheSame(@NonNull Flashcard oldItem, @NonNull Flashcard newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Flashcard oldItem, @NonNull Flashcard newItem) {
            return oldItem.getQuestion().equals(newItem.getQuestion()) &&
                    oldItem.getAnswer().equals(newItem.getAnswer());
        }
    };

    public interface OnItemClickListener {
        void onEditClick(Flashcard flashcard);
        void onDeleteClick(Flashcard flashcard);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard current = getItem(position);
        holder.tvQuestion.setText(current.getQuestion());
        holder.tvAnswer.setText(current.getAnswer());

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(current);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(current);
        });
    }

    public Flashcard getFlashcardAt(int position) {
        return getItem(position);
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvQuestion;
        private final TextView tvAnswer;
        private final ImageView btnEdit;
        private final ImageView btnDelete;

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}