import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public abstract class Superclass {
    static ArrayList<String> deviceList = new ArrayList<>();
    public boolean correctness = false;
    String status = "Off";
    public static String name ;
    abstract void add(String[] line);
    abstract boolean getCorrectness();
    abstract String getStatus();
    public static void switchStatus(String device, String status){
        int objIndex = Superclass.deviceList.indexOf(device);
        Object object = Main.devFeauture.get(objIndex);
        if (object instanceof SmartLamp && !((SmartLamp) object).status.equals(status) && status.equals("Off")){
            ((SmartLamp) object).status = "Off";
        } else if (object instanceof SmartLamp && ((SmartLamp) object).status.equals(status) && status.equals("On")) {
            Main.output+="ERROR: This device is already switched on!\n";
        } else if (object instanceof SmartLamp && !((SmartLamp) object).status.equals(status) && status.equals("On")){
            ((SmartLamp) object).status = "On";
        } else if (object instanceof SmartLamp && ((SmartLamp) object).status.equals(status) && status.equals("Off")) {
            Main.output += "ERROR: This device is already switched off!\n";
        } else if (object instanceof SmartColorLamp && !((SmartColorLamp) object).status.equals(status) && status.equals("Off")) {
            ((SmartColorLamp) object).status = "Off";
        } else if (object instanceof SmartColorLamp && ((SmartColorLamp) object).status.equals(status) && status.equals("On")) {
            Main.output+= "ERROR: This device is already switched on!\n";
        } else if (object instanceof SmartColorLamp && !((SmartColorLamp) object).status.equals(status) && status.equals("On")) {
            ((SmartColorLamp) object).status = "On";
        } else if (object instanceof SmartColorLamp && ((SmartColorLamp) object).status.equals(status) && status.equals("Off")) {
            Main.output+= "ERROR: This device is already switched off!\n";
        } else if (object instanceof SmartCamera && !((SmartCamera) object).status.equals(status) && status.equals("On")) {
            ((SmartCamera) object).status = "On";
            ((SmartCamera) object).onTime = SetTimes.getCurrentTime();
        } else if (object instanceof SmartCamera && ((SmartCamera) object).status.equals(status) && status.equals("On")) {
            Main.output += "ERROR: This device is already switched on!\n";
        } else if (object instanceof SmartCamera && !((SmartCamera) object).status.equals(status) && status.equals("Off")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            LocalDateTime oldDateTime = LocalDateTime.parse(((SmartCamera) object).getOnTime(),formatter);
            LocalDateTime newDate = LocalDateTime.parse(SetTimes.getCurrentTime(),formatter);
            Duration duration = Duration.between(oldDateTime,newDate);
            ((SmartCamera) object).cameraMil = duration.toMillis();
            ((SmartCamera) object).megabyte =((SmartCamera) object).getMegabytePerMinute() * ((SmartCamera) object).cameraMil /60000;
            ((SmartCamera) object).status = "Off";
            ((SmartCamera) object).onTime = "none";
        } else if (object instanceof SmartCamera && ((SmartCamera) object).status.equals(status) && status.equals("Off")) {
            Main.output+= "ERROR: This device is already switched off!\n";
        } else if (object instanceof SmartPlug && ((SmartPlug) object).isPlug()) {
            if (!((SmartPlug) object).getStatus().equals(status) && status.equals("Off")){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                LocalDateTime oldDateTime = LocalDateTime.parse(((SmartPlug) object).getOnTimes(),formatter);
                LocalDateTime newDate = LocalDateTime.parse(SetTimes.getCurrentTime(),formatter);
                Duration duration = Duration.between(oldDateTime,newDate);
                long minute = duration.toMinutes();
                ((SmartPlug) object).watt += ((SmartPlug) object).getVoltage() * ((SmartPlug) object).getAmpere() * minute/60;
                ((SmartPlug) object).status = "Off";
                ((SmartPlug) object).onTimes = "none";
            }else if (((SmartPlug) object).getStatus().equals(status) && status.equals("Off")){
                Main.output +="ERROR: This device is already switched off!\n";
            } else if (!((SmartPlug) object).getStatus().equals(status) && status.equals("On")) {
                ((SmartPlug) object).status = "On";
                ((SmartPlug) object).onTimes = SetTimes.getCurrentTime();
            }else {
                Main.output +="ERROR: This device is already switched on!\n";
            }
        }else if (object instanceof SmartPlug && !((SmartPlug) object).isPlug()) {
            if (!((SmartPlug) object).getStatus().equals(status) && status.equals("On")){
                ((SmartPlug) object).status = "On";
                ((SmartPlug) object).onTimes = SetTimes.getCurrentTime();
            } else if (((SmartPlug) object).getStatus().equals(status) && status.equals("On")) {
                Main.output += "ERROR: This device is already switched on!\n";
            } else if (!((SmartPlug) object).getStatus().equals(status) && status.equals("Off")) {
                ((SmartPlug) object).status = "Off";
                ((SmartPlug) object).onTimes = "none";
            } else {
                Main.output += "ERROR: This device is already switched off!\n";
            }
        }
    }
    public static void changeName(String[] line){
        if (line.length != 3){
            Main.output += "ERROR: Erroneous Command!\n";
        } else if (line[1].equals(line[2])) {
            Main.output+="ERROR: Both of the names are the same, nothing changed!\n";
        } else if (!Superclass.deviceList.contains(line[1])) {
            Main.output += "ERROR: There is not a smart device with this name!\n";
        } else if (Superclass.deviceList.contains(line[2])) {
            Main.output+= "ERROR: There is already a smart device with same name!\n";
        }else {
            int index = Superclass.deviceList.indexOf(line[1]);
            Main.devFeauture.get(index).setName(line[2]);
            Superclass.deviceList.set(index,line[2]);
        }
    }
    public static void removeAndZ(String line,String remOrZ){
        try {
            if (Main.devFeauture.get(Superclass.deviceList.indexOf(line)) instanceof SmartColorLamp) {
                Object colorLamp = Main.devFeauture.get(Superclass.deviceList.indexOf(line));
                if (remOrZ.equals("remove")) {
                    ((SmartColorLamp) colorLamp).status = "Off";
                }
                String status = ((SmartColorLamp) colorLamp).getStatus().toLowerCase(Locale.ROOT);
                int value = 0;
                String kelvinColor = "";
                if (((SmartColorLamp) colorLamp).getColorCode() > 0) {
                    value = ((SmartColorLamp) colorLamp).getColorCode();
                    kelvinColor = "and its color value is " + String.format("0x%06X", value) + " with ";
                } else if (((SmartColorLamp) colorLamp).getKelvinValue() > 0) {
                    value = ((SmartColorLamp) colorLamp).getKelvinValue();
                    kelvinColor = "and its kelvin value is " + value + "K with ";
                }
                String bright = ((SmartColorLamp) colorLamp).getBrightness() + "%";
                String switchTime = null;
                if (SetTimes.getDeviceDict().containsKey(line)) {
                    switchTime = SetTimes.getDeviceDict().get(line);
                }
                String command = String.format("Smart Color Lamp %s is %s " + kelvinColor + "%s brightness and its time " +
                        "to switch its status is %s", line, status, bright, switchTime);
                Main.output+= command +"\n";
            }else if (Main.devFeauture.get(Superclass.deviceList.indexOf(line)) instanceof SmartLamp) {
                Object lamp = Main.devFeauture.get(Superclass.deviceList.indexOf(line));
                if (remOrZ.equals("remove")){
                    ((SmartLamp) lamp).status = "Off";
                }
                String status = ((SmartLamp) lamp).getStatus().toLowerCase(Locale.ROOT);
                String kelvin = ((SmartLamp) lamp).getKelvinValue() +"K";
                String bright = ((SmartLamp) lamp).getBrightness() + "%";
                String switchTime = null;
                if (SetTimes.getDeviceDict().containsKey(line)){
                    switchTime =  SetTimes.getDeviceDict().get(line);
                }
                String command = String.format("Smart Lamp %s is %s and its kelvin value is %s with %s " +
                        "brightness and its time to switch its status is %s",line,status,kelvin,bright,switchTime);
                Main.output+= command+"\n";
            } else if (Main.devFeauture.get(Superclass.deviceList.indexOf(line)) instanceof SmartCamera) {
                Object cam = Main.devFeauture.get(Superclass.deviceList.indexOf(line));
                if (remOrZ.equals("remove")){
                    if (((SmartCamera) cam).getStatus().equals("On")){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                        LocalDateTime oldDateTime = LocalDateTime.parse(((SmartCamera) cam).getOnTime(),formatter);
                        String current = SetTimes.getCurrentTime();
                        if (current.length()==18){
                            current = SetTimes.getCurrentTime().substring(0,11) +"0"+SetTimes.getCurrentTime().substring(11);
                        }
                        LocalDateTime newDate = LocalDateTime.parse(current,formatter);
                        Duration duration = Duration.between(oldDateTime,newDate);
                        ((SmartCamera) cam).cameraMil = duration.toMillis();
                        ((SmartCamera) cam).megabyte =((SmartCamera) cam).getMegabytePerMinute() * ((SmartCamera) cam).cameraMil /60000;
                        ((SmartCamera) cam).status = "Off";
                        ((SmartCamera) cam).onTime = null;
                    }
                }
                String status = ((SmartCamera) cam).getStatus().toLowerCase(Locale.ROOT);
                double megaB = ((SmartCamera) cam).getMegabyte();
                DecimalFormat decimalF = new DecimalFormat("0.00");
                String megaByte = decimalF.format(megaB) +"MB";
                String switchTime = null;
                if (SetTimes.getDeviceDict().containsKey(line)){
                    switchTime = SetTimes.getDeviceDict().get(line);
                }
                String command = String.format("Smart Camera %s is %s and used %s of storage so far (excluding " +
                        "current status), and its time to switch its status is %s.",line,status,megaByte,switchTime);
                Main.output+= command + "\n";
            } else if (Main.devFeauture.get(Superclass.deviceList.indexOf(line)) instanceof SmartPlug) {
                Object plug = Main.devFeauture.get(Superclass.deviceList.indexOf(line));
                if (remOrZ.equals("remove")){
                    if (!((SmartPlug ) plug).getOnTimes().equals("none") && ((SmartPlug ) plug).getStatus().equals("On")){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
                        LocalDateTime oldDateTime = LocalDateTime.parse(((SmartPlug) plug).getOnTimes(),formatter);
                        String current = SetTimes.getCurrentTime();
                        if (current.length() == 18){
                            current = SetTimes.getCurrentTime().substring(0,11) +"0"+SetTimes.getCurrentTime().substring(11);
                        }
                        LocalDateTime newDate = LocalDateTime.parse(current,formatter);
                        Duration duration = Duration.between(oldDateTime,newDate);
                        long minute = duration.toMinutes();
                        ((SmartPlug) plug).watt = ((SmartPlug) plug).getVoltage() * ((SmartPlug) plug).getAmpere() * minute/60;
                        ((SmartPlug) plug).status = "Off";
                        ((SmartPlug) plug).onTimes = "none";
                    }
                }
                DecimalFormat decimalF = new DecimalFormat("0.00");
                String status = ((SmartPlug) plug).getStatus().toLowerCase(Locale.ROOT);
                String watt = decimalF.format(((SmartPlug) plug).getWatt()) + "W";
                String switchTime = null;
                if (SetTimes.getDeviceDict().containsKey(line)){
                    switchTime = SetTimes.getDeviceDict().get(line);
                }
                String command = String.format("Smart Plug %s is %s and consumed %s so far (excluding " +
                        "current device), and its time to switch its status is %s.",line,status,watt,switchTime);
                Main.output+= command+"\n";
            }
        }catch (Exception e){
            Main.output += "";
        }
    }
    public static void getInput(String[] inputs ){
        for (String eachLine : inputs){
            String[] lines = eachLine.split("\\s");
            Main.output += "Command:" + eachLine + "\n";
            if(!inputs[0].contains("SetInitialTime")){
                Main.output += "ERROR: First command must be set initial time! Program is going to terminate!";
                break;
            }
            if (lines[0].equals("SetInitialTime") && inputs[0].equals(eachLine)){
                if (SetTimes.initialTime(lines[1]).equals("Error")){
                    Main.output += "ERROR: Time format is not correct!";
                    break;
                }else {
                    SetTimes.initialTime(lines[1]);
                    String result = String.format("SUCCESS: Time has been set to %s!",SetTimes.getCurrentTime());
                    Main.output+= result +"\n";
                }
            } else if (lines[0].equals("Add")&& lines[1].equals("SmartPlug")){
                SmartPlug smartPlug = new SmartPlug();
                smartPlug.add(lines);
                if (smartPlug.getCorrectness()){
                    Main.devFeauture.add(smartPlug);
                }
            } else if (lines[0].equals("Add") && lines[1].equals("SmartCamera")) {
                SmartCamera smartCamera = new SmartCamera();
                smartCamera.add(lines);
                if (smartCamera.getCorrectness()){
                    Main.devFeauture.add(smartCamera);
                }
            } else if (lines[0].equals("Add") && lines[1].equals("SmartLamp")) {
                SmartLamp smartLamp = new SmartLamp();
                smartLamp.add(lines);
                if (smartLamp.getCorrectness()){
                    Main.devFeauture.add(smartLamp);
                }
            } else if (lines[0].equals("Add") && lines[1].equals("SmartColorLamp")) {
                SmartColorLamp colorLamp = new SmartColorLamp();
                colorLamp.add(lines);
                if (colorLamp.getCorrectness()){
                    Main.devFeauture.add(colorLamp);
                }
            } else if (lines[0].equals("SetTime")) {
                SetTimes.setTime(lines[1]);
            } else if (lines[0].equals("SkipMinutes")) {
                try {
                    if (lines.length == 2 && Integer.parseInt(lines[1]) > 0){
                        SetTimes.skipMinutes(lines[1]);
                    }
                    else if (lines.length == 2 && Integer.parseInt(lines[1]) == 0) {
                        Main.output += "ERROR: There is nothing to skip!\n";
                    } else if (lines.length == 2 && Integer.parseInt(lines[1]) < 0) {
                        Main.output+="ERROR: Time cannot be reversed!\n" ;
                    } else if (lines.length != 2) {
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    Main.output += "ERROR: Erroneous Command!\n";
                }
            } else if (lines[0].equals("SetSwitchTime")) {
                SetTimes.setSwitchTime(lines);
            } else if (lines[0].equals("Nop")) {
                SetTimes.nop();
            } else if (lines[0].equals("Remove")) {
                if (lines.length!= 2){
                    Main.output += "ERROR: Erroneous Command!\n";
                } else if (!Superclass.deviceList.contains(lines[1])) {
                    Main.output += "ERROR: There is not such a device!\n";
                }else {
                    Main.output += "SUCCESS: Information about removed smart device is as follows:\n";
                    Superclass.removeAndZ(lines[1],"remove");
                    Object deletedObj = Main.devFeauture.get(Superclass.deviceList.indexOf(lines[1]));
                    Superclass.deviceList.remove(Superclass.deviceList.indexOf(lines[1]));
                    Main.devFeauture.remove(deletedObj);
                }
            } else if (lines[0].equals("ChangeName")) {
                Superclass.changeName(lines);
            } else if (lines[0].equals("PlugIn")) {
                SmartPlug smartPlug = new SmartPlug();
                smartPlug.plugIn(lines);
            } else if (lines[0].equals("PlugOut")) {
                SmartPlug smartPlug = new SmartPlug();
                smartPlug.plugOut(lines);
            } else if (lines[0].equals("Switch")) {
                if (lines.length != 3){
                    Main.output += "ERROR: Erroneous Command!\n";
                } else if (!Superclass.deviceList.contains(lines[1])) {
                    Main.output+= "ERROR: There is not such a device!\n";
                }else {
                    Superclass.switchStatus(lines[1],lines[2]);
                }
            } else if (lines[0].equals("SetKelvin")) {
                SmartLamp smartLamp = new SmartLamp();
                smartLamp.setKelvinValue(lines);
            } else if (lines[0].equals("SetBrightness")) {
                SmartLamp smartLamp = new  SmartLamp();
                smartLamp.setBrightness(lines);
            } else if (lines[0].equals("SetColorCode")) {
                SmartColorLamp colorObject = new SmartColorLamp();
                colorObject.setColorCode(lines);
            }else if (lines[0].equals("SetWhite")) {
                SmartLamp smartLamp = new SmartLamp();
                smartLamp.setWhite(lines);
            } else if (lines[0].equals("SetColor")) {
                SmartColorLamp colorObject = new SmartColorLamp();
                colorObject.setColor(lines);
            } else if (lines[0].equals("ZReport")) {
                Main.output+= "Time is:\t"+SetTimes.getCurrentTime() +"\n";
                for (String device: Superclass.deviceList){
                    Superclass.removeAndZ(device,"zReport");
                }
            } else {
                Main.output+= "ERROR: Erroneous Command!\n";
            }
        }
    }

    public String getName(){
        return name;
    }

    abstract void setName(String name);
}
