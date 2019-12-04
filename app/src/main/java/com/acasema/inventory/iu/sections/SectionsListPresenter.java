package com.acasema.inventory.iu.sections;

import android.os.AsyncTask;

import com.acasema.inventory.data.model.Sections;
import com.acasema.inventory.data.repository.SectionsRepository;

import java.util.List;

public class SectionsListPresenter implements SectionsListContract.Presenter {
    private SectionsListContract.View view;

    public SectionsListPresenter(SectionsListContract.View view){
        this.view = view;
    }

    //region DependencyListContract.Presenter
    @Override
    public void delete(Sections sections) {
        //borrar y conprobar que se a podidio
        if (SectionsRepository.getInstance().remove(sections)){
            //conprovar si no hay datos
            if (SectionsRepository.getInstance().getList().isEmpty())
                view.showImagenNoData();
            view.onSuccessDelete();
        }
    }
    @Override
    public void undo(Sections sections) {
        if (SectionsRepository.getInstance().add(sections)){
                view.onSuccessUndo(sections);
                view.hideImagenNoData();
                //if (SectionsRepository.getIntancie().getList().size()==1)
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



    private class LoadDataTast extends AsyncTask<Void,Void, List<Sections>>{

        @Override
        protected List<Sections> doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return SectionsRepository.getInstance().getList();
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
        protected void onPostExecute(List<Sections> sectionsList) {
            super.onPostExecute(sectionsList);
            view.hideProgress();
            if(sectionsList.isEmpty())
                view.showImagenNoData();
            else {
                //view.showData(dependencyList);
                if(!view.hasData())
                    view.hideImagenNoData();

                view.onSuccess(sectionsList);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    //endregion
}
