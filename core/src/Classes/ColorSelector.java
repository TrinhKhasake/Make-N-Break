package Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ColorSelector extends ImageButton {

    public ColorSelector(Color color, String textureFilePath) {
        super(createButtonStyle(textureFilePath));
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GlobalState.selectedColor = color;
//                System.out.println(textureFilePath);
            }
        });
    }

    private static ImageButtonStyle createButtonStyle(String textureFilePath) {
        //Texture to draw
        Texture texture =  new Texture(Gdx.files.internal(textureFilePath));
        TextureRegion textureRegion = new TextureRegion(texture);
        TextureRegionDrawable drawable = new TextureRegionDrawable(textureRegion);

        ImageButtonStyle buttonStyle = new ImageButtonStyle();
        buttonStyle.up = drawable;
        return buttonStyle;
    }
}
