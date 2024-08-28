package Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.makeandbreak.game.MakeAndBreak;

import java.io.FileNotFoundException;

public class MainMenuScreen extends ScreenAdapter {
    private final MakeAndBreak game;
    private Stage stage;
    private BitmapFont font;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Music music;
    private Sound clicksound;
    private Texture img= new Texture(Gdx.files.internal("anhgame.png"));;
    public MainMenuScreen(MakeAndBreak game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(1520 , 1200, gamecam);
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

        font = new BitmapFont();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create buttons
        TextButton playButton = createButton("Play", 300, Gdx.graphics.getHeight() -350);
        TextButton controlButton = createButton("Control", 300, game.HEIGHT - 450);
        TextButton ruleButton = createButton("Rule", 300, Gdx.graphics.getHeight() - 400);

        // Add click listeners to the buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.stop();
                clicksound.play();
                game.setScreen(new BufferScreen((MakeAndBreak) game));
            }
        });
        controlButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.stop();
                clicksound.play();
                game.setScreen(new ControlScreen(game));
            }
        });
        ruleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.stop();
                clicksound.play();
                game.setScreen(new RuleScreen(game));
            }
        });

        // Add buttons to the stage
        stage.addActor(playButton);
        stage.addActor(ruleButton);
        stage.addActor(controlButton);
    }

    private TextButton createButton(String text, float x, float y) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        TextButton button = new TextButton(text, style);
        button.setPosition(x, y);
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("blueBackground.jpg"))));
        button.setWidth(200); // Set your desired width
        button.setHeight(40); // Set your desired height

        style.fontColor = null;

        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        // Draw the background
        game.batch.draw(img, -0, -0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        img.dispose();
        font.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }
}