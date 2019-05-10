package de.codecentric.mule.modules.assertobjectequals.internal;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "assertobjectequals")
@org.mule.runtime.extension.api.annotation.Extension(name = "AssertObjectEquals")
@Configurations(Configuration.class)
public class ModuleExtension {

}
