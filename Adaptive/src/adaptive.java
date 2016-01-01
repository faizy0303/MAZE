//FILE STORAGE IO for MAZES
// FORWARD REPEATED A*
//UI implemented code. Braking ties done. Final path states stored

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.*;

class state
{
	int x,y;
	int h;
	int g;
	int f;
	int value;
	int search; 
	state parent;
	int durl[] = new int[]{1,1,1,1};
	boolean visited;
}
class method_fn
{
    int height = 101;  //maze-size
    int width = 101;  //maze-size
    int r,c;
    int maze[][] = new int[height][width];

    public int[][] generateMaze() {       
  // System.out.println("INSIDE Generate");
    // Initializ
    for (int i = 0; i < height; i++)
        for (int j = 0; j < width; j++)
            maze[i][j] = 1;
 
     Random rand = new Random();
    
     // Generate random r
     r = rand.nextInt(height);
     while (r % 2 == 0) {
         r = rand.nextInt(height);
     }
     // Generate random c
     c = rand.nextInt(width);
     while (c % 2 == 0) {
         c = rand.nextInt(width);
     }
     // Starting cell
     maze[r][c] = 0;
 
     // Allocate the maze with recursive method
     recursion(height,width,maze,r, c);

     return maze;
 }

//storing in file
 
public	void StoreMazeInFile(int k,int[][] maze, int r,int c,int goal_x,int goal_y)
	{
		String filename = "Maze"+String.valueOf(k)+".txt";
		try (
				PrintStream output = new PrintStream(new File(filename));
				){
				output.println(r);
				output.println(c); 
				output.println(goal_x); 
				output.println(goal_y); 
			for(int i =0;i<101;i++){
				for (int j=0;j<101;j++){
						output.println(maze[i][j]);            	
				}
			}
			output.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		
	}
//storing in file 

 public void recursion(int height,int width,int maze[][],int r, int c) {
     // 4 random directions
     Integer[] randDirs = generateRandomDirections();
     // Examine each direction
     for (int i = 0; i < randDirs.length; i++) {
 
         switch(randDirs[i]){
         case 1: // Up
             // Whether 2 cells up is out or not
             if (r - 2 <= 0)
                 continue;
             if (maze[r - 2][c] != 0) {
                 maze[r-2][c] = 0;
                 maze[r-1][c] = 0;
                 recursion(height,width,maze,r - 2, c);
             }
             break;
         case 2: // Right
             // Whether 2 cells to the right is out or not
             if (c + 2 >= width - 1)
                 continue;
             if (maze[r][c + 2] != 0) {
                 maze[r][c + 2] = 0;
                 maze[r][c + 1] = 0;
                 recursion(height,width,maze,r, c + 2);
             }
             break;
         case 3: // Down
             // Whether 2 cells down is out or not
             if (r + 2 >= height - 1)
                 continue;
             if (maze[r + 2][c] != 0) {
                 maze[r+2][c] = 0;
                 maze[r+1][c] = 0;
                 recursion(height,width,maze,r + 2, c);
             }
             break;
         case 4: // Left
             // Whether 2 cells to the left is out or not
             if (c - 2 <= 0)
                 continue;
             if (maze[r][c - 2] != 0) {
                 maze[r][c - 2] = 0;
                 maze[r][c - 1] = 0;
                 recursion(height,width,maze,r, c - 2);
             }
             break;
         }
     }
 
 }
 
 /**
 * Generate an array with random directions 1-4
 * @return Array containing 4 directions in random order
 */
    public Integer[] generateRandomDirections() {
      ArrayList<Integer> randoms = new ArrayList<Integer>();
      for (int i = 0; i < 4; i++)
           randoms.add(i + 1);
      Collections.shuffle(randoms);
 
     return randoms.toArray(new Integer[4]);
 }

}

class Binaryheap1 
{
	int heapsize = 0;
	state heap[];
	Binaryheap1(int capacity )
	{
		heap = new state[capacity + 1];
	}
	int parentt(int i)
	{
		return ((i-1)/2);
	}
	void insert(state a)
	{
		//System.out.println("Entered heap insert: heapsize = "+ heapsize);
		heap[heapsize] = a;
		//System.out.println("After heap[heapsize] = a for heapsize = "+ heapsize);
		heapsize++;
		//System.out.println("After heapsize++ , heapsize = "+ heapsize);
		heapifyup(heapsize-1);
	}
	
