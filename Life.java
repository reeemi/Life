package GameOfLife;
import java.io.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Life {

//---------------------------CLASS VARIABLES--------------------------------------//
	
	private static final int DIM1 = 50;
	private static final int DIM2 = 50;
	
	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HEIGHT = 530;
	
	private static final int FRAME_START_X = 300;
	private static final int FRAME_START_Y = 100;
	
	private static boolean[][] board;
	
	private static JFrame mainFrame = new JFrame("Game of Life");
	
	private static JButton startButton = new JButton();
	private static int START_BUTTON_WIDTH = 100;
	private static int START_BUTTON_HEIGHT = 100;
	private static int START_BUTTON_X = 500;
	private static int START_BUTTON_Y = 0;
	private static boolean stopped = true;
	
	private static JButton resetButton = new JButton();
	private static int RESET_BUTTON_WIDTH = 100;
	private static int RESET_BUTTON_HEIGHT = 100;
	private static int RESET_BUTTON_X = 600;
	private static int RESET_BUTTON_Y = 0;
	
	private static JSlider timeSlider = new JSlider(JSlider.HORIZONTAL, 0,1000,500);
	private static int TIME_SLIDER_WIDTH = 100;
	private static int TIME_SLIDER_HEIGHT = 100;
	private static int TIME_SLIDER_X = 500;
	private static int TIME_SLIDER_Y = 150;
	
	private static OurPanel[][] pane;
	private static final int PANE_WIDTH = 10;
	private static final int PANE_HEIGHT = 10;

	private static BufferedReader reader;
	
	private static boolean isRunning = true;
	
	private static int timeBetweenSteps = 500;
	
	
//--------------------------------INITIALISATION----------------------------------//	
	
	public static void initialise(){
		
		// Initialisation of the main board
		board = createRandomBoard(DIM1,DIM2);
		
		// Initialisation of the main frame
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    mainFrame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
	    mainFrame.setLocation(FRAME_START_X, FRAME_START_Y);
	    mainFrame.setLayout(null);
	    
	    // Initilisation of the start button
	    startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if (stopped) startButton.setText("Stop");
                else 		 startButton.setText("Start");
                stopped =!stopped;
            }
	    });
	    startButton.setSize(START_BUTTON_WIDTH,START_BUTTON_HEIGHT);
	    startButton.setLocation(START_BUTTON_X,START_BUTTON_Y);
	    startButton.setText("Start");
	    startButton.setVisible(true);
	    mainFrame.add(startButton);
	    
	    
	    // Initilisation of the reset button
	    resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	board = createRandomBoard(DIM1,DIM2);
            	display(false);
            }
	    });
	    resetButton.setSize(RESET_BUTTON_WIDTH,RESET_BUTTON_HEIGHT);
	    resetButton.setLocation(RESET_BUTTON_X,RESET_BUTTON_Y);
	    resetButton.setText("Reset");
	    resetButton.setVisible(true);
	    mainFrame.add(resetButton);
	    
	    
	    // Initialisation of the time slider
	    timeSlider.addChangeListener(new ChangeListener() {
	    	public void stateChanged(ChangeEvent e){
	    	    JSlider source = (JSlider)e.getSource();
	    	    if (!source.getValueIsAdjusting()) {
	    	        timeBetweenSteps = (int)source.getValue();
	    	    }
	    	}
	    });
	    timeSlider.setSize(TIME_SLIDER_WIDTH,TIME_SLIDER_HEIGHT);
	    timeSlider.setLocation(TIME_SLIDER_X,TIME_SLIDER_Y);
	    timeSlider.setName("Time between steps");
	    timeSlider.setVisible(true);
	    mainFrame.add(timeSlider);
	    
	    
	    // Initialisation of the panels that depict the cells
	    pane = new OurPanel[DIM1][DIM2];
	    for(int x=0; x<DIM1; x++){
	    	for(int y=0; y<DIM2; y++){
		    	pane[x][y]=new OurPanel();
		    	mainFrame.add(pane[x][y]);
		    	pane[x][y].setLocation(x*PANE_WIDTH, y*PANE_HEIGHT);
		    	pane[x][y].setSize(PANE_WIDTH, PANE_WIDTH);
		    	pane[x][y].setAlive(board[x][y]);
			    pane[x][y].setVisible(true);
	    	}
	    }
		// Setting main frame visibility true after everything else is done
	    // avoids bugs
	    mainFrame.setVisible(true);
	    
	    
		// Initialisation of the input reader
		reader = new BufferedReader(new InputStreamReader(System.in));
	
	}
	
	
	public static boolean[][] createEmptyBoard(int width, int height){
		// Mit false gefuellt
		return new boolean[width][height];
	}
	
	public static boolean[][] createRandomBoard(int width, int height){
		boolean[][] rBoard = new boolean[width][height];
		for(int i = 0; i<width;i++){
			for(int j = 0; j<height;j++){
				rBoard[i][j] = (Math.random() > 0.5);
			}
		}
		return rBoard;
	}
	

//-----------------------------------DISPLAY--------------------------------------//
	
	public static void display(boolean console){
		int width = board.length;
		int height = board[0].length;
		
		
		if (console){
			for(int j = 0; j<height;j++){
				for(int i = 0; i<width;i++){
					System.out.print(board[i][j]?'x':'o');
				}
				System.out.println();
			}
			
			System.out.println();
			System.out.println();
			System.out.println();	
		}
		else {
			for(int j = 0; j<height;j++){
				for(int i = 0; i<width;i++){
					pane[j][i].setAlive(board[i][j]);
				}
				
			}

		}

	}
	
	
//-------------------------------UPDATE-------------------------------------------//
	
	public static boolean[][] update(boolean[][] board){
		int width = board.length;
		int height = board[0].length;
		
		boolean[][] rBoard = createEmptyBoard(width, height);
	
		
		int c;
		for(int i = 0; i<width;i++){
			for(int j = 0; j<height;j++){
				
				//resetting neighbours variable to zero and counting neighbours
				c = 0;
				if(j> 0 && board[i][j-1]) c++;
				if(j < (height-1) && board[i][j+1]) c++;
				if(i > 0 && j > 0 && board[i-1][j-1]) c++;
				if(i > 0 && j < (height-1) && board[i-1][j+1]) c++;
				if(i > 0 && board[i-1][j]) c++;
				if(i < (width-1) && j < (height-1) && board[i+1][j+1]) c++;
				if(i < (width-1) && j > 0 && board[i+1][j-1]) c++;
				if(i <  (width-1) && board[i+1][j]) c++;
				
				//applying rules
				
				if (board[i][j]){
					if (c<2 || c>3) rBoard[i][j] = false;
					else rBoard[i][j] = true;
				}else{
					if (c==3) rBoard[i][j] = true;
					else rBoard[i][j] = false;
				}
				
			}
		}		
		return rBoard;
	}
	
//-----------------------------INPUT HANDLING-------------------------------------//
	
	public static void handleInput(BufferedReader reader){
		/*try{
			String line = reader.readLine();
		} catch(Exception emptyException){};
		*/  // Not useable in graphic mode
		try{
			Thread.sleep(timeBetweenSteps);
		} catch (Exception emptyException){};
	}
	

	
//---------------------------------MAIN-------------------------------------------//	
	
	public static void main(String[] args) {
		
		initialise();
		
		// Main loop
		while (isRunning){
			if (!stopped) {
				display(false);
				mainFrame.repaint();
				board = update(board);
				handleInput(reader);
			}
		}
	}

}
