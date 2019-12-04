package com.acasema.inventory.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acasema.inventory.R;
import com.acasema.inventory.data.model.Dependency;
import com.acasema.inventory.data.repository.DependencyRepository;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DependencyAdapter extends RecyclerView.Adapter<DependencyAdapter.ViewHolder> {
    private ArrayList<Dependency> list;
    private  OnManageDependencyListener listener;

    public void delete(Dependency deleted) {
        list.remove(deleted);
    }
    public void add(Dependency add){
        list.add(add);
    }


    public interface OnManageDependencyListener{

        void onEditDependency(Dependency dependency);
        void onDeleteDependency(Dependency dependency);
    }

    /**
     * los datos se van a usar desde el repositorio
     */

    public DependencyAdapter(OnManageDependencyListener litener) {
        //list= (ArrayList) DependencyRepository.getIntancie().getList();
        list =new  ArrayList<>();
        this.listener = litener;
    }
    public DependencyAdapter() {
        //list= (ArrayList) DependencyRepository.getIntancie().getList();
        list =new  ArrayList<>();
    }
    @NonNull
    @Override
    public DependencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

    public void setOnManageDependencyListener(DependencyAdapter.OnManageDependencyListener listenerAdapter) {
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
        public  void bind (final Dependency dependency, final OnManageDependencyListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditDependency(dependency);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onDeleteDependency(dependency);
                    return true;
                }
            });
        }
    }

    public void clear() {
        list.clear();
    }

    public void load(List<Dependency> dependencyList) {
        list.addAll(dependencyList);
    }
}
