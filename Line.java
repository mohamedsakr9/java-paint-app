import java.awt.*;

class Line extends Shape {
    public Line(int x1, int y1, int x2, int y2, Color color, boolean dotted) {
        super(x1, y1, x2, y2, color, false,dotted);
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
}