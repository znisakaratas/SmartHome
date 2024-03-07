public class SmartLamp extends Superclass {
    public int kelvinValue = 4000;
    public int brightness = 100;
    public void add(String[] line){
        if (line.length < 3 || line.length > 6){
            Main.output += "ERROR:Erroneous Command!\n";
        } else if (deviceList.contains(line[2])) {
            Main.output += "ERROR: There is already a smart device with same name!\n";
        } else if (line.length== 3) {
            deviceList.add(line[2]);
            name = line[2];
            correctness = true;
        } else if (line.length == 4) {
            if (line[3].equals("On") || line[3].equals("Off")) {
                deviceList.add(line[2]);
                name = line[2];
                status = line[3];
                correctness = true;
            }else {
                Main.output += "ERROR:Erroneous Command!\n";
            }
        } else if (line.length == 6) {
            if (line[3].equals("On") || line[3].equals("Off")) {
                try {
                    int kelvin= Integer.parseInt(line[4]); 
                    int bright= Integer.parseInt(line[5]);
                    if (kelvin <= 6500 && kelvin >=2000 && bright <=100 && bright>= 0){
                        deviceList.add(line[2]);
                        name = line[2];
                        status = line[3];
                        kelvinValue = kelvin;
                        brightness = bright;
                        correctness = true;
                    } else if (kelvin <= 0) {
                        Main.output +="ERROR: Kelvin value must be a positive number!\n";
                    } else if (kelvin<2000 || kelvin > 6500) {
                        Main.output += "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
                    } else if (bright>100) {
                        Main.output +="ERROR: Brightness must be in range of 0%-100%!\n";
                    }else{
                        Main.output += "ERROR: Brightness value must be a positive number!\n";
                    }
                }catch (NumberFormatException e){
                    Main.output += "ERROR:Erroneous Command!\n";
                }
            }else {
                Main.output += "ERROR:Erroneous Command!\n";
            }
        }else {
            Main.output += "ERROR:Erroneous Command!\n";
        }
    }
    public void setBrightness(String[] line){
        if (line.length != 3){
            Main.output += "ERROR:Erroneous Command!\n";
        } else if (!deviceList.contains(line[1])) {
            Main.output += "ERROR: There is not such a device!\n";
        } else if (Main.devFeauture.get(deviceList.indexOf(line[1])) instanceof SmartLamp) {
            Object lampObject = Main.devFeauture.get(deviceList.indexOf(line[1]));
            try {
                int bright = Integer.parseInt(line[2]);
                if (bright > 100){
                    Main.output+= "ERROR: Brightness must be in range of 0%-100%!\n";
                } else if (bright < 0) {
                    Main.output+= "ERROR: Brightness value must be a positive number!\n";
                }else {
                    ((SmartLamp) lampObject).brightness = bright;
                }
            }catch (NumberFormatException e){
                Main.output += "ERROR:Erroneous Command!\n";
            }
        } else if (Main.devFeauture.get(deviceList.indexOf(line[1])) instanceof SmartColorLamp) {
            Object clampObject = Main.devFeauture.get(deviceList.indexOf(line[1]));
            try {
                int bright = Integer.parseInt(line[2]);
                if (bright > 100){
                    Main.output+= "ERROR: Brightness must be in range of 0%-100%!\n";
                } else if (bright < 0) {
                    Main.output+= "ERROR: Brightness value must be a positive number!\n";
                }else {
                    ((SmartColorLamp) clampObject).brightness = bright;
                }
            }catch (NumberFormatException e){
                Main.output += "ERROR:Erroneous Command!\n";
            }
        }else {
            Main.output+= "ERROR: This device is not a smart lamp!\n";
        }
    }
    public void setWhite(String[] line){
        if (line.length!= 4){
            Main.output+= "ERROR: Erroneous Command!\n";
        } else if (!deviceList.contains(line[1])){
            Main.output+= "ERROR: There is not such a device!\n";
        } else if (Main.devFeauture.get(deviceList.indexOf(line[1])) instanceof SmartLamp) {
            Object obj = Main.devFeauture.get(deviceList.indexOf(line[1]));
            String[] kelvinList = {line[0],line[1],line[2]};
            try {
                int bright = Integer.parseInt(line[3]);
                int codeValue =Integer.parseInt(line[2]);
                if (bright>= 0 && bright <= 100){
                    setKelvinValue(kelvinList);
                    if (((SmartLamp) obj).getKelvinValue() == Integer.parseInt(line[2])){
                        String[] brightList = {line[0],line[1],line[3]};
                        setBrightness(brightList);
                    }
                }else if (bright> 100){
                    if (codeValue >6500 || codeValue<2000){
                        Main.output+= "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
                    }else {
                        Main.output+= "ERROR: Brightness must be in range of 0%-100%!\n";
                    }
                }else {
                    if (codeValue >6500 || codeValue<2000){
                        Main.output+= "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
                    }else {
                        Main.output+= "ERROR: Brightness value must be a positive number!\n";
                    }
                }
            }catch (Exception e){
                Main.output+= "ERROR: Erroneous Command!\n";
            }

        } else if (Main.devFeauture.get(deviceList.indexOf(line[1])) instanceof SmartColorLamp) {
            Object obj = Main.devFeauture.get(deviceList.indexOf(line[1]));
            String[] kelvinList = {line[0],line[1],line[2]};
            try {
                int bright = Integer.parseInt(line[3]);
                int codeValue =Integer.parseInt(line[2]);
                if (bright>= 0 && bright <= 100){
                    setKelvinValue(kelvinList);
                    if (((SmartColorLamp) obj).getKelvinValue() == Integer.parseInt(line[2])){
                        String[] brightList = {line[0],line[1],line[3]};
                        setBrightness(brightList);
                    }
                }else if (bright> 100){
                    if (codeValue >6500 || codeValue<2000){
                        Main.output+= "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
                    }else {
                        Main.output+= "ERROR: Brightness must be in range of 0%-100%!\n";
                    }
                }else {
                    if (codeValue >6500 || codeValue<2000){
                        Main.output+= "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
                    }else {
                        Main.output+= "ERROR: Brightness value must be a positive number!\n";
                    }
                }
            }catch (Exception e){
                Main.output+= "ERROR: Erroneous Command!\n";
            }
        }else {
            Main.output+= "ERROR: This device is not a smart lamp!\n";
        }
    }
    public void setKelvinValue(String[] line) {
        if (line.length!= 3){
            Main.output+= "ERROR: Erroneous Command!\n";
        } else if (!deviceList.contains(line[1])) {
            Main.output+= "ERROR: There is not such a device!\n";
        }else if (Main.devFeauture.get(deviceList.indexOf(line[1])) instanceof SmartLamp){
            try {
                Object lampObj = Main.devFeauture.get(deviceList.indexOf(line[1]));
                int kelvin = Integer.parseInt(line[2]);
                if (kelvin <= 0){
                    Main.output+= "ERROR: Kelvin value must be a positive number!\n";
                } else if (kelvin < 2000 || kelvin > 6500) {
                    Main.output+= "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
                }else {
                    ((SmartLamp) lampObj).kelvinValue = kelvin;
                }
            }catch (NumberFormatException e){
                Main.output+= "ERROR: Erroneous Command!\n";
            }
        } else if (Main.devFeauture.get(deviceList.indexOf(line[1])) instanceof SmartColorLamp) {
            try {
                Object lampObj = Main.devFeauture.get(deviceList.indexOf(line[1]));
                int kelvin = Integer.parseInt(line[2]);
                if (((SmartColorLamp) lampObj).getColorCode() > 0 && ((SmartColorLamp) lampObj).getKelvinValue()== 0 ){
                    ((SmartColorLamp) lampObj).colorCode = 0;
                    ((SmartColorLamp) lampObj).kelvinValue = kelvin;
                }else {
                    if (kelvin <= 0){
                        Main.output+= "ERROR: Kelvin value must be a positive number!\n";
                    } else if (kelvin < 2000 || kelvin > 6500) {
                        Main.output+= "ERROR: Kelvin value must be in range of 2000K-6500K!\n";
                    }else {
                        ((SmartColorLamp) lampObj).kelvinValue = kelvin;
                    }
                }
            }catch (NumberFormatException e){
                Main.output+= "ERROR: Erroneous Command!\n";
            }
        }else {
            Main.output+= "ERROR: This device is not a smart lamp!\n";
        }
    }
    public int getBrightness() {
        return brightness;
    }

    public int getKelvinValue() {
        return kelvinValue;
    }
    public String getStatus(){
        return status;
    }
    public boolean getCorrectness(){
        return correctness;
    }
    void setName(String name) {
        Superclass.name = name;
    }


}
