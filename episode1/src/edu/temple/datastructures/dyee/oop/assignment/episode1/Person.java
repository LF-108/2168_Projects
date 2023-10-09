//Lara Fernandes
//9/14/23
//Assignment 1


package edu.temple.datastructures.dyee.oop.assignment.episode1;
public class Person extends Creature {
	
	
	//The player's direction, distance to the monster, and direction of the monster respectively
	int currentDirection = Model.STAY;
	int distanceToMonster = 0;
	int monsterDirection = 0;
    	
	
	//Stores the directions that are the most out of sight of the quibit
	int[]bestDirectionN = {Model.SW, Model.SE, Model.W, Model.E,Model.NW, Model.NE,Model.STAY, Model.S, Model.N};
	int[]bestDirectionNE = {Model.W, Model.S, Model.NW, Model.SE, Model.N, Model.E,Model.STAY, Model.SW, Model.NE};
	int[]bestDirectionE = {Model.NW, Model.SW, Model.S, Model.N, Model.NE, Model.SE,Model.STAY, Model.W, Model.E};
	int[]bestDirectionSE = {Model.N, Model.W, Model.NE, Model.SW, Model.E, Model.S,Model.STAY, Model.NW,Model.SE};
	int[]bestDirectionS = {Model.NW, Model.NE, Model.W, Model.E, Model.SW, Model.SE,Model.STAY, Model.N, Model.S};
	int[]bestDirectionSW = {Model.N, Model.E, Model.NW, Model.SE,Model.W, Model.S,Model.STAY, Model.NE, Model.SW};
	int[]bestDirectionW = {Model.NE, Model.SE, Model.N, Model.S, Model.NW, Model.SW,Model.STAY, Model.E, Model.W};
	int[]bestDirectionNW = {Model.S, Model.E, Model.SE, Model.NE, Model.N, Model.W,Model.STAY, Model.SE, Model.NW};

	
	public Person(Model model, int row, int column) {
        super(model, row, column);
    }
	
