package Classes;

import static com.badlogic.gdx.math.MathUtils.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Screens.GameOverScreen;

public class Quiz {
    private final int NUMQUIZ = 21;
    private List<Integer> quizzes;
    public int currentQuiz;
    public Quiz(){
        quizzes = new ArrayList<>();
        for (int i = 1; i <= NUMQUIZ; i++) {
            quizzes.add(i);
        }
        // Shuffle the list to ensure randomness
        Collections.shuffle(quizzes);
    }

    public int returnQuiz(){
        if (quizzes.isEmpty()) {
            throw new IllegalStateException("No more quizzes left");
        }
        int newQuiz;
        do {
            newQuiz = quizzes.remove(random.nextInt(quizzes.size()));
        } while (newQuiz == currentQuiz);
        currentQuiz = newQuiz;
        return newQuiz;
    }

    public String[] getQuizFiles() {
        String imgFile = "cards/" + this.currentQuiz + ".png";
        String txtFile = "ans/" + this.currentQuiz + ".txt";
        return new String[] {imgFile, txtFile};
    }
}
