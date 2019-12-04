package com.acasema.inventory.data.repository;

import com.acasema.inventory.data.model.Dependency;

import java.util.ArrayList;
import java.util.List;

public class DependencyRepository {

    private List<Dependency> list;
    private static DependencyRepository repository;

    /**
     * se inicializan todas la propiedades estalicas de la clase en un metodo;
     * se evita comprovar que repository se a null
     */
    static {
        repository = new DependencyRepository();
    }

    private DependencyRepository(){
        list= new ArrayList<>();
        initialice();
    }

    private void initialice() {
        list.add(new Dependency("2º Ciclo Formativo superior", "2CFGS", "aula de informatica", "2018", "g"));
        list.add(new Dependency("1º Ciclo Formativo superior", "1CFGS", "aula de informatica", "2019", "g"));
        list.add(new Dependency("2º Ciclo Formativo medio", "2CFGM", "aula de informatica", "2020", "g"));
        list.add(new Dependency("1º Ciclo Formativo medio", "1CFGM", "aula de informatica", "2018", "g"));
        list.add(new Dependency("4ºA segundaria", "4ESOA", "aula normal", "2019", "g"));
        list.add(new Dependency("4ºB segundaria", "4ESOB", "aula normal", "2019","g"));
        list.add(new Dependency("4ºC segundaria", "4ESOC", "aula normal", "2020","g"));
        list.add(new Dependency("3ºA segundaria", "3ESOA", "aula normal", "2018","g"));
        list.add(new Dependency("3ºB segundaria", "3ESOB", "aula normal", "2020","g"));
        list.add(new Dependency("3ºC segundaria", "3ESOC", "aula normal", "2018","g"));
    }

    public static DependencyRepository getIntancie() {
        return repository;
    }

    public List<Dependency> getList() {
        return list;
    }

    public boolean add(Dependency dependency) {
        if (buscar(dependency)>=0)
            return false;
        return list.add(dependency);
    }

    public int buscar(Dependency dependency) {
        for (int i = 0; i < list.size(); i++) {
            if (dependency.equals(list.get(i)))
                return i;
        }
        return -1;
    }

    public Dependency buscar(String ShortName) {
        for (int i = 0; i < list.size(); i++) {
            if (ShortName.equals(list.get(i).getShortname()))
                return list.get(i);
        }
        return null;
    }

    public void edit(Dependency dependency) {
       list.get(buscar(dependency)).edit(dependency);
    }

    public boolean remove(Dependency dependency){
        return list.remove(dependency);
    }

    public List<String> toStringAray(){
        List<String> list = new ArrayList<>();

        for (int i = 0; i < this.list.size(); i++) {
            list.add(this.list.get(i).getShortname());
        }

        return list;
    }
}
