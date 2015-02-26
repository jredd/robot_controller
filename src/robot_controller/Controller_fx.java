package robot_controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.control.Slider;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.control.Control;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller_fx implements Initializable {

    @FXML
    private Button btn_speed_slow;
    @FXML
    private Button btn_speed_medium;
    @FXML
    private Button btn_turn_left;
    @FXML
    private Button btn_turn_right;
    @FXML
    private Button btn_claw_open;
    @FXML
    private Button btn_claw_close;
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
    @FXML
    private Label label_video;

    private int current_claw_angle=45;
    private boolean show_video = false;
    private boolean reverse_velocity = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing Application and setting things");

        // Set initial rotation angle of the claw hand
        line_claw_top.getTransforms().add(new Rotate(current_claw_angle*-1));
        line_claw_bottom.getTransforms().add(new Rotate(current_claw_angle));
    }

    public void move_forwards_or_backwards(MouseEvent Event) {
        Object event_type = Event.getEventType();
        String control_id = ((Control)Event.getSource()).getId();
        int velocity;
        if (control_id.equals("btn_speed_slow")) {
            velocity = 700;
        }else if (control_id.equals("btn_speed_medium")) {
            velocity = 500;
        }else if (control_id.equals("btn_speed_fast")) {
            velocity = 300;
        } else {
            velocity = 200;
        }

        FadeTransition ft;
        if (reverse_velocity) {
            ft = new FadeTransition(Duration.millis(velocity), indicator_backwards);
        } else {
            ft = new FadeTransition(Duration.millis(velocity), indicator_forwards);
        }

        if (event_type == MouseEvent.MOUSE_PRESSED) {
//            System.out.println("Mouse pressed - "+control_id);

            if (reverse_velocity) {
                indicator_backwards.setVisible(true);
            } else {
                indicator_forwards.setVisible(true);
            }

            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setCycleCount(Animation.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
        }else if (event_type == MouseEvent.MOUSE_RELEASED) {
//            System.out.println("Mouse Released");

            ft.stop();
            if (reverse_velocity) {
                indicator_backwards.setVisible(false);
            } else {
                indicator_forwards.setVisible(false);
            }
        }
    }

    public void turn_left_or_right(MouseEvent Event) {
        Object event_type = Event.getEventType();
        String control_id = ((Control)Event.getSource()).getId();

        FadeTransition ft;
        if (control_id.equals("btn_turn_left")) {
            ft = new FadeTransition(Duration.millis(400), indicator_left);
        } else {
            ft = new FadeTransition(Duration.millis(400), indicator_right);
        }

        if (event_type == MouseEvent.MOUSE_PRESSED) {
            System.out.println("Mouse pressed - "+control_id);

            if (control_id.equals("btn_turn_left")) {
                indicator_left.setVisible(true);
            } else {
                indicator_right.setVisible(true);
            }

            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setCycleCount(Animation.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
        }else if (event_type == MouseEvent.MOUSE_RELEASED) {
            System.out.println("Mouse Released");

            ft.stop();
            if (control_id.equals("btn_turn_left")) {
                indicator_left.setVisible(false);
            } else {
                indicator_right.setVisible(false);
            }
        }
    }

    public void toggle_video(ActionEvent Event) {
        if (show_video) {
            System.out.println("stop video");
            label_video.setVisible(true);
            show_video = false;
        } else {
            System.out.println("start video");
            label_video.setVisible(false);
            show_video = true;
        }
    }

    public void toggle_reverse(ActionEvent Event) {
        if (reverse_velocity) {
//            System.out.println("going to move forwards");
            reverse_velocity = false;
        } else {
//            System.out.println("going to move backwards");
            reverse_velocity = true;
        }
    }

    public void change_arm_angle(MouseEvent Event) {
//        System.out.println(slider_arm.getValue());
        Rotate rotate = new Rotate(slider_arm.getValue()*-1);
        line_arm.getTransforms().clear();
        line_arm.getTransforms().add(rotate);
    }

    public void claw_open_or_close(MouseEvent Event) {
        String control_id = ((Control)Event.getSource()).getId();

        if (control_id.equals("btn_claw_open")) {
            if (current_claw_angle <= 75) {
                btn_claw_close.setDisable(false);
                line_claw_top.getTransforms().add(new Rotate(-1));
                line_claw_bottom.getTransforms().add(new Rotate(1));
                current_claw_angle+=1;
            } else {
                btn_claw_open.setDisable(true);
            }
        }else {
            if (current_claw_angle > 0) {
                btn_claw_open.setDisable(false);
                line_claw_top.getTransforms().add(new Rotate(1));
                line_claw_bottom.getTransforms().add(new Rotate(-1));
                current_claw_angle-=1;
            } else {
                btn_claw_close.setDisable(true);
            }
        }
    }
}
