package methode;

import java.util.List;

import model.TweetSkeleton;

public class KNN {
	
	/**
	 * Compute the distance between 2 string as (length of string1 + length of string2 - sharedWord)/(length of string1 + length of string2)
	 * @param ts1
	 * @param ts2
	 * @return
	 */
	public static int distance1 (TweetSkeleton ts1, TweetSkeleton ts2)
	{
		String[] words1 = ts1.getText().split(" ");
		String[] words2 = ts2.getText().split(" ");
		int motsCommuns = 0;
		
		for (String s1 : words1) 
		{
			for (String s2 : words2)
			{
				if(!s1.equals("@") && !s1.equals("RT") && !s1.equals("URL") && !s1.equals(".") && !s1.equals("#") && !s1.equals(",")
						&&	!s1.equals("?") &&	!s1.equals(":") &&	!s1.equals("!") &&	!s1.equals(" ")  &&	!s1.equals("	") &&	!s1.equals(")") &&	!s1.equals("")    
						)
					if(s1.equals(s2)){
						motsCommuns+=1;
					}
			}
		}
		
		return (words1.length + words2.length - (motsCommuns*2))/ (words1.length + words2.length); 
	}
	
	public static void knn_annotation(TweetSkeleton ts, int neighbours, List<TweetSkeleton> learningDB)
	{
		int[] nearest_neighbour = new int[neighbours];
		int[] nearest_neighbour_distance = new int[neighbours];
		int max_distance = -1;
		int indice_max_distance = -1;
		
		for(int i=0;i<neighbours;i++)
		{
			nearest_neighbour[i] = i;
			nearest_neighbour_distance[i] = KNN.distance1(ts, learningDB.get(i));
			
			if(nearest_neighbour_distance[i]>max_distance)
			{
				max_distance = nearest_neighbour_distance[i];
				indice_max_distance = i;
			}
		}
		
		for(int i=neighbours;i<learningDB.size();i++)
		{
			int d = KNN.distance1(ts, learningDB.get(i));
			
			if(d<max_distance)
			{
				nearest_neighbour[indice_max_distance] = i;
				nearest_neighbour_distance[indice_max_distance] = d;
				indice_max_distance = search_indice_value_max(nearest_neighbour_distance);
				max_distance = nearest_neighbour_distance[indice_max_distance];
				
			//	System.out.println("CHANGEMENT !!!!!!!!");
			}
		}
	//	System.out.println("Pour le tweet :"+ts.getText());
		ts.setAnnotation(vote(nearest_neighbour,learningDB));
		
	}
	
	/**
	 * Vote for the annotation to give to a tweet, given his nearest neighbours
	 * @param nearest_neighbour 
	 * @return An annotation based on nearest_neighbour
	 * 
	 * If there is a tie, we return a neutral annotation
	 */
	private static int vote(int[] nearest_neighbour, List<TweetSkeleton> learningDB) {
		
		@SuppressWarnings("unused")
		int no_annotation = 0;
		int negatif = 0;
		int neutral = 0;
		int positif = 0;
		
		int tmp = -2;
		for(int i=0;i<nearest_neighbour.length;i++)
		{
			tmp = learningDB.get(nearest_neighbour[i]).getAnnotation();
			String tmpText = learningDB.get(nearest_neighbour[i]).getText()+" "+learningDB.get(nearest_neighbour[i]).getId();
			System.out.println("Annotation : "+tmp+" avec le tweet : "+tmpText);
			if (tmp==-1)
				no_annotation+=1;
			else if (tmp == 0)
				negatif +=1;
			else if (tmp == 1)
				neutral +=1;
			else if (tmp == 2)
				positif +=1;
			else
				System.out.println("PROBLEM ANNOTATION NON CONFORM");
			
			
		}
		System.out.println("\n");
		
		if (negatif>Math.max(neutral,positif))
			return 0;
		if (neutral>Math.max(negatif, positif))
			return 1;
		if (positif>Math.max(neutral, negatif))
			return 2;
		
		return 1;
		
		
	}
	
	

	/**
	 * Search the index of the max element in the specified array 
	 * @param tab  An array containing integer
	 * @return The index of the maximum value found in the tab, -1 if the tab is null
	 */
	private static int search_indice_value_max(int[] tab) {
		if (tab==null)
			return -1;
		int max = 0;
		for(int i=1;i<tab.length;i++)
		{
			if (tab[i]>tab[max])
				max = i;
		}
		return max;
	}
	
}
