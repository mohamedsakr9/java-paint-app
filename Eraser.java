import java.awt.*;

class Eraser extends Shape {
    private static final int ERASER_SIZE = 10;

    public Eraser(int x, int y, Color color) {
        super(x, y, x + ERASER_SIZE, y + ERASER_SIZE, color, true, false);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x1 - ERASER_SIZE/2, y1 - ERASER_SIZE/2, ERASER_SIZE, ERASER_SIZE);
		
    }

    public static void drawPreview(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.drawRect(x - ERASER_SIZE/2, y - ERASER_SIZE/2, ERASER_SIZE, ERASER_SIZE);
    }
}