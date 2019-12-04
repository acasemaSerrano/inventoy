package com.acasema.inventory.iu.dependency;

import android.os.AsyncTask;

import com.acasema.inventory.data.model.Dependency;
import com.acasema.inventory.data.repository.DependencyRepository;

import java.util.List;

public class DependencyListPresenter implements DependencyListContract.Presenter {
    private DependencyListContract.View view;

    public DependencyListPresenter(DependencyListContract.View view){
        this.view = view;
    }

    //region DependencyListContract.Presenter
    @Override
    public void delete(Dependency dependency) {
        //borrar y conprobar que se a podidio
        if (DependencyRepository.getIntancie().remove(dependency)){
            //conprovar si no hay datos
            if (DependencyRepository.getIntancie().getList().isEmpty())
                view.showImagenNoData();
            view.onSuccessDelete();
        }
    }
    @Override
    public void undo(Dependency dependency) {
        if (DependencyRepository.getIntancie().add(dependency)){
                view.onSuccessUndo(dependency);
                view.hideImagenNoData();
                //if (DependencyRepository.getIntancie().getList().size()==1)
                    //view.hideImagenNoData();
        }
    }

    @Override
    public void load() {
        new LoadDataTast().execute();
/*        new AsyncTask<Void,Void, List<Dependency>>(){
            @Override
            protected List<Dependency> doInBackground(Void... voids) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return DependencyRepository.getIntancie().getList();
            }
        };*/

    }



    private class LoadDataTast extends AsyncTask<Void,Void, List<Dependency>>{

        @Override
        protected List<Dependency> doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return DependencyRepository.getIntancie().getList();
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!view.hasData())
                view.hideImagenNoData();
            view.showProgress();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(List<Dependency> dependencyList) {
            super.onPostExecute(dependencyList);
            view.hideProgress();
            if(dependencyList.isEmpty())
                view.showImagenNoData();
            else {
                //view.showData(dependencyList);
                if(!view.hasData())
                    view.hideImagenNoData();

                view.onSuccess(dependencyList);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    //endregion
}
