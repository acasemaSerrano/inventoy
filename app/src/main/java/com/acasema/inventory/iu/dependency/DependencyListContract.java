package com.acasema.inventory.iu.dependency;

import com.acasema.inventory.data.model.Dependency;
import com.acasema.inventory.iu.base.BaseView;

import java.util.List;

public interface DependencyListContract {
    interface  View extends BaseView<Presenter> {
        void showProgress();
        void hideProgress();
        void showImagenNoData();
        boolean hasData();
        void hideImagenNoData();
        void onSuccess(List<Dependency> dependencyList);
        void onSuccessDelete();
        void onSuccessUndo(Dependency dependency);

    }
    interface Presenter{
        void delete(Dependency dependency);
        void load();
        void undo(Dependency dependency);
    }
}
