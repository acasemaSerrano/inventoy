package com.acasema.inventory.iu.sections;

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
import com.acasema.inventory.data.model.Sections;
import com.acasema.inventory.data.repository.DependencyRepository;
import com.acasema.inventory.data.repository.SectionsRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;


public class SectionsManageFragment extends Fragment  implements SectionsManageContract.View{
    public static final String TAG = "DependencyManageFragment";


    private TextInputLayout tilName, tilShortName, tilDescripcion, tilImg;
    private TextInputEditText tiedName, tiedShortName, tiedDescripcion, tiedImg;

    private Spinner spnInventory;
    private Sections sections;
    private boolean editable;

    private FloatingActionButton fad;
    private SectionsManageContract.Presenter presenter;

    public static Fragment newInstance(Bundle bundle) {
        SectionsManageFragment fragment = new SectionsManageFragment();

        fragment.sections = new Sections();
        fragment.editable = false;
        if (bundle != null)
        {
            fragment.setArguments(bundle);
            //Dependency sections =bundle.getParcelable(Dependency.TAG);
            Sections sections = (Sections) bundle.getSerializable(Sections.TAG);

            fragment.sections = sections;
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
        return inflater.inflate(R.layout.fragment_sections_manage, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fad = view.findViewById(R.id.fadSalveDep);
        intFad();
        tilName =view.findViewById(R.id.tilName);
        tilShortName =view.findViewById(R.id.tilShortName);
        tilDescripcion =view.findViewById(R.id.tilDescripcion);
        tilImg =view.findViewById(R.id.tilImg);

        tiedName =view.findViewById(R.id.tiedShortName);
        tiedShortName =view.findViewById(R.id.tiedShortName);
        tiedDescripcion =view.findViewById(R.id.tiedDescripcion);
        tiedImg=view.findViewById(R.id.tiedImg);

        spnInventory = view.findViewById(R.id.spDependencia);

        tiedName.setText(sections.getName());
        tiedShortName.setText(sections.getShortName());
        tiedDescripcion.setText(sections.getDescripcion());
        tiedImg.setText(sections.getUrlImg());



        ArrayAdapter<String> comboAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, DependencyRepository.getIntancie().toStringAray());
        //Cargo el spinner con los datos
        spnInventory.setAdapter(comboAdapter);

        if (editable)
            spnInventory.setSelection(DependencyRepository.getIntancie().buscar(sections.getDependency()));

        //RND5:
        tiedName.setEnabled(!editable);
    }

    private void intFad() {
        fad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidSections())
                presenter.validadteSections(getSections());

            }
        });
    }

    public boolean isValidSections(){
        //RND1: canpos vacio
        if (tiedName.getText().toString().isEmpty()){
            showErrorThis(getResources().getString(R.string.errNameEmpty));
            return false;
        }
        if (tiedShortName.getText().toString().isEmpty()){
            showErrorThis(getResources().getString(R.string.errShortNameEmpty));
            return false;
        }
        if (tiedDescripcion.getText().toString().isEmpty()){
            showErrorThis(getResources().getString(R.string.errescripcionEmpty));
            return false;
        }
        return true;
    }

    private void showErrorThis(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    public Sections getSections() {
        return new Sections(tiedName.getText().toString(), tiedShortName.getText().toString(), DependencyRepository.getIntancie().buscar((String) spnInventory.getSelectedItem()), tiedDescripcion.getText().toString(), tiedImg.getText().toString());
    }

    //region metodos que vienen del contraro DependencyManageContract.View
    @Override
    public void setPresente(SectionsManageContract.Presenter presente) {
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
        Sections sections = getSections();
        if (editable)
            presenter.edit(sections);
        else
            presenter.add(sections);
    }
    //endregion
}
