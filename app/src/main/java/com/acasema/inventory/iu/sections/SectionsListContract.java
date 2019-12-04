package com.acasema.inventory.iu.sections;

import com.acasema.inventory.data.model.Sections;
import com.acasema.inventory.iu.base.BaseView;

import java.util.List;

public interface SectionsListContract {
    interface  View extends BaseView<Presenter> {
        void showProgress();
        void hideProgress();
        void showImagenNoData();
        boolean hasData();
        void hideImagenNoData();
        void onSuccess(List<Sections> sectionsList);
        void onSuccessDelete();
        void onSuccessUndo(Sections sections);

    }
    interface Presenter{
        void delete(Sections sections);
        void load();
        void undo(Sections sections);
    }
}
