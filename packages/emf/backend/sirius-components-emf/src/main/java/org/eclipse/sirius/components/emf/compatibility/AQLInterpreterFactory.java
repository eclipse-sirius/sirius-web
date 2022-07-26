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
package org.eclipse.sirius.components.emf.compatibility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.compatibility.api.IAQLInterpreterFactory;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.viewpoint.description.JavaExtension;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This class is used to create a new AQL interpreter using all the Java classes defined in a viewpoint.
 *
 * @author sbegaudeau
 */
@Service
public class AQLInterpreterFactory implements IAQLInterpreterFactory {

    private final Logger logger = LoggerFactory.getLogger(AQLInterpreterFactory.class);

    @Override
    public AQLInterpreter create(DiagramDescription diagramDescription) {
        // @formatter:off
        var javaClasses = Optional.of(diagramDescription.eContainer())
                .filter(Viewpoint.class::isInstance)
                .map(Viewpoint.class::cast)
                .map(this::getJavaServices)
                .orElse(new ArrayList<>());
        // @formatter:on

        List<EPackage> ePackages = diagramDescription.getMetamodel();
        return new AQLInterpreter(javaClasses, ePackages);
    }

    @Override
    public AQLInterpreter create(ViewExtensionDescription viewExtensionDescription) {
        // @formatter:off
        List<Viewpoint> viewpoints = Optional.of(viewExtensionDescription.eContainer())
                .filter(Group.class::isInstance)
                .map(Group.class::cast)
                .map(Group::getOwnedViewpoints)
                .orElse(new BasicEList<>());

        var javaClasses = viewpoints.stream()
                .map(this::getJavaServices)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on

        List<EPackage> ePackages = viewExtensionDescription.getMetamodels();

        return new AQLInterpreter(javaClasses, ePackages);
    }

    private List<Class<?>> getJavaServices(Viewpoint viewpoint) {
        List<Class<?>> classes = new ArrayList<>();

        // @formatter:off
        List<String> qualifiedNames = viewpoint.getOwnedJavaExtensions().stream()
                .map(JavaExtension::getQualifiedClassName)
                .collect(Collectors.toList());
        // @formatter:on

        for (String qualifiedName : qualifiedNames) {
            if (qualifiedName.contains("::")) { //$NON-NLS-1$
                this.logger.warn("Sirius Web does not support Acceleo-style :: references"); //$NON-NLS-1$
                continue;
            }
            try {
                Class<?> aClass = Class.forName(qualifiedName);
                classes.add(aClass);
            } catch (ClassNotFoundException exception) {
                this.logger.warn("Could not load class '{}'", qualifiedName); //$NON-NLS-1$
            } catch (NoClassDefFoundError exception) {
                this.logger.error("Could not load class '{}'; not all dependencies could be " //$NON-NLS-1$
                        + "instantiated by the JVM: {}", qualifiedName, exception.getMessage()); //$NON-NLS-1$
            }
        }

        return classes;
    }
}