	void heapifyup(int index)
	{
		state tempint = heap[index];
		//int k = index;
		while((tempint.f<heap[parentt(index)].f) && index>0)
		{
			tempint = heap[index];
			heap[index] = heap[parentt(index)];
			heap[parentt(index)] = tempint;
			index = parentt(index);
		}
		
		if(tempint.f==heap[parentt(index)].f)//added loop zhilmil tie
		{
			while(tempint.g < heap[parentt(index)].g && tempint.f==heap[parentt(index)].f)
			{
				tempint = heap[index];
				heap[index] = heap[parentt(index)];
				heap[parentt(index)] = tempint;
				index = parentt(index);
			}
		}
	}

	state minelement()
	{
		return heap[0];
	}

	void delete(int index)
	{
		heap[index] = heap[heapsize - 1];
		//System.out.println("m here inside delete" + index+ "heapsize" + heapsize);
		heapsize--;
		//System.out.println("m here inside delete" + index+ "heapsize" + heapsize);
		heapifydown(index);	
	}
	
	void heapifydown(int index)
	{
		//System.out.println("m here inside delete" + index+ "heapsize" + heapsize); 
		int min,l,r;
		state tempstate;
		//System.out.println("m here inside delete" + index+ "heapsize" + heapsize);
		
		l = child(index,1);
		r = child(index,2);
		min = index;
		if (l < heapsize && (heap[l].f < heap[min].f))
			min = l;
		if (r < heapsize && (heap[r].f < heap[min].f))
			min = r;
		if(r < heapsize && heap[r].f == heap[min].f)  //added loop zhilmil tie
		{
			if(heap[r].g < heap[min].g)
				min = r;
		}
		if(l < heapsize && heap[l].f == heap[min].f)  //added loop zhilmil tie
		{
			if(heap[l].g < heap[min].g)
				min = l;
		}	
		if (min != index)
		{
			tempstate = heap[index];
			heap[index] = heap[min];
			heap[min] = tempstate;
			heapifydown(min);
		}	
	}	
	
	int find(state s)
	{
		int i=0;
		while(i<heapsize)
		{
			if(s.x == heap[i].x)
			{
				if(s.y == heap[i].y)
				{
					return i+1;
				}
			}
			i++;
		}
		return 0;
	}

	boolean isnull()
	{
		//System.out.println("checking emptyiness");
		if(heapsize ==0)
			return true;
		return false;
	}	

	int child(int index,int k)
	{
		return (index*2+k);
	}

