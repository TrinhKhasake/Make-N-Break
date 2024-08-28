package Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.makeandbreak.game.MakeAndBreak;

import java.io.FileNotFoundException;

import Classes.ColorSelector;
import Classes.CustomButton;
import Classes.Grid;
import Classes.Quiz;

public class ClassicScreen extends ApplicationAdapter implements Screen, InputProcessor {
    final MakeAndBreak game;
    private final OrthographicCamera gamecam;
    private final Viewport gameport;
    private final Stage stage;
    private Texture img;
    private final Quiz quiz = new Quiz();
    private boolean change = true;

    private final BitmapFont font;
    private float timeCount; // Timer variable
    private static Integer score;
    private int worldTimer; // Initial timer value in seconds
    private final Label countdownLabel;
    private Label timeLabel;
    private Label scoreLabel;
    private Label scoreCountLabel;
    private Music music;
    private Sound cor_sound,fal_sound,vic_sound;
    public ClassicScreen(MakeAndBreak game) throws FileNotFoundException {
        //Gamecam
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(game.WIDTH , game.HEIGHT, gamecam);
        gamecam.setToOrtho(false, game.WIDTH,game.HEIGHT);
        gamecam.translate((float) -game.WIDTH /2, (float) -game.HEIGHT /2);

        //music
        music = Gdx.audio.newMusic(Gdx.files.internal("playscreen_msc.mp3"));
        music.setLooping(true);
        music.setVolume(0.8f);
        music.play();

        //sound
        cor_sound=Gdx.audio.newSound(Gdx.files.internal("correct_sound.mp3"));
        fal_sound=Gdx.audio.newSound(Gdx.files.internal("false_sound.mp3"));
        vic_sound=Gdx.audio.newSound(Gdx.files.internal("victory.mp3"));

        //Init Stage
        stage = new Stage();
        Grid grid = new Grid();
        grid.setPosition(-200,-75);
        stage.addActor(grid);

        //Color Selector
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE,Color.BLACK, Color.GRAY, Color.PURPLE, Color.WHITE};

        for (int i = 0; i < colors.length; i++) {
            String colorName = getColorName(colors[i]);
            ColorSelector colorSelector = new ColorSelector(colors[i], colorName + ".png");
            colorSelector.setPosition(30 + i%4*75, -300 + i/4*140); // Adjust these values as needed
            colorSelector.setSize(50, 120);
            stage.addActor(colorSelector);
        }

        //Quiz
        quiz.returnQuiz();

        //Handle Input
        InputProcessor inputProcessor = new InputAdapter(){
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.E){
                    gameOver();
                }
                if (keycode == Input.Keys.SPACE){
                    try {
                        if(grid.checkMatrix(quiz.getQuizFiles()[1])){
                            System.out.println("Correct");
                            cor_sound.play(0.7f);
                            quiz.currentQuiz = quiz.returnQuiz();
                            change = true;
                            addScore(10);
                        } else {
                            System.out.println("TryAgain");
                            fal_sound.play();
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    for (int i = 0; i < grid.getRows(); i++) {
                        for (int j = 0; j < grid.getColumns(); j++) {
                            CustomButton button = grid.getButton(i, j);
                            button.setColor(Color.YELLOW);
                        }
                    }
                    return true;
                }
                return false;
            }
        };

        InputMultiplexer Multiplexer = new InputMultiplexer();
        Multiplexer.addProcessor(stage);
        Multiplexer.addProcessor(inputProcessor);
        Multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(Multiplexer);

        //Quiz
        game.batch = new SpriteBatch();

        //Timer and score
        //define a table used to organize hud's labels
        Table table = new Table();
        table.setPosition(0,-400);
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);
        // Initialize timer variables
        worldTimer = 90; // Initial time in seconds
        timeCount = 0;
        score = 0;

        //load font
        font=new BitmapFont(Gdx.files.internal("horizon.fnt"));

        //define our labels using the String, and a Label style consisting of a font and color

        timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.GOLD));
        timeLabel.setFontScale(0.7f);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font, Color.GOLD));
        countdownLabel.setFontScale(0.85f);

        scoreLabel = new Label("SCORE", new Label.LabelStyle(font, Color.GOLD));
        scoreLabel.setFontScale(0.7f);

        scoreCountLabel=new Label(String.format("%03d", score), new Label.LabelStyle(font, Color.GOLD));
        scoreCountLabel.setFontScale(0.85f);

        //add our labels to our table, padding the top, and giving them all equal width with
        table.add(timeLabel).expandX().padRight(50);
        table.add(scoreLabel).expandX().padRight(1200);

        table.row();//add a second row to our table

        table.add(countdownLabel).expandX().padRight(50);
        table.add(scoreCountLabel).expandX().padRight(1200);

        //add our table to the stage
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    private String getColorName(Color color) {
        if (Color.RED.equals(color)) {
            return "red";
        } else if (Color.BLUE.equals(color)) {
            return "blue";
        } else if (Color.GREEN.equals(color)) {
            return "green";
        } else if (Color.ORANGE.equals(color)) {
            return "orange";
        } else if (Color.BLACK.equals(color)) {
            return "black";
        } else if (Color.GRAY.equals(color)) {
            return "gray";
        } else if (Color.PURPLE.equals(color)) {
            return "purple";
        } else if (Color.WHITE.equals(color)) {
            return "white";
        } else {
            return "unknown";
        }
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
        gamecam.update();
    }

    @Override
    public void render(float delta) {
        update(delta);

        //main screen color ( Navy blue )
        Gdx.gl.glClearColor(0/255f, 0/255f, 128/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Cards
        game.batch.begin();
        game.batch.draw(img, 420, 350, 456/4*3, 320/4*3);
        game.batch.end();

        //Grid
        stage.setViewport(gameport);
        stage.draw();

    }

    private void update(float delta) {
        timeCount += delta;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                music.stop();
                vic_sound.play();
                gameOver();
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
        if (change) {
            // Dispose of the old texture
            if (img != null) {
                img.dispose();
            }

            // Load the new texture
            String[] quizFiles = quiz.getQuizFiles();
            img = new Texture(quizFiles[0]);
            change = false;
        }
    }

    public void addScore(int value){
        score += value;
        scoreCountLabel.setText(String.format("%03d", score));
    }

    private void gameOver() {
        music.stop();
        game.setScreen(new GameOverScreen(game,score));
    }

    @Override
    public void create() {
        img = new Texture(quiz.getQuizFiles()[0]);
        super.create();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        img.dispose();
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
