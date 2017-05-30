package Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Graphical interface for the input window 
 * @author b_a_l
 *
 */
public class inputSelector{
	
	public static ChoiceBox<String> type=setTypes();
	private static ArrayList<Number> inputArray=new ArrayList<Number>();
	private static ArrayList<Number> inputCopy = new ArrayList<Number>();
	public static Boolean doubleflag;
	private static Stage window;
	
	/**
	 * Displays the input selection window
	 * @return An ArrayList of Number that contains the values to sort
	 */
	public static ArrayList<Number> display(){
		
		//Stage
		window = new Stage();
		window.setTitle("Load Input");
		makeACopy(inputArray,inputCopy);
		inputArray.clear();
		//Main.doubleInput.clear();
		//Main.integerInput.clear();
		//File loader
		FileChooser loader=new FileChooser();
		loader.setTitle("Select the input file");
		
		//Stage
		window = new Stage();
		window.setTitle("Load Input");
		
		ChoiceBox<String> choice=setChoices();

		//Layouts
		GridPane selection=new GridPane();
		GridPane buttons=new GridPane();
		BorderPane layout=new BorderPane();
		
		//Texts
		TextField n=new TextField();
		n.setPrefWidth(60);
		TextArea input=new TextArea("Input preview:\n");
		input.setDisable(false);
		input.setEditable(false);
		input.setPrefWidth(50);
		input.setPrefHeight(100);
		Label label=new Label("Insert n");
		TextField data=new TextField("data");
		data.setPrefWidth(100);
		
		//BUTTONS
		Button ok=new Button("Ok");
		Button cancel=new Button("Cancel");
		Button clear=new Button("Clear");
		/**
		 * Button gen --> generate a random array of values
		 */
		Button gen=new Button("Generate >>");
		Button add=new Button("Add >>");
		setUpButtons(ok,cancel,clear,gen,add,input,n,data,loader, choice);
				
		
		//setting layouts
		GridPane.setConstraints(ok,1, 1);
		GridPane.setConstraints(cancel, 2, 1);
		buttons.getChildren().addAll(ok,cancel);
		buttons.setPadding(new Insets(10,10,10,10));
		buttons.setHgap(10);
		buttons.setAlignment(Pos.CENTER);
		layout.setBottom(buttons);
		selection.setPadding(new Insets(10,10,10,10));
		selection.setHgap(10);
		selection.setVgap(10);
		GridPane.setConstraints(choice, 0, 0);
		GridPane.setConstraints(type, 0, 1);
		GridPane.setConstraints(label, 1, 1);
		GridPane.setConstraints(n, 2, 1);
		GridPane.setConstraints(data, 0, 3);
		GridPane.setConstraints(add, 1, 3);
		GridPane.setConstraints(clear, 3, 3);
		GridPane.setConstraints(gen, 3, 1);
		selection.getChildren().addAll(choice,label,type,n,data,add,gen,clear);
		layout.setTop(selection);
		layout.setCenter(input);
		layout.setPadding(new Insets(10,10,10,10));	
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		if(inputArray.isEmpty())
		    makeACopy(inputCopy,inputArray);
		return inputArray;	
	}

	private static void makeACopy(ArrayList<Number> source, ArrayList<Number> dest)
	{
	    // TODO Auto-generated method stub
	    if(!source.isEmpty())
	    {
		dest.clear();
		for(Number n : source)
		    dest.add(n);
	    }
	    
	}

	private static void setUpButtons(Button ok, Button cancel, Button clear, Button gen, Button add, TextArea input, TextField n, TextField data, FileChooser loader, ChoiceBox<String> choice)
	{
		setUpOkAndCancelButtons(ok,cancel);
		setUpClearAndGenButtons(clear,gen,input,n);
		setUpAddButton(add,data,input);
		setUpChoice(choice,add,data,n,gen,input,loader);
		setUpType(type,add,data);
		gen.setDisable(true);
		n.setDisable(true);
		add.setDisable(true);
		data.setDisable(true);
	    
	}

