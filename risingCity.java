import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//Define a public class called risingCity 
public class risingCity {

    MinHeap heap;
    RedBlackTree tree;

    BufferedWriter writer;

    Building minBuilding = null;
    Building[] tempBuildings = new Building[2000];
    int[] buildingsToRemove = new int[2000];
    int buildingsWaiting = 0, removeCount = 0;
    static int globalTime = 0;
    int changer = 0;

    public risingCity() throws IOException {
        heap = new MinHeap(2000);
        tree = new RedBlackTree();
        writer = new BufferedWriter(new FileWriter("output_file.txt"));
        
    }

    public void insertBuilding(int buildingNo, int time) {
        Building b = new Building(buildingNo, 0, time);
        tempBuildings[buildingsWaiting++] = b;
        tree.insert(b);
    }


    //Used to increment executed time with respect to global time 
    boolean passOneDay() throws IOException {
        globalTime++;
        if(changer == 0) {
            if(minBuilding != null) {
                minBuilding.executedTime = minBuilding.tempExecTime;
                Building b = heap.remove();
                heap.insert(minBuilding);
            }
            for(int i = 0; i < buildingsWaiting; i++) {
                heap.insert(tempBuildings[i]);
            }
            for(int i = 0; i < removeCount; i++) {
                tree.delete(buildingsToRemove[i]);
            }

            removeCount = buildingsWaiting = 0;
            changer = 5;
            minBuilding = heap.getMin();
        }
        if(minBuilding.tempExecTime + 1 == minBuilding.totalTime) {
            heap.remove();
            //tree.delete(minBuilding.buildingNumber);
            minBuilding.tempExecTime++;
            buildingsToRemove[removeCount++] = minBuilding.buildingNumber;
            System.out.println("(" + minBuilding.buildingNumber + "," + globalTime + ")");
            writer.write("(" + minBuilding.buildingNumber + "," + globalTime + ")\n");
            if(heap.isEmpty()) {
                writer.flush();
                writer.close();
                return false;
            }
            changer = 1;
            minBuilding = null;
            // Building completed
        } else {
            minBuilding.tempExecTime++;
        }
        changer--;
        return true;
    }

    public void printBuilding(int buildingNo) throws IOException {
        System.out.println(tree.getNode(buildingNo)+"");
        writer.write(tree.getNode(buildingNo)+"\n");
    }

    public void printBuildings(int buildingNo1, int buildingNo2) throws IOException {
        StringBuilder sb = new StringBuilder();
        for(Building b : tree.getNodes(buildingNo1, buildingNo2)) {
            sb.append(b+",");
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb.toString());
        writer.write(sb.toString()+"\n");
    }

    public static void main(String[] args) {
        
        try {
            risingCity city = new risingCity();
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] vals = line.split(":");
                int time = Integer.parseInt(vals[0]);                
                while(time > globalTime) {
                    city.passOneDay();
                }
                if(vals[1].trim().charAt(0) == 'I') {
                    String i = vals[1].substring(8,vals[1].length() - 1);
                    String[] inputs = i.split(",");
                    city.insertBuilding(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                } else if(vals[1].trim().contains(",")) {
                    String i = vals[1].substring(15,vals[1].length() - 1);
                    String[] inputs = i.split(",");
                    city.printBuildings(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                } else {
                    String i = vals[1].substring(15,vals[1].length() - 1);
                    city.printBuilding(Integer.parseInt(i));
                }
                
            }
            while(city.passOneDay());
            reader.close();            
        } catch(IOException e) {}
        

    }

}
