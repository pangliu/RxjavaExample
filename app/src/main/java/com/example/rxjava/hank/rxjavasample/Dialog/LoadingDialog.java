package com.example.rxjava.hank.rxjavasample.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.rxjava.hank.rxjavasample.R;

public class LoadingDialog extends Dialog{

    private Context context;
    private Dialog dialog;
    private ProgressBar loading;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
        dialog.setContentView(R.layout.dialog_loading);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        initView();
    }

    private void initView() {
        loading = (ProgressBar) dialog.findViewById(R.id.progress_loading);
        loading.setVisibility(View.VISIBLE);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
