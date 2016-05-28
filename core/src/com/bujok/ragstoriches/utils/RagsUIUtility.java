package com.bujok.ragstoriches.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Tojoh on 5/17/2016.
 */
public class RagsUIUtility
{
    public BitmapFont headerFont;
    public BitmapFont normalFont;
    public Skin defaultSkin = new Skin();

    private TextureAtlas buttonAtlas = null;

    public static RagsUIUtility instance = new RagsUIUtility();

    private RagsUIUtility()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("kenney.nl/fonts/kenvector_future.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 36;
        this.headerFont = generator.generateFont(parameter);
        parameter.size = 24;
        this.normalFont = generator.generateFont(parameter);

        this.defaultSkin.add("header-font", this.headerFont);
        this.defaultSkin.add("default-font", this.normalFont);

        this.buttonAtlas = new TextureAtlas(Gdx.files.internal("kenney.nl/edited/skin/kenney_ui_skin.atlas"));
        this.defaultSkin.addRegions(this.buttonAtlas);

        generator.dispose();
    }

    public TextButton createDefaultButton(String text, String colour)
    {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = this.defaultSkin.getFont("default-font");
        textButtonStyle.up = this.defaultSkin.getDrawable("button_cartoon_up_" + colour);
        textButtonStyle.down = this.defaultSkin.getDrawable("button_cartoon_down_" + colour);

        return new TextButton(text, textButtonStyle);
    }

    public TextButton createToggleButton(String text, String colour)
    {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = this.defaultSkin.getFont("default-font");
        textButtonStyle.up = this.defaultSkin.getDrawable("button_cartoon_up_" + colour);
        textButtonStyle.down = this.defaultSkin.getDrawable("button_cartoon_down_yellow");
        textButtonStyle.checked = this.defaultSkin.getDrawable("button_cartoon_down_yellow");

        return new TextButton(text, textButtonStyle);
    }

    public Label createDefaultLabel(String text)
    {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = this.defaultSkin.getFont("default-font");

        return new Label(text, labelStyle);
    }

    public Drawable getDrawable(String id)
    {
        return this.defaultSkin.getDrawable(id);
    }

    public Table createPanelTable(float width, float height, String type)
    {
        Table result = new Table();
        result.setWidth(width);
        result.setHeight(height);
        result.setBackground(this.getDrawable(type));
        return result;
    }

    public void dispose()
    {
        this.headerFont.dispose();
        this.normalFont.dispose();
        this.buttonAtlas.dispose();
        this.defaultSkin.dispose();
    }


    public static RagsUIUtility getInstance()
    {
        return RagsUIUtility.instance;
    }
}
