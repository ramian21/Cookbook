package cookbook;

import java.util.ArrayList;
//import java.util.Arrays;

public class Recipe
{
	private String recipeName;
	private int serves;
	private int prepTime;
	private int cookTime;
	private int totalTime;
	private ArrayList<String> ingredients;
	private ArrayList<String> instructions;

	public Recipe(String rName, String pTime, String cTime, String ser, ArrayList<String> ing, ArrayList<String> inst)
	{
		recipeName = rName;
		serves = Integer.parseInt(ser);
		prepTime = Integer.parseInt(pTime);
		cookTime = Integer.parseInt(cTime);
		totalTime = prepTime + cookTime;
		ingredients = ing;
		instructions = inst;
	}

	public String getRecipeName()
	{
		return recipeName;
	}

	public void setRecipeName(String recipeName)
	{
		this.recipeName = recipeName;
	}

	public int getServes()
	{
		return serves;
	}

	public void setServes(int serves)
	{
		this.serves = serves;
	}

	public int getPrepTime()
	{
		return prepTime;
	}

	public void setPrepTime(int prepTime)
	{
		this.prepTime = prepTime;
	}

	public int getCookTime()
	{
		return cookTime;
	}

	public void setCookTime(int cookTime)
	{
		this.cookTime = cookTime;
	}

	public int getTotalTime()
	{
		return totalTime;
	}

	public void setTotalTime(int totalTime)
	{
		this.totalTime = totalTime;
	}

	public ArrayList<String> getIngredients()
	{
		return ingredients;
	}

	public void setIngredients(ArrayList<String> ingredients)
	{
		this.ingredients = ingredients;
	}

	public ArrayList<String> getInstructions()
	{
		return instructions;
	}

	public void setInstructions(ArrayList<String> instructions)
	{
		this.instructions = instructions;
	}

	public String toString()
	{
		String total = "";
		total += recipeName;
		total += ", serves " + serves;
		total += "\r\n\r\nprep time: " + prepTime;
		total += ", cook time: " + cookTime;
		total += ", total time: " + totalTime;
		total += "\r\n\r\nIngredients: \r\n";
		for (int k = 0; k < ingredients.size(); k++)
		{
			total += ingredients.get(k) + "\r\n";
		}
		total += "\r\nInstructions: \r\n";
		for (int k = 0; k < instructions.size(); k++)
		{
			total += instructions.get(k) + "\r\n";
		}
		total += "\r\n\r\n---\r\n\r\n";
		return total;
	}

}
