package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.makeandbreak.game.MakeAndBreak;

public class ControlScreen implements Screen {
    private final MakeAndBreak game;
    private BitmapFont font;

    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Stage stage;
    private Music music;
    private Sound clicksound;
    private final Texture background = new Texture(Gdx.files.internal("control.png"));

    public ControlScreen(MakeAndBreak game) {
        this.game = game;
    }

    @Override
    public void show() {
        //music
        music = Gdx.audio.newMusic(Gdx.files.internal("mainmenu_msc.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();

        //sound
        clicksound=Gdx.audio.newSound(Gdx.files.internal("clicksound.mp3"));

        gamecam = new OrthographicCamera();
        gameport = new FitViewport(game.WIDTH, game.HEIGHT, gamecam);
        gamecam.setToOrtho(false, game.WIDTH, game.HEIGHT);

        font = new BitmapFont(Gdx.files.internal("ruleFont.fnt"));
        stage = new Stage(gameport);
        Gdx.input.setInputProcessor(stage);
        TextButton returnButton = createButton("Return to Menu", 0, 600);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                music.stop();
                clicksound.play();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Add the return button to the stage
        stage.addActor(returnButton);
    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();

        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.end();

        // Draw the stage (UI elements)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        font.dispose();
        background.dispose();  // Dispose of the background texture
        stage.dispose();
    }

    private TextButton createButton(String text, float x, float y) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        TextButton button = new TextButton(text, style);
        button.setPosition(x, y);
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("blueBackground.jpg"))));
        button.setWidth(200);
        button.setHeight(40);

        style.fontColor = null;

        return button;
    }
}
