package pl.dele.testy;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;

import java.io.File;
import java.io.IOException;

public class MyExample {

    public static final String FOLDER_PATH = "photos";

    public static void main(String[] args) throws IOException, ImageReadException {
        //metadataExample(new File(FOLDER_PATH + "/20200722_102330.jpg"));

        File folder = new File(FOLDER_PATH);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".jpg"));

        for (int i = 0; i < listOfFiles.length; ++i){
            if (listOfFiles[i].isFile()){
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()){
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    public static void metadataExample(final File file) throws ImageReadException,
            IOException {

        final ImageMetadata metadata = Imaging.getMetadata(file);
        final TagInfo tagInfo = ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL;
        if (metadata instanceof JpegImageMetadata) {

            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

            final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
            if (field == null) {
                System.out.println(tagInfo.name + ": " + "Not Found.");
            } else {
                System.out.println(tagInfo.name + ": "
                        + field.getValueDescription());
            }
        }
    }


}
