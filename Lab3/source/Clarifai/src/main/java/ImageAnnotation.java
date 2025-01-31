import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.typography.hershey.HersheyFont;
//import org.apache.spark.{SparkContext, SparkConf}

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;


/**
 * Created by Naga on 24-01-2017.
 */
public class ImageAnnotation {


    public static void main(String[] args) throws IOException {
        final ClarifaiClient client = new ClarifaiBuilder("OgDeCgDk8IUr4PERJshLL-OHDJdDTAcQpY_rC2gA", "neOyFo-lT2GAPk7N7fzTFfljmvI0NvwUWz3VkeIu")
                .client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
                .buildSync(); // or use .build() to get a Future<ClarifaiClient>
        client.getToken();

        File file = new File("output/frames");
        File[] files = file.listFiles();
        for (int i=0; i<files.length;i++){
            ClarifaiResponse response = client.getDefaultModels().generalModel().predict()
                    .withInputs(
                            ClarifaiInput.forImage(ClarifaiImage.of(files[i]))
                    )
                    .executeSync();
            List<ClarifaiOutput<Concept>> predictions = (List<ClarifaiOutput<Concept>>) response.get();
            MBFImage image = ImageUtilities.readMBF(files[i]);
            int x = image.getWidth();
            int y = image.getHeight();

            System.out.println("*************" + files[i] + "***********");
            List<Concept> data = predictions.get(0).data();


            for (int j = 0; j < data.size(); j++) {
              //  System.out.println(data.get(j).name() + " - " + data.get(j).value());
                //image.drawText(data.get(j).name(), (int)Math.floor(Math.random()*x), (int) Math.floor(Math.random()*y), HersheyFont.ASTROLOGY, 20, RGBColour.RED);
                String name = "output/tags/new";
               // st.add(data.get(j).name());
                File outputFile = new File(name);
               //val st=
            }

            //DisplayUtilities.displayName(image, "image" + i);
        }


    }
}
