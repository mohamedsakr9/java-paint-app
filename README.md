# Java Drawing Application 🎨🖌️

## 📌 Overview  
The **Java Drawing Application** is an interactive **graphical tool** that allows users to draw and manipulate shapes such as **lines, rectangles, ovals, freehand sketches, and erasers**. This project is built using **Java Applets** and **AWT** for graphical rendering and user interactions.

## ✨ Features  
✔️ **Draw Lines, Rectangles, Ovals, and Freehand Sketches**  
✔️ **Customize Colors** (Black, Red, Green, Blue)  
✔️ **Dotted Lines & Filled Shapes** support  
✔️ **Eraser Tool** for easy corrections  
✔️ **Undo & Clear** functions for easy editing  
✔️ **Buffered Rendering** for flicker-free drawing  

## 🛠️ Technologies Used  
- **Java Applets** (for rendering and interaction)  
- **AWT (Abstract Window Toolkit)** (GUI components & graphics)  
- **Event Handling** (Mouse, Action, and Item Listeners)  

## 📂 Project Structure  
```
📁 JavaDrawingApp  
│── 📜 Main.java        # Main Applet class handling UI & interactions  
│── 📜 Shape.java       # Abstract base class for all shapes  
│── 📜 Line.java        # Line shape implementation  
│── 📜 Rectangle.java   # Rectangle shape implementation  
│── 📜 Oval.java        # Oval shape implementation  
│── 📜 FreeHand.java    # Freehand drawing tool  
│── 📜 Eraser.java      # Eraser tool implementation  
│── 📜 main.html        # HTML file for running the applet  
```

## 🚀 How to Run  
### **Option 1: Using an Applet Viewer**  
1. Compile the Java files:  
   ```bash  
   javac *.java  
   ```  
2. Run the applet viewer:  
   ```bash  
   appletviewer main.html  
   ```  

### **Option 2: Running in a Browser (Legacy Support Needed)**  
1. Ensure **Java Applet Plugin** is installed.  
2. Open `main.html` in a browser.  




