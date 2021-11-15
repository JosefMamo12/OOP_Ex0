package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.*;

/**
 * This class represent an elevator brain which control
 * the building elevators schedules, in the next assignments i will
 * add some calculations about distances between elevator and add them.
 * in this specific algorithm i
 */
public class ElevatorAlgo1 implements ElevatorAlgo {
    public static final int UP = 1, DOWN = -1;
    Building _building;
    int elevNum;
    Vector<Integer>[] floorVector;
    PriorityQueue<Integer>[] pqUp;
    PriorityQueue<Integer>[] pqDown;
    int[] elevDir;


    @Override
    public Building getBuilding() {
        return this._building;
    }

    /**
     * Building constructor
     *
     * @param b
     */
    public ElevatorAlgo1(Building b) {
        this._building = b;
        elevNum = b.numberOfElevetors();
        floorVector = new Vector[elevNum];
        pqUp = new PriorityQueue[elevNum];
        pqDown = new PriorityQueue[elevNum];
        elevDir = new int[elevNum];
        for (int i = 0; i < elevNum; i++) {
            floorVector[i] = new Vector<>();
            pqUp[i] = new PriorityQueue<>(Comparator.naturalOrder());
            pqDown[i] = new PriorityQueue<>(Comparator.reverseOrder());
            elevDir[i] = UP;
        }
    }

    @Override
    public String algoName() {
        return null;
    }

    /**
     * In this we check which elevator is empty and can take some calls.
     *
     * @param c
     * @return
     */
    public int checkIfElevatorEmpty(CallForElevator c) {
        int index = -1;
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < elevNum; i++) {
            if (floorVector[i].isEmpty() && dist(i, c.getSrc()) < minDistance) {
                index = i;
                minDistance = dist(i, c.getSrc());
            }
        }

        return index;
    }

    /**
     * Check the distance between elevators.
     *
     * @param elevPos
     * @param src
     * @return
     */


    public int dist(int elevPos, int src) {
        return Math.abs(_building.getElevetor(elevPos).getPos() - src);
    }

    /**
     * The main algorithm for finding the best elevator to pick up
     * the specified call.
     * in my mind i try to search for the closest elevator to pick up the specific call
     * i tried to divide it to edge incidents.
     *
     * @param c the call for elevator (src, dest)
     * @return
     */

    @Override
    public int allocateAnElevator(CallForElevator c) {
        int index = checkIfElevatorEmpty(c);
        if (index != -1) {
            addToQueue(index, c.getSrc(), c.getDest(), c.getType());
            return index;
        }
        for (int i = 0; i < elevNum; i++) {
            Elevator curr = _building.getElevetor(i);
            int nextSrc = floorVector[i].get(0);
            int nextDest = floorVector[i].lastElement();
            // Between ranges of calls
            if (c.getSrc() >= nextSrc && c.getDest() <= nextDest) {
                addToQueue(i, c.getSrc(), c.getDest(), c.getType());
                return i;
            }

            // partial range
            if (curr.getState() == Elevator.UP && c.getType() == CallForElevator.UP) {
                if (c.getSrc() <= nextSrc && curr.getPos() <= c.getSrc()) {
                    addToQueue(i, c.getSrc(), c.getDest(), curr.getState());
                }
            } else if (curr.getState() == Elevator.DOWN && c.getType() == CallForElevator.DOWN) {
                if (c.getSrc() >= nextSrc && curr.getPos() >= c.getSrc()) {
                    addToQueue(i, c.getSrc(), c.getDest(), curr.getState());
                }
                return i;
            }
            //Not in range
        }
        int minimalIndex = getMinimalQueueLength(c);
        addToQueue1(minimalIndex, c.getSrc(), c.getDest(), _building.getElevetor(minimalIndex).getState());
        return minimalIndex;
    }

    private void addToQueue1(int minimalIndex, int src, int dest, int state) {
        if (state == Elevator.UP) {
            if (!pqUp[minimalIndex].contains(src))
                pqUp[minimalIndex].add(src);
            if (!pqUp[minimalIndex].contains(dest))
                pqUp[minimalIndex].add(dest);
        } else {
            if (!pqDown[minimalIndex].contains(src))
                pqDown[minimalIndex].add(src);
            if (!pqDown[minimalIndex].contains(dest))
                pqUp[minimalIndex].add(dest);
        }

    }

    private int getMinimalQueueLength(CallForElevator c) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < elevNum; i++) {
            if (floorVector[i].lastElement() - c.getSrc() < min)
                min = floorVector[i].lastElement() - c.getSrc();
            index = i;
        }
        return index;
    }

    public void addToQueue(int elev, int src, int dest, int state) {
        if (!floorVector[elev].contains(src))
            floorVector[elev].add(src);
        if (!floorVector[elev].contains(dest))
            floorVector[elev].add(dest);
        if (state == UP) {
            floorVector[elev].sort(Comparator.naturalOrder());
        } else {
            floorVector[elev].sort(Comparator.reverseOrder());
        }
    }

    @Override
    public void cmdElevator(int elev) {
        Elevator curr = _building.getElevetor(elev);
        if (floorVector[elev].isEmpty()) {
            if (!pqUp[elev].isEmpty() || !pqDown[elev].isEmpty()) {
                if (pqUp[elev].isEmpty() && elevDir[elev] == UP) {
                    elevDir[elev] = DOWN;
                    floorVector[elev].addAll(pqDown[elev]);
                    floorVector[elev].sort(Comparator.reverseOrder());
                } else {
                    elevDir[elev] = UP;
                    floorVector[elev].addAll(pqUp[elev]);
                    floorVector[elev].sort(Comparator.naturalOrder());
                }
            }
            return;
        }
        if (curr.getPos() == floorVector[elev].get(0)) {
            curr.goTo(floorVector[elev].remove(0));
        } else {
            if (curr.getPos() < floorVector[elev].get(0)) {
                elevDir[elev] = UP;
            } else {
                elevDir[elev] = DOWN;
            }
            curr.goTo(floorVector[elev].get(0));
        }
    }

    /**
     * My function to check some outs.
     */
    public void elevatorStatus() {
        for (int i = 0; i < elevNum; i++) {
            Elevator curr = _building.getElevetor(i);
            System.out.println("Elevator: " + i + " State: " + curr.getState() + " Position: " + curr.getPos());
        }
    }
}
