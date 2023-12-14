package com.samply.feedbackagent;

import com.samply.feedbackagent.blaze.SpecimenExtensionUpdater;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SpecimenExtensionUpdaterTests {
    @Test
    void contextLoads() {
    }
    @Test
    void updateExtensions() {
        // Create an instance of the SpecimenExtensionUpdater
        SpecimenExtensionUpdater updater = new SpecimenExtensionUpdater("http://localhost:8091/fhir");

        // Specify the specimen IDs to update and the publication reference URL
        List<String> specimenIds = Arrays.asList("bbmri-0-specimen-0", "bbmri-0-specimen-1");
        String publicationRefUrl = "https://pubexample.com/publication123";

        // Call the updateSpecimenWithExtension method to update the specimens
        updater.updateSpecimenWithExtension(specimenIds, publicationRefUrl);

        // Print a message indicating the update was successful
        System.out.println("Specimens updated successfully!");
    }
}
