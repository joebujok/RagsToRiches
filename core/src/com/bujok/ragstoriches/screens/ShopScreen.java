package com.bujok.ragstoriches.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bujok.ragstoriches.RagsGame;
import com.bujok.ragstoriches.buildings.Shop;
import com.bujok.ragstoriches.buildings.items.StockContainer;
import com.bujok.ragstoriches.buildings.items.StockItem;
import com.bujok.ragstoriches.map.GameMap;
import com.bujok.ragstoriches.messages.MessageType;
import com.bujok.ragstoriches.people.Person;
import com.bujok.ragstoriches.screens.components.UISideBar;
import com.bujok.ragstoriches.screens.components.UITopStatusBar;
import com.bujok.ragstoriches.utils.RagsUIUtility;

import java.util.ArrayList;
import java.util.List;

public class ShopScreen extends MapScreen
{
    final String TAG = "ShopScreen";
    OrthographicCamera camera;
    private Shop currentShop = null;


    private TextureAtlas greyPanelAtlas; //** Holds the entire image **//
    private NinePatch panel; //** Will Point to button2 (a NinePatch) **//

    List<Person> people = new ArrayList<Person>();
    private Texture squareButtonUp;
    private Texture squareButtonDown;
    private UISideBar sideBar;

    public ShopScreen(final RagsGame game)
    {
        super(game);
        this.initialiseMap();
        this.initialiseComponents();
    }

    private void initialiseMap()
    {
        this.mapFrame = new GameMap(this.mapLayer, null, new Texture(Gdx.files.internal("shop.png")));
    }

    private void initialiseComponents()
    {
        this.currentShop = new Shop(mapLayer, game,1);
        mapLayer.addActor(currentShop);

        greyPanelAtlas = new TextureAtlas(Gdx.files.internal("kenney.nl/edited/skin/kenney_ui_skin.atlas")); //** buttonsAtlas has both buttons **//
        panel = greyPanelAtlas.createPatch("panel_grey");
        //squareButtonUp = new Texture(Gdx.files.internal("kenney.kenney.nl/edited/uipack_fixed/PNG/blue_button11.png"));
        //squareButtonDown = new Texture(Gdx.files.internal("kenney.kenney.nl/edited/uipack_fixed/PNG/blue_button12.png"));

        final TextButton button = RagsUIUtility.getInstance().createDefaultButton("Buy a Melon", "blue");

        button.setWidth(360f);
        button.setHeight(46f);
        button.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 - 10f);


        uiLayer.addActor(button);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                button.setText("Buy Another Melon");
                // todo: // FIXME: 5/31/2016
            }
        });

        // game.nativeFunctions.HelloWorld();

        final TextButton toggleMapButton = RagsUIUtility.getInstance().createDefaultButton("Toggle Map", "blue");

        toggleMapButton.setWidth(360f);
        toggleMapButton.setHeight(46f);
        toggleMapButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 - 76f);

        toggleMapButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                ShopScreen.this.game.showOutScreen();
            }
        });

        uiLayer.addActor(toggleMapButton);

        final TextButton gotoButton = RagsUIUtility.getInstance().createDefaultButton("Go Shopping", "blue");

        gotoButton.setWidth(360f);
        gotoButton.setHeight(46f);
        gotoButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 - 142f);

        gotoButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Person p = ShopScreen.this.people.get(1);
                if (p != null)
                {
                    //p.moveAlongPath();
                    p.getShopBehaviour().run();
                }

            }
        });

        uiLayer.addActor(gotoButton);

        final TextButton showGridButton = RagsUIUtility.getInstance().createDefaultButton("Toggle Grid", "blue");

        showGridButton.setWidth(360f);
        showGridButton.setHeight(46);
        showGridButton.setPosition(Gdx.graphics.getWidth() /2 - 200f, Gdx.graphics.getHeight()/2 - 208f);

        showGridButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Person p = ShopScreen.this.people.get(0);
                if (p != null)
                {
                    p.setGridVisible(!p.isGridVisible());
                }
            }
        });

        uiLayer.addActor(showGridButton);

        ScaleByAction sba = new ScaleByAction();
        sba.setAmount(1.1f);
        sba.setDuration(0f);
        SequenceAction sa = new SequenceAction(sba);


        Person p = new Person("Leader",new TextureRegion(new Texture(Gdx.files.internal("shopper.png"))), this.currentShop, this.game);
        spriteLayer.addActor(p);
        p.setX(520);
        p.setY(470);
        p.addAction(sa);
        this.people.add(p);

        // loop through and get each subsequent person following the last.
        for (int i = 1; i < 2 ; i++)
        {
            ScaleByAction sba2 = new ScaleByAction();
            sba.setAmount(1.1f);
            sba.setDuration(0f);
            SequenceAction sa2 = new SequenceAction(sba);

            Person lastPerson = p;
            p = new Person("Follower" + i, new TextureRegion(new Texture(Gdx.files.internal("shopper.png"))), this.currentShop, this.game);
            p.setVisible(false);
            spriteLayer.addActor(p);
            p.addAction(sa2);
            this.people.add(p);
        }

        this.sideBar = new UISideBar(this.uiLayer);
    }


    @Override
    public void render(float delta)
    {
        super.render(delta);

    }

    @Override
    protected void renderUI(float delta)
    {
        super.renderUI(delta);
    }

    @Override
    public void dispose() {
        greyPanelAtlas.dispose();
        super.dispose();
    }

}