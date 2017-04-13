package no.ueland.uHoneyPot;

/**
 * Created by Ueland on 4/13/17.
 */
public class Util {
    public static void die(Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
        System.exit(1);
    }
}
