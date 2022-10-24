import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Instance of the game
public class Game extends JPanel{
    
    int crx,cry;	//location of the crossing
    int tempy=0;
    int car_x,car_y;    //x and y location of user's car
    int speedX,speedY;	//the movement values of the user's car
    int nOpponent;      //the number of opponent vehicles in the game
    String imageLoc[]; //array used to store oponnent car images
    int lx[],ly[];  //integer arrays used to store the x and y values of the oncoming vehicles
    int score;      //intger variable used to store the current score of the player
    int highScore;  //integer variable used to store the high score of the player
    int speedOpponent[]; //integer array used to store the spped value of each opponent vehicle in the game
    boolean isFinished; //boolean that will be used the end the game when a colision occurs
    boolean isUp, isDown, isRight, isLeft;  //boolean values that show when a user clicks the corresponding arrow key
    
    public Game(){
        //Listener to get input from user when a key is pressed and released
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) { //when a key is released
                stopCar(e); //stop movement of car
            }
            public void keyPressed(KeyEvent e) { //when a key is pressed
                moveCar(e); //move the car in the direction given by the key
            }
        });
        setFocusable(true); //indicates the JPanel can be focused
        initialize();
    }
    public void initialize(){
        car_x = car_y = 300;    //initialling setting the user's car location to (300,300)
        isUp = isDown = isLeft = isRight = false;   //initial arrow key values set to false, meaning user has not pressed any arrow keys
        speedX = speedY = 0;    //movement of the car in the x and y direction initially set to 0 (starting position)
        nOpponent = 0;  //set the number of opponent cars initially to zero
        lx = new int[20]; //array to be used to store the x position of all enemy cars
        ly = new int[20]; //array to be used to store the y position of all enemy cars
        imageLoc = new String[20];
        speedOpponent = new int[20]; //integer array used to store the spped value of each opponent vehicle in the game
        isFinished = false; //when false, game is running, when true, game has ended
        score = highScore = 0;  //initialling setting the current score and the highscore to zero
    }
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D obj = (Graphics2D) g;
        obj.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try{
            obj.drawImage(getToolkit().getImage("images/highway.png"), 0, 0,this); //draw road on window
            tempy += 1;
            if(tempy < 500){
                obj.drawImage(getToolkit().getImage("images/highway.png"), 0, tempy ,this); //draw road on window
                obj.drawImage(getToolkit().getImage("images/highway.png"), 0, tempy-486 ,this); //draw road on window
            }
            else{
                tempy = 0;
                obj.drawImage(getToolkit().getImage("images/highway.png"), 0, tempy ,this); //draw road on window
            }
            obj.drawImage(getToolkit().getImage("images/bike.png"),car_x,car_y,this);   //draw car on window
            

            if(isFinished){ //if collision occurs
                obj.drawImage(getToolkit().getImage("images/boom.png"),car_x-30,car_y-30,this); //draw explosion image on window at collision to indicate the collision has occured
            }
            
            if(this.nOpponent > 0){ //if there is more than one opponent car in the game
                for(int i=0;i<this.nOpponent;i++){ //for every opponent car
                    obj.drawImage(getToolkit().getImage(this.imageLoc[i]),this.lx[i],this.ly[i],this); //draw onto window
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    public void stopCar(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){   //if user clicks on the up arrow key
            isUp = false;
            speedY = 0; //set speed of car to zero
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){    //if user clicks on the down arrow key
            isDown = false;
            speedY = 0; //set speed of car to zero
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){    //if user clicks on the left arrow key
            isLeft = false;
            speedX = 0; //set speed of car to zero
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){   //if user clicks on the right arrow key
            isRight = false;
            speedX = 0; //set speed of car to zero
        }
    }
    public void moveCar(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){   //if user clicks on the up arrow key
            isUp = true;
            speedY = -1;     //moves car foward
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){ //if user clicks on the down arrow key
            isDown = true;
            speedY = 2;    //moves car backwards
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){ //if user clicks on the right arrow key
            isRight = true;
            speedX = 2;     //moves car to the right
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){ //if user clicks on the left arrow key
            isLeft = true;
            speedX = -2;    //moves car to the left
        }
    }
    void generateCar(){
        this.imageLoc[this.nOpponent] = "images/car_left_"+((int)((Math.random()*100)%3)+1)+".png"; //assign images to the opponent cars             
        this.ly[this.nOpponent] = -10; //set opponent cars start positions
        
        int p1=0,p2=0; 
        int p = (int)(Math.random()*10)/2; 
        if(p == 1 || p == 0){     
            p1 = 130;
            p2 = 330;
        }
        else if(p>=4){          
            p1 = 330;
            p2 = 220;
        }
        else if(p >= 2){ 
            p1 = 220;    
            p2 = 130;
        }
        if(this.nOpponent > 0){
            if(this.lx[this.nOpponent-1]==p1)
                this.lx[this.nOpponent] = p2;
            else if(this.lx[this.nOpponent-1] == p2)
                this.lx[this.nOpponent] = p1;
            else{
                if(this.lx[this.nOpponent-1]==130)
                    this.lx[this.nOpponent] = 220;
                else
                    this.lx[this.nOpponent] = 130;  
            }
        }
        else
            this.lx[this.nOpponent] = p1;
        
        this.speedOpponent[this.nOpponent] = (int)(Math.random()*100)%3+2; 
        if(this.nOpponent>0){
            if(this.speedOpponent[this.nOpponent-1] > this.speedOpponent[this.nOpponent]){
                this.speedOpponent[this.nOpponent] = this.speedOpponent[this.nOpponent-1]-1;
                this.ly[this.nOpponent] = this.ly[this.nOpponent-1]-100;
            }
            else{
                this.speedOpponent[this.nOpponent-1] = this.speedOpponent[this.nOpponent];
                this.ly[this.nOpponent] = this.ly[this.nOpponent-1]-170; 
            }  
        }

        this.nOpponent++;
    }
    void checkCollision(){
        for(int i=0;i<nOpponent;i++){ //for all opponent cars
            if((lx[i] > car_x && lx[i]-car_x <= 50) || (car_x >= lx[i] && car_x-lx[i]<=20)){
                if((car_y > ly[i] && car_y-ly[i] <= 100)||(car_y<=ly[i] && ly[i]-car_y <= 80)){
                    System.out.println("My car : "+car_x+", "+car_y);
                    System.out.println("Colliding car : "+lx[i]+", "+ly[i]);
                    this.finish(); //end game and print end message
                }
            }
                   
        }
    }
    
    void moveRoad(int count){
        car_x += speedX; //update car x position
        car_y += speedY; //update car y position
        
        //case analysis to restrict car from going outside the left side of the screen
        if(car_x < 70)   //if the car has reached or gone under its min x axis value
            car_x = 70;  //keep it at its min x axis value
        
        //case analysis to restrict car from going outside the right side of the screen
        if(car_x+100 >= 450) //if the car has reached or gone over its max x axis value
            car_x = 450-100; //keep it at its max x axis value
        
        //case analysis to restrict car from going outside the right side of the road
        if(car_y <= 75)    //if the car has reached the the right side of the road or is trying to go further
            car_y = 75;    //keep the car where it is
        
        //case analysis to restrict car from going outside the left side of the road
        if(car_y > 360) //if the car has reached the the left side of the road or is trying to go further
            car_y = 360; //keep the car where it is
        
        for(int i=0;i<this.nOpponent;i++){ //for all opponent cars
            this.ly[i] += speedOpponent[i]; //move across the screen based on already calculated speed values
            if(this.ly[i] > 490){
                this.nOpponent--;
                score++;
            }
        }
        if(score > highScore)   //if the current score is higher than the high score
            highScore = score;
    }
    
    //function that will display message after user has lost the game
    void finish(){
        String str = "";    //create empty string that will be used for a congratulations method
        isFinished = true;  //indicates that game has finished to the rest of the program
        this.repaint();     //tells the window manager that the component has to be redrawn
        if(score == highScore && score != 0) //if the user scores a new high score, or the same high score
            str = "\nCongratulations!!! Its a high score";  //create a congratulations message
        //JOptionPane.showMessageDialog(this,"Game Over!!!\nYour Score : "+score+"\nHigh Score : "+highScore+str,     "Game Over", JOptionPane.YES_NO_OPTION);    //displays the congratulations message and a message saying game over and the users score and the high score
        int reply = JOptionPane.showConfirmDialog(this, "Game Over!!!\nYour Score : "+score+"\nHigh Score : "+highScore+str+"\nDo you want to play again ?","Game Over", JOptionPane.YES_NO_OPTION);
        
        if (reply == JOptionPane.YES_OPTION) {
            int temp = highScore;
            initialize();
            highScore = temp;
        } else {
            System.exit(ABORT);
        }
    }
    public static void main(String args[]){
        JFrame frame = new JFrame("Car Racing Game");   //creating a new JFrame window to display the game
        Game game = new Game(); //creating a new instance of a Game
        frame.add(game);		//Graphics2D components are added to JFrame Window
        frame.setSize(500,500); //setting size of screen to 500x500
        frame.setResizable(false);
        frame.setVisible(true); //allows the JFrame and its children to displayed on the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int count = 1, c = 1;
        while(true){
            game.moveRoad(count);   //move the road
            while(c <= 1){
                game.repaint();     //redraw road to match new locations
                try{
                    Thread.sleep(5);    //wait so that the road appears to be moving continously
                }
                catch(Exception e){
                    System.out.println(e);
                }
                c++;
            }
            c = 1;
            count++; //increment count value
            
            if( count % 450 == 0){ //if there is less than 4 cars and count timer reaches 200
                game.generateCar();
                game.generateCar();
            }
            game.checkCollision();
        }
    }
}