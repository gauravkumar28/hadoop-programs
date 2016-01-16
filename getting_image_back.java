import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

 import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.*;
 
public class getting_image_back {
 

	public static void main(String[] args) throws IOException{
	
		File f = new File("./result/part-r-00000");
InputStream in = new FileInputStream(f);
// convert the inpustream to a byte array
byte[] buf = null;
try {
    buf = new byte[in.available()];
    while (in.read(buf) != -1) {
    }
} catch (Exception e) {
    System.out.println("Got exception while is -> bytearr conversion: " + e);
}
// now convert it to a bytearrayinputstream
for(int i=0;i<buf.length;i++)
{System.out.print(buf[i]+"  ");


}
	}
}
