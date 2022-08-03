/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import java.util.stream.Collectors;

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
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * The validator for {@link DiagramDescription}.
 *
 * @author gcoutable
 */
public class DiagramDescriptionValidator implements EValidator {

    public static final String DIAGRAM_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The diagram description \"%1$s\" does not have a valid domain class"; //$NON-NLS-1$

    public static final String DIAGRAM_ELEMENT_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The element description \"%1$s\" does not have a valid domain class"; //$NON-NLS-1$

    public static final String CREATE_INSTANCE_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The create instance operation \"%1$s\" does not have a valid domain class"; //$NON-NLS-1$

    public static final String SIRIUS_COMPONENTS_EMF_PACKAGE = "org.eclipse.sirius.components.emf"; //$NON-NLS-1$

    @Override
    public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    @Override
    public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean isValid = true;
        if (eObject instanceof DiagramDescription) {
            DiagramDescription diagramDescription = (DiagramDescription) eObject;
            isValid = this.hasProperDomainType(diagramDescription, diagnostics) && isValid;
        }
        if (eObject instanceof DiagramElementDescription) {
            DiagramElementDescription diagramElementDescription = (DiagramElementDescription) eObject;
            isValid = this.hasProperDomainType(diagramElementDescription, diagnostics) && isValid;
        }
        if (eObject instanceof NodeStyleDescription) {
            NodeStyleDescription nodeStyle = (NodeStyleDescription) eObject;
            isValid = this.hasProperColor(nodeStyle, diagnostics) && isValid;
        }
        if (eObject instanceof Conditional) {
            Conditional conditional = (Conditional) eObject;
            isValid = this.conditionIsPresent(conditional, diagnostics) && isValid;
        }
        if (eObject instanceof ConditionalNodeStyle) {
            ConditionalNodeStyle conditionalNodeStyle = (ConditionalNodeStyle) eObject;
            isValid = this.conditionalStyleIsPresent(conditionalNodeStyle, diagnostics) && isValid;
        }
        if (eObject instanceof CreateInstance) {
            CreateInstance createInstance = (CreateInstance) eObject;
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
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The condition should not be empty", //$NON-NLS-1$
                    new Object [] {
                            conditional,
                            ViewPackage.Literals.CONDITIONAL__CONDITION,
                    });
            // @formatter:on

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean conditionalStyleIsPresent(ConditionalNodeStyle conditionalNodeStyle, DiagnosticChain diagnostics) {
        boolean isValid = conditionalNodeStyle.getStyle() != null;

        if (!isValid && diagnostics != null) {
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The style should not be empty", //$NON-NLS-1$
                    new Object [] {
                            conditionalNodeStyle,
                            ViewPackage.Literals.CONDITIONAL_NODE_STYLE__STYLE,
                    });
            // @formatter:on

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperColor(NodeStyleDescription nodeStyle, DiagnosticChain diagnostics) {
        boolean isValid = !nodeStyle.getColor().isBlank();

        if (!isValid && diagnostics != null) {
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The color should not be empty", //$NON-NLS-1$
                    new Object [] {
                            nodeStyle,
                            ViewPackage.Literals.STYLE__COLOR,
                    });
            // @formatter:on

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperDomainType(DiagramDescription diagramDescription, DiagnosticChain diagnostics) {
        boolean isValid = false;
        ResourceSet resourceSet = diagramDescription.eResource().getResourceSet();
        List<Entity> entities = this.getDomainEntitiesFromResourceSet(resourceSet);
        List<EPackage> ePackages = this.getEPackagesFromRegistry(resourceSet.getPackageRegistry());

        String domainType = Optional.ofNullable(diagramDescription.getDomainType()).orElse(""); //$NON-NLS-1$
        isValid = entities.stream().anyMatch(entity -> this.describesEntity(domainType, entity));

        if (!isValid && !domainType.isBlank()) {
            // @formatter:off
            isValid = ePackages.stream()
                    .map(EPackage::getEClassifiers)
                    .flatMap(Collection::stream)
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .anyMatch(classifier -> new DomainClassPredicate(domainType).test(classifier));
            // @formatter:off
        }

        if (!isValid && diagnostics != null) {
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    String.format(DIAGRAM_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE, domainType),
                    new Object [] {
                            diagramDescription,
                            ViewPackage.Literals.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE,
                    });
            // @formatter:on

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperDomainType(DiagramElementDescription diagramElementDescription, DiagnosticChain diagnostics) {
        boolean isValid = false;
        ResourceSet resourceSet = diagramElementDescription.eResource().getResourceSet();
        List<Entity> entities = this.getDomainEntitiesFromResourceSet(resourceSet);
        List<EPackage> ePackages = this.getEPackagesFromRegistry(resourceSet.getPackageRegistry());

        String domainType = Optional.ofNullable(diagramElementDescription.getDomainType()).orElse(""); //$NON-NLS-1$
        isValid = entities.stream().anyMatch(entity -> this.describesEntity(domainType, entity));

        if (!isValid && !domainType.isBlank()) {
            // @formatter:off
            isValid = ePackages.stream()
                    .map(EPackage::getEClassifiers)
                    .flatMap(Collection::stream)
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .anyMatch(classifier -> new DomainClassPredicate(domainType).test(classifier));
            // @formatter:off
        }

        if (!isValid && diagnostics != null) {
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    String.format(DIAGRAM_ELEMENT_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE, domainType),
                    new Object [] {
                            diagramElementDescription,
                            ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE,
                    });
            // @formatter:on

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperDomainType(CreateInstance createInstance, DiagnosticChain diagnostics) {
        boolean isValid = false;
        ResourceSet resourceSet = createInstance.eResource().getResourceSet();
        List<Entity> entities = this.getDomainEntitiesFromResourceSet(resourceSet);
        List<EPackage> ePackages = this.getEPackagesFromRegistry(resourceSet.getPackageRegistry());

        String domainType = Optional.ofNullable(createInstance.getTypeName()).orElse(""); //$NON-NLS-1$
        isValid = entities.stream().anyMatch(entity -> this.describesEntity(domainType, entity));

        if (!isValid && !domainType.isBlank()) {
            // @formatter:off
            isValid = ePackages.stream()
                    .map(EPackage::getEClassifiers)
                    .flatMap(Collection::stream)
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .anyMatch(classifier -> new DomainClassPredicate(domainType).test(classifier));
            // @formatter:off
        }

        if (!isValid && diagnostics != null) {
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    String.format(CREATE_INSTANCE_INVALID_DOMAIN_TYPE_ERROR_MESSAGE, domainType),
                    new Object [] {
                            createInstance,
                            ViewPackage.Literals.CREATE_INSTANCE__TYPE_NAME,
                    });
            // @formatter:on

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
            result = Objects.equals(domainType, domainName + "::" + entity.getName()); //$NON-NLS-1$
        }
        return result;
    }

    private List<Entity> getDomainEntitiesFromResourceSet(ResourceSet resourceSet) {
        // @formatter:off
        return resourceSet.getResources().stream()
                .map(Resource::getContents)
                .flatMap(Collection::stream)
                .filter(e -> DomainPackage.Literals.DOMAIN.equals(e.eClass()))
                .map(Domain.class::cast)
                .map(Domain::getTypes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        // @formatter:on
    }

    private List<EPackage> getEPackagesFromRegistry(EPackage.Registry ePackageRegistry) {
        List<EPackage> allEPackage = new ArrayList<>();

        // @formatter:off
        List<EPackage> ePackages = ePackageRegistry.entrySet().stream()
                .map(Entry::getValue)
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .collect(Collectors.toList());
        allEPackage.addAll(ePackages);

        ePackages.stream()
                .map(this::getSubPackages)
                .forEach(allEPackage::addAll);
        // @formatter:on
        return allEPackage;
    }

    private List<EPackage> getSubPackages(EPackage ePackage) {
        List<EPackage> allEPackage = new ArrayList<>();

        EList<EPackage> eSubpackages = ePackage.getESubpackages();
        allEPackage.addAll(eSubpackages);

        // @formatter:off
        eSubpackages.stream()
            .map(this::getSubPackages)
            .forEach(allEPackage::addAll);
        // @formatter:on

        return allEPackage;
    }
}
