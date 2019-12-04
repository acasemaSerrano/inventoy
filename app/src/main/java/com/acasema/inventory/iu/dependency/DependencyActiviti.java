package com.acasema.inventory.iu.dependency;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.acasema.inventory.R;
import com.acasema.inventory.data.model.Dependency;

public class DependencyActiviti extends AppCompatActivity implements DependencyListFragment.OnManageDependencyListener {
//acticiti que controla los fragment

    private DependencyListFragment dependencyListFragment;
    private DependencyListPresenter dependencyListPresenter;
    private DependencyManageFragment dependencyManageFragment;
    private DependencyManagePresenter dependencyManagePresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependency);
        showListFragment();
    }

    private void showListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        dependencyListFragment = (DependencyListFragment) fragmentManager.findFragmentByTag(DependencyListFragment.TAG);
        if (dependencyListFragment == null)
        {
            dependencyListFragment = (DependencyListFragment) DependencyListFragment.newInstance(null);
            fragmentManager.beginTransaction().add(android.R.id.content, dependencyListFragment, DependencyListFragment.TAG).commit();
        }
        dependencyListPresenter =new DependencyListPresenter(dependencyListFragment);
        dependencyListFragment.setPresente(dependencyListPresenter);
    }
    private void showManageFragment(Dependency dependency) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        dependencyManageFragment = (DependencyManageFragment) getSupportFragmentManager().findFragmentByTag(DependencyManageFragment.TAG);
        if (dependencyManageFragment == null){
            Bundle bundle = null;
            if (dependency != null){
                bundle = new Bundle();
                //bundle.putParcelable(Dependency.TAG, dependency);
                bundle.putSerializable(Dependency.TAG, dependency);
            }
            dependencyManageFragment = (DependencyManageFragment) DependencyManageFragment.newInstance(bundle);
            //region contrato Fragment-Presenter
            //despues de crear la vista siampre se crea el presenter
            dependencyManagePresenter = new DependencyManagePresenter(dependencyManageFragment);
            dependencyManageFragment.setPresente(dependencyManagePresenter);
            //endregion
            fragmentTransaction.replace(android.R.id.content, dependencyManageFragment, DependencyManageFragment.TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onManageDependency(Dependency dependency) {
        showManageFragment(dependency);
    }
    // este metodo muestra el Fragment DependencyManageFragment
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        showManageFragment(null);
    }

}