	private static void setUpType(ChoiceBox<String> choice, Button add, TextField data)
	{
		type.setOnAction(e->{
		    if(choice.getValue() != "Select Input source...")
		    {
			if(type.getValue() != "Select Input type...")
			{
                            if(type.getValue().equals("Double"))
                                    doubleflag=true;
                            else if(type.getValue().equals("Integer"))
                                    doubleflag=false;
			}
			else
			    doubleflag = null;
		    }
		    else 
		    {
			if(type.getValue() != "Select Input type...")
			    doubleflag = type.getValue() == "Double" ? true : false;
			else
			    doubleflag = null;
			add.setDisable(true);
			data.setDisable(true);
		    }
		});
	    
	}

	private static void setUpChoice(ChoiceBox<String> choice, Button add, TextField data, TextField n, Button gen,
		TextArea input, FileChooser loader)
	{
	    // 
		//Choices actions
		choice.setOnAction(e->{
			switch (choice.getValue()){
			case "Select Input source...":
                            add.setDisable(true);
                            data.setDisable(true);
                            n.setDisable(true);
                            gen.setDisable(true);
			    break;
			case "Input from file...":
				add.setDisable(true);
				data.setDisable(true);
				n.setDisable(true);
				gen.setDisable(true);
				File file=loader.showOpenDialog(window);
				if(file!=null){
				if(fileOpen(file)){
					input.clear();
					input.appendText("Input preview:\n");
					for(int i=0;i<inputArray.size();i++)
						input.appendText(inputArray.get(i).toString()+"\n");}
				else
					AlertBox.display("Input file error!","Please check your input file and try again");}
				break;
			case "Random Input":
				add.setDisable(true);
				n.setDisable(false);
				data.setDisable(true);
				gen.setDisable(false);
				break;
			case "Manual Insert":
				n.setDisable(true);
				gen.setDisable(true);
				add.setDisable(false);
				data.setDisable(false);
				break;
				}
		});		
		
	}

	private static void setUpAddButton(Button add, TextField data, TextArea input)
	{
	    // 
		add.setOnAction(e->{
		    Boolean noerrors = true;
		    StringTokenizer tokenizer = new StringTokenizer(data.getText());
		    try
		    {
		    			input.clear();
                        while(tokenizer.hasMoreTokens() && noerrors)
                        {
                            String datum = tokenizer.nextToken();
                            if(manual(datum))
                        	input.appendText(datum + "\n");

                        }
		    }
		    catch (NumberFormatException nfe)
		    {
			if(doubleflag)
                            AlertBox.display("Input Error", "There is at least a non Double value in the input");
			else
                            AlertBox.display("Input Error", "There is at least a non Integer value in the input");
			noerrors = false;
		    }
		});

	    
	}

	private static void setUpClearAndGenButtons(Button clear, Button gen, TextArea input, TextField n)
	{
		clear.setOnAction(e->{
			inputArray.clear();
			input.clear();
			input.appendText("Input preview:\n");
		});

		gen.setOnAction(e->{
			input.clear();
			input.appendText("Input preview:\n");
			try{

                        if(n.getText().equals(""))
                            AlertBox.display("Input Error!", "Please insert n");
                        else if(n.getText().matches("[^0-9]+$"))
                            AlertBox.display("Input Error!", "n must be an integer (no spaces)");
                        else if(type.getValue().equals("Select Input type..."))
                                AlertBox.display("Input Error!", "Please select a type");
                        else{
                                if(doubleflag)
                                        doubleRandom(Integer.parseInt(n.getText()));
                                else
                                        integerRandom(Integer.parseInt(n.getText()));
                                
                                for(int i=0;i<Integer.parseInt(n.getText());i++){
                                        input.appendText(inputArray.get(i).toString()+"\n");}
                                }
			}
			catch (NumberFormatException nfe) 
			{AlertBox.display("Input Error!", "Input for n is not an Integer");}
		});
	    
	}

	private static void setUpOkAndCancelButtons(Button ok, Button cancel)
	{
            ok.setOnAction(e->{
                    if((inputArray.isEmpty()))
                            AlertBox.display("Input Error!", "The input is empty, please insert some data");
                    else
                    {
							Main.integerInput.clear();
							Main.doubleInput.clear();
                            window.close();
                    }
            });
            cancel.setOnAction(e->{
                    window.close();
            });			
	}

