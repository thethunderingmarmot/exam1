package a02a.e2;

import java.util.Random;

public class LogicImpl implements Logic {

    private enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT,
        INVALID
    }

    private final Pair<Integer, Integer> size;
    
    private int[][] cells;
    private Pair<Integer, Integer> position;
    private Direction direction;
    private int number;

    public LogicImpl(final Pair<Integer, Integer> size) {
        this.size = new Pair<Integer,Integer>(size.getX(), size.getY());
        this.cells = new int[this.size.getX()][this.size.getY()];
    }

    private Pair<Integer, Integer> randomPosition() {
        Random random = new Random();
        return new Pair<>(random.nextInt(this.size.getX()),
                          random.nextInt(this.size.getY()));
    }

    private void resetCells()
    {
        for(int i = 0; i < this.cells.length; i++) {
            for(int j = 0; j < this.cells[i].length; j++) {
                this.cells[i][j] = -1;
            }
        }
    }

    @Override
    public void begin() {
        resetCells();

        this.position = randomPosition();
        this.direction = Direction.UP;
        this.number = 0;

        this.cells[this.position.getX()][this.position.getY()] = this.number;
    }

    @Override
    public int getNumber() {
        return this.cells[position.getX()][position.getY()];
    }

    @Override
    public Pair<Integer, Integer> getPosition() {
        return this.position;
    }

    private Pair<Integer, Integer> getOffset(final Direction newDirection) {
        int offsetX = newDirection == Direction.RIGHT ? 1
                    : newDirection == Direction.LEFT ? -1
                    : 0;
        
        int offsetY = newDirection == Direction.UP ? -1
                    : newDirection == Direction.DOWN ? 1
                    : 0;

        return new Pair<Integer,Integer>(offsetX, offsetY);
    }

    private Pair<Integer, Integer> getNewPosition(final Direction newDirection) {
        return new Pair<>(this.position.getX() + getOffset(newDirection).getX(),
                          this.position.getY() + getOffset(newDirection).getY());
    }

    private boolean checkBounds(final Direction newDirection) {
        return newDirection == Direction.UP && getNewPosition(newDirection).getY() >= 0
            || newDirection == Direction.RIGHT && getNewPosition(newDirection).getX() < this.size.getX()
            || newDirection == Direction.DOWN && getNewPosition(newDirection).getY() < this.size.getY()
            || newDirection == Direction.LEFT && getNewPosition(newDirection).getX() >= 0;
    }

    private boolean checkEmpty(final Direction newDirection) {
        return this.cells[getNewPosition(newDirection).getX()][getNewPosition(newDirection).getY()] == -1;
    }

    private boolean checkBoundsAndEmpty(final Direction newDirection) {
        return checkBounds(newDirection) && checkEmpty(newDirection);
    }

    private Direction changeDirection() {
        return checkBoundsAndEmpty(Direction.UP) ? Direction.UP
             : checkBoundsAndEmpty(Direction.RIGHT) ? Direction.RIGHT
             : checkBoundsAndEmpty(Direction.DOWN) ? Direction.DOWN
             : checkBoundsAndEmpty(Direction.LEFT) ? Direction.LEFT
             : Direction.INVALID;
    }

    @Override
    public boolean nextStep() {
        Direction newDirection = checkBoundsAndEmpty(this.direction) ? this.direction : changeDirection();

        if(newDirection == Direction.INVALID) {
            return false;
        }

        this.number++;
        this.direction = newDirection;
        this.position = getNewPosition(this.direction);
        this.cells[this.position.getX()][this.position.getY()] = this.number;

        return true;
    }

}
