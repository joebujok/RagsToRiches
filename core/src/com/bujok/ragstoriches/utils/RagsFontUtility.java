package com.bujok.ragstoriches.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Tojoh on 5/17/2016.
 */
public class RagsFontUtility
{
    public static BitmapFont HEADER_FONT;

    public static BitmapFont NORMAL_FONT;

    static
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("kenney.nl assets/fonts/kenvector_future.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 36;
        RagsFontUtility.HEADER_FONT = generator.generateFont(parameter);
        parameter.size = 24;
        RagsFontUtility.NORMAL_FONT = generator.generateFont(parameter);

        generator.dispose();
    }




}
