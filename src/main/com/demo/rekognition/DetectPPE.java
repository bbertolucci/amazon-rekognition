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

public class DetectPPE {

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

        detectPPE(rekClient, sourceImage );
        rekClient.close();
    }

    public static void detectPPE(RekognitionClient rekClient, String sourceImage) {

        try {

            InputStream sourceStream = new FileInputStream(sourceImage);
            SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);

            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            ProtectiveEquipmentSummarizationAttributes summarizationAttributes = ProtectiveEquipmentSummarizationAttributes.builder()
                    .minConfidence(80F)
                    .requiredEquipmentTypesWithStrings("FACE_COVER") // , "HAND_COVER", "HEAD_COVER"
                    .build();

            DetectProtectiveEquipmentRequest request = DetectProtectiveEquipmentRequest.builder()
                    .image(souImage)
                    .summarizationAttributes(summarizationAttributes)
                    .build();

            DetectProtectiveEquipmentResponse result = rekClient.detectProtectiveEquipment(request);
            List<ProtectiveEquipmentPerson> persons = result.persons();

            for (ProtectiveEquipmentPerson person: persons) {
                System.out.println("ID: " + person.id());
                List<ProtectiveEquipmentBodyPart> bodyParts=person.bodyParts();
                if (bodyParts.isEmpty()){
                    System.out.println("\tNo body parts detected");
                } else
                    for (ProtectiveEquipmentBodyPart bodyPart: bodyParts) {
                        System.out.println("\t" + bodyPart.name() + ". Confidence: " + bodyPart.confidence().toString());
                        List<EquipmentDetection> equipmentDetections=bodyPart.equipmentDetections();

                        if (equipmentDetections.isEmpty()){
                            System.out.println("\t\tNo PPE Detected on " + bodyPart.name());
                        } else {
                            for (EquipmentDetection item: equipmentDetections) {
                                System.out.println("\t\tItem: " + item.type() + ". Confidence: " + item.confidence().toString());
                                System.out.println("\t\tCovers body part: "
                                        + item.coversBodyPart().value().toString() + ". Confidence: " + item.coversBodyPart().confidence().toString());

                                System.out.println("\t\tBounding Box");
                                BoundingBox box =item.boundingBox();

                                System.out.println("\t\tLeft: " +box.left().toString());
                                System.out.println("\t\tTop: " + box.top().toString());
                                System.out.println("\t\tWidth: " + box.width().toString());
                                System.out.println("\t\tHeight: " + box.height().toString());
                                System.out.println("\t\tConfidence: " + item.confidence().toString());
                                System.out.println();
                            }
                        }
                    }
            }
            System.out.println("Person ID Summary\n-----------------");

            System.out.println("With required equipment "+ result.summary().personsWithRequiredEquipment());
            System.out.println("Without required equipment "+ result.summary().personsWithoutRequiredEquipment());
            System.out.println("Indeterminate "+ result.summary().personsIndeterminate());

        } catch (RekognitionException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
