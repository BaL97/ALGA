package animation;

import java.util.ArrayList;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class mergesort {

static double speed = 400;

static int[] helper;

static ArrayList<Integer> CenterX = new ArrayList();
private static ArrayList<HBox> sorting=new ArrayList<HBox>();
int listindex = 0;

public static void start(ArrayList<Comparable> arr, ArrayList<StackPane> array, GridPane animation) throws Exception {
	array.clear();
	animation.getChildren().clear();
	ArrayList<InputItem> items=new ArrayList<InputItem>();
	for(int i=0;i<arr.size();i++){
		items.add(new InputItem(arr.get(i).toString(),i));
		array.add(items.get(i).getPane());
	}
	for(int i=0;i<array.size();i++)
	animation.setConstraints(array.get(i), i, 0);
	
	animation.getChildren().addAll(array);
	
	
    SequentialTransition sq = new SequentialTransition();
   // sq = MergeSort(arr,list,sq);
    
    //ogni volta che separa il vettore in due creare un hbox ad aggiungerla ad animation (cambiare animation in gridbox)
    //sq.play(); */
}

private static TranslateTransition AddtoOriginal(StackPane sp, double speed,int X){
    TranslateTransition t = new TranslateTransition();
    t.setNode(sp);
    t.setDuration(Duration.millis(speed));
    t.setToX(X);
    t.setToY(300);
    return t;
}

public static SequentialTransition MergeSort(ArrayList<Comparable> arr,ArrayList<StackPane> list,SequentialTransition sq) {
    int number = arr.size();
    helper = new int[number];
    mergesort(0, number - 1,arr,sq,list);
    return sq;
}

private static void mergesort(int low, int high,ArrayList<Comparable> arr,SequentialTransition sq,ArrayList<StackPane> list) {
    // check if low is smaller then high, if not then the array is sorted
    if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            // Sort the left side of the array
            mergesort(low, middle,arr,sq,list);
            // Sort the right side of the array
            mergesort(middle + 1, high,arr,sq,list);
            // Combine them both
            merge(low, middle, high,arr,list,sq);
    }

}


private static void merge(int low, int middle, int high,ArrayList<Comparable> arr,ArrayList<StackPane> list,SequentialTransition sq) {
// Copy both parts into the helper array
    for (int i = low; i <= high; i++) {
            helper[i] = Integer.parseInt(arr.get(i).toString());
    }
    int i = low;
    int j = middle + 1;
    int k = low;
    // Copy the smallest values from either the left or the right side back
    // to the original array

    while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                    arr.set(k, helper[i]);
                   sq.getChildren().add(AddtoOriginal(list.get(i),speed,CenterX.get(k)));
                    i++;
            } else {
                    arr.set(k, helper[j]);
                    sq.getChildren().add(AddtoOriginal(list.get(j),speed,CenterX.get(k)));
                    j++;
            }
            k++;
    }
    // Copy the rest of the left side of the array into the target array
    while (i <= middle) {
    		arr.set(k, helper[i]);
            sq.getChildren().add(AddtoOriginal(list.get(i),speed,CenterX.get(k)));
            k++;
            i++;
    }

    ParallelTransition pl = new ParallelTransition();
    ArrayList<TranslateTransition> Transitionlist = new ArrayList<>(high-low);

    for (int z = low; z <= high; z++) {
        TranslateTransition t = new TranslateTransition();
        t.setNode(list.get(z));
        t.setDuration(Duration.millis(speed));
        t.setByY(-300);
        Transitionlist.add(t);  
}
pl.getChildren().addAll(Transitionlist);
sq.getChildren().add(pl);

}
}