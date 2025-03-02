import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Main extends Applet implements ActionListener, MouseListener, MouseMotionListener, ItemListener {
    private List<Shape> shapes = new ArrayList<>(); 
    private Stack<List<Shape>> history = new Stack<>();
    private Color currentColor = Color.BLACK;
    private String currentShapeType = "Line";
    private boolean isDrawing = false;
    private boolean isErasing = false;
    private boolean isClear = false;
    private boolean fill = false;
    private boolean isFreeHand = false;
    private boolean isDotted = false;
    private int currentX1, currentY1;
    private int currentX2, currentY2;
    private Checkbox fillCheckbox;
    private Checkbox dottedCheckbox;
    private Image offscreenBuffer;
    private Graphics offscreenGraphics;
    private Shape currentShape;

    public void init() {
        Button blackBtn = new Button("BLACK");
        Button redBtn = new Button("RED");
        Button greenBtn = new Button("GREEN");
        Button blueBtn = new Button("BLUE");
        Button lineBtn = new Button("Line");
        Button rectBtn = new Button("Rectangle");
        Button ovalBtn = new Button("Oval");
        Button freeHandBtn = new Button("Pen");
        Button eraseBtn = new Button("Eraser");
        Button clearBtn = new Button("Clear");
        Button undoBtn = new Button("Undo");
		Button newBtn = new Button("New");

        fillCheckbox = new Checkbox("Fill");
        dottedCheckbox = new Checkbox("Dotted");

        add(blackBtn);
        add(redBtn);
        add(greenBtn);
        add(blueBtn);
        add(lineBtn);
        add(rectBtn);
        add(ovalBtn);
        add(freeHandBtn);
        add(fillCheckbox);
        add(dottedCheckbox);
        add(eraseBtn);
        add(clearBtn);
        add(undoBtn);
		add(newBtn);

        redBtn.addActionListener(this);
        greenBtn.addActionListener(this);
        blueBtn.addActionListener(this);
        blackBtn.addActionListener(this);
        lineBtn.addActionListener(this);
        rectBtn.addActionListener(this);
        ovalBtn.addActionListener(this);
        freeHandBtn.addActionListener(this);
        eraseBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        undoBtn.addActionListener(this); 
		newBtn.addActionListener(this);
        fillCheckbox.addItemListener(this);
        dottedCheckbox.addItemListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        setBackground(Color.WHITE);
    }

    public void update(Graphics g) {
        if (offscreenBuffer == null || 
            offscreenBuffer.getWidth(this) != getWidth() || 
            offscreenBuffer.getHeight(this) != getHeight()) {
            offscreenBuffer = createImage(getWidth(), getHeight());
            offscreenGraphics = offscreenBuffer.getGraphics();
        }

        offscreenGraphics.setColor(getBackground());
        offscreenGraphics.fillRect(0, 0, getWidth(), getHeight());
        paint(offscreenGraphics);
        g.drawImage(offscreenBuffer, 0, 0, this);
    }

    public void paint(Graphics g) {
        if (isClear) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            isClear = false;
        }

        for (Shape shape : shapes) {
            shape.draw(g);
        }

        if (isDrawing) {
            if (isErasing) {
                Eraser.drawPreview(g, currentX2, currentY2);
            } else if (currentShape != null) {
                currentShape.draw(g);
            }
        } else if (isErasing) {
            Eraser.drawPreview(g, currentX2, currentY2);
        }
    }

    private Shape createShape() {
        switch (currentShapeType) {
            case "Line":
                return new Line(currentX1, currentY1, currentX2, currentY2, currentColor, isDotted);
            case "Rectangle":
                return new Rectangle(currentX1, currentY1, currentX2, currentY2, currentColor, fill, isDotted);
            case "Oval":
                return new Oval(currentX1, currentY1, currentX2, currentY2, currentColor, fill, isDotted);
            case "Pen":
                return new FreeHand(currentX2, currentY2, currentColor, isDotted);
            case "Eraser":
                return new Eraser(currentX1, currentY1, getBackground());
            default:
                return null;
        }
    }

    private void saveState() {
        history.push(new ArrayList<>(shapes)); // Save a copy of the current shapes
    }

    public void itemStateChanged(ItemEvent ie) {
        if (ie.getSource() == fillCheckbox) {
            fill = fillCheckbox.getState();
        } 
        if (ie.getSource() == dottedCheckbox) {
            isDotted = dottedCheckbox.getState();
        }
        if (isDrawing) {
            currentShape = createShape();
            repaint();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        switch (action) {
            case "RED":
                currentColor = Color.RED;
                isErasing = false;
                isFreeHand = false;
                break;
            case "GREEN":
                currentColor = Color.GREEN;
                isErasing = false;
                isFreeHand = false;
                break;
            case "BLUE":
                currentColor = Color.BLUE;
                isErasing = false;
                isFreeHand = false;
                break;
            case "BLACK":
                currentColor = Color.BLACK;
                isErasing = false;
                isFreeHand = false;
                break;
            case "Line":
                currentShapeType = "Line";
                isErasing = false;
                isFreeHand = false;
                break;
            case "Rectangle":
                currentShapeType = "Rectangle";
                isErasing = false;
                isFreeHand = false;
                break;
            case "Oval":
                currentShapeType = "Oval";
                isErasing = false;
                isFreeHand = false;
                break;
            case "Pen":
                currentShapeType = "Pen";
                isErasing = false;
                isFreeHand = true;
                break;
            case "Eraser":
                isErasing = true;
                isFreeHand = false;
                break;
            case "Clear":
                // Show a confirmation dialog before clearing
                int response = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to clear the canvas?", 
                    "Confirm Clear", 
                    JOptionPane.YES_NO_OPTION
                );
                if (response == JOptionPane.YES_OPTION) {
                    saveState(); // Save the current state before clearing
                    isClear = true;
                    shapes.clear(); // Clear the canvas
                    repaint();
                }
                break;
            case "Undo":
                undo();
                break;
			case "New":
                int response2 = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to switch to new canvas?", 
                    "New Canvas", 
                    JOptionPane.YES_NO_OPTION
                );
                if (response2 == JOptionPane.YES_OPTION) {
                    
                    isClear = true;
                    shapes.clear();
					history.clear();
                    repaint();
                }
                break;	
        }

        if (isDrawing) {
            currentShape = createShape();
        }
        repaint();
    }

    private void undo() {
        if (!history.isEmpty()) {
            shapes = history.pop(); // Restore the previous state
            repaint();
        } 
    }

    public void mousePressed(MouseEvent me) {
        saveState(); // Save the current state before making changes
        currentX1 = me.getX();
        currentY1 = me.getY();
        currentX2 = currentX1;
        currentY2 = currentY1;
        isDrawing = true;

        if (isErasing) {
            shapes.add(new Eraser(currentX1, currentY1, getBackground()));
        } else if (isFreeHand) {
            FreeHand newSegment = new FreeHand(currentX1, currentY1, currentColor, isDotted);
            newSegment.x2 = currentX2;
            newSegment.y2 = currentY2;
            shapes.add(newSegment);
        } else {
            currentShape = createShape();
        }
        repaint();
    }

    public void mouseReleased(MouseEvent me) {
        if (!isErasing && !isFreeHand && currentShape != null) {
            shapes.add(currentShape);
            currentShape = null;
        }
        isDrawing = false;
        repaint();
    }

    public void mouseDragged(MouseEvent e) {
        currentX2 = e.getX();
        currentY2 = e.getY();

        if (isErasing) {
            shapes.add(new Eraser(currentX2, currentY2, getBackground()));
        } else if (isFreeHand) {
            FreeHand newSegment = new FreeHand(currentX1, currentY1, currentColor, isDotted);
            newSegment.x2 = currentX2;
            newSegment.y2 = currentY2;
            shapes.add(newSegment);
            currentX1 = currentX2;
            currentY1 = currentY2;
        } else {
            currentShape = createShape();
        }
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        currentX2 = e.getX();
        currentY2 = e.getY();
        if (isErasing) {
            repaint();
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}