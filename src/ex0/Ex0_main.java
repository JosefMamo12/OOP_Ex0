package ex0;

import ex0.algo.ElevatorAlgo;
import ex0.algo.ElevatorAlgo1;
import ex0.simulator.Simulator_A;
import gui.MyFrame;

/**
 * This is the main file of Ex0 (OOP), Do play with it and make sure you know how to operate the simulator before
 * starting to implement the algorithm.
 */
public class Ex0_main implements Runnable {
    public static Long ID0=308528538L, ID1 =31254934L, ID2 = null;
    public static void main(String[] args) {
        Thread ex0 = new Thread(new Ex0_main());
        ex0.start();


    }

    @Override
    public void run() {
            String codeOwner = codeOwner();
            Simulator_A.setCodeOwner(codeOwner);
            int stage = 3
                    ;  // any case in [0,9].
            System.out.println("Ex0 Simulator: isStarting, stage="+stage+") ... =  ");
            String callFile = null; // use the predefined cases [1-9].
            // String callFile = "data/Ex0_stage_2__.csv"; /
            Simulator_A.initData(stage, callFile);  // init the simulator data: {building, calls}.
            MyFrame myFrame = new MyFrame(Simulator_A.getBuilding());
            ElevatorAlgo ex0_alg = new ElevatorAlgo1(Simulator_A.getBuilding());
            Simulator_A.initAlgo(ex0_alg); // init the algorithm to be used by the simulator
            Simulator_A.runSim(); // run the simulation - should NOT take more than few seconds.

            long time = System.currentTimeMillis();
            String report_name = "out/Ex0_report_case_"+stage+"_"+time+"_ID_.log";
            Simulator_A.report(report_name);
            Simulator_A.writeAllCalls("out/Ex0_Calls_case_"+stage+"_.csv"); // time,src,dest,state,elevInd, dt.
        }

        private static String codeOwner() {
            String owners = "none";
            if(ID0!=null) {owners = ""+ID0;}
            if(ID1!=null) {owners += ","+ID1;}
            if(ID2!=null) {owners += ","+ID2;}
            return owners;
        }
    }
