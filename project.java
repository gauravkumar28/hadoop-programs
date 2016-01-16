import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
 
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.ImageTransformer;
import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
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
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path; 
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
 
public class project {
       
        public static class ImageMapper extends Mapper<Text, BytesWritable, Text, BytesWritable>{
 public static final String XML_FILE = 	"/home/hduser/hadoop2/programs/haarcascade_frontalface_default.xml";
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

}mage_byte=value.getBytes();
BufferedImage imag=ImageIO.read(new ByteArrayInputStream(image_byte));
*/

//Configuration conf =context.getConfiguration();
//String XML_FILE=conf.get("/user/hduser/input/haarcascade_frontalface_default.xml");
IplImage image1 = IplImage.createFrom(imag);
/* for face detection*/   

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

 		
//		cvSaveImage("/user/hduser/result.jpg", image1);



/*   for cany edge detection   */
/*
IplImage imageGray = cvCreateImage(cvSize(image1.width(), image1.height()), IPL_DEPTH_8U, 1);
        
        //convert image to grayscale
        cvCvtColor(image1, imageGray, CV_BGR2GRAY );
         
        //create canvas frame named 'Demo'
//        CanvasFrame canvas = new CanvasFrame("RGB");
  //      CanvasFrame canvas2 = new CanvasFrame("Gray"); 
        //Show image in canvas frame
    //    canvas.showImage(image);
      //  canvas2.showImage(imageGray);
		
        IplImage gray = new IplImage(imageGray);
        
        
        IplImage edge = cvCreateImage(cvSize(gray.width(), gray.height()), IPL_DEPTH_8U, 1);
        
        //run Canny edge detection..
        cvCanny(gray, edge, 140, 240, 7);

*/
BufferedImage c = image1.getBufferedImage();
 /*FileSystem dfs = FileSystem.get(context.getConfiguration());
                Path newimgpath = new Path("/user/hduser/input/demo200.jpg");
                dfs.createNewFile(newimgpath);
                FSDataOutputStream ofs = dfs.create(newimgpath);
                ImageIO.write(imag, "jpg", ofs);*/
//new File("gaurav.png")



long start=System.currentTimeMillis();
FileSystem dfs = FileSystem.get(context.getConfiguration());
		Path newimgpath = new Path(context.getWorkingDirectory(),String.valueOf(start)+".jpg");
		dfs.createNewFile(newimgpath);
		FSDataOutputStream ofs = dfs.create(newimgpath);
		ImageIO.write(c, "jpg", ofs);





// ImageIO.write(imag, "jpg", new File("/user/hduser/output/gaurav.jpg"));

ByteArrayOutputStream baos = new ByteArrayOutputStream();
     ImageIO.write(c, "jpg", baos);
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
//Configuration conf = new Configuration();
/*
JobConf job1 = new JobConf();
DistributedCache.addCacheFile(new URI("/user/hduser/input/haarcascade_frontalface_default.xml"),job1);
//conf.addResource(new Path("/user/hduser/input/haarcascade_frontalface_default.xml"));
//conf.addResource(new Path("/user/hduser/input/haarcascade_frontalface_default.xml"));               
                //This is the line that makes the hadoop run locally
                //conf.set("mapred.job.tracker", "local");
 */
                String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
                if (otherArgs.length != 2) {
                        System.err.println("Usage: project <in> <out>");
                        System.exit(2);
                }
                Job job = new Job(conf, "face detection");
                job.setJarByClass(project.class);
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
