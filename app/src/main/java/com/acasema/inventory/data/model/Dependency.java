package com.acasema.inventory.data.model;



import androidx.annotation.Nullable;

import java.io.Serializable;

public class Dependency implements Serializable {

    public static final String TAG = "dependency";

    private String name;
    private String shortname;
    private String description;
    private String inventory;
    private String uslImagen;

    public Dependency(String name, String shortname, String description, String inventory, String uslImagen) {
        this.name = name;
        this.shortname = shortname;
        this.description = description;
        this.inventory = inventory;
        this.uslImagen = uslImagen;
    }

    public Dependency(){
        this.name = null;
        this.shortname = null;
        this.description = null;
        this.inventory = null;
        this.uslImagen = null;
    }

    @Override
    public String toString() {
        return "Dependency{" +
                "name='" + name + '\'' +
                ", shortname='" + shortname + '\'' +
                ", description='" + description + '\'' +
                ", uslImagen='" + uslImagen + '\'' +
                '}';
    }

    //region geter-Seter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getInventory() {
        return inventory;
    }
    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getUslImagen() {
        return uslImagen;
    }
    public void setUslImagen(String uslImagen) {
        this.uslImagen = uslImagen;
    }
    //endregion

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this.getClass() != obj.getClass())return false;
        return this.shortname.equals(((Dependency)obj).getShortname());
    }

    public void edit(Dependency d){
        this.name = d.getName();
        this.description = d.getDescription();
        this.inventory = d.getInventory();
        this.uslImagen = d.getUslImagen();
    }
}
