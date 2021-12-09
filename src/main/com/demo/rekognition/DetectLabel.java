package com.demo.rekognition;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class DetectLabel {

    public static void main(String[] args) {

        final String USAGE = "\n" +
                "Usage: " +
                "   <sourceImage>\n\n" +
                "Where:\n" +
                "   sourceImage - the path to the image that contains text (for example, \\AWS\\pic1.png). \n\n";

        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String sourceImage = args[0] ;
        Region region = Region.US_EAST_1;

        ProfileCredentialsProvider profileCredentialsProvider = ProfileCredentialsProvider.builder().profileName("Rekognition").build();
        RekognitionClient rekClient = RekognitionClient.builder()
                .credentialsProvider(profileCredentialsProvider)
                .region(region)
                .build();

        detectLabels(rekClient, sourceImage );
        rekClient.close();
    }

    public static void detectLabels(RekognitionClient rekClient, String sourceImage) {

        try {

            InputStream sourceStream = new FileInputStream(sourceImage);
            SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);

            // Create an Image object for the source image
            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder()
                    .image(souImage)
                    .maxLabels(10)
                    .build();

            DetectLabelsResponse labelsResponse = rekClient.detectLabels(detectLabelsRequest);
            List<Label> labels = labelsResponse.labels();

            System.out.println("Detected labels for the given photo");
            for (Label label: labels) {
                System.out.println(label.name() + ": " + label.confidence().toString());
            }


        } catch (RekognitionException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
