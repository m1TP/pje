package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Graph extends JPanel{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8040133771873183620L;
	
	private int [] polarite;
	private int maxPolarite;
	private String nomCSV;
	
	public Graph(int [] polarite, String nomCSV){
		this.polarite=polarite;
		this.nomCSV=nomCSV;
		
		this.maxPolarite=polarite[0];
		for(int i=1;i<polarite.length;i++){
			this.maxPolarite=Math.max(this.maxPolarite, polarite[i]);
		}
		
	}
	
	
	public void paintComponent(Graphics g){
		Font font = new Font("Courier",Font.BOLD,12);
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString(" Annotation pour le csv : "+ nomCSV, 10, 10);
		
		for(int i=0;i<6;i++){
			
			g.setColor(Color.BLACK);
			g.drawString(""+polarite[i], 20 + ( i * 85 ), 24 + 100 - (polarite[i] * 100 / maxPolarite));
			
			if(i<3) g.setColor(new Color(55,143,237));
			else g.setColor(new Color(94,214,169));
			g.fillRect( 20 + ( i * 85 ), 25 + 100 - (polarite[i] * 100 / maxPolarite), 80, polarite[i] * 100 / maxPolarite );
			
			switch(i){
				case 0:
					g.setColor(Color.BLACK);
					g.drawString("Positif",20 + ( i * 85 ), 135);
					break;
				case 1:
					g.setColor(Color.BLACK);
					g.drawString("Neutre",20 + ( i * 85 ), 135);
					break;
				case 2:
					g.setColor(Color.BLACK);
					g.drawString("Negatif",20 + ( i * 85 ), 135);
					break;
				case 3:
					g.setColor(Color.BLACK);
					g.drawString("Positif KNN",20 + ( i * 85 ), 135);
					break;
				case 4:
					g.setColor(Color.BLACK);
					g.drawString("Neutre KNN",20 + ( i * 85 ), 135);
					break;
				case 5:
					g.setColor(Color.BLACK);
					g.drawString("Negatif KNN",20 + ( i * 85 ), 135);
					break;
			}
			
		}
	}

}
