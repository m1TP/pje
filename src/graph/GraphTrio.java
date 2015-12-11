package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GraphTrio extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1099162029276214436L;

	
	private int [] polarite;
	private int maxPolarite;
	
	public GraphTrio(int [] polarite){
		this.polarite=polarite;
		
		this.maxPolarite=polarite[0];
		for(int i=1;i<polarite.length;i++){
			this.maxPolarite=Math.max(this.maxPolarite, polarite[i]);
		}
		
	}
	
	
	public void paintComponent(Graphics g){
		Font font = new Font("Courier",Font.BOLD,12);
		g.setFont(font);
		
		for(int i=0;i<9;i++){
			
			g.setColor(Color.BLACK);
			g.drawString(""+polarite[i], 20 + ( i * 85 ), 24 + 100 - (polarite[i] * 100 / maxPolarite));
			
			if(i<3) g.setColor(new Color(55,143,237));
			else if(i<6) g.setColor(new Color(204,51,51));
			else g.setColor(new Color(94,214,169));
			g.fillRect( 10 + ( i * 85 ), 25 + 100 - (polarite[i] * 100 / maxPolarite), 70, polarite[i] * 100 / maxPolarite );
			
			switch(i){
				case 0:
					g.setColor(Color.BLACK);
					g.drawString("Positif",10 + ( i * 85 ), 135);
					break;
				case 1:
					g.setColor(Color.BLACK);
					g.drawString("Neutre",10 + ( i * 85 ), 135);
					g.drawString("Réel",30 + ( i * 85 ), 10);
					break;
				case 2:
					g.setColor(Color.BLACK);
					g.drawString("Negatif",10 + ( i * 85 ), 135);
					break;
				case 3:
					g.setColor(Color.BLACK);
					g.drawString("Positif",10 + ( i * 85 ), 135);
					break;
				case 4:
					g.setColor(Color.BLACK);
					g.drawString("Neutre",10 + ( i * 85 ), 135);
					g.drawString("KNN",30 + ( i * 85 ), 10);
					break;
				case 5:
					g.setColor(Color.BLACK);
					g.drawString("Negatif",10 + ( i * 85 ), 135);
					break;
				case 6:
					g.setColor(Color.BLACK);
					g.drawString("Positif",10 + ( i * 85 ), 135);
					break;
				case 7:
					g.setColor(Color.BLACK);
					g.drawString("Neutre",10 + ( i * 85 ), 135);
					g.drawString("Bayésien",20 + ( i * 85 ), 10);
					break;
				case 8:
					g.setColor(Color.BLACK);
					g.drawString("Negatif",10 + ( i * 85 ), 135);
					break;
			}
			
		}
		
		
	}
}
