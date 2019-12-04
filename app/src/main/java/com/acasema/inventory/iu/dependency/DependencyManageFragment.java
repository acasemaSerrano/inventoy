package com.acasema.inventory.iu.dependency;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.acasema.inventory.R;
import com.acasema.inventory.data.model.Dependency;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class DependencyManageFragment extends Fragment  implements DependencyManageContract.View{
    public static final String TAG = "DependencyManageFragment";


    private EditText etShortName;
    private EditText etName;
    private Spinner spnInventory;
    private EditText etDescripcion;

    private Dependency dependency;
    private boolean editable;

    private FloatingActionButton fad;
    private DependencyManageContract.Presenter presenter;

    public static Fragment newInstance(Bundle bundle) {
        DependencyManageFragment fragment = new DependencyManageFragment();

        fragment.dependency = new Dependency();
        fragment.editable = false;
        if (bundle != null)
        {
            fragment.setArguments(bundle);
            //Dependency dependency =bundle.getParcelable(Dependency.TAG);
            Dependency dependency = (Dependency) bundle.getSerializable(Dependency.TAG);

            fragment.dependency = dependency;
            fragment.editable = true;

        }

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dependency_manage, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fad = view.findViewById(R.id.fadSalveDep);
        intFad();
        etShortName =view.findViewById(R.id.etShortName);
        etName =view.findViewById(R.id.etName);
        spnInventory =view.findViewById(R.id.spnInventory);
        etDescripcion =view.findViewById(R.id.etDescripcion);

        etShortName.setText(dependency.getShortname());
        etName.setText(dependency.getName());
        etDescripcion.setText(dependency.getDescription());

        ArrayAdapter<CharSequence> spinerOption = ArrayAdapter.createFromResource(view.getContext(), R.array.inventory_array, android.R.layout.simple_spinner_item);
        spnInventory.setSelection(spinerOption.getPosition(dependency.getInventory()));

        //RND5:
            etShortName.setEnabled(!editable);
    }
/*
este metodo valida la dependencia
 */
    private void intFad() {
        fad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidDependency())
                presenter.validadteDependency(getDependency());

            }
        });
    }

    /**
     * metodo que comprueva las reclas de requisito RND1,RND5, RND7 (de compre siempre al tener una opcion por defecto siempre)
     * @return
     */
    public boolean isValidDependency(){
        //RND1: canpos vacio
        if (TextUtils.isEmpty(etName.getText().toString())){
            showErrorThis(getResources().getString(R.string.errNameEmpty));
            return false;
        }
        if (TextUtils.isEmpty(etShortName.getText().toString())){
            showErrorThis(getResources().getString(R.string.errShortNameEmpty));
            return false;
        }
        if (TextUtils.isEmpty(etDescripcion.getText().toString())){
            showErrorThis(getResources().getString(R.string.errescripcionEmpty));
            return false;
        }
        return true;
    }

    private void showErrorThis(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * metodo que recoge una dependencia
     * @return
     */
    public Dependency getDependency() {
        return new Dependency(etName.getText().toString(),etShortName.getText().toString(),etDescripcion.getText().toString(),spnInventory.getSelectedItem().toString(),"");
    }

    //region metodos que vienen del contraro DependencyManageContract.View
    @Override
    public void setPresente(DependencyManageContract.Presenter presente) {
        this.presenter = presente;
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * este metodo llamado desde el presentador depues de ass o editar ace qu emuentre DependenyListFragment
     */
    @Override
    public void onSuccess() {
        getActivity().onBackPressed();
    }

    /**
     * metodo que es llamada delde el presentador despues de copmprovar
     * la dependencia correcta/valida
     */
    @Override
    public void onSuccessValidate() {
        Dependency dependency = getDependency();
        if (editable){
            presenter.edit(dependency);
        }
        else
            presenter.add(dependency);
    }
    //endregion
}
