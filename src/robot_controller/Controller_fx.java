package robot_controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.control.Control;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ResourceBundle;
import java.net.URL;

/**
 * Created by Jason Redd on 2/28/15.
 * jwr110030
 * cs6301
 * Simple ui for controlling a robot
 */


// Main ui controller for the robot
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
    private Button btn_make_video_larger;
    @FXML
    private Button btn_make_video_smaller;
    @FXML
    private Button btn_claw_close;
    @FXML
    private Slider slider_arm;
    @FXML
    private ImageView image_view_video_feed;
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
    @FXML
    private Label label_temperature;
    @FXML
    private Pane pane_video_feed;
    @FXML
    private Pane pane_temperature;

    private int current_claw_angle=45;
    private boolean show_video = false;
    private boolean reverse_velocity = false;
    robot_mvc robot_mvc = new robot_mvc();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing Application and setting things");

        // Set initial rotation angle of the claw hand
        int claw_state = robot_mvc.get_current_arm_angle();
        line_claw_top.getTransforms().add(new Rotate(claw_state*-1));
        line_claw_bottom.getTransforms().add(new Rotate(claw_state));

        // Set the initial arm angle
        int current_arm_angle = robot_mvc.get_current_arm_angle();
        slider_arm.setValue(current_arm_angle);
        line_arm.getTransforms().add(new Rotate(current_arm_angle*-1));

        // Setup clip on image pane so zooming in doesn't flow out of bounds
        Rectangle rect = new Rectangle(660, 535);
        pane_video_feed.setClip(rect);

    }

    public void move_forwards_or_backwards(MouseEvent Event) {
        Object event_type = Event.getEventType();
        String control_id = ((Control)Event.getSource()).getId();
        FadeTransition ft;
        int velocity;

        // Set the animation speed based on which button is pressed
        if (control_id.equals("btn_speed_slow")) {
            velocity = 700;
        }else if (control_id.equals("btn_speed_medium")) {
            velocity = 500;
        }else if (control_id.equals("btn_speed_fast")) {
            velocity = 300;
        } else {
            velocity = 200;
        }

        // Set the animation onto the proper indicator
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

        // Set the animation onto the proper indicator
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

    public void change_video_size(MouseEvent Event) {
        String control_id = ((Control)Event.getSource()).getId();
        double scale_x = image_view_video_feed.getScaleX();
        double scale_y = image_view_video_feed.getScaleY();

        // Make sure to enable and disable the minus button based on scale
        if (scale_x <= 2){
            btn_make_video_smaller.setDisable(true);
        }else {
            btn_make_video_smaller.setDisable(false);
        }

        if (control_id.equals("btn_make_video_larger")) {
            System.out.println(control_id);

            image_view_video_feed.setScaleX(scale_x+1);
            image_view_video_feed.setScaleY(scale_x+1);
        } else {
            if (scale_x != 1) {
                image_view_video_feed.setScaleX(scale_x-1);
                image_view_video_feed.setScaleY(scale_y-1);
            }
        }
    }

    public void toggle_video(ActionEvent Event) {
        if (show_video) {
            System.out.println("stop video");
            label_video.setVisible(true);
            pane_video_feed.setVisible(false);
            show_video = false;


        } else {
            System.out.println("start video");
            label_video.setVisible(false);
            pane_video_feed.setVisible(true);
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
        line_arm.getTransforms().clear();
        line_arm.getTransforms().add(new Rotate(slider_arm.getValue()*-1));
    }

    public void get_current_temperature(MouseEvent Event) {
        pane_temperature.setVisible(true);
        label_temperature.setText(""+robot_mvc.get_robots_current_temperature()+" °F");
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
