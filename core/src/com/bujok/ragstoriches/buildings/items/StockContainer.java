package com.bujok.ragstoriches.buildings.items;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bujok.ragstoriches.messages.MessageType;


/**
 * Created by tojoh on 30/01/2016.
 */
public class StockContainer extends Image implements Telegraph {

    private final static String TAG = "StockContainer";
    protected String mStockType;
    protected int mStockQuantity;
    protected int stockID;

    protected Rectangle mImage;
    protected Vector3 mCurrentPosition;
    protected boolean mInfoShowing = false;
    protected Table infoBoxTable;
    private boolean redrawInfoBox = false;
    private Stage stage ;
    //Todo
    // fix hack already loaded in shopscreen....
    private Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));


    //Texture texture = new Texture(Gdx.files.internal("shopper.png"));


    public StockContainer(int StockID, String type, int qty, Texture texture)
    {
        super(texture);

        this.stockID = StockID;
        this.mStockType = type;
        this.mStockQuantity = qty;
        this.setTouchable(Touchable.enabled);
        this.setBounds(getX(),getY(),getWidth(),getHeight());
        //subscribe to stock changes
        MessageManager.getInstance().addListener(this, MessageType.StockLevelUpdate);

    }

   @Override
    public void draw(Batch batch, float parentAlpha)
   {
       TextureRegionDrawable textureRegionDrawable = (TextureRegionDrawable) getDrawable();
       textureRegionDrawable.draw(batch,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation() );
       if(redrawInfoBox == true && this.stage != null){
           infoBoxTable.remove();
           createInfoBox(stage, skin);
           redrawInfoBox = false;
       }
       if(infoBoxTable != null)
       {
           infoBoxTable.setPosition(((this.getWidth() / 2 )+ this.getX()) - (infoBoxTable.getWidth() / 2), this.getY() - 10  + infoBoxTable.getHeight());
       }
        super.draw(batch, parentAlpha);
    }

    public Vector3 getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(Vector3 currentPosition)
    {
        this.mCurrentPosition = currentPosition;
    }

    public int getStockQuantity() {
        return mStockQuantity;
    }

    public void setStockQuantity(int StockQuantity) {
        this.mStockQuantity = StockQuantity;
    }

    public void toggleInfoBox(Stage stage, Skin skin){
        if(infoBoxTable != null){
            infoBoxTable.remove();
            infoBoxTable = null;
        }else{
            this.stage = stage;
            createInfoBox( stage, skin);
        }

    }
    public String getStockType() {
        return mStockType;
    }

    public void createInfoBox(Stage stage, Skin skin){
        infoBoxTable = new Table();
        //set background color of table
        Pixmap pm1 = new Pixmap(1, 1, Pixmap.Format.RGB565);
        pm1.setColor(new Color(0x0190C3D4));
        pm1.fill();
        infoBoxTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));

        Label typeLabel = new Label("Type : " + this.mStockType,skin);
        typeLabel.setAlignment(Align.left);
        final Label quantityLabel = new Label("Quantity : " + this.mStockQuantity,skin);
        quantityLabel.setAlignment(Align.left);

        TextButton closeTextButton = new TextButton("Close",skin,"default");
        closeTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                infoBoxTable.remove();
            }
        });
        closeTextButton.align(Align.right);
        infoBoxTable.add(closeTextButton);
        infoBoxTable.row();
        infoBoxTable.add(typeLabel);
        infoBoxTable.row();
        infoBoxTable.add(quantityLabel);
        Array<Cell> cells = infoBoxTable.getCells();
        for (Cell c :cells) {
            if(c != cells.get(0)){
                c.pad(0,20,0,20);
                c.align(Align.left);
            }

        }
        infoBoxTable.pack();
        infoBoxTable.setPosition(this.getX() - (infoBoxTable.getWidth() / 2), this.getY()  + infoBoxTable.getHeight());
        stage.addActor(infoBoxTable);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
    }



    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message){
            case MessageType.StockLevelUpdate:
                StockItem stockItem = (StockItem) msg.extraInfo;
                // update the stocklisting shop var
                if(stockItem.getItemID() == this.stockID){
                    if(infoBoxTable != null){
                        redrawInfoBox = true;
                    }
                }
                return true;
            }

        return false;
    }

    public Integer getStockID() {
        return stockID;
    }
}
