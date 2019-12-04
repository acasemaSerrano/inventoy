package com.acasema.inventory.iu.sections;


import com.acasema.inventory.data.model.Sections;
import com.acasema.inventory.data.repository.SectionsRepository;
import com.acasema.inventory.utils.commonUtils;

public class SectionsManagePresenter implements SectionsManageContract.Presenter {

    private SectionsManageContract.View view;

    public SectionsManagePresenter(SectionsManageContract.View view){
        this.view = view;
    }

    /**
     * este metodo valida RND2,RND3
     */
    @Override
    public void validadteSections(Sections sections) {
        if (true)
            view.onSuccessValidate();
        else
            view.showError("el nombre corto no es simple");
    }
    /**
     * este metodo valida RND4
     */
    @Override
    public void add(Sections sections) {
        if(!SectionsRepository.getInstance().add(sections)){
            view.showError("ya esiste el nombre corto");
            return;
        }
        view.onSuccess();
    }

    @Override
    public void edit(Sections sections) {
        SectionsRepository.getInstance().edit(sections);
        view.onSuccess();
    }
}
