package util;

import java.util.Map;

public class Proba {

	public double probaNeu;
	public double probaNeg;
	public double probaPos;
	
	public Map<String,Triplet> probaWord;
	
	public Proba(double probaNeg, double probaNeu, double probaPos, Map<String,Triplet> probaword)
	{
		this.probaNeg=probaNeg;
		this.probaNeu=probaNeu;
		this.probaPos=probaPos;
		this.probaWord=probaword;
	}
}
