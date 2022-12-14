/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.compatibility.emf.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.compatibility.emf.EPackageService;
import org.eclipse.sirius.components.compatibility.services.diagrams.api.IToolImageProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.tool.ContainerCreationDescription;
import org.eclipse.sirius.diagram.description.tool.EdgeCreationDescription;
import org.eclipse.sirius.diagram.description.tool.NodeCreationDescription;
import org.eclipse.sirius.ecore.extender.business.api.accessor.EcoreMetamodelDescriptor;
import org.eclipse.sirius.ecore.extender.business.internal.accessor.ecore.EcoreIntrinsicExtender;
import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;
import org.springframework.stereotype.Service;

/**
 * Class used to compute the image of a tool.
 *
 * @author pcdavid
 * @author sbegaudeau
 */
@Service
public class ToolImageProvider implements IToolImageProvider {

    private static final String ICON_PATH = "iconPath";

    private static final String ICON = "icon";

    private static final Pattern SEPARATOR = Pattern.compile("(::?|\\.)");

    private final IObjectService objectService;

    private final EPackage.Registry ePackageRegistry;

    public ToolImageProvider(IObjectService objectService, EPackage.Registry ePackageRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.ePackageRegistry = Objects.requireNonNull(ePackageRegistry);
    }

    @Override
    public String getImage(AbstractToolDescription abstractToolDescription) {
        // @formatter:off
        return this.getImagePathFromIconPath(abstractToolDescription)
                .or(() -> this.getImagePathFromDomainClass(abstractToolDescription))
                .orElse("");
        // @formatter:on
    }

    private Optional<String> getImagePathFromIconPath(AbstractToolDescription abstractToolDescription) {
        var optionalIconPathEAttribute = Optional.ofNullable(abstractToolDescription.eClass().getEStructuralFeature(ICON_PATH));

        if (optionalIconPathEAttribute.isEmpty()) {
            optionalIconPathEAttribute = Optional.ofNullable(abstractToolDescription.eClass().getEStructuralFeature(ICON));
        }
        // @formatter:off
        return optionalIconPathEAttribute.map(abstractToolDescription::eGet)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .filter(iconPath -> !iconPath.isBlank())
                .map(this::normalize);
        // @formatter:on
    }

    private String normalize(String iconPath) {
        String path = iconPath;
        if (!iconPath.startsWith("/")) {
            path = "/" + iconPath;
        }

        int index = path.indexOf('/', 1);
        if (index != -1) {
            path = path.substring(index);
        }
        return path;
    }

    private Optional<String> getImagePathFromDomainClass(AbstractToolDescription abstractToolDescription) {
        // @formatter:off
        var optionalInstance = this.getMappings(abstractToolDescription).stream()
                .map(this::getDomainClass)
                .flatMap(Optional::stream)
                .filter(domainClass -> !domainClass.isBlank())
                .findFirst()
                .flatMap(this::getInstance);
        // @formatter:on

        return optionalInstance.map(instance -> this.objectService.getImagePath(optionalInstance.get()));
    }

    private List<DiagramElementMapping> getMappings(AbstractToolDescription abstractToolDescription) {
        List<DiagramElementMapping> mappings = new ArrayList<>();

        if (abstractToolDescription instanceof NodeCreationDescription) {
            mappings.addAll(((NodeCreationDescription) abstractToolDescription).getNodeMappings());
        } else if (abstractToolDescription instanceof ContainerCreationDescription) {
            mappings.addAll(((ContainerCreationDescription) abstractToolDescription).getContainerMappings());
        } else if (abstractToolDescription instanceof EdgeCreationDescription) {
            mappings.addAll(((EdgeCreationDescription) abstractToolDescription).getEdgeMappings());
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
