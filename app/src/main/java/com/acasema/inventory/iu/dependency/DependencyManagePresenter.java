package com.acasema.inventory.iu.dependency;


import com.acasema.inventory.data.model.Dependency;
import com.acasema.inventory.data.repository.DependencyRepository;
import com.acasema.inventory.utils.commonUtils;

public class DependencyManagePresenter implements DependencyManageContract.Presenter {

    private DependencyManageContract.View view;

    public DependencyManagePresenter(DependencyManageContract.View view){
        this.view = view;
    }

    /**
     * este metodo valida RND2,RND3
     */
    @Override
    public void validadteDependency(Dependency dependency) {
        if (!commonUtils.checkPatternShortName(dependency.getShortname()))
            view.onSuccessValidate();
        else
            view.showError("el nombre corto no es simple");
    }
    /**
     * este metodo valida RND4
     */
    @Override
    public void add(Dependency dependency) {
        if(!DependencyRepository.getIntancie().add(dependency)){
            view.showError("ya esiste el nombre corto");
            return;
        }
        view.onSuccess();
    }

    @Override
    public void edit(Dependency dependency) {
        DependencyRepository.getIntancie().edit(dependency);
        view.onSuccess();
    }
}
