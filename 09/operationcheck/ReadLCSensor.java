import lejos.nxt.*;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;

public class ReadLCSensor
{
  public static void main (String[] aArg) 
  {
    // ライトセンサＡ（左）
    LightSensor lightA = new LightSensor(SensorPort.S1, true);
    // ライトセンサＢ（中央）
    ColorHTSensor colorBsense = new ColorHTSensor(SensorPort.S2);
    // ライトセンサＣ（右）
    LightSensor lightC = new LightSensor(SensorPort.S3, true);
	String[] colorNames = new String[]{"-","red","grn","blu","yel","mag","org","wht","blc","pin","gry","lgry","dgry","cyn"};
	  
   while (true) {
      
      // ESCAPE ボタン押下時に停止
      if (Button.ESCAPE.isDown())
        break;

	   LCD.drawString("LightA = "+lightA.readNormalizedValue(), 0, 0);
	   Color colorB = colorBsense.getColor();
	   String colorBstr = "ColorB = "+colorNames[colorBsense.getColorID()+1];
	   LCD.drawString(colorBstr, 0, 1);
	   LCD.drawString("LightC = "+lightC.readNormalizedValue(), 0, 2);
    }
  }
}
