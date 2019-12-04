package com.acasema.inventory.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acasema.inventory.R;
import com.acasema.inventory.data.model.Sections;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.ViewHolder> {
    private ArrayList<Sections> list;
    private  OnManageSectionsListener listener;

    public void delete(Sections sections) {
        list.remove(sections);
    }
    public void add(Sections add){
        list.add(add);
    }


    public interface OnManageSectionsListener{

        void onEditSections(Sections sections);
        void onDeleteSections(Sections sections);
    }

    /**
     * los datos se van a usar desde el repositorio
     */

    public SectionsAdapter(OnManageSectionsListener litener) {
        //list= (ArrayList) DependencyRepository.getIntancie().getList();
        list =new  ArrayList<>();
        this.listener = litener;
    }
    public SectionsAdapter() {
        //list= (ArrayList) DependencyRepository.getIntancie().getList();
        list =new  ArrayList<>();
    }
    @NonNull
    @Override
    public SectionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dependecy_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.icon.setLetter(list.get(position).getName());
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnManageSectionsListener(SectionsAdapter.OnManageSectionsListener listenerAdapter) {
        listener = listenerAdapter;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        MaterialLetterIcon icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon =itemView.findViewById(R.id.icon);
            tvName = itemView.findViewById(R.id.tvName);
        }
        public  void bind (final Sections sections, final OnManageSectionsListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditSections(sections);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onDeleteSections(sections);
                    return true;
                }
            });
        }
    }

    public void clear() {
        list.clear();
    }

    public void load(List<Sections> sectionsList) {
        list.addAll(sectionsList);
    }
}
