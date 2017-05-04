/*Da Fare:
 * Bottone clear
 * Input da file
 * Gestione di tutte le eccezioni
 */

package Graphics;
import visualizer.*;

import java.awt.Choice;
import java.util.ArrayList;
import java.util.Random;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class inputSelector {
	
	private static ArrayList<Comparable> inputArray=new ArrayList<Comparable>();
	
	public static Integer display(){
		Stage window = new Stage();
		window.setTitle("Load Input");
		ChoiceBox<String> choice=new ChoiceBox<>();
		ChoiceBox<String> type=new ChoiceBox<>();
		
		
		GridPane buttons=new GridPane();
		BorderPane layout=new BorderPane();
		
		TextField n=new TextField();
		//Ok-Cancel Buttons
		Button ok=new Button("Ok");
		Button cancel=new Button("Cancel");
		Button gen=new Button("Generate >>");
		

		
		ok.setOnAction(e->loadInput(window,choice.getValue(), type.getValue()));
		cancel.setOnAction(e->window.close());	
		
		//setting layouts
		buttons.setConstraints(ok,1, 1);
		buttons.setConstraints(cancel, 2, 1);
		buttons.getChildren().addAll(ok,cancel);
		buttons.setPadding(new Insets(10,10,10,10));
		buttons.setHgap(10);
		buttons.setAlignment(Pos.CENTER);
		layout.setBottom(buttons);
		
		GridPane selection=new GridPane();
		
		TextArea input=new TextArea("Input preview:\n");
		//insert n
		Label label=new Label("Insert n");
		n.setPrefWidth(40);

		//Input Data manual(type.getValue(),data.getText())&&
		TextField data=new TextField("data");
		data.setPrefWidth(100);
		Button add=new Button("Add >>");
		add.setOnAction(e->{
			if(manual(type.getValue(),data.getText())&&(choice.getValue().equals("Manual Insert"))&&!(type.getValue().equals("Select Input type..."))&&!(data.getText().equals("data")))
				input.appendText(data.getText()+"\n");
			else
				AlertBox.display("Input Error!", "Please check your input");
		});
				
		//choicebox: set the input choice --> select 1-load file, 2-casual input (choice the type and the lenght), 3-Manual Insert
		choice.getItems().add("Input from file...");
		choice.getItems().add("Random Input");
		choice.getItems().add("Manual Insert");
		choice.getItems().add("Select Input source...");
		choice.setValue("Select Input source...");
		choice.setOnAction(e->{
			switch (choice.getValue()){
			case "Input from file...":
				/*choice 1 --> *open the file loader window
				 *when the file is added control the input in runtime and throw the exception if is not right
				 *if the input file is correct close the window with success message and at ok button action 
				 *load the input into the algorithm
				 */
				add.setDisable(true);
				data.setDisable(true);
				n.setDisable(true);
				gen.setDisable(true);
				break;
			case "Random Input":
				/*choice 2 --> 
				*select type and the n
				*autogenerate the input
				*at the ok load input into the algorithm
				*/
				add.setDisable(true);
				n.setDisable(false);
				data.setDisable(true);
				gen.setDisable(false);
				break;
			case "Manual Insert":
				/*choice 3 --> 
				*select type
				*insert each data in the text field selecting adding 
				*control the input data each time and increase the n value
				* at ok action close the window and load input into the algorithm
				*/
				add.setDisable(false);
				n.setDisable(true);
				data.setDisable(false);
				gen.setDisable(true);
				break;
				}
		});
		layout.setPadding(new Insets(10,10,10,10));	
		
		//Comparable type selection
		type.getItems().add("Integer");
		type.getItems().add("String");
		type.getItems().add("Double");
		type.getItems().add("Select Input type...");
		type.setValue("Select Input type...");
		
		//Input flow
		input.setDisable(false);
		input.setPrefWidth(50);
		input.setPrefHeight(100);
		
		gen.setOnAction(e->{
			input.clear();
			input.appendText("Input preview:\n");
			if(n.getText().equals(""))
				AlertBox.display("Input Error!", "Please insert n");
			else if(type.getValue().equals("Select Input type..."))
				AlertBox.display("Input Error!", "Please select a type");
			else{
				random(Integer.parseInt(n.getText()),type.getValue());
				for(int i=0;i<Integer.parseInt(n.getText());i++)
					input.appendText(inputArray.get(i).toString()+"\n");}
		});
		
		//layout
		selection.setPadding(new Insets(10,10,10,10));
		selection.setHgap(10);
		selection.setVgap(10);
		selection.setConstraints(choice, 0, 0);
		selection.setConstraints(type, 0, 1);
		selection.setConstraints(label, 1, 1);
		selection.setConstraints(n, 2, 1);
		selection.setConstraints(data, 0, 3);
		selection.setConstraints(add, 1, 3);
		selection.setConstraints(gen, 3, 1);
		selection.getChildren().addAll(choice,label,type,n,data,add,gen);
		layout.setTop(selection);
		layout.setCenter(input);
		Scene scene=new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	
		
		return(1);
	}

	private static ArrayList<Comparable> loadInput(Stage window, String source, String type) {
		if(inputArray.isEmpty())
			AlertBox.display("Input Error!", "The input is empty, please insert some data");
		return inputArray;
	}

	private static boolean manual(String type, String value) {
		Comparable v=value;
		try {
			if(type.equals("Integer"))
				v=Integer.parseInt(value); //catch exceptions
			else if(type.equals("Double"))
				v=Double.parseDouble(value); //catch exceptions
		} catch (Exception e) {return false;}
		
		if((inputArray.isEmpty())){
			inputArray.add(v);
			return true;}
		else if ((inputArray.get(0).getClass().getName().equals(v.getClass().getName()))){
			inputArray.add(v);
			return true;}
		return false;
	}

	private static void random(Integer n, String type) {
		inputArray.clear();
		Random random=new Random();
		Comparable k;
		
		if(type.equals("Integer")){
		for (int i=0;i<n;i++){
			System.out.println("ok");
			k=random.nextInt(1000);
			inputArray.add(k);}}
		else if(type.equals("Double")){
			for (int i=0;i<n;i++){
				k=random.nextDouble()*1000;
				inputArray.add(k);}
		}
		else {
			for (int i=0;i<n;i++){
				k=randomString(10);
				inputArray.add(k);}}
	}
	
	public static String randomString(int length){
		Random rand = new Random();
		StringBuffer tempStr = new StringBuffer();
		tempStr.append("");
		for (int i = 0; i < length; i++) {
		int c = rand.nextInt(122 - 48) + 48;
		if((c >= 58 && c <= 64) || (c >= 91 && c <= 96)){
		i--;
		continue;
		}
		tempStr.append((char)c);

		}
		return tempStr.toString();
		}
}
