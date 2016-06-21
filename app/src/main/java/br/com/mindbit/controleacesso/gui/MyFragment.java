package br.com.mindbit.controleacesso.gui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.mindbit.R;

/**
 * Created by Bruna on 20/06/2016.
 */
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.my_fragment_layout, container, false);
    }
}
