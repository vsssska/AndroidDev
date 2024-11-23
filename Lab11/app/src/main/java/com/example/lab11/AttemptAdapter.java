package com.example.lab11;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AttemptAdapter extends RecyclerView.Adapter<AttemptAdapter.AttemptViewHolder> {
    private final List<String> attemptList = new ArrayList<>();

    public void submitList(List<String> newList) {
        attemptList.clear();
        attemptList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AttemptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        return new AttemptViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull AttemptViewHolder holder, int position) {
        holder.textView.setText(attemptList.get(position));
    }

    @Override
    public int getItemCount() {
        return attemptList.size();
    }

    static class AttemptViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        AttemptViewHolder(@NonNull TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }
}
