
//TODO: figure out when/how to commit a new recipe being added
//TODO: refresh list view after new recipe is committed 

package cookbook;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Launcher extends Application
{

	public static Stage currentStage;
	private Scene main, findRecipeScene, addRecipeScene, listScene, recipeScene, manualAddScene;
	private Recipebook r;
	private ArrayList<Scene> sceneHistory = new ArrayList<>();
	MainMenu menu;
	FindRecipe findRecipe;
	AddRecipe addRecipe;
	ListPane listpane;
	RecipePane recipePane;
	ManualAdd manualAdd;

	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception
	{
		currentStage = primaryStage;
		r = new Recipebook();
		menu = new MainMenu(1280, 720);
		findRecipe = new FindRecipe(1280, 720);
		addRecipe = new AddRecipe(1280, 720);
		listpane = new ListPane(1280, 720);
		recipePane = new RecipePane(1280, 720, r.getRecipes().get(1));
		manualAdd = new ManualAdd(1280, 720);
		main = new Scene(menu, 1280, 720);
		findRecipeScene = new Scene(findRecipe, 1280, 720);
		addRecipeScene = new Scene(addRecipe, 1280, 720);
		listScene = new Scene(listpane, 1280, 720);
		recipeScene = new Scene(recipePane, 1280, 720);
		manualAddScene = new Scene(manualAdd, 1280, 720);

		currentStage.setTitle("Cookbook");
		currentStage.setScene(main);
		sceneHistory.add(main);
		currentStage.show();

	}

	private class AddRecipe extends Pane
	{
		private Button home = new Button("Home");
		private Button back = new Button("Back");
		private Button file = new Button("Import from a text file");
		private Button webpage = new Button("Import from a webpage");
		private Button manual = new Button("Enter details manually");

		public AddRecipe(int maxX, int maxY)
		{
			back.relocate(maxX / 2 - 640, maxY / 2 - 360);
			back.setMinHeight(120);
			back.setMinWidth(120);
			back.setOnAction(e -> lastState());

			home.relocate(maxX / 2 + 640 - 120, maxY / 2 - 360);
			home.setMinHeight(120);
			home.setMinWidth(120);
			home.setOnAction(e -> switchState(main));

			file.relocate(maxX / 2 - 130 - 125 - 260, maxY / 2 + 20 - 180);
			file.setMinHeight(360);
			file.setMinWidth(260);

			webpage.relocate(maxX / 2 - 130, maxY / 2 + 20 - 180);
			webpage.setMinHeight(360);
			webpage.setMinWidth(260);

			manual.relocate(maxX / 2 + 130 + 125, maxY / 2 + 20 - 180);
			manual.setMinHeight(360);
			manual.setMinWidth(260);
			manual.setOnAction(e -> switchState(manualAddScene));

			this.getChildren().addAll(home, back, file, webpage, manual);
		}
	}

	private class ManualAdd extends Pane
	{

		private Button home = new Button("Home");
		private Button back = new Button("Back");
		private Button addIng = new Button("Add");
		private Button addIns = new Button("Add");
		private Button next = new Button("Next");
		private Label recipeTitle, prepTime, cookTime, serves, ingredients, instructions;
		private TextField title, prep, cook, serve, ing, inst;
		private ArrayList<TextField> ingList, insList;

		public ManualAdd(int maxX, int maxY)
		{

			ingList = new ArrayList<>();
			insList = new ArrayList<>();

			title = new TextField();
			prep = new TextField();
			cook = new TextField();
			serve = new TextField();
			ing = new TextField();
			inst = new TextField();

			back.relocate(maxX / 2 - 640, maxY / 2 - 360);
			back.setMinHeight(120);
			back.setMinWidth(120);
			back.setOnAction(e -> lastState());

			home.relocate(maxX / 2 + 640 - 120, maxY / 2 - 360);
			home.setMinHeight(120);
			home.setMinWidth(120);
			home.setOnAction(e -> switchState(main));

			recipeTitle = new Label("Recipe Title");
			prepTime = new Label("Prep Time");
			cookTime = new Label("Cook Time");
			serves = new Label("Serves");
			ingredients = new Label("Ingredients");
			instructions = new Label("Instructions");

			recipeTitle.relocate(maxX / 2 - 175, maxY / 2 - 280);
			title.setMinSize(350, 30);
			title.relocate(maxX / 2 - 75, maxY / 2 - 285);

			prepTime.relocate(maxX / 2 - 420, maxY / 2 - 200);
			prep.setMinSize(100, 30);
			prep.relocate(maxX / 2 - 340, maxY / 2 - 205);

			cookTime.relocate(maxX / 2 - 80, maxY / 2 - 200);
			cook.setMinSize(100, 30);
			cook.relocate(maxX / 2, maxY / 2 - 205);

			serves.relocate(maxX / 2 + 190, maxY / 2 - 200);
			serve.setMinSize(50, 30);
			serve.relocate(maxX / 2 + 250, maxY / 2 - 205);

			ingredients.relocate(maxX / 2 - 600, maxY / 2 - 60);
			ing.setMinSize(350, 30);
			ing.relocate(maxX / 2 - 500, maxY / 2 - 65);
			ingList.add(ing);

			addIng.setMinSize(60, 30);
			addIng.relocate(maxX / 2 - 125, maxY / 2 - 65);
			addIng.setOnAction(e -> addIngBox(maxX, maxY));

			instructions.relocate(maxX / 2 + 30, maxY / 2 - 60);
			inst.setMinSize(350, 30);
			inst.relocate(maxX / 2 + 130, maxY / 2 - 65);
			insList.add(inst);

			addIns.setMinSize(60, 30);
			addIns.relocate(maxX / 2 + 505, maxY / 2 - 65);
			addIns.setOnAction(e -> addInsBox(maxX, maxY));

			next.relocate(maxX - 120, maxY - 120);
			next.setMinSize(120, 120);
			next.setOnAction(e -> {
				ArrayList<String> ingString = new ArrayList<>();
				for (int k = 0; k < ingList.size(); k++)
					ingString.add(ingList.get(k).getText());
				ArrayList<String> insString = new ArrayList<>();
				for (int k = 0; k < insList.size(); k++)
					insString.add(insList.get(k).getText());
				Recipe rec = new Recipe(title.getText(), prep.getText(), cook.getText(), serve.getText(), ingString,
						insString);
				r.getRecipes().add(rec);
				recipePane = new RecipePane(1280, 720, rec);
				recipeScene = new Scene(recipePane, 1280, 720);
				switchState(recipeScene);
			});

			this.getChildren().addAll(home, back, recipeTitle, prepTime, cookTime, serves, ingredients, instructions,
					title, prep, cook, serve, ing, inst, addIng, addIns, next);
		}

		void addIngBox(int maxX, int maxY)
		{
			TextField temp = new TextField();
			temp.setMinSize(350, 30);
			temp.relocate(maxX / 2 - 500, maxY / 2 - 60 + ingList.size() * 45);
			addIng.relocate(maxX / 2 - 125, maxY / 2 - 65 + ingList.size() * 45);
			ingList.add(temp);
			this.getChildren().add(temp);

		}

		void addInsBox(int maxX, int maxY)
		{
			TextField temp = new TextField();
			temp.setMinSize(350, 30);
			temp.relocate(maxX / 2 + 130, maxY / 2 - 60 + insList.size() * 45);
			addIns.relocate(maxX / 2 + 505, maxY / 2 - 65 + insList.size() * 45);
			insList.add(temp);
			this.getChildren().add(temp);
		}
	}

	private class FindRecipe extends Pane
	{

		private Button home = new Button("Home");
		private Button back = new Button("Back");

		public FindRecipe(int maxX, int maxY)
		{

			back.relocate(maxX / 2 - 640, maxY / 2 - 360);
			back.setMinHeight(120);
			back.setMinWidth(120);
			back.setOnAction(e -> lastState());

			home.relocate(maxX / 2 + 640 - 120, maxY / 2 - 360);
			home.setMinHeight(120);
			home.setMinWidth(120);
			home.setOnAction(e -> switchState(main));

			this.getChildren().addAll(back, home);
		}

		// TODO: write findrecipe algorithm and UI
	}

	private class MainMenu extends Pane
	{
		private TextField searchBar = new TextField("Enter search terms here");
		private Button listView = new Button("List View");
		private Button findRecipe = new Button("Find a Recipe");
		private Button addRecipe = new Button("Add a Recipe");
		private Button history = new Button("History/Favorites");
		private Button settings = new Button("export");

		public MainMenu(int maxX, int maxY)
		{

			searchBar.relocate(maxX / 2 - 320, maxY / 2 - 260);
			searchBar.setMinHeight(60);
			searchBar.setMinWidth(640);

			listView.relocate(maxX / 2 - 515, maxY / 2 - 100);
			listView.setMinHeight(150);
			listView.setMinWidth(450);
			// listView.setOnAction(e -> switchState(recipeScene));
			listView.setOnAction(e -> switchState(listScene));

			findRecipe.relocate(maxX / 2 + 65, maxY / 2 - 100);
			findRecipe.setMinHeight(150);
			findRecipe.setMinWidth(450);
			findRecipe.setOnAction(e -> switchState(findRecipeScene));

			addRecipe.relocate(maxX / 2 - 515, maxY / 2 + 110);
			addRecipe.setMinHeight(150);
			addRecipe.setMinWidth(450);
			addRecipe.setOnAction(e -> switchState(addRecipeScene));

			history.relocate(maxX / 2 + 65, maxY / 2 + 110);
			history.setMinHeight(150);
			history.setMinWidth(450);

			settings.relocate(maxX / 2 - 640, maxY / 2 - 360);
			settings.setMinHeight(120);
			settings.setMinWidth(120);
			settings.setOnAction(e -> export());

			this.getChildren().addAll(searchBar, listView, findRecipe, addRecipe, history, settings);
		}

	}

	private class RecipePane extends Pane
	{
		private Button home = new Button("Home");
		private Button back = new Button("Back");

		public RecipePane(int maxX, int maxY, Recipe recipe)
		{
			back.relocate(maxX / 2 - 640, maxY / 2 - 360);
			back.setMinHeight(120);
			back.setMinWidth(120);
			back.setOnAction(e -> lastState());

			home.relocate(maxX / 2 + 640 - 120, maxY / 2 - 360);
			home.setMinHeight(120);
			home.setMinWidth(120);
			home.setOnAction(e -> switchState(main));

			// declaring containers and labels

			BorderPane cardPane = new BorderPane(); // the overall layout pane of the recipe card
			ScrollPane ingredientsPane = new ScrollPane(); // the scrollable pane that shows ingredients
			ScrollPane instructionsPane = new ScrollPane(); // the scrollable pane that shows instructions
			GridPane titlePane = new GridPane(); // the topmost part of the card that includes recipe name and time needed
			HBox timeHbox = new HBox(); // layout that contains both times needed
			VBox ingredientVbox = new VBox(); // container that displays all ingredients in vertical order
			VBox instructionVbox = new VBox(); // container that displays all instructions in vertical order
			Label name = new Label(recipe.getRecipeName()); //
			Label serves = new Label("" + recipe.getServes()); //
			Label prep = new Label("Prep Time: " + recipe.getPrepTime() + " min"); // getting info from the recipe to use as labels
			Label cook = new Label("Cook Time: " + recipe.getCookTime() + " min"); //
			Label ingTitle = new Label("Ingredients"); // titles for the VBoxes
			Label insTitle = new Label("Instructions"); //

			// formatting Panes and Boxes

			cardPane.setMinSize(800, 480); // size of recipe card is 800, 480
			cardPane.setMaxSize(800, 480);
			cardPane.relocate(maxX / 2 - 400, maxY / 2 - 240); // relocate to just below center

			ingredientsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // hiding the horizontal scrollbar for both VBoxes
			instructionsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

			ingredientsPane.setMaxHeight(370); // max height determined by total size of recipe card, 480, -110 for the size of the title Pane
			ingredientsPane.setMaxWidth(200);
			// ingredientsPane.setStyle("-fx-border-color: gray");

			instructionsPane.setMaxHeight(370);
			instructionsPane.setMinWidth(600); // min width set to prevent smaller Panes if instructions are short
			// instructionsPane.setStyle("-fx-border-color: gray");

			titlePane.setMinSize(800, 110);
			titlePane.setMaxSize(800, 110);
			titlePane.setHgap(5);
			titlePane.setVgap(5);
			// titlePane.setStyle("-fx-background-color: #fefcfa; -fx-border-color: gray");
			titlePane.setStyle("-fx-border-color: gray");

			timeHbox.setPadding(new Insets(0, 285, 0, 300)); // setting the padding and spacing
			timeHbox.setSpacing(10);
			// timeHbox.setStyle("-fx-background-color: #FAFBED");

			ingredientVbox.setMinWidth(200);
			ingredientVbox.setMaxWidth(200);
			ingredientVbox.setMinHeight(370);
			ingredientVbox.setPadding(new Insets(0, 20, 5, 5));
			ingredientVbox.setSpacing(5);
			// ingredientVbox.setStyle("-fx-background-color: #F6EFEB");

			instructionVbox.setMinWidth(600);
			instructionVbox.setMaxWidth(600);
			instructionVbox.setPadding(new Insets(0, 20, 5, 5));
			instructionVbox.setSpacing(5);

			name.setFont(new Font("System", 48));
			// name.setStyle("-fx-text-fill: #33180d");
			ingTitle.setFont(new Font("System", 18)); // setting the font and size of the titles
			insTitle.setFont(new Font("System", 18));

			// populating containers

			timeHbox.getChildren().addAll(prep, cook);

			GridPane.setHalignment(name, HPos.CENTER);
			GridPane.setHalignment(timeHbox, HPos.CENTER);

			titlePane.add(name, 0, 1);
			titlePane.add(timeHbox, 0, 2);

			ingredientVbox.getChildren().add(ingTitle);
			instructionVbox.getChildren().add(insTitle);

			for (int k = 0; k < recipe.getIngredients().size(); k++)
			{
				Label temp = new Label("•" + recipe.getIngredients().get(k));
				temp.setWrapText(true);
				ingredientVbox.getChildren().add(temp);
			}

			for (int k = 0; k < recipe.getInstructions().size(); k++)
			{
				Label temp = new Label("•" + recipe.getInstructions().get(k));
				temp.setWrapText(true);
				instructionVbox.getChildren().add(temp);
			}

			ingredientsPane.setContent(ingredientVbox);
			ingredientsPane.setMinWidth(200);
			ingredientsPane.setStyle("-fx-border-color: gray");
			instructionsPane.setContent(instructionVbox);
			instructionsPane.setMinWidth(200);
			instructionsPane.setStyle("-fx-border-color: gray");

			cardPane.setTop(titlePane);
			cardPane.setLeft(ingredientsPane);
			cardPane.setCenter(instructionsPane);

			this.getChildren().addAll(back, home, cardPane);

		}

	}

	private class ListPane extends Pane
	{
		private Button home = new Button("Home");
		private Button back = new Button("Back");

		public ListPane(int maxX, int maxY)
		{

			back.relocate(maxX / 2 - 640, maxY / 2 - 360);
			back.setMinHeight(120);
			back.setMinWidth(120);
			back.setOnAction(e -> lastState());

			home.relocate(maxX / 2 + 640 - 120, maxY / 2 - 360);
			home.setMinHeight(120);
			home.setMinWidth(120);
			home.setOnAction(e -> switchState(main));

			DisplayPane displayPane = new DisplayPane();
			ScrollPane display = new ScrollPane();
			display.relocate(maxX / 2 - 400, maxY / 2 - 200);
			display.setMinWidth(800);
			display.setMaxHeight(480);
			display.setHbarPolicy(ScrollBarPolicy.NEVER);
			display.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			display.setContent(displayPane);
			this.getChildren().addAll(display, home, back);
		}

		private class DisplayPane extends Pane
		{

			private int titleCount = 0;
			private int currentRecipe = 0;
			private int[] letterArray = new int[26];

			public DisplayPane()
			{
				ArrayList<Pane> paneArray = new ArrayList<>();
				for (int k = 0; k < r.getRecipes().size(); k++)
				{
					letterArray[r.getRecipes().get(k).getRecipeName().toUpperCase().charAt(0) - 65]++;
				}
				for (int k = 0; k < 26; k++)
				{

					if (letterArray[k] > 0)
					{
						Pane p = new Pane();
						Label l = new Label("" + ((char) (k + 65)));
						p.relocate(0, titleCount * 64 + currentRecipe * 45);
						p.setMinHeight(64);
						p.setMinWidth(800);
						p.setStyle("-fx-background-color: #777777");

						l.relocate(0, titleCount * 64 + currentRecipe * 45);
						l.setFont(new Font("System", 48));

						titleCount++;
						this.getChildren().addAll(p, l);
						for (int j = 0; j < letterArray[k]; j++)
						{
							Pane p2 = new Pane();
							paneArray.add(p2);
							Label l2 = new Label(r.getRecipes().get(currentRecipe).getRecipeName());
							p2.relocate(45, titleCount * 64 + currentRecipe * 45);
							p2.setStyle("-fx-background-color: #FFFFFF");
							l2.relocate(45, titleCount * 64 + currentRecipe * 45);
							l2.setFont(new Font("System", 24));
							p2.setMinHeight(45);
							p2.setMinWidth(755);
							// System.out.println(r.getRecipes().get(currentRecipe));
							// p2.setOnMouseClicked(e -> {
							// int temp = 0;
							// for (int f = 0; f < paneArray.size(); f++)
							// {
							// if (p2.equals(paneArray.get(f)))
							// temp = f; // TO-DO: recursify this
							// }
							// System.out.println(temp);
							// recipePane = new RecipePane(1280, 720, r.getRecipes().get(temp));
							// recipeScene = new Scene(recipePane, 1280, 720);
							// switchState(recipeScene);
							// });
							this.getChildren().addAll(p2, l2);
							currentRecipe++;
						}
					}

				}
				for (int j = 0; j < paneArray.size(); j++)
				{
					int it = j;
					paneArray.get(it).setOnMouseClicked(e -> {
						int temp = 0;
						for (int f = 0; f < paneArray.size(); f++)
						{
							if (paneArray.get(it).equals(paneArray.get(f)))
								temp = f; // TO-DO: recursify this
						}
						recipePane = new RecipePane(1280, 720, r.getRecipes().get(temp));
						recipeScene = new Scene(recipePane, 1280, 720);
						switchState(recipeScene);
					});
				}
			}
		}
	}

	private void switchState(Scene s)
	{
		currentStage.setScene(s);
		sceneHistory.add(s);
	}

	private void lastState()
	{
		this.switchState(sceneHistory.get(sceneHistory.size() - 2));

		if (sceneHistory.size() > 2)
		{
			sceneHistory.remove(sceneHistory.size() - 2);
			sceneHistory.remove(sceneHistory.size() - 1);
		} else
			sceneHistory.remove(sceneHistory.size() - 1);
	}

	private void export()
	{
		FileWriter fw = null;
		try
		{
			fw = new FileWriter("resources/testexport.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(r.toString());
			bw.close();
			System.out.println("Recipes saved as testexport.txt in resources");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(r.toString());
	}

}
