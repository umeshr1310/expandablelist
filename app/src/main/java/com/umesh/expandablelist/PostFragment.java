package com.umesh.expandablelist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.infideap.expandablerecyclerview.ExpandableRecycler;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PostFragment extends Fragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostFragment() {
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostFragment newInstance(int columnCount) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expandable_list, container, false);
        Context context = view.getContext();
        final TextView actionTextView = view.findViewById(R.id.textView_action);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        Gson gson = new Gson();
        try {
            // Convert json to POJO
            List<Post> posts =
                    Arrays.asList(gson.fromJson(readFromAsset("data/post.json"), Post[].class));
            PostExpandableRecyclerViewAdapter adapter
                    = new PostExpandableRecyclerViewAdapter(posts, mListener);
            // adapter.setToggleDrawable(null); // set custom icon for toggle
            adapter.setShowToggle(false); // hide or show toggle
            // set divider color
            adapter.setDividerColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            // set divider color none
            // adapter.setDividerColor(0);
            recyclerView.setAdapter(adapter);
            // listen to expand/collapse state
            adapter.setToggleListener(new ExpandableRecycler.ToggleListener() {
                @Override
                public void onExpand(int position) {
                    actionTextView.setText("Expand at index " + position);
                }
                @Override
                public void onCollapse(int position) {
                    actionTextView.setText("Collapse at index " + position);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }
    /**
     * This method use to read data from Asset Resource Directory (Local).
     *
     * @param path path of the local REST
     * @return plain text from file
     * @throws IOException
     */
    private String readFromAsset(String path) throws IOException {
        byte[] buffer;
        InputStream inputStream;
        try {
            inputStream = getContext().getAssets().open(path);
        } catch (IOException e) {
//            e.a(new Throwable("Local file must store in Asset Resource Folder."));
            IOException ioException = new IOException(e.toString() + "\n\r" +
                    "Local REST must store in Asset Resource Folder.");
            ioException.setStackTrace(e.getStackTrace());
            throw ioException;
//            throw new IOException();
        }
        int size = inputStream.available();
        if (size == 0) {
        }
        buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        return new String(buffer);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Post item);
    }
}
