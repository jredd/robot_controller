package robot_controller;

import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.animation.FadeTransition.*;
import javafx.util.Duration;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class Controller_fx implements Initializable {

    @FXML
    private Button btn_speed_slow;
    @FXML
    private Slider slider_arm;
    @FXML
    private ImageView indicator_left;
    @FXML
    private ImageView indicator_right;
    @FXML
    private ImageView indicator_forwards;
    @FXML
    private ImageView indicator_backwards;
    @FXML
    private Line line_arm;
    @FXML
    private Line line_claw_top;
    @FXML
    private Line line_claw_bottom;

    private boolean show_video = false;
    private boolean reverse_velocity = true;
    private boolean continue_turning = false;
    private boolean continue_moving = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing Application and setting things");

        slider_arm.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("changed - "+(newValue));
            }
        });
    }

    public void set_speed_slow(MouseEvent Event) {
        System.out.println("Start moving - slow");
        System.out.println(Event.isPrimaryButtonDown());
        continue_moving = true;
//        object img_pane = (ImageView) loader.load(indicator_forwards);
        while (Event.isPrimaryButtonDown()) {
            System.out.println("just working");
            try {
                TimeUnit.MICROSECONDS.sleep(3000);
            } catch (InterruptedException e){
                System.out.println(e);
            }

//            indicator_forwards.setVisible(true);
//            FadeTransition ft = new FadeTransition(Duration.millis(300), indicator_forwards);
//            ft.setFromValue(0);
//            ft.setToValue(1);
//            ft.setAutoReverse(true);
//            ft.play();
        }
    }

    public void set_speed_medium(MouseEvent Event) {
        System.out.println("Start moving - medium");
    }

    public void set_speed_fast(MouseEvent Event) {
        System.out.println("Start moving - fast");
    }

    public void stop_vehicle(MouseEvent Event) {
        System.out.println("Stopped moving");

        continue_moving = false;
    }

    public void claw_open(MouseEvent Event) {
        System.out.println("Claw Open");
    }

    public void claw_close(MouseEvent Event) {
        System.out.println("Claw Close");
    }

    public void claw_stop(MouseEvent Event) {
        System.out.println("Claw stop");
    }

    public void turn_left(MouseEvent Event) {
        System.out.println("Turn left");
    }

    public void turn_right(MouseEvent Event) {
        System.out.println("Turn right");
    }

    public void turn_stop(MouseEvent Event) {
        System.out.println("Turn stop");
    }

    public void toggle_video(ActionEvent Event) {
        if (show_video) {
            System.out.println("stop video");
            show_video = false;
        } else {
            System.out.println("start video");
            show_video = true;

        }
    }

    public void toggle_reverse(ActionEvent Event) {
        if (reverse_velocity) {
            System.out.println("going to move backwards");
            reverse_velocity = false;
        } else {
            System.out.println("going to move forwards");
            reverse_velocity = true;
        }
    }

}
