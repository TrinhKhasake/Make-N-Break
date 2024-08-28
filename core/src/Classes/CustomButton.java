package Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CustomButton extends ImageButton {
    private Color selectedColor = Color.YELLOW;
    private static final ImageButtonStyle buttonStyle;
    static {
        //Default Texture
        Texture texture =  new Texture(Gdx.files.internal("yellow.png"));
        TextureRegion textureRegion = new TextureRegion(texture);
        buttonStyle = new ImageButtonStyle();
        buttonStyle.up =  new TextureRegionDrawable(textureRegion);
    }
    public CustomButton(Grid grid, int id){
        super(buttonStyle);
        this.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Color wasColor = getSelectedColor();
                int row = id / grid.ROWNUM;
                int col = id % grid.ROWNUM;
                //horizontally
                if (button == Input.Buttons.RIGHT){
                    if (col > 0 && col < grid.COLNUM - 1){
                        Color rightColor = grid.getButton(row, col + 1).getSelectedColor();
                        Color leftColor = grid.getButton(row, col - 1).getSelectedColor();
                        if ((rightColor == leftColor) && (rightColor == wasColor)){
                            if (rightColor == Color.YELLOW){
                                setColor(GlobalState.selectedColor);
                                grid.getButton(row, col - 1).setColor(GlobalState.selectedColor);
                                grid.getButton(row, col + 1).setColor(GlobalState.selectedColor);
                            } else {
                                Color local = Color.YELLOW;
                                setColor(local);
                                grid.getButton(row, col - 1).setColor(local);
                                grid.getButton(row, col + 1).setColor(local);
                            }
                        } else {
                            System.out.println("Invalid move");
                        }
                    } else {
                        System.out.println("Invalid move (outter)");
                    }

                }else if (button == Input.Buttons.LEFT){
                    //vertically
                    if (row > 0 && row < grid.ROWNUM - 1) {
                        Color topColor = grid.getButton(row - 1, col).getSelectedColor();
                        Color bottomColor = grid.getButton(row + 1, col).getSelectedColor();
                        if ((topColor == bottomColor) && (topColor == wasColor)) {
                            if (topColor == Color.YELLOW){
                                setColor(GlobalState.selectedColor);
                                grid.getButton(row - 1, col).setColor(GlobalState.selectedColor);
                                grid.getButton(row + 1, col).setColor(GlobalState.selectedColor);
                            } else {
                                Color local = Color.YELLOW;
                                setColor(local);
                                grid.getButton(row - 1, col).setColor(local);
                                grid.getButton(row + 1, col).setColor(local);
                            }
                        } else {
                            System.out.println("Invalid move");
                        }
                    } else {
                        System.out.println("Invalid move (outter)");
                    }
                }
//                return super.touchDown(event, x, y, pointer, button);
                return false;

            }


        });
    }

    public void setColor(Color color) {
        ImageButtonStyle style = getButtonStyle(color);
        this.setStyle(style);
    }

    private ImageButtonStyle getButtonStyle(Color color) {
        ImageButtonStyle style = new ImageButtonStyle();

        // Set the image of the button based on the color
        String imagePath;
        if (color == Color.RED) {
            imagePath = "red.png";
        } else if (color == Color.GREEN) {
            imagePath = "green.png";
        } else if (color == Color.BLUE) {
            imagePath = "blue.png";
        } else if (color == Color.ORANGE) {
            imagePath = "orange.png";
        } else if (color == Color.BLACK) {
            imagePath = "black.png";
        } else if (color == Color.PURPLE) {
            imagePath = "purple.png";
        } else if (color == Color.GRAY) {
            imagePath = "gray.png";
        } else if (color == Color.WHITE) {
            imagePath = "white.png";
        } else {
            imagePath = "yellow.png";
        }

        Texture texture = new Texture(Gdx.files.internal(imagePath));
        TextureRegion textureRegion = new TextureRegion(texture);

        style.up = new TextureRegionDrawable(textureRegion);
        this.selectedColor = color;
        return style;
    }

    @Override
    public boolean isPressed() {
        return super.isPressed();
    }

    public boolean isChecked() {
        return super.isChecked();
    }

    public Color getSelectedColor() {
        return this.selectedColor;
    }
}
