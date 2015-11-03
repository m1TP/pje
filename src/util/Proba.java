package util;

import java.util.Map;

public class Proba {

	public float probaNeu;
	public float probaNeg;
	public float probaPos;
	
	public Map<String,Triplet> probaWord;
	
	public Proba(float probaNeg, float probaNeu, float probaPos, Map<String,Triplet> probaword)
	{
		this.probaNeg=probaNeg;
		this.probaNeu=probaNeu;
		this.probaPos=probaPos;
		this.probaWord=probaword;
	}
}
