package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private List<Article> articles;
    private AdapterView.OnItemClickListener onItemClickListener;
    // Constructeur pour initialiser le contexte et la liste des articles
    public NewsAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Liaison au fichier XML d'un élément de la liste
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        // Récupération de l'article actuel
        Article article = articles.get(position);

        // Mise à jour des vues avec les données de l'article
        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());

        // Chargement de l'image avec Glide
        Glide.with(context)
                .load(article.getUrlToImage())
                .placeholder(R.drawable.placeholder_image) // Image par défaut si aucune n'est disponible
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("description", article.getDescription());
            intent.putExtra("imageUrl", article.getUrlToImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    // Classe interne pour gérer les vues de chaque élément de la liste
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        ShapeableImageView imageView;
        private TextView title;
        public NewsViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title); // Référence au TextView du titre
            descriptionTextView = itemView.findViewById(R.id.description); // Référence au TextView de la description
            imageView = itemView.findViewById(R.id.image); // Référence à l'image de l'article
        }

        public void bind(Article article, OnItemClickListener listener) {
            title.setText(article.getTitle());
            itemView.setOnClickListener(v -> listener.onItemClick(article));
        }
    }
}
