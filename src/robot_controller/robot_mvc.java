package robot_controller;

import java.util.Random;

/**
 * Created by Jason Redd on 2/28/15.
 * jwr110030
 * cs6301
 * simple mvc for setting the initial state of the robots ui
 */
public class robot_mvc {

    public int get_random_range(int range) {
        Random random_generator = new Random();
        return random_generator.nextInt(range);
    }

    // Meant for when the UI initializes
    public int get_current_claw_sate() {
        Random random_generator = new Random();
        return get_random_range(85)+5;
    }

    // Meant for when the UI initializes
    public int get_current_arm_angle() {
        return get_random_range(90);
    }

    public int get_robots_current_temperature() {
        return get_random_range(120);
    }

}
