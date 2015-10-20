package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import essai.ControllerCSV;
import model.TweetSkeleton;
import view.InterfaceRecherche;

public class ControlerSaveManuel implements ActionListener {

	private Object [][] data;
	private InterfaceRecherche frame;
	private List<TweetSkeleton> listTweets;
	
	public ControlerSaveManuel(List<TweetSkeleton> listTweets,Object [][] data, InterfaceRecherche frame){
		this.listTweets=listTweets;
		this.data=data;
		this.frame=frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Save tweets
		
		// Modifie la liste de TweetSkeleton contenant les tweets en fonction de l'annotation souhait�
		for(int i=0;i<data.length;i++){
			if(data[i][2]=="Positif")
				listTweets.get(i).setAnnotation(4);
			else if(data[i][2]=="Neutre")
				listTweets.get(i).setAnnotation(2);
			else if(data[i][2]=="Negatif")
				listTweets.get(i).setAnnotation(0);	
			else
				listTweets.get(i).setAnnotation(-1);
			System.out.println("yolooooooooooo");
		}
		
		//Save sur le fichier CSV
		if(data.length>0)
		{
			ControllerCSV csv = new ControllerCSV(new File( listTweets.get(0).getQuery().toLowerCase()+".csv"));
			csv.doAll(listTweets);
		}
		//And close frame
		frame.dispose();
	}

}
