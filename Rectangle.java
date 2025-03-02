import java.awt.*;

class Rectangle extends Shape {
    public Rectangle(int x1, int y1, int x2, int y2, Color color, boolean fill, boolean dotted) {
        super(x1, y1, x2, y2, color, fill, dotted);
    }

    @Override
    public void draw(Graphics g) {
        Stroke originalStroke = null;
        if (drawDotted(g, color)) {
            originalStroke = ((Graphics2D) g).getStroke();
        } else {
            g.setColor(color);
        }
        
		int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);
       
        
        if (fill) {
            g.fillRect(x, y, width, height);
        } else {
            g.drawRect(x, y, width, height);
        }
        
        restoreStroke(g, originalStroke);
    }
}