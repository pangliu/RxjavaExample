package com.example.rxjava.hank.rxjavasample.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxjava.hank.rxjavasample.DataInfo.VersionInfo;
import com.example.rxjava.hank.rxjavasample.Dialog.LoadingDialog;
import com.example.rxjava.hank.rxjavasample.Fragment.Presenter.VersionContract;
import com.example.rxjava.hank.rxjavasample.Fragment.Presenter.VersionPresenter;
import com.example.rxjava.hank.rxjavasample.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTest1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTest1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTest1 extends Fragment implements VersionContract.view{

//    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private TextView tvVersion;
    private Button btnGetVersion;
    private VersionPresenter mPresenter;
    private LoadingDialog loadingDialog;

    public FragmentTest1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTest1.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTest1 newInstance(String param1, String param2) {
        FragmentTest1 fragment = new FragmentTest1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentTest1 newInstance() {
        FragmentTest1 fragment = new FragmentTest1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("msg", "fragment1 onCreate");
        mContext = getActivity();
        mPresenter = new VersionPresenter(mContext, this);
        loadingDialog = new LoadingDialog(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("msg", "fragment1 onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test1, container, false);
        tvVersion = (TextView) view.findViewById(R.id.tv_version);
        btnGetVersion = (Button) view.findViewById(R.id.btn_get_version);
        btnGetVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                mPresenter.getVersion("android");
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("msg", "fragment1 onAttach");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("msg", "fragment1 onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("msg", "fragment1 onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("msg", "fragment1 onDetach");
//        mListener = null;
    }

    @Override
    public void updateVersion() {

    }

    @Override
    public void showVersion(VersionInfo versionInfo) {
        tvVersion.setText("版本號: " + versionInfo.getVersion());
        loadingDialog.dismiss();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
