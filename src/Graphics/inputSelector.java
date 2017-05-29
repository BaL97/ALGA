package Graphics;
import java.io.BufferedReader;
import java.io.File;
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
 * Graphics interface for the selection of the input, Handling exceptions between input types
 * @author b_a_l
 *
 */
public class inputSelector{
	
	public static ChoiceBox<String> type=setTypes();
	private static ArrayList<Comparable> inputArray=new ArrayList<Comparable>();
	public static Boolean doubleflag;
	
	/**
	 * show the window in which select the input
	 * @return
	 * return an arraylist of comparable elemnts that contains the values to sort
	 */
	public static ArrayList<Comparable> display(){
		inputArray.clear();
		Main.doubleInput.clear();
		Main.integerInput.clear();
		//File loader
		FileChooser loader=new FileChooser();
		loader.setTitle("Select the input file");
		
		//Stage
		Stage window = new Stage();
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
		ok.setOnAction(e->{
			if((inputArray.isEmpty()))
				AlertBox.display("Input Error!", "The input is empty, please insert some data");
			else
				window.close();
		});
		Button cancel=new Button("Cancel");
		cancel.setOnAction(e->{
			inputArray.clear();
			window.close();
		});			
		Button clear=new Button("Clear");
		clear.setOnAction(e->{
			inputArray.clear();
			input.clear();
			input.appendText("Input preview:\n");
		});
		
		/**
		 * Button gen --> generate a random array of values
		 */
		Button gen=new Button("Generate >>");
		gen.setOnAction(e->{
			inputArray.clear();
			input.clear();
			input.appendText("Input preview:\n");
			
			try{
			if(n.getText().equals(""))
				AlertBox.display("Input Error!", "Please insert n");
			else if(type.getValue().equals("Select Input type..."))
				AlertBox.display("Input Error!", "Please select a type");
			else if(choice.getValue().equals("Select Input source..."))
				AlertBox.display("Input Error!", "Please select an input method");
			else{
				if(doubleflag)
					doubleRandom(Integer.parseInt(n.getText()));
				else
					integerRandom(Integer.parseInt(n.getText()));
				
				for(int i=0;i<Integer.parseInt(n.getText());i++){
					input.appendText(inputArray.get(i).toString()+"\n");}
				}}catch (Exception d) {AlertBox.display("Input Error!", "Wrong input for n");}
		});
		Button add=new Button("Add >>");
		add.setOnAction(e->{
		    StringTokenizer tokenizer = new StringTokenizer(data.getText());
		    while(tokenizer.hasMoreTokens())
		    {
			String datum = tokenizer.nextToken();
			if(manual(datum))
				input.appendText(datum +"\n");
			else
				AlertBox.display("Input Error!", "Please check your input");
		    }
		});
				
		//Choices actions
		choice.setOnAction(e->{
			switch (choice.getValue()){
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
				add.setDisable(false);
				n.setDisable(true);
				data.setDisable(false);
				gen.setDisable(true);
				break;
				}
		});		
		
		type.setOnAction(e->{
			if(type.getValue().equals("Double"))
				doubleflag=true;
			else if(type.getValue().equals("Integer"))
				doubleflag=false;
		});
		
		//setting layouts
		buttons.setConstraints(ok,1, 1);
		buttons.setConstraints(cancel, 2, 1);
		buttons.getChildren().addAll(ok,cancel);
		buttons.setPadding(new Insets(10,10,10,10));
		buttons.setHgap(10);
		buttons.setAlignment(Pos.CENTER);
		layout.setBottom(buttons);
		selection.setPadding(new Insets(10,10,10,10));
		selection.setHgap(10);
		selection.setVgap(10);
		selection.setConstraints(choice, 0, 0);
		selection.setConstraints(type, 0, 1);
		selection.setConstraints(label, 1, 1);
		selection.setConstraints(n, 2, 1);
		selection.setConstraints(data, 0, 3);
		selection.setConstraints(add, 1, 3);
		selection.setConstraints(clear, 3, 3);
		selection.setConstraints(gen, 3, 1);
		selection.getChildren().addAll(choice,label,type,n,data,add,gen,clear);
		layout.setTop(selection);
		layout.setCenter(input);
		layout.setPadding(new Insets(10,10,10,10));	
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return inputArray;	
	}

	/**
	 * input type choice
	 * @return
	 * Integer, Double
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
	 * choice the input methos
	 * @return
	 * input from file, random input, manual input
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
	 * insert a single value into the array
	 * @param value
	 * value to insert into the array
	 * @return
	 * return true if the value is added correctly, else false
	 */
	private static boolean manual(String value) {
		Double v;
		Integer k;
		
		if(doubleflag){
			if((inputArray.isEmpty())||(inputArray.get(0).getClass().toString().equals("class java.lang.Double"))){
			try {
				v=Double.parseDouble(value); //catch exceptions
			} catch (Exception e) {return false;}
			
			inputArray.add(v);
			return true;}
			return false;}
		
		else{
			if((inputArray.isEmpty())||(inputArray.get(0).getClass().toString().equals("class java.lang.Integer"))){
			try {
				k=Integer.parseInt(value);
			} catch (Exception e) {return false;}
			inputArray.add(k);
			return true;
		}
		return false;}
	}

	/**
	 * generates a random array of Integer elements
	 * @param n
	 * length of the array
	 */
	private static void integerRandom(Integer n) {
		inputArray.clear();
		Random random=new Random();
		Integer k;
		
		for (int i=0;i<n;i++){
			k=random.nextInt(1000);
			inputArray.add(k);}
	}
	
	/**
	 * generates a random array of Double elements
	 * @param n
	 * length of the array
	 */
	private static void doubleRandom(Integer n){
		inputArray.clear();
		Double k;
		Random random=new Random();
			for (int i=0;i<n;i++){
				k=random.nextDouble()*1000;
				inputArray.add(k);}
		}
	
	/**
	 * open the file and load the elements into the input array
	 * @param n
	 * file
	 * @return
	 * return true if the file is opened correctly, else false
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
			b.close();} catch (Exception e) {return false;}
		return flag;
	}
}


