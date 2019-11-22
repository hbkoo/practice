package com.example.acer.wuli.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.wuli.main.activity.MovieListActivity;
import com.example.acer.wuli.R;


public class SearchPageFragment extends Fragment {
    View view;
    private TextView tv_hot1;
    private TextView tv_hot2;
    private TextView tv_hot3;
    private TextView tv_hot4;
    private TextView tv_hot5;
    private TextView tv_type_hot;
    private TextView tv_type_comedy;
    private TextView tv_type_romance;
    private TextView tv_type_action;
    private TextView tv_type_drama;
    private TextView tv_type_animation;
    private TextView tv_type_thriller;
    private TextView tv_type_all;
    private EditText et_search;
    private ImageView iv_cancel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_searchpage,null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setListener();
    }

    private void setListener(){
        et_search = view.findViewById(R.id.et_search);
        iv_cancel=view.findViewById(R.id.iv_cancel);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId ==EditorInfo.IME_ACTION_SEARCH){
                    ((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    Log.i("info","search_result_list_item");
                    String searchContent=et_search.getText().toString().trim();
                    if(!searchContent.equals("")||searchContent!=null){
                        String searchType="name";
                        //TODO 跳转
   //                     Log.i("test",searchContent);
                        Intent intent=new Intent(getActivity(),MovieListActivity.class);
                        intent.putExtra("searchType",searchType);
                        intent.putExtra("searchContent",searchContent);
                        startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });
        et_search.addTextChangedListener(new EditChangedListener());
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
                iv_cancel.setVisibility(View.GONE);
            }
        });

        tv_hot1=view.findViewById(R.id.tv_hot1);
        tv_hot2=view.findViewById(R.id.tv_hot2);
        tv_hot3=view.findViewById(R.id.tv_hot3);
        tv_hot4=view.findViewById(R.id.tv_hot4);
        tv_hot5=view.findViewById(R.id.tv_hot5);

        tv_hot1.setOnClickListener(new HotSearchListener());
        tv_hot2.setOnClickListener(new HotSearchListener());
        tv_hot3.setOnClickListener(new HotSearchListener());
        tv_hot4.setOnClickListener(new HotSearchListener());
        tv_hot5.setOnClickListener(new HotSearchListener());

        tv_type_hot=view.findViewById(R.id.tv_type_hot);
        tv_type_comedy=view.findViewById(R.id.tv_type_comedy);
        tv_type_romance=view.findViewById(R.id.tv_type_romance);
        tv_type_action=view.findViewById(R.id.tv_type_action);
        tv_type_drama=view.findViewById(R.id.tv_type_drama);
        tv_type_animation=view.findViewById(R.id.tv_type_animation);
        tv_type_thriller=view.findViewById(R.id.tv_type_thriller);
        tv_type_all=view.findViewById(R.id.tv_type_all);

        tv_type_hot.setOnClickListener(new TypeSearchListener());
        tv_type_comedy.setOnClickListener(new TypeSearchListener());
        tv_type_romance.setOnClickListener(new TypeSearchListener());
        tv_type_action.setOnClickListener(new TypeSearchListener());
        tv_type_drama.setOnClickListener(new TypeSearchListener());
        tv_type_animation.setOnClickListener(new TypeSearchListener());
        tv_type_thriller.setOnClickListener(new TypeSearchListener());
        tv_type_all.setOnClickListener(new TypeSearchListener());
    }

    class HotSearchListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView textView=view.findViewById(v.getId());
            String searchType="name";
            String searchContent=textView.getText().toString();
 //           Log.i("test",searchContent);
            //TODO 跳转
            Intent intent=new Intent(getActivity(),MovieListActivity.class);
            intent.putExtra("searchType",searchType);
            intent.putExtra("searchContent",searchContent);
            startActivity(intent);

        }
    }

    class TypeSearchListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView textView=view.findViewById(v.getId());
            String searchType="type";
            String searchContent=textView.getText().toString();
            Log.i("test",searchContent);
            //TODO 跳转
            Intent intent=new Intent(getActivity(),MovieListActivity.class);
            intent.putExtra("searchType",searchType);
            intent.putExtra("searchContent",searchContent);
            startActivity(intent);
        }
    }

    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String searchContent=et_search.getText().toString().trim();
            if(!searchContent.equals("")||searchContent!=null){
                iv_cancel.setVisibility(View.VISIBLE);
            }else{
                iv_cancel.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onStop() {
        et_search.setText("");
        iv_cancel.setVisibility(View.GONE);
        super.onStop();
    }
}
