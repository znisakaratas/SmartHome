import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class SmartPlug extends Superclass{
    private double ampere ;
    public double watt;
    private final int voltage = 220;
    public String onTimes ;
    private boolean plug = false;
    public void add(String[] line){
        if (line.length < 3){
            Main.output += "ERROR:Erroneous Command\n";
        } else if (line.length == 3) {
            if (!deviceList.contains(line[2])){
                deviceList.add(line[2]);
                name = line[2];
                ampere = 0;
                correctness = true;
                onTimes = "none";
            }else {
                Main.output += "ERROR: There is already a smart device with same name!\n";
            }
        } else if (line.length == 4) {
            if (deviceList.contains(line[2])){
                Main.output += "ERROR: There is already a smart device with same name!\n";
            } else if (line[3].equals("On") || line[3].equals("Off")) {
                deviceList.add(line[2]);
                ampere = 0;
                status = line[3];
                onTimes="none";
                name = line[2];
                correctness = true;
            }else{
                Main.output += "ERROR: Erroneous Command!\n";
            }
        } else if (line.length == 5) {
            if (deviceList.contains(line[2])){
                Main.output += "ERROR: There is already a smart device with same name!\n";
            } else if (line[3].equals("On") || line[3].equals("Off")) {
                try {
                    if (Double.parseDouble(line[4]) <=0){
                        Main.output += "ERROR: Ampere value must be a positive number!\n";
                    } else {
                        ampere = Double.parseDouble(line[4]);
                        deviceList.add(line[2]);
                        status = line[3];
                        name= line[2];
                        correctness = true;
                        if (line[3].equals("On")){
                            onTimes = SetTimes.getCurrentTime();
                            plug = true;
                        }else {
                            onTimes = "none";
                        }
                    }
                }catch (NumberFormatException e){
                    Main.output += "ERROR: Erroneous Command!\n";
                }
            }else {
                Main.output += "ERROR: Erroneous Command!\n";
            }
        } else {
            Main.output += "ERROR: Erroneous Command!\n";
        }

    }
    public void plugIn(String[] line){
        if (line.length != 3){
            Main.output += "ERROR: Erroneous Command!\n";
        } else if (!deviceList.contains(line[1])) {
            Main.output += "ERROR: There is not such a device!\n";
        }else {
            int objIndex = deviceList.indexOf(line[1]);
            Object plugObj = Main.devFeauture.get(objIndex);
            try {
                if (Integer.parseInt(line[2]) <= 0){
                    Main.output += "ERROR: Ampere value must be a positive number!\n";
                }else if (plugObj instanceof SmartPlug){
                    if (((SmartPlug) plugObj).getStatus().equals("On") && !((SmartPlug) plugObj).isPlug()){
                        ((SmartPlug) plugObj).onTimes = SetTimes.getCurrentTime();
                        ((SmartPlug) plugObj).plug = true;
                        ((SmartPlug) plugObj).ampere = Integer.parseInt(line[2]);
                    } else if (((SmartPlug) plugObj).getStatus().equals("On") && ((SmartPlug) plugObj).isPlug()) {
                        Main.output += "ERROR: There is already an item plugged in to that plug!\n";
                    } else if (((SmartPlug) plugObj).getStatus().equals("Off") && !((SmartPlug) plugObj).isPlug()){
                        ((SmartPlug) plugObj).ampere = Integer.parseInt(line[2]);
                        ((SmartPlug) plugObj).onTimes = "none";
                        ((SmartPlug) plugObj).plug = true;
                    }else if (((SmartPlug) plugObj).isPlug()){
                        Main.output += "ERROR: There is already an item plugged in to that plug!\n";
                    }
                }else {
                    Main.output += "ERROR: This device is not a smart plug!\n";
                }

            }catch (NumberFormatException e){
                Main.output += "ERROR: Erroneous Command!\n";
            }
        }
    }
    public void plugOut(String[] line){
        if (line.length != 2){
            Main.output += "ERROR: Erroneous Command!\n";
        } else if (!deviceList.contains(line[1])) {
            Main.output += "ERROR: There is not such a device!\n";
        }else {
            int index = deviceList.indexOf(line[1]);
            Object plugObject = Main.devFeauture.get(index);
            if (plugObject instanceof SmartPlug && ((SmartPlug) plugObject).isPlug() ){
                if (((SmartPlug) plugObject).getStatus().equals("On")){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                    LocalDateTime oldDateTime = LocalDateTime.parse(((SmartPlug) plugObject).getOnTimes(),formatter);
                    LocalDateTime newDate = LocalDateTime.parse(SetTimes.getCurrentTime(),formatter);
                    Duration duration = Duration.between(oldDateTime,newDate);
                    long minute = duration.toMinutes();
                    ((SmartPlug) plugObject).watt += getVoltage() * getAmpere() * minute/60;
                    ((SmartPlug) plugObject).onTimes = "none";
                    ((SmartPlug) plugObject).plug = false;
                }else {
                    ((SmartPlug) plugObject).plug = false;
                }
            }else {
                Main.output += "ERROR: This plug has no item to plug out from that plug!\n";
            }
        }
    }
    public String getStatus(){
        return status;
    }
    public double getAmpere() {
        return ampere;
    }
    public int getVoltage(){
        return voltage;
    }
    public boolean getCorrectness(){
        return correctness;
    }
    public String getOnTimes(){
        return onTimes;
    }

    public boolean isPlug() {
        return plug;
    }
    public double getWatt() {
        return watt;
    }

    @Override
    void setName(String name) {
        Superclass.name = name;
    }
}


