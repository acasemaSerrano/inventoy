package com.acasema.inventory.iu.sections;

import com.acasema.inventory.data.model.Sections;
import com.acasema.inventory.iu.base.BaseView;

public interface SectionsManageContract {
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
        void validadteSections(Sections sections);
        void add(Sections sections);
        void edit(Sections sections);
    }
}
