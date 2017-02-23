import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * Insert file/class javadoc here
 */
public class PokemonGame extends JFrame {
    public static final int FRAME_WIDTH = 750;
    public static final int FRAME_HEIGHT = 750;
    public static final int PANEL_WIDTH = 750;
    public static final int PANEL_HEIGHT = 350;
    public static final int H_GAP = 0;
    public static final int START_X = 350;
    public static final int START_Y = 150;
    public static final int MIDDLE_X = 350;
    public static final int MIDDLE_Y = 150;
    
    // Declaration of primary panels
    // There may only be one primary panel active at a time. Subpanels, such as
    // menus within the game, are subpanels within the primary panel
    // Battles will probably have their own panel
    private StartPanel startScreen;
    private GamePanel gameScreen;
    private MenuPanel menuScreen;
    
    public static void main(String[] args) {
        PokemonGame game = new PokemonGame();
        
        game.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        game.setLocationRelativeTo(null);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
    }
    
    public PokemonGame() {
        setLayout(new GridLayout(2, 1, 0, H_GAP));
        setTitle("Pokemon");
        // Initialize start screen
        startScreen = new StartPanel();
        add(startScreen);
    }
    
    //---------------------------Transition functions---------------------------
    public void loadGame() {
        gameScreen = new GamePanel();
        menuScreen = new MenuPanel();
        
        remove(startScreen);
        add(gameScreen);
        add(menuScreen);
        gameScreen.requestFocusInWindow();
        invalidate();
        validate();
    }
    
    //--------------------------------------------------------------------------
    //--------------------------Panel Inner Classes-----------------------------
    //--------------------------------------------------------------------------
    // TODO Perhaps it's better to put each of these in their own file when they
    // get bit enough. Not sure on the pros and cons of inner classes

    //------------------------------Start Screen--------------------------------
    public class StartPanel extends JPanel {
        JLabel dummy = new JLabel("Press enter!");

        public StartPanel() {
            add(dummy);

            this.setFocusable(true);
            addKeyListener(new StartKeyListener());
        }
        
        public class StartKeyListener implements KeyListener {
            public void keyPressed(KeyEvent e) {
                int kc = e.getKeyCode();
                // Load the game when enter is pressed
                if (kc == KeyEvent.VK_ENTER)
                    loadGame();
            }

            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        }
    } // end class StartScreen

    
    //-------------------------------Game Screen--------------------------------
    public class GamePanel extends JPanel {
        // TODO Possibly keep this as a focus disabler / game pauser?
        // Unless there's a better way...
        private boolean enabled;
        private PokemonMap curMap;
        private BufferedImage mapImage;
        private Player player;

        // Player movement variables
        private static final int WALK_DELAY = 30;
        int lastKeyPressed;
        int keyCycles;
        

        // Do game initializations here
        public GamePanel() {
            enabled = true;
            this.setFocusable(true);
            addKeyListener(new GameKeyListener());
            
            // Fires to render every 10 ms
            Timer t = new Timer(10, new TimerListener());
            t.start();

            player = new Player(START_X, START_Y);

            lastKeyPressed = KeyEvent.VK_UNDEFINED;
            keyCycles = 0;

            // Load current map
            // TODO: Need to write a way to read matrices. For now just reading
            // matrix for temp_map2.jpg
            int x = 34;
            int y = 34;
            int[][] m = new int[x][y];

            // Read through temp_map2.txt to make an array of locations
            try {
                FileReader file       = new FileReader("maps" + File.separator + "temp_map2.txt");
                BufferedReader buffer = new BufferedReader(file);
                String nextLine       = buffer.readLine();
                
                int i = 0;
                int j = 0;

                while (nextLine != null) {
                    // Skip empty lines
                    if (nextLine.length() > 0) {
                        String[] nums = nextLine.split(" ");
                        m[i][j] = Integer.parseInt(nums[i]);
                        i++;
                    }

                    i = 0;
                    j++;
                    nextLine = buffer.readLine();
                }

                buffer.close();
            }
            catch(FileNotFoundException e) {
                System.out.println("temp not found in directory. \n");
            }
            catch(IOException e) {
                System.out.println("Error reading temp\n");
            }

            curMap = new PokemonMap("maps" + File.separator + "temp_map2.jpg", 1700, 1700, START_X, START_Y, 24, 20, m);
            // Take subimage
            mapImage = curMap.getCurImage();
        }
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // For more advanced 2D graphics
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Render stuff
            AffineTransform mapScale = new AffineTransform();
            g2.drawImage(mapImage, new AffineTransformOp(mapScale, AffineTransformOp.TYPE_BILINEAR), 0, 0);

            // Draw the player's sprite
            g2.drawImage(player.getSprite(), player.getX(), player.getY(), null);
            
            requestFocus();
        } // end method paintComponent
        
