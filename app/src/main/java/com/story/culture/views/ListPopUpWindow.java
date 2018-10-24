package com.story.culture.views;

import android.view.KeyEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by lyjio on 2017/11/1.
 *
 * @author lxf
 */

public class ListPopUpWindow extends PopupWindow {

    public ListPopUpWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void setContentView(View contentView) {
        if (contentView != null) {
            super.setContentView(contentView);
            contentView.setFocusable(true);   //
            contentView.setFocusableInTouchMode(true);   //
            contentView.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            dismiss();
                            return true;
                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }


}
