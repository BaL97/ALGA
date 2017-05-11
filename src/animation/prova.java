package animation;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class prova extends Application {
double speed = 1000;

int[] helper;

final ArrayList<Integer> CenterX = new ArrayList();
int listindex = 0;
@Override
public void start(Stage primaryStage) throws Exception {

    Pane pane = new Pane();
    ArrayList<StackPane> list = new ArrayList<>();
    Random random = new Random(5);
    for (int i = 0; i < 13; i++) {
        int num = random.nextInt(10);
        Rectangle rectangle = new Rectangle(40, (num * 10) + 50);
        rectangle.setFill(Color.valueOf("#FF7F50"));
        Text text = new Text(String.valueOf(num));
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
        stackPane.setId(String.valueOf(num));
        stackPane.getChildren().addAll(rectangle, text);
        StackPane.setAlignment(text,Pos.TOP_CENTER);
        stackPane.setAlignment(Pos.TOP_CENTER);
        stackPane.setTranslateX(60*i);
        list.add(stackPane);
    }


    pane.getChildren().addAll(list);

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(pane);
    HBox hBox1 = new HBox();
    Button b = new Button("Sort");


    AnchorPane bottomPane = new AnchorPane();
    hBox1.getChildren().add(b);
    bottomPane.getChildren().add(hBox1);     
    borderPane.setBottom(bottomPane);


    b.setOnAction(event -> {
        SequentialTransition sq = new SequentialTransition();
        int[] arr;
        arr = generateArray(list);
        sq = MergeSort(arr, list,sq);
        b.setDisable(true);
        sq.play();
    });

    Scene scene = new Scene(borderPane,800, 800);
    primaryStage.setTitle("Sorting");
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();

    for (int i = 0; i < 13; i++) {
        int centerx = (int) list.get(i).getLayoutX();
        CenterX.add(i,centerx+60*i);
        System.out.println(centerx+60*i);

    }
}



private int[] generateArray(ArrayList<StackPane> list) {
    int arr[] = new int[list.size()];
    for (int i = 0; i < arr.length; i++) {
        arr[i] = Integer.parseInt(list.get(i).getId());
    }
    return arr;
}

private TranslateTransition AddtoOriginal(StackPane sp, double speed,int X){
    TranslateTransition t = new TranslateTransition();
    t.setNode(sp);
    t.setDuration(Duration.millis(speed));
    t.setToX(X);
    t.setToY(300);
    return t;

}

public SequentialTransition MergeSort(int arr[],ArrayList<StackPane> list,SequentialTransition sq) {
    int number = arr.length;
    this.helper = new int[number];
    mergesort(0, number - 1,arr,sq,list);
    return sq;
}

private void mergesort(int low, int high,int arr[],SequentialTransition sq,ArrayList<StackPane> list) {
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


private void merge(int low, int middle, int high,int arr[],ArrayList<StackPane> list,SequentialTransition sq) {
// Copy both parts into the helper array
    for (int i = low; i <= high; i++) {
            helper[i] = arr[i];

    }
    int i = low;
    int j = middle + 1;
    int k = low;
    // Copy the smallest values from either the left or the right side back
    // to the original array

    while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                    arr[k] = helper[i];
                    sq.getChildren().add(AddtoOriginal(list.get(i),speed,CenterX.get(k)));
                    i++;
            } else {
                    arr[k] = helper[j];
                    sq.getChildren().add(AddtoOriginal(list.get(j),speed,CenterX.get(k)));
                    j++;
            }
            k++;
    }
    // Copy the rest of the left side of the array into the target array
    while (i <= middle) {
            arr[k] = helper[i];
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

public static void main(String[] args) {
    launch(args);
}
}