	/**
	 * Sets up the choice box for the type selection
	 * @return A ChoiceBox that lets the user select between possible input types
	 * (Integer and Double)
	 */
	private static ChoiceBox<String> setTypes() {
		ChoiceBox<String> type=new ChoiceBox<String>();
		type.getItems().add("Integer");
		type.getItems().add("Double");
		type.getItems().add("Select Input type...");
		type.setValue("Select Input type...");
		return type;
	}

	/**
	 * Sets up the choice box for the input source selection
	 * @return A ChoiceBox that lets the user select between possible input sources
	 * (Random, Manual and from File)
	 */
	private static ChoiceBox<String> setChoices() {
		ChoiceBox<String> choice=new ChoiceBox<String>();
		choice.getItems().add("Input from file...");
		choice.getItems().add("Random Input");
		choice.getItems().add("Manual Insert");
		choice.getItems().add("Select Input source...");
		choice.setValue("Select Input source...");
		return choice;
	}

	/**
	 * Given a numeric value, in the form of a String, it adds it to the inputArray
	 * @param input 
	 * @param value The value to insert into the array
	 * @return true if the value is added correctly, false otherwise
	 */
	private static boolean manual(String value) throws NumberFormatException {
		Double v;
		Integer k;
		if(doubleflag == null)
		    AlertBox.display("Input Error", "Please set the input type");
		else
		{
                    if(doubleflag)
                    {
                            if((inputArray.isEmpty())||(inputArray.get(0).getClass().toString().equals("class java.lang.Double")))
                            {
                                try {
                                        v=Double.parseDouble(value); //catch exceptions
                                } catch (NullPointerException e) {return false;}
                                catch (NumberFormatException nfe)
                                {
                                    throw nfe;
                                }

                                inputArray.add(v);
                                return true;
                            }
                            else
                        	AlertBox.display("Input Error", "You selected Integer for the input type, you can't change it without clearing the input");
                     }
                    else
                    {
                            if((inputArray.isEmpty())||(inputArray.get(0).getClass().toString().equals("class java.lang.Integer"))){
                            try {
                                    k=Integer.parseInt(value);
                            } catch (NullPointerException e) {return false;}
                            catch (NumberFormatException nfe)
                            {
                                throw nfe;
                            }
                            inputArray.add(k);
                            return true;
                            }
                            else
                        	AlertBox.display("Input Error", "You selected Double for the input type, you can't change it without clearing the input");
                    }
		}
		return false;
	}

	/**
	 * Generates n random Integer elements between 0 and 999 and adds them to the inputArray
	 * @param n The quantity of numbers to generate
	 */
	private static void integerRandom(Integer n) {
		inputArray.clear();
		Random random=new Random();
		Integer k;
		
		for (int i=0;i<n;i++){
			k=random.nextInt(n);
			inputArray.add(k);}
	}
	
	/**
	 * Generates n random Double elements between 0 and 1000 and adds them to the inputArray
	 * @param n The quantity of numbers to generate
	 */
	private static void doubleRandom(Integer n){
		inputArray.clear();
		Double k;
		Random random=new Random();
			for (int i=0;i<n;i++){
				k=random.nextDouble()*n;
				inputArray.add(k);}
		}
	
	/**
	 * Open the input file and loads the elements into the inputArray
	 * @param n The file to be read
	 * @return true if the file is opened and read correctly, false otherwise
	 */
	private static boolean fileOpen(File n){
		if(n.exists())
		inputArray.clear();
		boolean flag=false;
		try{
			FileReader f;
			f=new FileReader(n);
			BufferedReader b;
			b=new BufferedReader(f);
			String t;
			StringTokenizer tokenizer = new StringTokenizer(b.readLine());
			t = tokenizer.nextToken();
			if((t.equals("Double"))||(t.equals("Integer"))){ 
				if(t.equals("Double"))
					doubleflag=true;
				else
					doubleflag=false;
				flag=true;
				while (tokenizer.hasMoreTokens()){
					if(!manual(tokenizer.nextToken()))
						flag=false;
				}
			}
			b.close();} 
		catch(FileNotFoundException fnf)
		{ 
		    AlertBox.display("Error", "File was not found");
		    return false;
		}
		catch (Exception e) {return false;}
		return flag;
	}
}