	//Chooses the best move by going through an array the has the directions compared to each type in
	//order from best to worst based on how close they are to that direction
	public int bestMove(int direction) {
		int bestMove = Model.STAY;
		
		switch(direction){
		
		//Breaks the loops so that the best direction is chosen, as other directions
		//that are not the best option could still be movable
		case Model.N:
			for(int i = 0; i<8; i++ ) {
			  if(canMove(bestDirectionN[i])== true && bestDirectionW[i]!= monsterDirection) {
				  bestMove = bestDirectionN[i];
				  break;
			  }
			}
			break;
			
		case Model.NE:
			for(int i = 0; i<8; i++ ) {
				  if(canMove(bestDirectionNE[i])== true && bestDirectionW[i]!= monsterDirection) {
					  bestMove = bestDirectionNE[i];
					  break;
				  }
				}
			break;
			
		case Model.E:
			for(int i = 0; i<8; i++ ) {
				  if(canMove(bestDirectionE[i])== true && bestDirectionW[i]!= monsterDirection) {
					  bestMove = bestDirectionE[i];
					  break;
				  }
				}
			break;
			
		case Model.SE:
			for(int i = 0; i<8; i++ ) {
				  if(canMove(bestDirectionSE[i])== true && bestDirectionW[i]!= monsterDirection) {
					  bestMove = bestDirectionSE[i];
					  break;
				  }
				}
			break;
			
		case Model.S:
			for(int i = 0; i<8; i++ ) {
				  if(canMove(bestDirectionS[i])== true && bestDirectionW[i]!= monsterDirection) {
					  bestMove = bestDirectionS[i];
					  break;
				  }
				}
			break;
			
		case Model.SW:
			for(int i = 0; i<8; i++ ) {
				  if(canMove(bestDirectionSW[i])== true && bestDirectionW[i]!= monsterDirection) {
					  bestMove = bestDirectionSW[i];
					  break;
				  }
				}
			break;
			
		case Model.W:
			for(int i = 0; i<8; i++ ) {
				  if(canMove(bestDirectionW[i])== true && bestDirectionW[i]!= monsterDirection) {
					  bestMove = bestDirectionW[i];
					  break;
				  }
				}
			break;
			
		case Model.NW:
			for(int i = 0; i<8; i++ ) {
				  if(canMove(bestDirectionNW[i])== true && bestDirectionNW[i]!= monsterDirection) {
					  bestMove = bestDirectionNW[i]; 
					  break;
				  }
				}
			
			break;
		}
		
	
		return bestMove;
	}

    
    //@Override
    int decideMove() {
        
    	
    		/*Makes the player spin around to look for quibit, registering the direction
    	    accordingly and choosing the best move to stay out of sight using bestMove()*/
    		for(int s = Model.MIN_DIRECTION; s <= Model.MAX_DIRECTION; s++ ) {
    			if(look(s) == Model.MONSTER) {
    			
    			monsterDirection = s;
    			
    			distanceToMonster = distance(s);
    			
    		    currentDirection = bestMove(monsterDirection);
    		    		
    			}
    		}
    		
    	    /*Chooses the player's direction if the monster sneaks up on them 
    		attempts to move out of sight by moving diagonally and away, else
    		just tries to put as far of a distance as possible between the 
    		player and monster. Since the secondary directions have more possibilities
    		for escaping the quibit (N,S,W,E only have 3 options that don't move towards
    		the quibit) there are 5 options for them instead of 3*/
    		
    		if(distanceToMonster <= 1) {
    			
    			if(monsterDirection == Model.N) {
    				
    				if(canMove(Model.SW) ) {
    				currentDirection = Model.SW;
    				}
    				
    				else if(canMove(Model.SE)){
    					currentDirection = Model.SE;
    				}
    				
    				else {
    					currentDirection = Model.S;
    				}
    				
    			}
    			else if(monsterDirection == Model.NE) {
    				
    					if(canMove(Model.W)) {
        				currentDirection = Model.W;
        				}
    				
        				else if(canMove(Model.S)){
        					currentDirection = Model.S;
        				}
    					
        				else if(canMove(Model.NW)) {
        					currentDirection = Model.SW;
        				}
    				    
        				else if(canMove(Model.SE)) {
        					currentDirection = Model.SE;
        				}
        				else {
        					currentDirection = Model.SW;
        				}
    					
    			}
    			else if(monsterDirection == Model.E) {
    				
    					if(canMove(Model.NW)) {
        				currentDirection = Model.NW;
        				}
    					
        				else if(canMove(Model.SW)){
        					currentDirection = Model.SW;
        				}
    					
        				else{
        					currentDirection = Model.W;
        				}
        				
    					
    			}
    			else if(monsterDirection == Model.SE) {
    				
    				    if(canMove(Model.W)) {
        				currentDirection = Model.W;
        				}
    				    
        				else if(canMove(Model.N)){
        					currentDirection = Model.N;
        				}
    				    
        				else if(canMove(Model.NE)){
        					currentDirection = Model.NE;
        				}
    				    
        				else if(canMove(Model.SW)){
        					currentDirection = Model.SW;
        				}
    				    
        				else {
        					currentDirection = Model.NW;
        				}
    			}
    			else if(monsterDirection == Model.S) {
    				
    				   if(canMove(Model.NW)) {
        				currentDirection = Model.NW;
        				}
    				   
        				else if(canMove(Model.NE)){
        					currentDirection = Model.NE;
        				}
    				   
        				else {
        					currentDirection = Model.N;
        				}
    				   
    			}
    			else if(monsterDirection == Model.SW) {
    					
    					if(canMove(Model.N)) {
        				currentDirection = Model.N;
        				}
    				
        				else if(canMove(Model.E)){
        					currentDirection = Model.E;
        				}
    				
        				else if(canMove(Model.NW)) {
        					currentDirection = Model.NW;
        				}
    					
        				else if(canMove(Model.SE)) {
        					currentDirection = Model.SE;
        				}
    					
        				else {
        					currentDirection = Model.NE;
        				}
    			}
    			
    			else if(monsterDirection == Model.W) {
    				
    					if(canMove(Model.NE)) {
        				currentDirection = Model.NE;
        				}
    					
        				else if(canMove(Model.SE)){
        					currentDirection = Model.SE;
        				}
    					
        				else {
        					currentDirection = Model.E;
        				}
    			}
    			else if(monsterDirection == Model.NW) {
    				
    					if(canMove(Model.S)) {
        				currentDirection = Model.S;
        				}
    					
        				else if(canMove(Model.E)){
        					currentDirection = Model.SE;
        				}
    					
        				else if(canMove(Model.NE)){
        					currentDirection = Model.NE;
        				}
    					
        				else if(canMove(Model.SW)) {
        					currentDirection = Model.SW;
        				}
    					
        				else {
        					currentDirection = Model.SE;
        				}
    			}
 
    		}
    		
    		
    		
			/*Checks if a bush is in the direction that the monster was last in, and if so
			stays put (since if the monster is around that direction it should keep the 
			player hidden. Returns the direction to get the player to stay and ignore any other
			conditions that apply to their current position, since staying is the best option*/
			
    		for(int x = 0; x<8; x++) {
    			
				if(look(x)==Model.BUSH && monsterDirection == x) {
					currentDirection = Model.STAY;
					return currentDirection;
				}
				
    		}
    			
     	
    		return currentDirection;
    }
}


    		
    		
   
    		

    



    		
    		
    		
    
     

