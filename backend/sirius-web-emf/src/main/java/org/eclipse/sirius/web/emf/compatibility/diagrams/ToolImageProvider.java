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
package org.eclipse.sirius.web.emf.compatibility.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.tool.ContainerCreationDescription;
import org.eclipse.sirius.diagram.description.tool.EdgeCreationDescription;
import org.eclipse.sirius.diagram.description.tool.NodeCreationDescription;
import org.eclipse.sirius.ecore.extender.business.api.accessor.EcoreMetamodelDescriptor;
import org.eclipse.sirius.ecore.extender.business.internal.accessor.ecore.EcoreIntrinsicExtender;
import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.emf.compatibility.EPackageService;

/**
 * Class used to compute the image of a tool.
 *
 * @author pcdavid
 * @author sbegaudeau
 */
public class ToolImageProvider implements Supplier<String> {

    private static final String ICON_PATH = "iconPath"; //$NON-NLS-1$

    private static final Pattern SEPARATOR = Pattern.compile("(::?|\\.)"); //$NON-NLS-1$

    private final IObjectService objectService;

    private final EPackage.Registry ePackageRegistry;

    private final AbstractToolDescription abstractToolDescription;

    public ToolImageProvider(IObjectService objectService, EPackage.Registry ePackageRegistry, AbstractToolDescription abstractToolDescription) {
        this.objectService = Objects.requireNonNull(objectService);
        this.ePackageRegistry = Objects.requireNonNull(ePackageRegistry);
        this.abstractToolDescription = Objects.requireNonNull(abstractToolDescription);
    }

    @Override
    public String get() {
        // @formatter:off
        return this.getImagePathFromIconPath()
                .or(this::getImagePathFromDomainClass)
                .orElse(""); //$NON-NLS-1$
        // @formatter:on
    }

    private Optional<String> getImagePathFromIconPath() {
        var optionalIconPathEAttribute = Optional.ofNullable(this.abstractToolDescription.eClass().getEStructuralFeature(ICON_PATH));

        // @formatter:off
        return optionalIconPathEAttribute.map(iconPathEAttribute -> this.abstractToolDescription.eGet(iconPathEAttribute))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(iconPath -> !iconPath.isBlank())
                .map(this::normalize);
        // @formatter:on
    }

    private String normalize(String iconPath) {
        String path = iconPath;
        if (!iconPath.startsWith("/")) { //$NON-NLS-1$
            path = "/" + iconPath; //$NON-NLS-1$
        }

        int index = path.indexOf('/', 1);
        if (index != -1) {
            path = path.substring(index);
        }
        return path;
    }

    private Optional<String> getImagePathFromDomainClass() {
        // @formatter:off
        var optionalInstance = this.getMappings().stream()
                .map(this::getDomainClass)
                .flatMap(Optional::stream)
                .filter(domainClass -> !domainClass.isBlank())
                .findFirst()
                .flatMap(this::getInstance);
        // @formatter:on

        return optionalInstance.map(instance -> this.objectService.getImagePath(optionalInstance.get()));
    }

    private List<DiagramElementMapping> getMappings() {
        List<DiagramElementMapping> mappings = new ArrayList<>();

        if (this.abstractToolDescription instanceof NodeCreationDescription) {
            mappings.addAll(((NodeCreationDescription) this.abstractToolDescription).getNodeMappings());
        } else if (this.abstractToolDescription instanceof ContainerCreationDescription) {
            mappings.addAll(((ContainerCreationDescription) this.abstractToolDescription).getContainerMappings());
        } else if (this.abstractToolDescription instanceof EdgeCreationDescription) {
            mappings.addAll(((EdgeCreationDescription) this.abstractToolDescription).getEdgeMappings());
        }

        return mappings;
    }

    private Optional<String> getDomainClass(DiagramElementMapping diagramElementMapping) {
        Optional<String> optionalDomainClass = Optional.empty();
        if (diagramElementMapping instanceof AbstractNodeMapping) {
            AbstractNodeMapping mapping = (AbstractNodeMapping) diagramElementMapping;
            optionalDomainClass = Optional.ofNullable(mapping.getDomainClass());
        } else if (diagramElementMapping instanceof EdgeMapping) {
            EdgeMapping mapping = (EdgeMapping) diagramElementMapping;
            if (mapping.isUseDomainElement()) {
                optionalDomainClass = Optional.ofNullable(mapping.getDomainClass());
            }
        }
        return optionalDomainClass;
    }

    private Optional<EObject> getInstance(String domainClass) {
        Matcher matcher = SEPARATOR.matcher(domainClass);
        if (matcher.find()) {
            String packageName = domainClass.substring(0, matcher.start());
            String className = domainClass.substring(matcher.end());

            var optionalEPackage = new EPackageService().findEPackage(this.ePackageRegistry, packageName);
            if (optionalEPackage.isPresent()) {
                EPackage ePackage = optionalEPackage.get();
                EcoreIntrinsicExtender ecoreIntrinsicExtender = new EcoreIntrinsicExtender();
                ecoreIntrinsicExtender.updateMetamodels(List.of(new EcoreMetamodelDescriptor(ePackage)));
                EObject instance = ecoreIntrinsicExtender.createInstance(className);
                return Optional.ofNullable(instance);
            }
        }
        return Optional.empty();
    }

}
