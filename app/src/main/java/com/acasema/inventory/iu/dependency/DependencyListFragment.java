package com.acasema.inventory.iu.dependency;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.acasema.inventory.R;
import com.acasema.inventory.adapter.DependencyAdapter;
import com.acasema.inventory.data.model.Dependency;
import com.acasema.inventory.data.repository.DependencyRepository;
import com.acasema.inventory.iu.base.BaseDialogoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class DependencyListFragment extends Fragment implements DependencyListContract.View, BaseDialogoFragment.OnAcceptDialogLisener {

    public static final String TAG = "DependencyListFragment";
    public static final int CODE_DELETE = 300;
    private final int SPAN_COUNT = 3;

    //objeto con funciones de delegado
    private OnManageDependencyListener listener;
    //objeto con funciones de delegado
    private DependencyAdapter.OnManageDependencyListener listenerAdapter;

    private DependencyAdapter adapter;
    private DependencyListContract.Presenter presente;

    private FloatingActionButton fab;
    private RecyclerView rvDependency;
    private ImageView imgNodata;
    private ConstraintLayout progress;
    private Dependency deleted;


    public interface OnManageDependencyListener {
        void onManageDependency(Dependency dependency);
    }

    //region Metodos del ciclo de vida
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnManageDependencyListener) context;

        } catch (ClassCastException e) {
            throw new  ClassCastException(context.toString() + "must implenment OnManageDependencyListener");
        }
    }

    public static Fragment newInstance(Bundle bundle){
        DependencyListFragment fragment = new DependencyListFragment();
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

        View view = inflater.inflate(R.layout.fragment_dependency_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = view.findViewById(R.id.pb);
        rvDependency = view.findViewById(R.id.rvDependency);
        initListDependency();

        fab = view.findViewById(R.id.fab);
        intFab();

        imgNodata = view.findViewById(R.id.img);


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
                listener.onManageDependency(null);
            }
        });
    }

    /*
        METODO QIE INICIA EL RECICLE REVIEW
     */
    private void initListDependency() {

        initListenerAdapter();
        adapter = new DependencyAdapter();
        adapter.setOnManageDependencyListener(listenerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, RecyclerView.VERTICAL, false);

        rvDependency.setLayoutManager(linearLayoutManager);
        rvDependency.setAdapter(adapter);
    }

    /**
     * Metodo que inicializa el lisener que analiza eventos del adapter
     */
    private void initListenerAdapter() {
        listenerAdapter = new DependencyAdapter.OnManageDependencyListener() {
            @Override
            public void onEditDependency(Dependency dependency) {
                listener.onManageDependency(dependency);
            }

            @Override
            public void onDeleteDependency(Dependency dependency) {
                showDeleteDialogo(dependency);

            }
        };

    }
    private  void showDeleteDialogo(Dependency dependency){
        Bundle bundle = new Bundle();
        bundle.putString(BaseDialogoFragment.TITLE, getString(R.string.titulo_delete));
        bundle.putString(BaseDialogoFragment.MESSAGE, getString(R.string.tmessage_delete));

        BaseDialogoFragment dialogoFragment =BaseDialogoFragment.newIntace(bundle);
        dialogoFragment.setTargetFragment(DependencyListFragment.this, CODE_DELETE);

        dialogoFragment.show(getFragmentManager(), BaseDialogoFragment.TAG);

        deleted = dependency;
        //DependencyRepository.getIntancie().remove(dependency);
    }
    @Override
    public void onAcceptDialog() {
        DependencyDeleted();
    }

    private void DependencyDeleted() {
        presente.delete(deleted);
    }

    //endregion
    //region Metodos DependencyListContract.View
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
    public void setPresente(DependencyListContract.Presenter presente) {
        this.presente = presente;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onSuccess(List<Dependency> dependencyList) {
        adapter.clear();
        adapter.load(dependencyList);
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
    public void onSuccessUndo(Dependency dependency) {
        adapter.add(dependency);
    }

    private void showSnackBarDeleted() {
        final Dependency undoDependency = deleted;
        Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.useDeleted), Snackbar.LENGTH_SHORT).setAction(getString(R.string.undo), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoDeleted(undoDependency);
            }
        }).show();
    }

    private void undoDeleted(Dependency undoDependency) {
        presente.undo(undoDependency);

    }


    @Override
    public void onSuccess() {

    }
    //endregion
}


