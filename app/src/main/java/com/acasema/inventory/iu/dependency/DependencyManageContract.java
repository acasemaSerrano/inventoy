package com.acasema.inventory.iu.dependency;

import com.acasema.inventory.data.model.Dependency;
import com.acasema.inventory.iu.base.BaseView;

public interface DependencyManageContract {
    /**
     * interfaz qie coresponde al acontrato que se establece la vista (DependencyManageFragment)
     * y presentador (DependencyManagePresenter)
     */
    interface View extends BaseView<Presenter> {
        void onSuccessValidate();
    }
    /**
     * interfaz qie coresponde al acontrato que se establece la presentador (DependencyManagePresenter)
     * y vista (DependencyManageFragment)
     */
    interface Presenter {
        void validadteDependency(Dependency dependency);
        void add(Dependency dependency);
        void edit(Dependency dependency);
    }
}
