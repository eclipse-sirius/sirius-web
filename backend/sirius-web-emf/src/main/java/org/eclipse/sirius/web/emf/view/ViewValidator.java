/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.QueryParsing;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.web.domain.Domain;
import org.eclipse.sirius.web.domain.DomainPackage;
import org.eclipse.sirius.web.domain.Entity;
import org.eclipse.sirius.web.emf.compatibility.DomainClassPredicate;
import org.eclipse.sirius.web.interpreter.SimpleCrossReferenceProvider;
import org.eclipse.sirius.web.view.ChangeContext;
import org.eclipse.sirius.web.view.Conditional;
import org.eclipse.sirius.web.view.CreateInstance;
import org.eclipse.sirius.web.view.DiagramDescription;
import org.eclipse.sirius.web.view.DiagramElementDescription;
import org.eclipse.sirius.web.view.EdgeDescription;
import org.eclipse.sirius.web.view.NodeStyle;
import org.eclipse.sirius.web.view.SetValue;
import org.eclipse.sirius.web.view.UnsetValue;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * The validator for View.
 *
 * @author gcoutable
 */
public class ViewValidator implements EValidator {

    public static final String DIAGRAM_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The diagram description \"%1$s\" does not have a valid domain class"; //$NON-NLS-1$

    public static final String DIAGRAM_ELEMENT_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The element description \"%1$s\" does not have a valid domain class"; //$NON-NLS-1$

    public static final String CREATE_INSTANCE_INVALID_DOMAIN_TYPE_ERROR_MESSAGE = "The create instance operation \"%1$s\" does not have a valid domain class"; //$NON-NLS-1$

    public static final String SIRIUS_WEB_EMF_PACKAGE = "org.eclipse.sirius.web.emf"; //$NON-NLS-1$

    private static final String DOMAIN_URI_SCHEME = "domain://"; //$NON-NLS-1$

    private final IQueryBuilderEngine builder = QueryParsing.newBuilder(Query.newEnvironmentWithDefaultServices(new SimpleCrossReferenceProvider()));

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
            isValid = this.hasValidIntepretedExpression(diagramDescription, ViewPackage.Literals.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION, diagnostics) && isValid;
        }
        if (eObject instanceof DiagramElementDescription) {
            DiagramElementDescription diagramElementDescription = (DiagramElementDescription) eObject;
            isValid = this.hasProperDomainType(diagramElementDescription, diagnostics) && isValid;
            isValid = this.hasValidIntepretedExpression(diagramElementDescription, ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__LABEL_EXPRESSION, diagnostics) && isValid;
            isValid = this.hasValidIntepretedExpression(diagramElementDescription, ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, diagnostics) && isValid;
        }
        if (eObject instanceof EdgeDescription) {
            EdgeDescription edgeDescription = (EdgeDescription) eObject;
            isValid = this.hasValidIntepretedExpression(edgeDescription, ViewPackage.Literals.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION, diagnostics) && isValid;
            isValid = this.hasValidIntepretedExpression(edgeDescription, ViewPackage.Literals.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION, diagnostics) && isValid;
        }
        if (eObject instanceof NodeStyle) {
            NodeStyle nodeStyle = (NodeStyle) eObject;
            isValid = this.hasProperColor(nodeStyle, diagnostics) && isValid;
        }
        if (eObject instanceof Conditional) {
            Conditional conditional = (Conditional) eObject;
            isValid = this.conditionIsPresent(conditional, diagnostics) && isValid;
            isValid = this.hasValidIntepretedExpression(conditional, ViewPackage.Literals.CONDITIONAL__CONDITION, diagnostics) && isValid;
        }
        if (eObject instanceof ChangeContext) {
            ChangeContext changeContext = (ChangeContext) eObject;
            isValid = this.hasValidIntepretedExpression(changeContext, ViewPackage.Literals.CHANGE_CONTEXT__EXPRESSION, diagnostics) && isValid;
        }
        if (eObject instanceof CreateInstance) {
            CreateInstance createInstance = (CreateInstance) eObject;
            isValid = this.hasValidIntepretedExpression(createInstance, ViewPackage.Literals.CREATE_INSTANCE__VARIABLE_NAME, diagnostics) && isValid;
            isValid = this.hasProperDomainType(createInstance, diagnostics) && isValid;
        }
        if (eObject instanceof SetValue) {
            SetValue setValue = (SetValue) eObject;
            isValid = this.hasValidIntepretedExpression(setValue, ViewPackage.Literals.SET_VALUE__VALUE_EXPRESSION, diagnostics) && isValid;
        }
        if (eObject instanceof UnsetValue) {
            UnsetValue unsetValue = (UnsetValue) eObject;
            isValid = this.hasValidIntepretedExpression(unsetValue, ViewPackage.Literals.UNSET_VALUE__ELEMENT_EXPRESSION, diagnostics) && isValid;
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
                    SIRIUS_WEB_EMF_PACKAGE,
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

    private boolean hasValidIntepretedExpression(EObject eObject, EAttribute expressionAttribute, DiagnosticChain diagnostics) {
        boolean isValid = this.validateAQLExpressionSyntax(eObject, expressionAttribute);
        if (!isValid && diagnostics != null) {
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.WARNING,
                    SIRIUS_WEB_EMF_PACKAGE,
                    0,
                    "The condition should not be empty", //$NON-NLS-1$
                    new Object [] {
                            eObject,
                            expressionAttribute,
                    });
            // @formatter:on

            diagnostics.add(basicDiagnostic);
        }

        return isValid;
    }

    private boolean validateAQLExpressionSyntax(EObject eObject, EAttribute expressionAttribute) {
        String prefix = "aql:"; //$NON-NLS-1$
        String expression = (String) eObject.eGet(expressionAttribute);
        if (expression.startsWith(prefix)) {
            var result = this.builder.build(expression.substring(prefix.length()));
            if (!result.getErrors().isEmpty() || result.getDiagnostic().getSeverity() >= Diagnostic.WARNING) {
                return false;
            }
        }
        return true;
    }

    private boolean hasProperColor(NodeStyle nodeStyle, DiagnosticChain diagnostics) {
        boolean isValid = !nodeStyle.getColor().isBlank();

        if (!isValid && diagnostics != null) {
            // @formatter:off
            BasicDiagnostic basicDiagnostic = new BasicDiagnostic(Diagnostic.ERROR,
                    SIRIUS_WEB_EMF_PACKAGE,
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
        List<EPackage> ePackages = this.getDomainEPackagesFromRegistry(resourceSet.getPackageRegistry());

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
                    SIRIUS_WEB_EMF_PACKAGE,
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
        List<EPackage> ePackages = this.getDomainEPackagesFromRegistry(resourceSet.getPackageRegistry());

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
                    SIRIUS_WEB_EMF_PACKAGE,
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
        List<EPackage> ePackages = this.getDomainEPackagesFromRegistry(resourceSet.getPackageRegistry());

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
                    SIRIUS_WEB_EMF_PACKAGE,
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

    private List<EPackage> getDomainEPackagesFromRegistry(EPackage.Registry ePackageRegistry) {
        List<EPackage> allEPackage = new ArrayList<>();

        // @formatter:off
        List<EPackage> ePackages = ePackageRegistry.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(DOMAIN_URI_SCHEME))
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
