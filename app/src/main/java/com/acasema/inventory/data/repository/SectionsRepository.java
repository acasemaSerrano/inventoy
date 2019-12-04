package com.acasema.inventory.data.repository;

import com.acasema.inventory.data.model.Sections;

import java.util.ArrayList;
import java.util.List;

public class SectionsRepository {
    private ArrayList<Sections> list;
    private static SectionsRepository sectionsRepository;

    static {
        sectionsRepository = new SectionsRepository();
    }

    static public SectionsRepository getInstance(){
        return sectionsRepository;
    }
    private SectionsRepository(){
        list = new ArrayList<>();
        //init();
    }


    private void init() {
        if (SectionsRepository.getInstance().getList().size()>1)
        list.add(new Sections("node", "yo tampoco", DependencyRepository.getIntancie().getList().get(0), "una Sections","url"));
    }

    public List<Sections> getList() {
        return list;
    }

    public boolean add(Sections sections) {
        if (buscar(sections)>=0)
            return false;
        return list.add(sections);
    }

    public int buscar(Sections sections) {
        for (int i = 0; i < list.size(); i++) {
            if (sections.equals(list.get(i)))
                return i;
        }
        return -1;
    }

    public void edit(Sections sections) {
        list.get(buscar(sections)).edit(sections);
    }

    public boolean remove(Sections sections){
        return list.remove(sections);
    }

}
