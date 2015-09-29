package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import essai.RetrieveTweet;
import view.InterfacePrincipale;

public class ControlerRecherche implements ActionListener {


	public InterfacePrincipale view;
	
	public ControlerRecherche(InterfacePrincipale view){
		this.view = view;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {	
			if(!view.getMotCle().getText().equals("") && !view.getNbTweet().getText().equals(""))
				new RetrieveTweet().gogo(view.getMotCle().getText(),Integer.parseInt(view.getNbTweet().getText()));
		} catch (Exception e) {	e.printStackTrace();}
		
	}
}
