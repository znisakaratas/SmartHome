import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;

public class SetTimes{
    static ArrayList<String> switchList = new ArrayList<>();
    static ArrayList<String> deleteList = new ArrayList<>();
    static Map<String, String> deviceDict = new HashMap<>();
    public static String currentTime;
    public static String times;
    public static String initialTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            currentTime = dateTime.format(formatter);
            return dateTime.format(formatter);
        } catch (Exception e) {
            Main.output += "ERROR: Time format is not correct!\n";
            return "ERROR: Time format is not correct!";
        }
    }
    public static void setTime(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            if (currentTime.compareTo(dateTime.format(formatter)) >= 0){
                Main.output += "ERROR: Time cannot be reversed!\n";
            }else {
                currentTime = dateTime.format(formatter);
                if (switchList.size() > 0){
                    for (String time:switchList){
                        if (time.compareTo(currentTime) <= 0){
                            String key = null;
                            for (Map.Entry<String, String> entry : deviceDict.entrySet()) {
                                key = entry.getKey();
                                String value =entry.getValue();
                                if (value.equals(time)){
                                    int index = Superclass.deviceList.indexOf(key);
                                    String stats =  Main.devFeauture.get(index).getStatus();
                                    if (stats.equals("On")){
                                        Superclass.switchStatus(key,"Off");
                                    }else {
                                        Superclass.switchStatus(key,"On");
                                    }
                                    deleteList.add(time);
                                    deviceDict.remove(key);
                                    break;
                                }
                            }
                        }
                    }
                    switchList.removeAll(deleteList);
                    deleteList.clear();
                }
            }
        } catch (Exception e) {
            Main.output += "ERROR: Time format is not correct!\n";
        }
    }
    public static void skipMinutes(String minute){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(currentTime, formatter);
        LocalDateTime newDateTime = dateTime.plusMinutes(Integer.parseInt(minute));
        String newTime = newDateTime.format(formatter);
        setTime(newTime);
    }
    public static void setSwitchTime(String[] lines){
        if (!Superclass.deviceList.contains(lines[1])){
            Main.output += "ERROR: This device doesn't exist!\n";
        }else{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            try {
                LocalDateTime dateTime = LocalDateTime.parse(lines[2], formatter);
                times = dateTime.format(formatter);
                if (times.compareTo(getCurrentTime()) < 0){
                    Main.output += "ERROR: Switch time cannot be in the past!\n";
                }else {
                    switchList.add(times);
                    Collections.sort(switchList);
                    deviceDict.put(lines[1],lines[2]);
                }
            } catch (Exception e) {
                Main.output += "ERROR: Time format is not correct!\n";
            }
        }
    }
    public static void nop(){
        String key ;
        if (switchList.size() == 0 ){
            Main.output += "ERROR: There is nothing to switch!\n";
        }else {
            for (String time : switchList){
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_H:mm:ss");
                    LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
                    if (currentTime.compareTo(dateTime.format(formatter)) < 0){
                        currentTime = dateTime.format(formatter);
                        for (Map.Entry<String, String> entry : deviceDict.entrySet()) {
                            key = entry.getKey();
                            String value =entry.getValue();
                            if (value.equals(time)){
                                int index = Superclass.deviceList.indexOf(key);
                                String stats =  Main.devFeauture.get(index).getStatus();
                                if (stats.equals("On")){
                                    Superclass.switchStatus(key,"Off");
                                }else {
                                    Superclass.switchStatus(key,"On");
                                }
                                deviceDict.remove(key);
                                deleteList.add(time);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    Main.output += "ERROR: Time format is not correct!\n";
                }
                break;
            }
            switchList.removeAll(deleteList);
            deleteList.clear();
        }
    }

    public static String getCurrentTime(){
        return currentTime;
    }

    public static ArrayList<String> getSwitchList() {
        return switchList;
    }

    public static String getTimes() {
        return times;
    }

    public static Map<String, String> getDeviceDict() {
        return deviceDict;
    }

}
