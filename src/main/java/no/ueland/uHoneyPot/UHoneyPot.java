package no.ueland.uHoneyPot;

import java.util.ArrayList;

/**
 * Created by Ueland on 3/13/17.
 */
class UHoneyPot {

    private static final ArrayList<Integer> ports = new ArrayList<>();

    public static void main(String[] args) {
        if(args.length == 0 || args[0].equals("help")) {
            printHelp(args.length != 0);
            return;
        }
        getPortRanges(args);

        if(ports.size() == 0) {
            printHelp(true);
            return;
        }
        System.out.print("Starting UHoneyPot on ");
        if(ports.size() == 1) {
            System.out.print("port "+ports.get(0));
        } else {
            System.out.print(ports.size() + " ports");
        }
        System.out.print(" ...");
        for(int port : ports) {
            new Thread(new PortHandler(port)).start();
        }
        System.out.println(" Ok");
    }



    private static void getPortRanges(String[] args) {
        for(String arg : args) {
            getPortRange(arg);
        }
    }

    private static void getPortRange(String arg) {
        try {
            if (arg.contains("-")) {
                String[] bits = arg.split("-");
                int low = Integer.parseInt(bits[0]);
                int high = Integer.parseInt(bits[1]);
                if(low > high) {
                    int tmp = low;
                    low = high;
                    high = tmp;
                }
                for(int i=low;i<=high;i++) {
                    ports.add(i);
                }
            } else {
                ports.add(Integer.parseInt(arg));
            }
        }catch(NumberFormatException nxe) {
            System.err.print("Unknown port: "+arg);
        }
    }

    private static void printHelp(boolean hasArgs) {
        if(!hasArgs) {
            System.out.println("Usage: UHoneyPot [List of one or more port to listen on]");
            System.out.println("Start the program with help as an argument to get a detailed usage example");
        } else {
            System.out.println("Ports:\nYou can add as many port arguments as you wish, you can also add port-ranges.");
            System.out.println("Example 1 - Listen on the default Telnet port: UHoneyPot 21");
            System.out.println("Example 2 - Listen on Telnet and HTTP port: UHoneyPot 21 80");
            System.out.println("Example 3 - Listen on a range: UHoneyPot 21-23");
            System.out.println("Example 4 - Listen on multiple ranges: UHoneyPot 21-23 80-90 440-443");
            System.out.println("");
            System.out.println("Log files:\n");
            System.out.println("Data gets stored to ./portnumber/todays-date.log");
            System.out.println("(Relative to the folder you run the program from)");
        }
    }
}
