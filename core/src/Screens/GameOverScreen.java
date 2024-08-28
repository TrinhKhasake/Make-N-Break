package Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.makeandbreak.game.MakeAndBreak;

public class GameOverScreen extends ScreenAdapter {
    private final MakeAndBreak game;
    private Stage stage;
    private Texture img = new Texture(Gdx.files.internal("endscreen_img.png"));
    private BitmapFont font,font1;
    private int score;
    private Label scoreCountLabel;
    private Music music;
    private Sound clicksound;

    public GameOverScreen(MakeAndBreak game, int score) {
        this.game = game;
        this.score= score;
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

        game.batch = new SpriteBatch();

        font=new BitmapFont(Gdx.files.internal("horizon.fnt"));
        font1=new BitmapFont();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create title label
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.GOLD);

        Label titleLabel = new Label("Game Over", labelStyle);
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2 - titleLabel.getWidth() / 2 , Gdx.graphics.getHeight() / 2 + 100);

        Label scoreLabel = new Label("Score",labelStyle);
        scoreLabel.setPosition(Gdx.graphics.getWidth() / 2 - titleLabel.getWidth() / 2 + 100 , Gdx.graphics.getHeight() / 2 );

        scoreCountLabel=new Label(String.format("%03d", score), new Label.LabelStyle(font, Color.GREEN));
        scoreCountLabel.setPosition(Gdx.graphics.getWidth() / 2 - titleLabel.getWidth() / 2 + 150  , Gdx.graphics.getHeight() / 2 - 75);

        stage.addActor(titleLabel);
        stage.addActor(scoreLabel);
        stage.addActor(scoreCountLabel);
        // Create buttons
        TextButton replayButton = createButton("Replay", 300, Gdx.graphics.getHeight() - 500);

        // Add click listeners to the buttons
        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clicksound.play();
                music.stop();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Add buttons to the stage
        stage.addActor(replayButton);
    }

    private TextButton createButton(String text, float x, float y) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font1;

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
        game.batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        font.dispose();
        stage.dispose();
    }
}