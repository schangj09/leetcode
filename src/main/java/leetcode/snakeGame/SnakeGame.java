package leetcode.snakeGame;

import java.util.Map;

/*
https://leetcode.com/problems/design-snake-game/description/
Medium

Design a Snake game that is played on a device with screen size height x width. Play the game online if you are not familiar with the game.

The snake is initially positioned at the top left corner (0, 0) with a length of 1 unit.

You are given an array food where food[i] = (ri, ci) is the row and column position of a piece of food that the snake can eat. When a snake eats a piece of food, its length and the game's score both increase by 1.

Each piece of food appears one by one on the screen, meaning the second piece of food will not appear until the snake eats the first piece of food.

When a piece of food appears on the screen, it is guaranteed that it will not appear on a block occupied by the snake.

The game is over if the snake goes out of bounds (hits a wall) or if its head occupies a space that its body occupies after moving (i.e. a snake of length 4 cannot run into itself).

Implement the SnakeGame class:

    SnakeGame(int width, int height, int[][] food) Initializes the object with a screen of size height x width and the positions of the food.
    int move(String direction) Returns the score of the game after applying one direction move by the snake. If the game is over, return -1.

 */
public class SnakeGame {
    int n; // number of food items, which is also the max body length
    int[][] food;
    int nextFood; // index of next food, which also serves the body length and score (-1 indicates game over)
    int[][] visited; // circular queue of visited cells
    int headIndex; // index of last visited cell
    int w; // size of the board w, h (cols, rows)
    int h;
    Map<String, int[]> dir = Map.of(
        "U", new int[] { -1, 0 },
        "L", new int[] { 0, -1 },
        "R", new int[] { 0, 1 },
        "D", new int[] { 1, 0 }
    );

    public SnakeGame(int width, int height, int[][] food) {
        this.food = food;
        n = food.length;
        visited = new int[n][2];
        headIndex = 0;
        nextFood = 0;
        w = width;
        h = height;
    }
    
    public int move(String direction) {
        int[] d = dir.get(direction);
        int nx = visited[headIndex][1] + d[1];
        int ny = visited[headIndex][0] + d[0];
        // check out of bounds (or game already over)
        if (nextFood == -1 || nx < 0 || nx == w || ny < 0 || ny == h) {
            nextFood = -1;
            return -1;
        }
        // if food, then guaranteed not to hit the body
        if (nextFood < food.length && nx == food[nextFood][1] && ny == food[nextFood][0]) {
            nextFood++;
        } else {
            // otherwise check for body hit
            for (int i = 0; i < bodyLength() - 1; i++) {
                int[] body = visited[(headIndex + n - i)%n];
                if (nx == body[1] && ny == body[0]) {
                    nextFood = -1;
                    return -1;
                }
            }
        }
        // move the head forward
        headIndex = (headIndex + 1)%n;
        visited[headIndex][1] = nx;
        visited[headIndex][0] = ny;

        // score is same as next food index
        return nextFood;
    }

    int bodyLength() {
        return nextFood + 1;
    }
}
