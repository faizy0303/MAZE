import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Mazeui extends JFrame{
	private JPanel[][] grids;
public Mazeui(int mat[][],int sx,int sy,int gx,int gy,ArrayList<state> finalstates)
{
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 this.setBounds(0,0,screenSize.width, screenSize.height-50);
		//this.setSize(600, 600);
		this.setTitle("maze game");
		this.setLocationRelativeTo(null); 
	    int size = 101;
	    int n,j,i;
	  
	    JPanel content = new JPanel(new GridLayout(size,size));
	    grids=new JPanel[101][101];
	    System.out.println("anwar goal_x: "+ gx+ " goal_y: "+gy +" start_x: "+ sx +" start_y "+sy);
	    for (i = 0; i < size; ++i) {
	    for(j=0;j<size;j++)
	    {
	      grids[i][j] = new JPanel();
	      grids[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
	      if(i==sx&&j==sy)	      
	      {
	    	  grids[i][j].setBackground( Color.BLUE);
	 
	      }
	      else if(i==gx&&j==gy)
	      {
	    	  //System.out.println("anwar "+ i+ " "+j);
	    	  grids[i][j].setBackground( Color.RED);
	      }
	      else if(mat[i][j]==0)
	    	  grids[i][j].setBackground( Color.WHITE);
	      else if(mat[i][j]==1)
	    	  grids[i][j].setBackground(Color.BLACK);
	      
	      content.add(grids[i][j]);
	      }
	     }
	    int s = finalstates.size();
	    //System.out.println("size ="+ s);
	   
		for (n=0; n<=s-1; n++)
		{    if(!(grids[finalstates.get(n).x][finalstates.get(n).y].getBackground().equals(Color.BLUE)||
				grids[finalstates.get(n).x][finalstates.get(n).y].getBackground().equals(Color.RED)))
			grids[finalstates.get(n).x][finalstates.get(n).y].setBackground( Color.GREEN);
			
		}
	    JScrollPane sp=new JScrollPane(content);
	    this.add(sp);
		}
public void ShowNoPathPopup()
{
	JOptionPane.showInternalMessageDialog(this.getContentPane(), "No Path exist");
}
}