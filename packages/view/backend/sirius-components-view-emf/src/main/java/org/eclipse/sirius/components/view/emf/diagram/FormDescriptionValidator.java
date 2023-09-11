/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.sirius.components.view.form.ContainerBorderStyle;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;

/**
 * The validator for {@link FormDescription}.
 *
 * @author arichard
 */
public class FormDescriptionValidator implements EValidator {

    public static final String FORM_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The form description \"%1$s\" does not have a valid domain class";

    public static final String FORM_PAGE_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The element description \"%1$s\" does not have a valid domain class";

    public static final String CREATE_INSTANCE_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The create instance operation \"%1$s\" does not have a valid domain class";

    public static final String SIRIUS_COMPONENTS_EMF_PACKAGE = "org.eclipse.sirius.components.emf";

    @Override
    public boolean validate(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        return true;
    }

    @Override
    public boolean validate(EClass eClass, EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
        boolean isValid = true;
        if (eObject instanceof FormDescription formDescription) {
            isValid = this.hasProperDomainType(formDescription, diagnostics) && isValid;
        }
        if (eObject instanceof PageDescription pageDescription) {
            isValid = this.hasProperDomainType(pageDescription, diagnostics) && isValid;
        }
        if (eObject instanceof WidgetDescriptionStyle widgetDescriptionStyle) {
            isValid = this.hasProperBackgroundColor(widgetDescriptionStyle, diagnostics) && isValid;
            isValid = this.hasProperForegroundColor(widgetDescriptionStyle, diagnostics) && isValid;
            isValid = this.hasProperColor(widgetDescriptionStyle, diagnostics) && isValid;
        }
        if (eObject instanceof ContainerBorderStyle containerBorderStyle) {
            isValid = this.hasProperBorderColor(containerBorderStyle, diagnostics) && isValid;
        }
        if (eObject instanceof Conditional conditional) {
            isValid = this.conditionIsPresent(conditional, diagnostics) && isValid;
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

    private boolean hasProperBackgroundColor(WidgetDescriptionStyle widgetDescriptionStyle, DiagnosticChain diagnostics) {
        EStructuralFeature backgroundColor = widgetDescriptionStyle.eClass().getEStructuralFeature("backgroundColor");
        boolean isValid = Objects.isNull(backgroundColor) || Objects.nonNull(widgetDescriptionStyle.eGet(backgroundColor));

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The background color should not be empty",
                    new Object [] {
                        widgetDescriptionStyle,
                        backgroundColor,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperForegroundColor(WidgetDescriptionStyle widgetDescriptionStyle, DiagnosticChain diagnostics) {
        EStructuralFeature foregroundColor = widgetDescriptionStyle.eClass().getEStructuralFeature("foregroundColor");
        boolean isValid = Objects.isNull(foregroundColor) || Objects.nonNull(widgetDescriptionStyle.eGet(foregroundColor));

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The foreground color should not be empty",
                    new Object [] {
                        widgetDescriptionStyle,
                        foregroundColor,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperColor(WidgetDescriptionStyle widgetDescriptionStyle, DiagnosticChain diagnostics) {
        EStructuralFeature color = widgetDescriptionStyle.eClass().getEStructuralFeature("color");
        boolean isValid = Objects.isNull(color) || Objects.nonNull(widgetDescriptionStyle.eGet(color));

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The color should not be empty",
                    new Object [] {
                        widgetDescriptionStyle,
                        color,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperBorderColor(ContainerBorderStyle containerBorderStyle, DiagnosticChain diagnostics) {
        boolean isValid = Objects.nonNull(containerBorderStyle.getBorderColor());

        if (!isValid && diagnostics != null) {
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_COMPONENTS_EMF_PACKAGE,
                    0,
                    "The border color should not be empty",
                    new Object [] {
                        containerBorderStyle,
                        FormPackage.Literals.CONTAINER_BORDER_STYLE__BORDER_COLOR,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperDomainType(FormDescription formDescription, DiagnosticChain diagnostics) {
        boolean isValid = false;
        ResourceSet resourceSet = formDescription.eResource().getResourceSet();
        List<Entity> entities = this.getDomainEntitiesFromResourceSet(resourceSet);
        List<EPackage> ePackages = this.getEPackagesFromRegistry(resourceSet.getPackageRegistry());

        String domainType = Optional.ofNullable(formDescription.getDomainType()).orElse("");
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
                    String.format(FORM_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE, domainType),
                    new Object [] {
                        formDescription,
                        ViewPackage.Literals.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE,
                    });

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean hasProperDomainType(PageDescription pageDescription, DiagnosticChain diagnostics) {
        boolean isValid = false;
        ResourceSet resourceSet = pageDescription.eResource().getResourceSet();
        List<Entity> entities = this.getDomainEntitiesFromResourceSet(resourceSet);
        List<EPackage> ePackages = this.getEPackagesFromRegistry(resourceSet.getPackageRegistry());

        String domainType = Optional.ofNullable(pageDescription.getDomainType()).orElse("");
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
                    String.format(FORM_PAGE_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE, domainType),
                    new Object [] {
                        pageDescription,
                        FormPackage.Literals.PAGE_DESCRIPTION__DOMAIN_TYPE,
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
