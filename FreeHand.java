import java.awt.*;

class FreeHand extends Shape {
	private static final int ERASER_SIZE = 10;
    public FreeHand(int x, int y, Color color,boolean dotted) {
        super(x, y, x, y, color, false, dotted);
    }

    @Override
	public void draw(Graphics g) {
        Stroke originalStroke = null;
        if (drawDotted(g, color)) {
            originalStroke = ((Graphics2D) g).getStroke();
        } else {
            g.setColor(color);
        }
        
        g.drawLine(x1, y1, x2, y2);
        
        restoreStroke(g, originalStroke);
    }
	public static void drawPreview(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.drawLine(x - ERASER_SIZE/2, y - ERASER_SIZE/2, ERASER_SIZE, ERASER_SIZE);
    }
}