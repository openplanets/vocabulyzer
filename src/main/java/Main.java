/**
 * Created by artur on 3/31/14.
 */


public class Main {
    public static void main(String[] args) {
        if (args.length < 1 || args[0] == null) {
            System.out
                    .println("Please provide paths to XML input file, XML output file and a vocabulary mapping");
            System.out
                    .println("e.g.: java -jar profiler.jar /home/user/input.xml /home/user/output.xml /home/user/vocabulary");
            return;
        }
        Controller ctr=null;
        if (args.length == 2) {
            ctr = new Controller();
        } else{
            ctr = new Controller(args[2]);
        }
        ctr.Execute(args[0],args[1]);
    }
}
