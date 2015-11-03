package util;

public class Triplet {

	public float neg;
	public float neu;
	public float pos;
	
	public Triplet(float neg, float neu, float pos)
	{
		this.neg=neg;
		this.neu=neu;
		this.pos=pos;
	}
	
	public String toString()
	{
		return neg+" "+neu+" "+pos;
	}
	
}
