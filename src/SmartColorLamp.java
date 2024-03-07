
public class SmartColorLamp extends SmartLamp {
    public int colorCode = 0;
    public void add(String[] line) {
        if (deviceList.contains(line[2])){
            Main.output += "ERROR: There is already a smart device with same name!\n";
        }
        else if (line.length == 3) {
            super.add(line);
        } else if (line.length == 4) {
            super.add(line);
        } else if (line.length == 6) {
            if (deviceList.contains(line[2])) {
            } else if (line[3].equals("On") || line[3].equals("Off")) {
                try {
                    if (line[4].contains("x")) {
                        int kelvin = Integer.parseInt(line[4].substring(2), 16);
                        int bright = Integer.parseInt(line[5]);
                        if (kelvin >= 0x0 && kelvin <= 0xFFFFFF && bright < 100 && bright > 0) {
                            deviceList.add(line[2]);
                            name = line[2];
                            status = line[3];
                            colorCode = kelvin;
                            kelvinValue = 0;
                            brightness = bright;
                            correctness =true;
                        } else if (kelvin < 0x0 || kelvin > 0xFFFFFF) {
                            Main.output += "ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n";
                        } else if (bright > 100) {
                            Main.output += "ERROR: Brightness must be in range of 0%-100%!\n";
                        } else {
                            Main.output += "ERROR: Brightness value must be a positive number!\n";
                        }
                    } else{
                        super.add(line);
                    }
                } catch (NumberFormatException e) {
                    Main.output += "ERROR: Erroneous Command!\n";
                }
            } else {
                Main.output += "ERROR: Erroneous Command!\n";
            }
        } else {
            Main.output += "ERROR: Erroneous Command!\n";
        }
    }
    public void setColor(String[] line){
        if (line.length != 4){
            Main.output += "ERROR: Erroneous Command!\n";
        } else if (!deviceList.contains(line[1])) {
            Main.output += "ERROR: There is not such a device!\n";
        }else if (Main.devFeauture.get(deviceList.indexOf(line[1])) instanceof SmartColorLamp){
            Object obj = Main.devFeauture.get(deviceList.indexOf(line[1]));
            try {
                int bright = Integer.parseInt(line[3]);
                int codeValue =Integer.parseInt(line[2].substring(2), 16);
                if (bright <= 100 && bright>= 0){
                    String[] colorLine = {line[0],line[1],line[2]};
                    setColorCode(colorLine);
                    if (String.format("0x%06X",((SmartColorLamp) obj).getColorCode()).equals(line[2])){
                        String[] brightLine = {line[0],line[1],line[3]};
                        super.setBrightness(brightLine);
                    }
                }else if (bright> 100){
                    if (codeValue >0xFFFFFF || codeValue<0X0){
                        Main.output += "ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n";
                    }else {
                        Main.output += "ERROR: Brightness must be in range of 0%-100%!\n";
                    }
                }else {
                    if (codeValue >0xFFFFFF || codeValue<0X0){
                        Main.output += "ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n";
                    }else {
                        Main.output += "ERROR: Brightness value must be a positive number!\n";
                    }
                }
            }catch (Exception e){
                Main.output += "ERROR: Erroneous Command!\n";
            }
        }else {
            Main.output += "ERROR: This device is not a smart color lamp!\n";
        }

    }
    @Override
    public void setBrightness(String[] line) {
        super.setBrightness(line);
    }

    @Override
    public void setKelvinValue(String[] line) {
        super.setKelvinValue(line);
    }
    public void setColorCode(String[] line){
        int index = deviceList.indexOf(line[1]);
        if (line.length != 3){
            Main.output += "ERROR: Erroneous Command!\n";
        } else if (!deviceList.contains(line[1])) {
            Main.output += "ERROR: There is not such a device!\n";
        } else if (Main.devFeauture.get(index) instanceof SmartColorLamp) {
            Object colorObj = Main.devFeauture.get(index);
            try {
                if (line[2].contains("x") && ((SmartColorLamp) colorObj).getKelvinValue() ==0){
                    int codeValue =Integer.parseInt(line[2].substring(2), 16);
                    if (codeValue >= 0x0 && codeValue <= 0xFFFFFF){
                        ((SmartColorLamp) colorObj).colorCode = codeValue;
                    } else {
                        Main.output += "ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n";
                    }
                }else {
                    ((SmartColorLamp) colorObj).kelvinValue = 0;
                    int codeValue =Integer.parseInt(line[2].substring(2), 16);
                    if (codeValue >= 0x0 && codeValue <= 0xFFFFFF){
                        ((SmartColorLamp) colorObj).colorCode = codeValue;
                    } else {
                        Main.output += "ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n";
                    }
                }
            }catch (NumberFormatException e){
                Main.output += "ERROR: Erroneous Command!\n";
            }
        }else {
            Main.output += "ERROR: This device is not a smart color lamp!\n";
        }
    }
    public int getColorCode(){
        return colorCode;
    }
    public int getBrightness() {
        return brightness;
    }
    void setName(String name) {
        Superclass.name = name;
    }
}
