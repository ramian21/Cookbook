package cookbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Recipebook
{
	public ArrayList<Recipe> recipes = new ArrayList<Recipe>();

	public Recipebook()
	{
		try
		{
			readRecipe();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Recipe> getRecipes()
	{
		return recipes;
	}

	public void readRecipe() throws FileNotFoundException
	{
		File test = new File("resources/test.txt");
		Scanner s = new Scanner(test);

		String recipeName, prepTime = null, cookTime = null, serves;
		ArrayList<String> ingredients;
		ArrayList<String> instructions;
		String currentLine;
		while (s.hasNextLine())
		{
			ingredients = new ArrayList<>();
			instructions = new ArrayList<>();
			currentLine = s.nextLine();
			while (currentLine.equals(""))
				currentLine = s.nextLine();
			// System.out.println(currentLine);
			recipeName = currentLine.substring(0, currentLine.indexOf("serves") - 2).trim();
			serves = currentLine.substring(currentLine.indexOf("serves") + 6).trim();
			currentLine = s.nextLine();
			currentLine = s.nextLine();

			prepTime = currentLine.substring(11, currentLine.indexOf("cook time: ")).replaceAll("[^0-9]", "");
			cookTime = currentLine.substring(currentLine.indexOf("cook time: ")).replaceAll("[^0-9]", "");
			currentLine = s.nextLine();

			currentLine = s.nextLine();

			while (!currentLine.startsWith("Ingredients"))
			{
				currentLine = s.nextLine();
			}
			currentLine = s.nextLine();

			while (!currentLine.startsWith("Instructions"))
			{
				if (!currentLine.equals(""))
					ingredients.add(currentLine);
				currentLine = s.nextLine();

			}
			currentLine = s.nextLine();

			while (!currentLine.equals(""))
			{
				instructions.add(currentLine);
				currentLine = s.nextLine();

			}
			if (s.hasNextLine())
				currentLine = s.nextLine();
			if (s.hasNextLine())
				currentLine = s.nextLine();
			Recipe first = new Recipe(recipeName, prepTime, cookTime, serves, ingredients, instructions);
			recipes.add(first);
			// System.out.println(toString1());
		}
		// System.out.println(toString1());
		recipes.sort((o1, o2) -> o1.toString().compareTo(o2.toString()));
		// System.out.println(recipes.toString());
		toString();
		s.close();
	}

	public String toString()
	{
		String total = "";
		for (int k = 0; k < recipes.size(); k++)
		{
			total += recipes.get(k).toString();
		}
		return total;

	}

}
