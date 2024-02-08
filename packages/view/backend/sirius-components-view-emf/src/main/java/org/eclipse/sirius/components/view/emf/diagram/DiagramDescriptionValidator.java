/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.compatibility.emf.DomainClassPredicate;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;

/**
 * The validator for {@link DiagramDescription}.
 *
 * @author gcoutable
 */
public class DiagramDescriptionValidator implements EValidator {

    public static final String DIAGRAM_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The diagram description \"%1$s\" does not have a valid domain class";

    public static final String DIAGRAM_ELEMENT_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The element description \"%1$s\" does not have a valid domain class";

    public static final String CREATE_INSTANCE_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The create instance operation \"%1$s\" does not have a valid domain class";

    public static final String SIRIUS_COMPONENTS_EMF_PACKAGE = "org.eclipse.sirius.components.emf";

    @Override
    public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    @Override
    public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean isValid = true;
        if (eObject instanceof DiagramDescription diagramDescription) {
            isValid = this.hasProperDomainType(diagramDescription, diagnostics) && isValid;
        }
        if (eObject instanceof DiagramElementDescription diagramElementDescription) {
            isValid = this.hasProperDomainType(diagramElementDescription, diagnostics) && isValid;
        }
        if (eObject instanceof NodeStyleDescription nodeStyle) {
            isValid = this.hasProperColor(nodeStyle, diagnostics) && isValid;
            isValid = this.hasProperBorderColor(nodeStyle, diagnostics) && isValid;

        }
        if (eObject instanceof ImageNodeStyleDescription imageNodeStyle) {
            isValid = this.hasProperShape(imageNodeStyle, diagnostics) && isValid;
        }
        if (eObject instanceof Conditional conditional) {
            isValid = this.conditionIsPresent(conditional, diagnostics) && isValid;
        }
        if (eObject instanceof ConditionalNodeStyle conditionalNodeStyle) {
            isValid = this.conditionalStyleIsPresent(conditionalNodeStyle, diagnostics) && isValid;
        }
        if (eObject instanceof CreateInstance createInstance) {
            isValid = this.hasProperDomainType(createInstance, diagnostics) && isValid;
        }
        return isValid;
    }

    @Override
    public boolean validate(EDataType eDataType, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    private boolean conditionIsPresent(Conditional conditional, DiagnosticChain diagnostics) {
        boolean isValid = !conditional.getCondition().isBlank();

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The condition should not be empty",
                    new Object [] {
                        conditional,
                        ViewPackage.Literals.CONDITIONAL__CONDITION,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean conditionalStyleIsPresent(ConditionalNodeStyle conditionalNodeStyle, DiagnosticChain diagnostics) {
        boolean isValid = conditionalNodeStyle.getStyle() != null;

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The style should not be empty",
                    new Object [] {
                        conditionalNodeStyle,
                        DiagramPackage.Literals.CONDITIONAL_NODE_STYLE__STYLE,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperColor(NodeStyleDescription nodeStyle, DiagnosticChain diagnostics) {
        boolean isValid = Objects.nonNull(nodeStyle.getColor());

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The color should not be empty",
                    new Object [] {
                        nodeStyle,
                        DiagramPackage.Literals.STYLE__COLOR,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperBorderColor(NodeStyleDescription nodeStyle, DiagnosticChain diagnostics) {
        boolean isValid = Objects.nonNull(nodeStyle.getBorderColor());

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The border color should not be empty",
                    new Object [] {
                        nodeStyle,
                        DiagramPackage.Literals.BORDER_STYLE__BORDER_COLOR,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperShape(ImageNodeStyleDescription imageNodeStyle, DiagnosticChain diagnostics) {
        boolean isValid = Objects.nonNull(imageNodeStyle.getShape());

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The shape should not be empty",
                    new Object [] {
                        imageNodeStyle,
                        DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperDomainType(DiagramDescription diagramDescription, DiagnosticChain diagnostics) {
        boolean isValid = false;
        ResourceSet resourceSet = diagramDescription.eResource().getResourceSet();
        List<Entity> entities = this.getDomainEntitiesFromResourceSet(resourceSet);
        List<EPackage> ePackages = this.getEPackagesFromRegistry(resourceSet.getPackageRegistry());

        String domainType = Optional.ofNullable(diagramDescription.getDomainType()).orElse("");
        isValid = entities.stream().anyMatch(entity -> this.describesEntity(domainType, entity));

        if (!isValid && !domainType.isBlank()) {
            isValid = ePackages.stream()
                    .map(EPackage::getEClassifiers)
                    .flatMap(Collection::stream)
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .anyMatch(classifier -> new DomainClassPredicate(domainType).test(classifier));
        }

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    String.format(DIAGRAM_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE, domainType),
                    new Object [] {
                        diagramDescription,
                        ViewPackage.Literals.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperDomainType(DiagramElementDescription diagramElementDescription, DiagnosticChain diagnostics) {
        boolean isValid = false;
        ResourceSet resourceSet = diagramElementDescription.eResource().getResourceSet();
        List<Entity> entities = this.getDomainEntitiesFromResourceSet(resourceSet);
        List<EPackage> ePackages = this.getEPackagesFromRegistry(resourceSet.getPackageRegistry());

        String domainType = Optional.ofNullable(diagramElementDescription.getDomainType()).orElse("");
        isValid = entities.stream().anyMatch(entity -> this.describesEntity(domainType, entity));

        if (!isValid && !domainType.isBlank()) {
            isValid = ePackages.stream()
                    .map(EPackage::getEClassifiers)
                    .flatMap(Collection::stream)
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .anyMatch(classifier -> new DomainClassPredicate(domainType).test(classifier));
        }

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    String.format(DIAGRAM_ELEMENT_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE, domainType),
                    new Object [] {
                        diagramElementDescription,
                        DiagramPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperDomainType(CreateInstance createInstance, DiagnosticChain diagnostics) {
        boolean isValid = false;
        ResourceSet resourceSet = createInstance.eResource().getResourceSet();
        List<Entity> entities = this.getDomainEntitiesFromResourceSet(resourceSet);
        List<EPackage> ePackages = this.getEPackagesFromRegistry(resourceSet.getPackageRegistry());

        String domainType = Optional.ofNullable(createInstance.getTypeName()).orElse("");
        isValid = entities.stream().anyMatch(entity -> this.describesEntity(domainType, entity));

        if (!isValid && !domainType.isBlank()) {
            isValid = ePackages.stream()
                    .map(EPackage::getEClassifiers)
                    .flatMap(Collection::stream)
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .anyMatch(classifier -> new DomainClassPredicate(domainType).test(classifier));
        }

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    String.format(CREATE_INSTANCE_INVALID_DOMAIN_TYPE_ERROR_MESSAGE, domainType),
                    new Object [] {
                        createInstance,
                        ViewPackage.Literals.CREATE_INSTANCE__TYPE_NAME,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean describesEntity(String domainType, Entity entity) {
        boolean result = false;
        if (Objects.equals(domainType, entity.getName())) {
            result = true;
        } else if (entity.eContainer() instanceof Domain) {
            String domainName = ((Domain) entity.eContainer()).getName();
            result = Objects.equals(domainType, domainName + "::" + entity.getName());
        }
        return result;
    }

    private List<Entity> getDomainEntitiesFromResourceSet(ResourceSet resourceSet) {
        return resourceSet.getResources().stream()
                .map(Resource::getContents)
                .flatMap(Collection::stream)
                .filter(e -> DomainPackage.Literals.DOMAIN.equals(e.eClass()))
                .map(Domain.class::cast)
                .map(Domain::getTypes)
                .flatMap(Collection::stream)
                .toList();
    }

    private List<EPackage> getEPackagesFromRegistry(EPackage.Registry ePackageRegistry) {
        List<EPackage> allEPackage = new ArrayList<>();

        List<EPackage> ePackages = ePackageRegistry.entrySet().stream()
                .map(Entry::getValue)
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .toList();
        allEPackage.addAll(ePackages);

        ePackages.stream()
                .map(this::getSubPackages)
                .forEach(allEPackage::addAll);

        return allEPackage;
    }

    private List<EPackage> getSubPackages(EPackage ePackage) {
        List<EPackage> allEPackage = new ArrayList<>();

        EList<EPackage> eSubpackages = ePackage.getESubpackages();
        allEPackage.addAll(eSubpackages);

        eSubpackages.stream()
            .map(this::getSubPackages)
            .forEach(allEPackage::addAll);

        return allEPackage;
    }
}
