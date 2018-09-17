package com.yaratech.yaratube.ui;

import android.widget.ImageView;

import com.yaratech.yaratube.data.model.Product;

public interface OnProductActionListener {
    void goFromProductToProdutDetails(Product product, ImageView imageView);

}
