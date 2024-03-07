public class SmartCamera extends Superclass{
    private double megabytePerMinute;
    public long cameraMil;
    public double megabyte;
    public String onTime;
    public void add(String[] line){
        if (line.length < 4){
            Main.output += "ERROR: Erroneous Command!\n";
        } else if (line.length == 4) {
            try {
                if (deviceList.contains(line[2])){
                    Main.output += "ERROR: There is already a smart device with same name!\n";
                } else if (Double.parseDouble(line[3]) <= 0) {
                    Main.output += "ERROR: Megabyte value must be a positive number!\n";
                }else {
                    deviceList.add(line[2]);
                    name = line[2];
                    megabytePerMinute = Double.parseDouble(line[3]);
                    correctness = true;
                }
            }catch (NumberFormatException e){
                Main.output += "ERROR: Erroneous Command!\n";
            }
        } else if (line.length == 5) {
            try {
                if (deviceList.contains(line[2])){
                    Main.output += "ERROR: There is already a smart device with same name!\n";
                } else if (Double.parseDouble(line[3]) <= 0) {
                    Main.output += "ERROR: Megabyte value must be a positive number!\n";
                }else{
                    deviceList.add(line[2]);
                    name = line[2];
                    megabytePerMinute= Double.parseDouble(line[3]);
                    status = line[4];
                    correctness= true;
                    if (line[4].equals("On")){
                        onTime = SetTimes.getCurrentTime();
                    }
                }
            }catch (NumberFormatException e){
                Main.output += "ERROR: Erroneous Command!\n";
            }
        }else {
            Main.output += "ERROR: Erroneous Command!\n";
        }
    }
    public double getMegabytePerMinute() {
        return megabytePerMinute;
    }
    public String getStatus(){
        return status;
    }
    public boolean getCorrectness(){
        return correctness;
    }
    public String getOnTime(){
        return onTime;
    }
    public double getMegabyte() {
        return megabyte;
    }
    void setName(String name) {
        Superclass.name = name;
    }
}
