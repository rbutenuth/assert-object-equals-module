package de.codecentric.mule.modules.assertobjectequals.internal;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "assertobjectequals")
@Extension(name = "AssertObjectEquals")
@Configurations(AssertObjectEqualsConfiguration.class)
public class AssertObjectEqualsExtension {

    /**
     * This method is used for calling implementation logic ONLY in testing/development purposes.
     * Once we reach "production" stage this method should be completely removed.
     */
    public static void main(String[] args) throws Exception{

        //This is the expected file from my local disk. I'm imitating sending of real file from srt/test/resources directory to the mule component.
        File expectedFile = new File("/Users/ddanijel/AnypointStudio/studio-workspace/test-aoe-module/src/test/resources/expected-file.xml");
        FileInputStream expectedFileInputStream = new FileInputStream(expectedFile);
        BufferedInputStream expectedFileBuffered = new BufferedInputStream(expectedFileInputStream);

        //This is the actual file from my local disk. I'm imitating sending of real file from srt/test/resources directory to the mule component.
        File actualFile = new File("/Users/ddanijel/AnypointStudio/studio-workspace/test-aoe-module/src/test/resources/actual-file.xml");
        FileInputStream actualFileInputStream = new FileInputStream(actualFile);
        BufferedInputStream actualFileBuffered = new BufferedInputStream(actualFileInputStream);

        List<String> additionalOptions = new ArrayList<>();
        additionalOptions.add("['addresses'][#]['timestamp'] ignore");

        AssertObjectEqualsOperations aoe = new AssertObjectEqualsOperations();
        //aoe.compareObjects(expectedFileBuffered, actualFileBuffered, additionalOptions);
        aoe.compareXml(expectedFileBuffered, actualFileBuffered, "IGNORE_COMMENTS");
    }
}
