package com.example.sae501.View.Caroussel;

import android.view.View;

import org.osmdroid.views.overlay.Polyline;

public class PageClasse {
    private final int image;
    private final String title;
    private final String description;
    private final String buttonText;
    private final View.OnClickListener buttonOnClick;

    /**
     * constructeur permettant de lié le caroussel aux valeurs
     *
     * @param image         image voulu
     * @param title         titre voulu
     * @param description   description voulu
     * @param buttonText    text du bouton voulu
     * @param buttonOnClick comportement du bouton voulu
     * @author Matisse Gallouin
     */
    public PageClasse(int image, String title, String description, String buttonText, View.OnClickListener buttonOnClick) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.buttonText = buttonText;
        this.buttonOnClick = buttonOnClick;
    }

    public int getLogoResId() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getButtonText() {
        return buttonText;
    }

    public View.OnClickListener getClickListener() {
        return buttonOnClick;
    }
}