        private void gameScreenTick() {
            // Operations for moving when a key is held
            keyCycles++;
            if ((keyCycles % WALK_DELAY) == 0) {
                switch (lastKeyPressed) {
                    case KeyEvent.VK_LEFT:
                        if(curMap.moveMapX(player.getX(), -1)) {
                            curMap.updateCurImage();
                            mapImage = curMap.getCurImage();
                        }
                        else {
                            player.moveLeft();
                        }
                        
                        break;

                    case KeyEvent.VK_RIGHT:
                        if(curMap.moveMapX(player.getX(), 1)) {
                            curMap.updateCurImage();
                            mapImage = curMap.getCurImage();
                        }
                        else {
                            player.moveRight();
                        }

                        break;

                    case KeyEvent.VK_UP:
                        if(curMap.moveMapY(player.getY(), -1)) {
                            curMap.updateCurImage();
                            mapImage = curMap.getCurImage();
                        }
                        else {
                            player.moveUp();
                        }

                        break;

                    case KeyEvent.VK_DOWN:
                        if(curMap.moveMapY(player.getY(), 1)) {
                            curMap.updateCurImage();
                            mapImage = curMap.getCurImage();
                        }
                        else {
                            player.moveDown();
                        }

                        break;
                }
            }

            repaint();
        }


        protected class GameKeyListener implements KeyListener {
            public void keyPressed(KeyEvent e) {
                int kc = e.getKeyCode();
                // Handle held keys separately from the OS's implementation
                if (kc == lastKeyPressed)
                    return;

                lastKeyPressed = kc;
                keyCycles = 0;

                switch (kc) {
                    case KeyEvent.VK_LEFT:
                        if(curMap.moveMapX(player.getX(), -1)) {
                            curMap.updateCurImage();
                            mapImage = curMap.getCurImage();
                        }
                        else {
                            player.moveLeft();
                        }
                        
                        break;

                    case KeyEvent.VK_RIGHT:
                        if(curMap.moveMapX(player.getX(), 1)) {
                            curMap.updateCurImage();
                            mapImage = curMap.getCurImage();
                        }
                        else {
                            player.moveRight();
                        }

                        break;

                    case KeyEvent.VK_UP:
                        if(curMap.moveMapY(player.getY(), -1)) {
                            curMap.updateCurImage();
                            mapImage = curMap.getCurImage();
                        }
                        else {
                            player.moveUp();
                        }

                        break;

                    case KeyEvent.VK_DOWN:
                        if(curMap.moveMapY(player.getY(), 1)) {
                            curMap.updateCurImage();
                            mapImage = curMap.getCurImage();
                        }
                        else {
                            player.moveDown();
                        }

                        break;
                }
            }
            
            public void keyReleased(KeyEvent e) {
                lastKeyPressed = KeyEvent.VK_UNDEFINED;
                keyCycles = 0;
            }
            
            public void keyTyped(KeyEvent e) {}
        }
        
        public class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (enabled) {
                    gameScreenTick();
                }
            }
        }
    } // end class GamePanel

    //-------------------------------Menu Screen-------------------------------
    public class MenuPanel extends JPanel {
        // TODO Possibly keep this as a focus disabler / game pauser?
        // Unless there's a better way...
        private boolean enabled;
        // TODO need to add a cursor or selection of some sort
        BufferedImage menuImage;

        // Do menu initializations here
        public MenuPanel() {
            enabled = true;
            this.setFocusable(true);
            //addKeyListener(new GameKeyListener());
            
            // Fires to render every 10 ms
            //Timer t = new Timer(10, new TimerListener());
            //t.start();

            // TODO eventually move this into some sort of map class
            try {
                menuImage = ImageIO.read(new File("pics" + File.separator + "menu.jpg"));
            }
            catch (IOException e) {}

            //player = new Player(50, 50);

            //lastKeyPressed = KeyEvent.VK_UNDEFINED;
            //keyCycles = 0;
        }
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // For more advanced 2D graphics
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Render stuff
            AffineTransform menuScale = new AffineTransform();
            menuScale.scale(2.2, 1.5);
            g2.drawImage(menuImage, new AffineTransformOp(menuScale, AffineTransformOp.TYPE_BILINEAR), 0, 0);

            // Draw the player's sprite
            //g2.drawImage(player.getSprite(), player.getX(), player.getY(), null);
            
            //requestFocus();
        } // end method paintComponent
    }

    //-------------------------------Battle Screen--------------------------------
    public class BattlePanel extends JPanel {
        // TODO Possibly keep this as a focus disabler / game pauser?
        // Unless there's a better way...
        private boolean enabled;
        
        public BattlePanel() {
            enabled = true;
            this.setFocusable(true);
            addKeyListener(new BattleKeyListener());
            
            // Fires to render every 10 ms
            Timer t = new Timer(10, new TimerListener());
            t.start();
        }
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // For more advanced 2D graphics
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Render stuff
            
            requestFocus();
        } // end method paintComponent
        
        private void battleScreenTick() {
            repaint();
        }


        protected class BattleKeyListener implements KeyListener {
            public void keyPressed(KeyEvent e) {
                int kc = e.getKeyCode();

                switch (kc) {
                }
            }
            
            public void keyReleased(KeyEvent e) {}
            
            public void keyTyped(KeyEvent e) {}
        }
        
        public class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (enabled) {
                    battleScreenTick();
                }
            }
        }
    } // end class GamePanel
}
