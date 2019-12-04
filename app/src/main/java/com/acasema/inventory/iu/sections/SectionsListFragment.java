package com.acasema.inventory.iu.sections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.acasema.inventory.R;
import com.acasema.inventory.adapter.SectionsAdapter;
import com.acasema.inventory.data.model.Sections;
import com.acasema.inventory.iu.base.BaseDialogoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SectionsListFragment extends Fragment implements SectionsListContract.View, BaseDialogoFragment.OnAcceptDialogLisener {

    public static final String TAG = "SectionsListFragment";
    public static final int CODE_DELETE = 300;
    private final int SPAN_COUNT = 3;

    //objeto con funciones de delegado
    private OnManageSectionsListener listener;
    //objeto con funciones de delegado
    private SectionsAdapter.OnManageSectionsListener listenerAdapter;

    private SectionsAdapter adapter;
    private SectionsListContract.Presenter presente;

    private FloatingActionButton fab;
    private RecyclerView rvSections;
    private ImageView imgNodata;
    private ConstraintLayout progress;
    private Sections deleted;


    public interface OnManageSectionsListener {
        void onManageSections(Sections sections);
    }

    //region Metodos del ciclo de vida
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnManageSectionsListener) context;

        } catch (ClassCastException e) {
            throw new  ClassCastException(context.toString() + "must implenment OnManageSectionsListener");
        }
    }

    public static Fragment newInstance(Bundle bundle){
        SectionsListFragment fragment = new SectionsListFragment();
        if (bundle != null)
        {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_sections_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = view.findViewById(R.id.ipd);
        rvSections = view.findViewById(R.id.rvSectoresList);
        initListSections();

        fab = view.findViewById(R.id.fabAdd);
        intFab();

        imgNodata = view.findViewById(R.id.imgNodata);


    }

    //se solicita una carga de datos al presentador
    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        presente.load();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    //endregion
    //region Metodos de inicializacion
    private void intFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onManageSections(null);
            }
        });
    }

    /*
        METODO QIE INICIA EL RECICLE REVIEW
     */
    private void initListSections() {

        initListenerAdapter();
        adapter = new SectionsAdapter();
        adapter.setOnManageSectionsListener(listenerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, RecyclerView.VERTICAL, false);

        rvSections.setLayoutManager(linearLayoutManager);
        rvSections.setAdapter(adapter);
    }

    /**
     * Metodo que inicializa el lisener que analiza eventos del adapter
     */
    private void initListenerAdapter() {
        listenerAdapter = new SectionsAdapter.OnManageSectionsListener() {
            @Override
            public void onEditSections(Sections sections) {
                listener.onManageSections(sections);
            }

            @Override
            public void onDeleteSections(Sections sections) {
                showDeleteDialogo(sections);

            }
        };

    }
    private  void showDeleteDialogo(Sections sections){
        Bundle bundle = new Bundle();
        bundle.putString(BaseDialogoFragment.TITLE, getString(R.string.titulo_delete));
        bundle.putString(BaseDialogoFragment.MESSAGE, getString(R.string.tmessage_delete));

        BaseDialogoFragment dialogoFragment =BaseDialogoFragment.newIntace(bundle);
        dialogoFragment.setTargetFragment(SectionsListFragment.this, CODE_DELETE);

        dialogoFragment.show(getFragmentManager(), BaseDialogoFragment.TAG);

        deleted = sections;
        //DependencyRepository.getIntancie().remove(sections);
    }
    @Override
    public void onAcceptDialog() {
        SectionsDeleted();
    }

    private void SectionsDeleted() {
        presente.delete(deleted);
    }

    //endregion
    //region Metodos SectionsListContract.View
    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);

    }

    @Override
    public void showImagenNoData() {
        imgNodata.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImagenNoData() {
        imgNodata.setVisibility(View.GONE);
    }

    @Override
    public boolean hasData(){
        return imgNodata.getVisibility() == View.GONE;
    }

    @Override
    public void setPresente(SectionsListContract.Presenter presente) {
        this.presente = presente;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onSuccess(List<Sections> sectionsList) {
        adapter.clear();
        adapter.load(sectionsList);
        //se dedevi llamar al metodo notifydataChanged del adaptar
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessDelete() {
        adapter.delete(deleted);
        adapter.notifyDataSetChanged();
        showSnackBarDeleted();
        deleted = null;
    }

    @Override
    public void onSuccessUndo(Sections sections) {
        adapter.add(sections);
    }

    private void showSnackBarDeleted() {
        final Sections undoSections = deleted;
        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.useDeleted), Snackbar.LENGTH_SHORT).setAction(getString(R.string.undo), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoDeleted(undoSections);
            }
        }).show();
    }

    private void undoDeleted(Sections undoSections) {
        presente.undo(undoSections);

    }


    @Override
    public void onSuccess() {

    }
    //endregion
}


