

package com.unicornheight.bakingapp.mvp.view;


import com.unicornheight.bakingapp.mvp.model.Cake;

import java.util.List;


public interface MainView extends BaseView {

    void onCakeLoaded(List<Cake> cakes);

    void onShowDialog(String message);

    void onHideDialog();

    void onShowToast(String message);

    void onClearItems();
}
