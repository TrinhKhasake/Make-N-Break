package Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Grid extends Table {
    private final CustomButton[][] buttons;
    public final int COLNUM = 6;
    public final int ROWNUM = 7;

    public Grid() {
        super();
        buttons = new CustomButton[ROWNUM][COLNUM]; // Initialize the array

        // Create a 6x6 grid of buttons
        for (int i = 0; i < ROWNUM; i++) {
            for (int j = 0; j < COLNUM; j++) {
                buttons[i][j] = new CustomButton(this, i * ROWNUM + j);
                add(buttons[i][j]).width(50).height(50).pad(5);
            }
            row();
        }
    }

    public CustomButton getButton(int row, int col) {
        if (row >= 0 && row < buttons.length && col >= 0 && col < buttons[row].length) {
            return buttons[row][col];
        } else {
            throw new IllegalArgumentException("Invalid row or column");
        }
    }

    public boolean checkMatrix(String filepath) throws FileNotFoundException {
        ArrayList<String> currentGrid = new ArrayList<>();
        ArrayList<String> answer;
        try {
            answer = Text2Array(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false or handle the exception as needed
        }
        for (CustomButton[] row : buttons) {
            for (CustomButton button : row) {
                currentGrid.add(button.getSelectedColor().toString());
            }
        }
        return currentGrid.equals(answer);
    }

    public ArrayList<String> Text2Array(String filepath) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        FileHandle file = Gdx.files.internal(filepath);
        BufferedReader reader = new BufferedReader(file.reader());

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // Print lines
//        for (String textLine : lines) {
//            System.out.println(textLine);
//        }
        return lines;
    }

}
