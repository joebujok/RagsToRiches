package com.bujok.ragstoriches.people;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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


/**
 * Created by joebu on 30/01/2016.
 */
public class Person extends Image {


    protected String mName;
    protected Integer mAge;
    protected String mID;
    protected Rectangle mImage;
    protected int mSpeed = 0;
    protected Vector3 mCurrentPosition;
    protected boolean mInfoShowing = false;
    protected Table infoBoxTable;

    // unique mID

    //Texture texture = new Texture(Gdx.files.internal("shopper.png"));


    public Person(String name, Texture texture) {

        super(texture);
        this.mName = name;
        this.mAge = MathUtils.random(15,95);
        this.setTouchable(Touchable.enabled);
        this.setBounds(getX(),getY(),getWidth(),getHeight());
        this.scaleBy(2f);


    }

   @Override
    public void draw(Batch batch, float parentAlpha) {
       // ((TextureRegionDrawable)getDrawable()).draw(batch,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
       //TextureRegion textureRegion = (TextureRegion) getDrawable();
       TextureRegionDrawable textureRegionDrawable = (TextureRegionDrawable) getDrawable();
       textureRegionDrawable.draw(batch,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation() );
     //  batch.draw((TextureRegion) getDrawable(),getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
       if(infoBoxTable != null){
           infoBoxTable.setPosition(((this.getWidth() / 2 )+ this.getX()) - (infoBoxTable.getWidth() / 2), this.getY() + this.getHeight()  + infoBoxTable.getHeight());
       }


      //  batch.draw(texture,this.getX(),getY());
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

    public void setCurrentPosition(Vector3 currentPosition) {
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

            Label nameLabel = new Label("Name : " + mName,skin);
            nameLabel.setAlignment(Align.left);
            final Label moneyLabel = new Label("Money : £123.45" ,skin);
            moneyLabel.setAlignment(Align.left);

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
            infoBoxTable.add(nameLabel);
            infoBoxTable.row();
            infoBoxTable.add(moneyLabel);
            Array<Cell> cells = infoBoxTable.getCells();
            for (Cell c :cells) {
                if(c != cells.get(0)){
                    c.pad(0,20,0,20);
                    c.align(Align.left);
                }

            }

            //table.setBackground("red");

            //infoBoxTable.debug();
            infoBoxTable.pack();
            infoBoxTable.setPosition(((this.getWidth() / 2 )+ this.getX()) - (infoBoxTable.getWidth() / 2), this.getY() + this.getHeight()  + infoBoxTable.getHeight());
            stage.addActor(infoBoxTable);
            Label.LabelStyle labelStyle = new Label.LabelStyle();



        }

    }
    public String getName() {
        return mName;
    }

    public Integer getAge() {
        return mAge;
    }
}
