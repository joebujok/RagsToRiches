package com.bujok.ragstoriches.shop;



import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
import com.bujok.ragstoriches.ai.IBasicAI;
import com.bujok.ragstoriches.ai.Scene2DAIController;


/**
 * Created by tojoh on 30/01/2016.
 */
public class StockContainer extends Image implements IBasicAI
{
    private final Scene2DAIController aiController;
    protected String mStockType;
    protected int mStockQuantity;
    protected String mID;
    protected Rectangle mImage;
    protected Vector3 mCurrentPosition;
    protected boolean mInfoShowing = false;
    protected Table infoBoxTable;

    // unique mID

    //Texture texture = new Texture(Gdx.files.internal("shopper.png"));


    public StockContainer(String type, int qty, Texture texture)
    {
        super(texture);

        this.aiController = new Scene2DAIController(this, true);
        
        this.mStockType = type;
        this.mStockQuantity = qty;
        this.setTouchable(Touchable.enabled);
        this.setBounds(getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void act(float delta)
    {
        this.aiController.update(delta);
        super.act(delta);
    }

   @Override
    public void draw(Batch batch, float parentAlpha)
   {
       TextureRegionDrawable textureRegionDrawable = (TextureRegionDrawable) getDrawable();
       textureRegionDrawable.draw(batch,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation() );
       if(infoBoxTable != null)
       {
           infoBoxTable.setPosition(((this.getWidth() / 2 )+ this.getX()) - (infoBoxTable.getWidth() / 2), this.getY() - 10  + infoBoxTable.getHeight());
       }
        super.draw(batch, parentAlpha);
    }

    public String getId() {
        return mID;
    }

    public void setId(String id) {
        this.mID = id;
    }

    public Vector3 getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(Vector3 currentPosition)
    {
        this.mCurrentPosition = currentPosition;
    }

    public void toggleInfoBox(Stage stage, Skin skin){
        if(infoBoxTable != null){
            infoBoxTable.remove();
            infoBoxTable = null;
        }else{
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

    }
    public String getStockType() {
        return mStockType;
    }

    public Integer getStockQuantity() {
        return mStockQuantity;
    }

    @Override
    public void goTo(IBasicAI target)
    {
        // do nothing.
    }

    @Override
    public Scene2DAIController getController()
    {
        return this.aiController;
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        return this.aiController.getLinearVelocity();
    }
}