	void printheap()
	{
		int i;
		if(heapsize==0)
			//System.out.println("heap ois empyt");
		//System.out.println("the heapsize is " + heapsize);
		for(i=0;i<heapsize;i++)
		{
			//System.out.println("The heap is " + heap[i].f);
		}
	} 
}
		

class adaptive {
static int indexof = 0;
static int counter =0;
public static void main(String args[]) throws FileNotFoundException
	{	
		Random rand = new Random();
		Integer infinity = Integer.MAX_VALUE;
		method_fn[] mazearr = new method_fn[50];	
        int mazeui[][]=new int[101][101];

		for(int i=0; i<50; i++) //generating mazes random
		{
			mazearr[i] = new method_fn();
			mazearr[i].generateMaze();
		}
		

		
		
// Reading a maze from textfile

		/*for(int i=0; i<50; i++) //StoreMazeInFile
		{
		    //initialising goal
			int goal_x = rand.nextInt(101);  //maze-size

			while (goal_x % 2 == 0) 
			{
	    	goal_x = rand.nextInt(101);  //maze-size
	 		}	

			int goal_y = rand.nextInt(101);  //maze-size  

			while (goal_y % 2 == 0) 
			{
	   		 goal_y = rand.nextInt(101);  //maze-size
			}
			//initialising goal end
			
		//	System.out.println("CaLLING generateMaze");
			mazearr[i].StoreMazeInFile(i,mazearr[i].maze,mazearr[i].r,mazearr[i].c,goal_x,goal_y);
			System.out.println("After StoreMazeInFile: "+ i +" goal_x = " + goal_x + " goal_y = " + goal_y + " s_startx = " + mazearr[i].r + "s_start.y = " + mazearr[i].c);
		}*/
		
		int goal_x = 0, goal_y = 0;
		
	for(int i=0; i<1; i++) //ScanMazeFromFile
	{
			mazearr[i] = new method_fn();
			//mazearr[i].maze = mazearr[i].ScanMazeFromFile("Maze0.txt");
			
			String fileName = "Maze0.txt";
			String line = null;
			int maze[][] = new int[101][101];
		try {

				FileReader fileReader = new FileReader(fileName);

				BufferedReader bufferedReader = new BufferedReader(fileReader);

				line = bufferedReader.readLine();
				mazearr[i].r = Integer.parseInt(line);
				
				line = bufferedReader.readLine();
				mazearr[i].c = Integer.parseInt(line);
				
				line = bufferedReader.readLine();
				goal_x = Integer.parseInt(line);
				
				line = bufferedReader.readLine();
				goal_y = Integer.parseInt(line);
				
				for(int k=0;k < 101; k++) //  scan maze from file
				{
					for(int j=0;j < 101; j++) 
					{	
							line = bufferedReader.readLine();
							maze[k][j] = Integer.parseInt(line);
							 
					}
				}

				bufferedReader.close();
			}
			catch(FileNotFoundException ex) {
				System.out.println("Unable to open file '" + fileName + "'");                
			}
			catch(IOException ex) {System.out.println("Error reading file '" + fileName + "'");                  
			}
			
		mazearr[i].maze = maze;
	}

// Reading a maze from textfile
		
		int z=0;// rand.nextInt(50);  //random-maze
		mazeui=mazearr[z].maze;
		//maze printing
		for(int i=0;i < 101; i++) //  random maze
	     {
	        for(int j=0;j < 101; j++)  //maze-size
	        {
	            System.out.print(mazearr[z].maze[i][j]);
	        }
	        System.out.println(" ");
	     }
	     
		//System.out.println("Radomly generated MAZE number: "+z+" from array of mazes");
		
    	
	
		int i,j,k;
		state[][] x = new state[101][101];  //maze-size
		state s_start = new state();
		s_start.x = mazearr[z].r;			// random maze
		s_start.y = mazearr[z].c;
		
		state s_goal = new state();
		s_goal.x = goal_x;
		s_goal.y = goal_y;
		s_goal.value = 0; 				//anwar added
		System.out.println("goal_x = " + s_goal.x + " goal_y = " + s_goal.y + " s_startx = " + s_start.x + "s_start.y = " + s_start.y);
		//System.out.println("initialising States now");
		int uiStartX=s_start.x ,uiStartY=s_start.y;
		int uiGoalX=s_goal.x ,uiGoalY=s_goal.y;
		
		
		for(i=0;i<101;i++)  //maze-size
		{
			for(j=0;j<101;j++)  //maze-size
			{
				x[i][j] = new state();
				x[i][j].x = i;
				x[i][j].y = j;
				x[i][j].value = mazearr[z].maze[i][j]; 			//random maze
				x[i][j].g = manhattan(s_start.x,s_start.y,i,j);
				x[i][j].h = manhattan(i,j,goal_x,goal_y); 
				x[i][j].f = x[i][j].g + x[i][j].h;
				x[i][j].visited = false;
			}
		}
		x[goal_x][goal_y] = s_goal;
		x[mazearr[z].r][mazearr[z].c] = s_start;		//random maze  //anwar swapped
		
				for(i=0;i<101;i++)  //maze-size
				{
					for(j=0;j<101;j++)  //maze-size
				        x[i][j].search = 0;
				}

			//Binaryheap1 open = new Binaryheap1(25);
			ArrayList<state> closed = new ArrayList<state>();
			//System.out.println("goal_x = " + goal_x + "goal_y" + goal_y + " S_tartx" + s_start.x + "s_start.y" + s_start.y);
			
			ArrayList<state> finalstates = new ArrayList<state>(); //stack
			int size = 0;
			
			long startTime = System.currentTimeMillis();
			//System.out.println("Before outer WHILE in main");
			while((s_start.x!=goal_x) || (s_start.y!=goal_y) )
			{
				System.out.println("INSIDE outer WHILE: value of sstart_x " + s_start.x + " and value of sstart_y " + s_start.y + " value of goal_x "+ goal_x +" and goal_y " + goal_y + " counter: "+counter);
				counter++;
				s_start.g = 0;
				s_start.search = counter;
				s_goal.g = 	infinity;
				s_goal.search = counter;
				/*for(i=0;i<open.heapsize;i++)
					open.delete(0);*/
				closed = null;
				//System.out.println("Before outer WHILE in main");
				Binaryheap1 open = new Binaryheap1(10201);
				open.insert(s_start);
				//System.out.println("Before outer WHILE in main");
				open.printheap();
				ComputePath(open,s_goal,closed,x,goal_x,goal_y);
				//System.out.println("We are out");
				
			
				if(open.isnull())
				{
					long endTime = System.currentTimeMillis();
					long time = endTime - startTime;
					System.out.println("Time taken by adaptive is: "+time);
					Mazeui drawui=new Mazeui(mazeui,uiStartX,uiStartY,uiGoalX,uiGoalY,finalstates);
					drawui.setVisible(true);
					drawui.ShowNoPathPopup();
					System.out.println("I cannot reach the target");
					return;
				//	System.exit(0);
				}

				ArrayList<state> current = new ArrayList<state>();
				int curpoint = 0;
				state cur = s_goal;
				
				while(cur.x!=s_start.x || cur.y!=s_start.y) 
				{
					current.add(cur);
					//System.out.println(" Inside 1 the value of x and y is " + cur.x + "and" + cur.y);
					curpoint++;
					//System.out.println(" Inside 2 the cur.x is " + cur.x + " the cur.y is "+ cur.y);
					cur = cur.parent;
					
				}
				//System.out.println("out of loop cur while");
				state temp; 
				current.add(cur); //adding s_start
				//System.out.println(" the cur.x is " + cur.x + " the cur.y is "+ cur.y);
				for(i=curpoint-1;i>=0;i--)
				{
					if(current.get(i)==s_goal)
					{
						i--;
						//System.out.println("here its breaking");
						break;
					}
					if(current.get(i).value==1)
					{
						//System.out.println("here its breaking because value is 1");
						if(current.get(i).x-current.get(i+1).x>0)
							{
							//System.out.println("here its breaking because value is d");
								temp = current.get(i+1);
								x[temp.x][temp.y].durl[0] = infinity;
								break; 
							}
						else if (current.get(i).y-current.get(i+1).y>0)
							{
							//System.out.println("here its breaking because value is r");
								temp = current.get(i+1);
								x[temp.x][temp.y].durl[2] = infinity;
								break;
							}
						else if(current.get(i+1).y-current.get(i).y>0)
							{
							//System.out.println("here its breaking because value is l");
								temp = current.get(i+1);
								x[temp.x][temp.y].durl[3] = infinity;
								break; 	
							}
						else if(current.get(i+1).x-current.get(i).x>0)
							{
							//System.out.println("here its breaking because value is u");
								temp = current.get(i+1);
								x[temp.x][temp.y].durl[1] = infinity;
								break; 
							}

					}
				}
				
				for(k=curpoint;k>=i+1;k--) //zhilmil adaptive
				{
					x[current.get(k).x][current.get(k).y].h = current.get(i+1).g + current.get(i+1).h - current.get(k).g; 
				}
				
				//System.out.println("here its breaking because value is breaking from for");
				temp = current.get(i+1);
				//s_start = x[temp.x][temp.y];  //stack
				
				if((s_start.x!=temp.x)||(s_start.y!=temp.y))  //stack
				{
					for (int s=curpoint; s>i; s--)
					{
						state path = current.get(s);
						finalstates.add(path);
					}
								// add(s_start)
					s_start = x[temp.x][temp.y];
			
				}
				
				System.out.println("the start new should start at x value " + s_start.x + " with y value " + s_start.y);
			
				if((temp.x == goal_x) && (temp.y == goal_y))	// stack
				{
					s_start = x[temp.x][temp.y];
					size = curpoint;
					for(int m = curpoint; m>=0; m--)
					{
						finalstates.add(current.get(m));
						//System.out.println("Pushing finalstate");
					}
					current.clear();
				}
				
			}
			
			long endTime = System.currentTimeMillis();
			
			if(counter !=0)
			{	
				System.out.println("ROUTE: ");	//stack
				size = finalstates.size();
					for (int n=0; n<=size-1; n++)
					{
						System.out.println("X: "+ finalstates.get(n).x + " Y: " + finalstates.get(n).y);
					}
			}
			
			long time = endTime - startTime;
			System.out.println("Time taken by adaptive is: "+time);
			
			System.out.println("I reached the target");
			Mazeui drawui=new Mazeui(mazeui,uiStartX,uiStartY,uiGoalX,uiGoalY,finalstates);
			drawui.setVisible(true);
			finalstates.clear();

	}  

static void ComputePath(Binaryheap1 open,state s_goal,ArrayList<state> closed,state[][] x,int goal_x,int goal_y)
{
	//System.out.println("the call ");
	Integer infinity = Integer.MAX_VALUE;
	int neighbourx[] = new int[]{+1,-1,0,0};
	int neighboury[] = new int[]{0,0,+1,-1};
	int i;
	//System.out.println("m here" +  s_goal.g + "value of" + open.minelement().g);
	while(s_goal.g> open.minelement().g && open.isnull()!=true)
	{
		//System.out.println("m here inside while");
		//System.out.println("m here inside while");
		state min;
		min = open.minelement();
		/*while(x[min.x][min.y].visited !=false)
		{
			open.delete(0);
			min = open.minelement();
			
		}*/
		open.delete(0);
		
		if (x[min.x][min.y].value == 1) //anwar inserted
			x[min.x][min.y].visited = true;
		open.printheap();
		//closed.add(indexof,min);
		//System.out.println("m herebefore for");

	for(i=0;i<4;i++)
	{//maze-size in if
		if((neighbourx[i]+min.x<101) && (neighboury[i]+min.y<101) &&(neighbourx[i]+min.x>=0)&& (neighboury[i]+min.y>=0))
		{
			state succ = x[neighbourx[i]+min.x][neighboury[i]+min.y];
		//System.out.println("first if of ComputePath" + succ.search +"value of counter" + counter);
		if (succ.x == goal_x && succ.y == goal_y)
			s_goal = succ;
		if(succ.search<counter)
			{
				succ.g = infinity;
				succ.search = counter;
				//System.out.println("first if of ComputePath-------");

			}
			//System.out.println("first if of ComputePath"+ succ.g+ "another value" + min.g+ "third value"+ min.durl[i]);
		if(succ.g > (min.g) + min.durl[i])
			{
				succ.g = min.g + min.durl[i];
				//System.out.println("right now succ is" + succ.x + "y value" + succ.y);
				//System.out.println("right now min is" +  min.x + "gjas" + min.y);
				succ.parent = min;
				//System.out.println("teh parent of index succ is min");
				
				if(open.find(succ)!=0)
					open.delete(open.find(succ));
				else
				{ 	//System.out.println("problem is here");
					succ.f = succ.g + succ.h;
					if(x[succ.x][succ.y].visited != true)
						open.insert(succ);
				//	System.out.println(" succ.x = " + succ.x + " succ.y = " + succ.y);
				}
				
			}
			open.printheap();
			//System.out.println("problem is here");
/*				if(open.find(succ)!=0)
					open.delete(open.find(succ));
				else
				{ 	//System.out.println("problem is here");
					succ.f = succ.g + succ.h;
					if(x[succ.x][succ.y].visited != true)
						open.insert(succ);
				//	System.out.println(" succ.x = " + succ.x + " succ.y = " + succ.y);
				}	*/
		}
		//System.out.println("value of i" + i);
	}

	
	}
}
	
	static int manhattan(int a,int b, int c ,int d)
		{
			return ((Math.abs(a-c))+(Math.abs(b-d)));
		}

}
