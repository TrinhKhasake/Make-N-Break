package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.makeandbreak.game.MakeAndBreak;

import java.io.FileNotFoundException;


public class BufferScreen implements Screen  {
    private final MakeAndBreak game;
    private final Viewport gameport;
    private final Stage stage;
    public BufferScreen(MakeAndBreak game){
        this.game = game;
        OrthographicCamera gamecam = new OrthographicCamera();
        gameport = new FitViewport(game.WIDTH, game.HEIGHT, gamecam);
        gamecam.setToOrtho(false, game.WIDTH, game.HEIGHT);
        gamecam.translate((float) -game.WIDTH /2, (float) -game.HEIGHT /2);
        this.stage = new Stage(gameport, game.batch);
    }
    @Override
    public void show() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ready.atlas"));
        Skin skin = new Skin(Gdx.files.internal("ready.json"),atlas);
        Gdx.input.setInputProcessor(stage);


        new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle letsbuildStyle;
        letsbuildStyle = skin.get("letsbuild", ImageButton.ImageButtonStyle.class);

        new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle readyStyle;
        readyStyle = skin.get("ready", ImageButton.ImageButtonStyle.class);

        ImageButton readyButton = new ImageButton(readyStyle);
        ImageButton letsbuildButton = new ImageButton(letsbuildStyle);
        letsbuildButton.setSize(300,102);
        readyButton.setSize(99*3,65*3);
        readyButton.setPosition((float) game.WIDTH / 2 - readyButton.getWidth() / 2, (float) game.HEIGHT / 2 - readyButton.getHeight() / 2 + letsbuildButton.getHeight()/2 + 10);
        letsbuildButton.setPosition((float) game.WIDTH / 2 - letsbuildButton.getWidth() / 2, (float) game.HEIGHT / 2 - letsbuildButton.getHeight() / 2);
        letsbuildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change the screen to ClassicScreen when the button is clicked
                try {
                    game.setScreen(new ClassicScreen(game));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        stage.addActor(readyButton);
        stage.addActor(letsbuildButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0/255f, 0/255f, 128/255f, 100/255f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.setViewport(gameport);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
