/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.compatibility.services.representations;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl;
import org.eclipse.sirius.properties.PropertiesPackage;
import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.PropertiesExtWidgetsReferencePackage;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.viewpoint.description.IdentifiedElement;
import org.eclipse.sirius.viewpoint.description.validation.ValidationPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Utility class used to load an odesign file.
 *
 * @author smonnier
 */
@Service
public class ODesignReader {

    private static final String ENVIRONMENT_ODESIGN_PATH = "model/Environment.odesign"; //$NON-NLS-1$

    private static final String ENVIRONMENT_ODESIGN_URI = "environment:/viewpoint"; //$NON-NLS-1$

    private static final String PROPERTIES_FILE_NAME = "plugin"; //$NON-NLS-1$

    private static final String PROPERTIES_FILE_EXTENSION = ".properties"; //$NON-NLS-1$

    private static final String BUNDLE_LOCALIZATION = "Bundle-Localization"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.getLogger(ODesignReader.class);

    public Optional<Group> read(ClassPathResource classPathResource) {
        Optional<Group> optionalGroup = Optional.empty();
        try (InputStream inputStream = classPathResource.getInputStream()) {
            optionalGroup = this.read(classPathResource.getFilename(), inputStream);
            if (optionalGroup.isPresent()) {
                ResourceBundle resourceBundle = this.findResourceBundle(classPathResource, Locale.getDefault());
                if (resourceBundle != null) {
                    this.localizeGroup(optionalGroup.get(), resourceBundle);
                }
            }
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return optionalGroup;
    }

    private Optional<Group> read(String fileName, InputStream inputStream) {
        ResourceSet resourceSet = new ResourceSetImpl();

        ECrossReferenceAdapter adapter = new ECrossReferenceAdapter();
        resourceSet.eAdapters().add(adapter);

        resourceSet.getPackageRegistry().put(ViewpointPackage.eNS_URI, ViewpointPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(DescriptionPackage.eNS_URI, DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(ValidationPackage.eNS_URI, ValidationPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(org.eclipse.sirius.diagram.description.DescriptionPackage.eNS_URI, org.eclipse.sirius.diagram.description.DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage.eNS_URI,
                org.eclipse.sirius.table.metamodel.table.description.DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(org.eclipse.sirius.tree.description.DescriptionPackage.eNS_URI, org.eclipse.sirius.tree.description.DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(org.eclipse.sirius.diagram.sequence.description.DescriptionPackage.eNS_URI, org.eclipse.sirius.diagram.sequence.description.DescriptionPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(PropertiesPackage.eNS_URI, PropertiesPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(PropertiesExtWidgetsReferencePackage.eNS_URI, PropertiesExtWidgetsReferencePackage.eINSTANCE);

        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("odesign", new XMIResourceFactoryImpl()); //$NON-NLS-1$

        ClassPathResource environmentClassPathResource = new ClassPathResource(ENVIRONMENT_ODESIGN_PATH);
        Resource environmentResource = new XMIResourceFactoryImpl().createResource(URI.createURI(ENVIRONMENT_ODESIGN_URI));
        try (InputStream environmentInputStream = environmentClassPathResource.getInputStream()) {
            environmentResource.load(environmentInputStream, this.getLoadOptions());
            resourceSet.getResources().add(environmentResource);
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        URI uri = URI.createURI(fileName);
        Resource resource = resourceSet.createResource(uri);

        try {
            resource.load(inputStream, this.getLoadOptions());
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        // @formatter:off
        return resource.getContents().stream()
                .findFirst()
                .filter(Group.class::isInstance)
                .map(Group.class::cast);
        // @formatter:on
    }

    private Map<String, Object> getLoadOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put(XMLResource.OPTION_DEFER_ATTACHMENT, Boolean.TRUE);
        options.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
        options.put(XMLResource.OPTION_USE_DEPRECATED_METHODS, Boolean.TRUE);
        options.put(XMLResource.OPTION_USE_PARSER_POOL, new XMLParserPoolImpl());
        options.put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, new HashMap<>());
        return options;

    }

    private ResourceBundle findResourceBundle(ClassPathResource classPathResource, Locale locale) throws IOException {
        URLConnection urlConnection = classPathResource.getURL().openConnection();
        if (urlConnection instanceof JarURLConnection) {
            JarURLConnection jarUrlConnection = (JarURLConnection) urlConnection;
            JarFile jarFile = jarUrlConnection.getJarFile();

            // Determine properties file name
            String propertiesFileName = PROPERTIES_FILE_NAME;
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                Attributes attributes = manifest.getMainAttributes();
                String bundleLocalizationValue = attributes.getValue(BUNDLE_LOCALIZATION);
                if (bundleLocalizationValue != null) {
                    propertiesFileName = bundleLocalizationValue;
                }
            }

            // Find the most specific properties file for the locale
            ZipEntry propertiesEntry = this.findPropertiesEntry(jarFile, propertiesFileName, locale);

            // Return property resource bundle if found
            if (propertiesEntry != null) {
                InputStream inputStream = jarFile.getInputStream(propertiesEntry);
                PropertyResourceBundle bundle = new PropertyResourceBundle(inputStream);
                inputStream.close();
                return bundle;
            }
        }
        return null;
    }

    private ZipEntry findPropertiesEntry(JarFile jarFile, String propertiesFileName, Locale locale) {
        String fileName = propertiesFileName;
        ZipEntry propertiesEntry = jarFile.getEntry(fileName + PROPERTIES_FILE_EXTENSION);

        String language = locale.getLanguage();
        if (language.isEmpty()) {
            return propertiesEntry;
        }
        fileName += '_' + language;
        ZipEntry localizationPropertiesEntry = jarFile.getEntry(fileName + PROPERTIES_FILE_EXTENSION);
        if (localizationPropertiesEntry != null) {
            propertiesEntry = localizationPropertiesEntry;
        }

        String script = locale.getScript();
        if (!script.isEmpty()) {
            fileName += '_' + script;
            localizationPropertiesEntry = jarFile.getEntry(fileName + PROPERTIES_FILE_EXTENSION);
            if (localizationPropertiesEntry != null) {
                propertiesEntry = localizationPropertiesEntry;
            }
        }

        String country = locale.getCountry();
        if (!country.isEmpty()) {
            fileName += '_' + country;
            localizationPropertiesEntry = jarFile.getEntry(fileName + PROPERTIES_FILE_EXTENSION);
            if (localizationPropertiesEntry != null) {
                propertiesEntry = localizationPropertiesEntry;
            }

            String variant = locale.getVariant();
            if (!variant.isEmpty()) {
                fileName += '_' + variant;
                localizationPropertiesEntry = jarFile.getEntry(fileName + PROPERTIES_FILE_EXTENSION);
                if (localizationPropertiesEntry != null) {
                    propertiesEntry = localizationPropertiesEntry;
                }
            }
        }

        return propertiesEntry;
    }

    private void localizeGroup(Group group, ResourceBundle resourceBundle) {
        TreeIterator<EObject> iterator = group.eAllContents();
        while (iterator.hasNext()) {
            EObject object = iterator.next();
            if (object instanceof IdentifiedElement) {
                IdentifiedElement element = (IdentifiedElement) object;
                String label = element.getLabel();
                if (label != null && label.length() >= 2 && label.charAt(0) == '%') {
                    label = label.substring(1);
                    if (label.charAt(0) != '%' && resourceBundle.containsKey(label)) {
                        element.setLabel(resourceBundle.getString(label));
                    }
                }
            }
        }
    }
}
