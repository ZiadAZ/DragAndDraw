package com.example.draganddraw;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DragAndDrawFragment extends Fragment {
    BoxDrawingView boxDrawingView;
    TextView textView;

    public static DragAndDrawFragment newInstance() {
        return new DragAndDrawFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drag_and_draw, container, false);
        boxDrawingView =v.findViewById(R.id.bdv);
        return v;
    }

}
