package ro.academyplus.swingy;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar swingy.jar [console|gui]");
            return;
        }

        switch (args[0]) {
            case "console":
                System.out.println("Launching console version...");
                break;
            case "gui":
                System.out.println("Launching GUI version...");
                break;
            default:
                System.out.println("Invalid mode: " + args[0]);
        }
    }
}
