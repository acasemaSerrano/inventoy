package com.acasema.inventory.data.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Sections implements Serializable {
    public static final String TAG = "sections";
    private String name;
    private String shortName;
    private Dependency dependency;
    private String descripcion;
    private String urlImg;

    //region get-set
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Dependency getDependency() {
        return dependency;
    }
    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImg() {
        return urlImg;
    }
    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
    //endregion
    //region contructores
    public Sections() {
        this.name = null;
        this.shortName = null;
        this.dependency = null;
        this.descripcion = null;
        this.urlImg = null;
    }
    public Sections(String name, String shortName, Dependency dependency, String descripcion, String urlImg) {
        this.name = name;
        this.shortName = shortName;
        this.dependency = dependency;
        this.descripcion = descripcion;
        this.urlImg = urlImg;
    }
    //endregion

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this.getClass() != obj.getClass())
            return false;
        return this.shortName.equals(((Sections)obj).getShortName());
    }

    public void edit(Sections s){
        this.name = s.getName();
        this.dependency = s.getDependency();
        this.descripcion = s.getDescripcion();
        this.urlImg = s.getUrlImg();
    }
}
