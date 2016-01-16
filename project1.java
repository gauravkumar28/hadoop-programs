import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
 
public class project1 {
       
        public static class ImageMapper extends Mapper<Text, BytesWritable, Text, BytesWritable>{
 public static final String XML_FILE = 	"/user/hduser/input/haarcascade_frontalface_default.xml";
                public void map(Text key, BytesWritable value, Context context) throws IOException,InterruptedException {
                       /* //get the md5 for this specific file
                String md5Str;
                        try {
                                md5Str = calculateMd5(value.getBytes());
                        } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                                context.setStatus("Internal error - can't find the algorithm for calculating the md5");
                                return;
                        }
                        Text md5Text = new Text(md5Str);
               
                        //put the file in the map where the md5 is the key, so duplicates will
                        // be grouped together for the reduce function
                context.write(md5Text, key);
*/

byte[] image_byte=value.getBytes();
BufferedImage imag=ImageIO.read(new ByteArrayInputStream(image_byte));

/* public void dumbTest(BufferedImage a)
{

   IplImage b = IplImage.createFrom(a);  //convert BufferedImage "a" into IplImage "b"
   BufferedImage c = b.getBufferedImage();  // convert IplImage "b" from previous step, back into a BufferedImage "c"
// "c" should be the same as "a", but when debugging stepping into, I realised they were not!

}*/

IplImage image1 = IplImage.createFrom(imag);

CvHaarClassifierCascade cascade = new 
				CvHaarClassifierCascade(cvLoad(XML_FILE));
		CvMemStorage storage = CvMemStorage.create();
		CvSeq sign = cvHaarDetectObjects(
				image1,
				cascade,
				storage,
				1.5,
				3,
				CV_HAAR_DO_CANNY_PRUNING);
 
		cvClearMemStorage(storage);
 
		int total_Faces = sign.total();	

 
		for(int i = 0; i < total_Faces; i++){
			CvRect r = new CvRect(cvGetSeqElem(sign, i));
			cvRectangle (
					image1,
					cvPoint(r.x(), r.y()),
					cvPoint(r.width() + r.x(), r.height() + r.y()),
					CvScalar.RED,
					2,
					CV_AA,
					0);
 
		}

 		
		//cvSaveImage("result.jpg", image1);

BufferedImage c = image1.getBufferedImage();

ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(image1, "png", baos);
     byte[] res=baos.toByteArray();
//BytesWritable b;
//b.set(res,0,res.getByteCount());
context.write(key,new BytesWritable(res));

                }
               
               
               
           
        }
 
        public static class ImageReducer extends Reducer<Text,BytesWritable,Text,BytesWritable> {
 
              public void reduce(Text key, BytesWritable value, Context context)
                                                        throws IOException, InterruptedException {
                        //Key here is the md5 hash while the values are all the image files that
                        // are associated with it. for each md5 value we need to take only
                        // one file (the first)
                       /* Text imageFilePath = null;
                        for (Text filePath : values) {
                                imageFilePath = filePath;
                                break;//only the first one
                        }
                        // In the result file the key will be again the image file path.*/
//byte[]  b=value.getBytes();
                        context.write(key,value);
                }

        }
 
        public static void main(String[] args) throws Exception {
                Configuration conf = new Configuration();
               
                //This is the line that makes the hadoop run locally
                //conf.set("mapred.job.tracker", "local");
 
                String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
                if (otherArgs.length != 2) {
                        System.err.println("Usage: wordcount <in> <out>");
                        System.exit(2);
                }
                Job job = new Job(conf, "face detection");
                job.setJarByClass(project1.class);
                job.setInputFormatClass(SequenceFileInputFormat.class);
                job.setMapperClass(ImageMapper.class);
                job.setReducerClass(ImageReducer.class);
                //job.setNumReduceTasks(2);
                job.setOutputKeyClass(Text.class);
                //job.setOutputValueClass(Text.class);
		job.setOutputValueClass(BytesWritable.class);
                FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
                FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
                System.exit(job.waitForCompletion(true) ? 0 : 1);
               
        }
}
