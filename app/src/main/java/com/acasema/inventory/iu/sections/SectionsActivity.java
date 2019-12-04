package com.acasema.inventory.iu.sections;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.acasema.inventory.R;
import com.acasema.inventory.data.model.Sections;

public class SectionsActivity extends AppCompatActivity implements SectionsListFragment.OnManageSectionsListener {
//acticiti que controla los fragment

    private SectionsListFragment sectionsListFragment;
    private SectionsListPresenter sectionsListPresenter;
    private SectionsManageFragment sectionsManageFragment;
    private SectionsManagePresenter sectionsManagePresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);
        showListFragment();
    }

    private void showListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        sectionsListFragment = (SectionsListFragment) fragmentManager.findFragmentByTag(SectionsListFragment.TAG);
        if (sectionsListFragment == null)
        {
            sectionsListFragment = (SectionsListFragment) SectionsListFragment.newInstance(null);
            fragmentManager.beginTransaction().add(android.R.id.content, sectionsListFragment, SectionsListFragment.TAG).commit();
        }
        sectionsListPresenter =new SectionsListPresenter(sectionsListFragment);
        sectionsListFragment.setPresente(sectionsListPresenter);
    }
    private void showManageFragment(Sections sections) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        sectionsManageFragment = (SectionsManageFragment) getSupportFragmentManager().findFragmentByTag(SectionsManageFragment.TAG);
        if (sectionsManageFragment == null){
            Bundle bundle = null;
            if (sections != null){
                bundle = new Bundle();
                //bundle.putParcelable(Dependency.TAG, sections);
                bundle.putSerializable(Sections.TAG, sections);
            }
            sectionsManageFragment = (SectionsManageFragment) SectionsManageFragment.newInstance(bundle);
            //region contrato Fragment-Presenter
            //despues de crear la vista siampre se crea el presenter
            sectionsManagePresenter = new SectionsManagePresenter(sectionsManageFragment);
            sectionsManageFragment.setPresente(sectionsManagePresenter);
            //endregion
            fragmentTransaction.replace(android.R.id.content, sectionsManageFragment, SectionsManageFragment.TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void onManageSections(Sections sections) {
        showManageFragment(sections);
    }
    // este metodo muestra el Fragment DependencyManageFragment
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        showManageFragment(null);
    }

}
