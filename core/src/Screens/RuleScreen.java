package Screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.makeandbreak.game.MakeAndBreak;

public class RuleScreen extends ScreenAdapter {
    private final MakeAndBreak game;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Texture backgroundTexture;
    private Stage stage;
    private Music music;
    private Sound clicksound;

    public RuleScreen(MakeAndBreak game) {
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

        game.batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("ruleFont1.fnt"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Load the background texture
        backgroundTexture = new Texture("endscreen_img.png");  // Replace with your background image

        // Create the stage for UI elements
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create a return button
        TextButton returnButton = createButton();

        // Add a click listener to the return button
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                clicksound.play();
                music.stop();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Add the return button to the stage
        stage.addActor(returnButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        // Draw the background
        game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw the rules text
        font.draw(game.batch, "Make 'n' Break Rule:", 10, Gdx.graphics.getHeight() - 50);
        font.draw(game.batch, "Objective:\n" +
                "The goal of Make 'n' Break is to earn points by successfully recreating various \nstructures within a specified time limit.\n" +
                "\n" +
                "Gameplay:\n" +
                "In this implementation, each player would take turn to play the game \nand receive a score\n" +
                "In each game, a card from the stack reveals to the player a structure that \nneeds to be replicated.\n" +
                "The player attempt to build the structure shown on the card \nusing building blocks on the side.\n" +
                "If the built structure is correct, the next card will be drawn\n" +
                "The round ends when the timer runs out.\n" +
                "\n" +
                "Scoring:\n" +
                "Players score 10 points if successfully replicates the structure\n" +
                "No points are awarded for structures that are significantly different \nfrom the target.\n" +
                "You must replicated exactly the structure shown to get a new card" +
                "\n" +
                "\nWinning:\n" +
                "The game typically consists of several rounds.\n" +
                "The player or team with the highest total score at the end of three rounds wins.", 30, Gdx.graphics.getHeight() - 75);

        game.batch.end();

        // Draw the stage (UI elements)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        font.dispose();
        backgroundTexture.dispose();  // Dispose of the background texture
        stage.dispose();
    }

    private TextButton createButton() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        TextButton button = new TextButton("Return to Menu", style);
        button.setPosition((float) 0, (float) 600);
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("blueBackground.jpg"))));
        button.setWidth(200);
        button.setHeight(40);

        style.fontColor = null;

        return button;
    }
}