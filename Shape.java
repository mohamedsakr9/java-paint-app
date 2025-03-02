import java.awt.*;

abstract class Shape {
    protected int x1, y1, x2, y2;
    protected Color color;
    protected boolean fill;
    protected boolean dotted;

    public Shape(int x1, int y1, int x2, int y2, Color color, boolean fill, boolean dotted) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.fill = fill;
        this.dotted = dotted;
    }

    protected boolean drawDotted(Graphics g, Color color) {
    Graphics2D g2d = (Graphics2D) g;
    if (dotted) {
        float[] dash = {4.0f, 4.0f};
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 4.0f, dash, 0.0f));
    } else {
        g2d.setStroke(new BasicStroke(1.0f));
    }
    g2d.setColor(color);
    return dotted;/////////
}


    
    protected void restoreStroke(Graphics g, Stroke originalStroke) {
        if (dotted) {
            ((Graphics2D) g).setStroke(originalStroke);
        }
    }

    public abstract void draw(Graphics g);
}