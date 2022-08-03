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
package org.eclipse.sirius.components.view.emf.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.view.emf.validation.DiagnosticAssertions.assertThat;

import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.view.ConditionalNodeStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.emf.diagram.DiagramDescriptionValidator;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link DiagramDescriptionValidator}.
 *
 * @author gcoutable
 */
public class ViewValidatorTests {

    private static final String SAMPLE_DOMAIN_NAME = "sample"; //$NON-NLS-1$

    private static final String SAMPLE_ENTITY_NAME = "SampleEntity"; //$NON-NLS-1$

    private static final String SIRIUS_COMPONENTS_EMF_PACKAGE = "org.eclipse.sirius.components.emf"; //$NON-NLS-1$

    @Test
    public void testNodeStyleDefaultValuesAreValid() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        NodeStyleDescription nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(nodeStyle.eClass(), nodeStyle, diagnosticChain, defaultContext);
        assertThat(validationResult).isTrue();

        assertThat(diagnosticChain).isEqualTo(new BasicDiagnostic(Diagnostic.OK, null, 0, null, null));
    }

    @Test
    public void testConditionalNodeStyleDefaultValuesAreValid() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        ConditionalNodeStyle conditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(conditionalNodeStyle.eClass(), conditionalNodeStyle, diagnosticChain, defaultContext);
        assertThat(validationResult).isTrue();

        assertThat(diagnosticChain).isEqualTo(new BasicDiagnostic(Diagnostic.OK, null, 0, null, null));
    }

    @Test
    public void testConditionalConditionIsAbsent() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        ConditionalNodeStyle conditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        conditionalNodeStyle.setCondition(""); //$NON-NLS-1$

        BasicDiagnostic expected = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        // @formatter:off
        expected.add(new BasicDiagnostic(Diagnostic.ERROR,
                SIRIUS_COMPONENTS_EMF_PACKAGE,
                0,
                "The condition should not be empty", //$NON-NLS-1$
                new Object [] {
                        conditionalNodeStyle,
                        ViewPackage.Literals.CONDITIONAL__CONDITION,
                })
            );
        // @formatter:on

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(conditionalNodeStyle.eClass(), conditionalNodeStyle, diagnosticChain, defaultContext);
        assertThat(validationResult).isFalse();
        assertThat(diagnosticChain).isEqualTo(expected);
    }

    @Test
    public void testConditionalStyleIsAbsent() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        ConditionalNodeStyle conditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();

        BasicDiagnostic expected = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        // @formatter:off
        expected.add(new BasicDiagnostic(Diagnostic.ERROR,
                SIRIUS_COMPONENTS_EMF_PACKAGE,
                0,
                "The style should not be empty", //$NON-NLS-1$
                new Object [] {
                        conditionalNodeStyle,
                        ViewPackage.Literals.CONDITIONAL_NODE_STYLE__STYLE,
        })
                );
        // @formatter:on

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(conditionalNodeStyle.eClass(), conditionalNodeStyle, diagnosticChain, defaultContext);
        assertThat(validationResult).isFalse();
        assertThat(diagnosticChain).isEqualTo(expected);
    }

    @Test
    public void testNodeStyleColorIsAbsent() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        RectangularNodeStyleDescription conditionalNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        conditionalNodeStyle.setColor(""); //$NON-NLS-1$

        BasicDiagnostic expected = new BasicDiagnostic(Diagnostic.ERROR, null, 0, null, null);
        // @formatter:off
        expected.add(new BasicDiagnostic(Diagnostic.ERROR,
                SIRIUS_COMPONENTS_EMF_PACKAGE,
                0,
                "The color should not be empty", //$NON-NLS-1$
                new Object [] {
                        conditionalNodeStyle,
                        ViewPackage.Literals.STYLE__COLOR,
                })
            );
        // @formatter:on

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(conditionalNodeStyle.eClass(), conditionalNodeStyle, diagnosticChain, defaultContext);
        assertThat(validationResult).isFalse();
        assertThat(diagnosticChain).isEqualTo(expected);
    }

    @Test
    public void testNodeDescriptionInvalidDomain() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        NodeDescription nodeDescription = ViewFactory.eINSTANCE.createNodeDescription();

        ResourceSetImpl resourceSet = new ResourceSetImpl();
        XMIResourceImpl xmiResource = new XMIResourceImpl();
        xmiResource.getContents().add(nodeDescription);
        resourceSet.getResources().add(xmiResource);

        BasicDiagnostic expected = new BasicDiagnostic(Diagnostic.ERROR, null, 0, null, null);
        // @formatter:off
        expected.add(new BasicDiagnostic(Diagnostic.ERROR,
                SIRIUS_COMPONENTS_EMF_PACKAGE,
                0,
                String.format(DiagramDescriptionValidator.DIAGRAM_ELEMENT_DESCRIPTION_INVALID_DOMAIN_TYPE_ERROR_MESSAGE, Optional.ofNullable(nodeDescription.getDomainType()).orElse("")), //$NON-NLS-1$
                new Object [] {
                        nodeDescription,
                        ViewPackage.Literals.DIAGRAM_ELEMENT_DESCRIPTION__DOMAIN_TYPE,
                })
            );

        // @formatter:on

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(nodeDescription.eClass(), nodeDescription, diagnosticChain, defaultContext);
        assertThat(validationResult).isFalse();
        assertThat(diagnosticChain).isEqualTo(expected);
    }

    @Test
    public void testNodeStyleDescriptionValidDomainInResourceSet() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        NodeDescription nodeDescription = ViewFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setDomainType(SAMPLE_ENTITY_NAME);

        ResourceSetImpl resourceSet = new ResourceSetImpl();
        XMIResourceImpl viewResource = new XMIResourceImpl();
        viewResource.getContents().add(nodeDescription);
        XMIResourceImpl domainResource = new XMIResourceImpl();
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(SAMPLE_DOMAIN_NAME);
        domainResource.getContents().add(domain);
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName(SAMPLE_ENTITY_NAME);
        domain.getTypes().add(entity);

        resourceSet.getResources().add(viewResource);
        resourceSet.getResources().add(domainResource);

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(nodeDescription.eClass(), nodeDescription, diagnosticChain, defaultContext);
        assertThat(validationResult).isTrue();
        assertThat(diagnosticChain).isEqualTo(new BasicDiagnostic(Diagnostic.OK, null, 0, null, null));
    }

    @Test
    public void testNodeStyleDescriptionValidQualifiedDomainInResourceSet() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        NodeDescription nodeDescription = ViewFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setDomainType(SAMPLE_DOMAIN_NAME + "::" + SAMPLE_ENTITY_NAME); //$NON-NLS-1$

        ResourceSetImpl resourceSet = new ResourceSetImpl();
        XMIResourceImpl viewResource = new XMIResourceImpl();
        viewResource.getContents().add(nodeDescription);
        XMIResourceImpl domainResource = new XMIResourceImpl();
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(SAMPLE_DOMAIN_NAME);
        domainResource.getContents().add(domain);
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName(SAMPLE_ENTITY_NAME);
        domain.getTypes().add(entity);

        resourceSet.getResources().add(viewResource);
        resourceSet.getResources().add(domainResource);

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(nodeDescription.eClass(), nodeDescription, diagnosticChain, defaultContext);
        assertThat(validationResult).isTrue();
        assertThat(diagnosticChain).isEqualTo(new BasicDiagnostic(Diagnostic.OK, null, 0, null, null));
    }

    @Test
    public void testNodeStyleDescriptionValidDomainInPackageRegistry() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        NodeDescription nodeDescription = ViewFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setDomainType(SAMPLE_ENTITY_NAME);

        ResourceSetImpl resourceSet = new ResourceSetImpl();
        XMIResourceImpl viewResource = new XMIResourceImpl();
        viewResource.getContents().add(nodeDescription);
        resourceSet.getResources().add(viewResource);

        EPackageRegistryImpl packageRegistryImpl = new EPackageRegistryImpl();
        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        ePackage.setName(SAMPLE_DOMAIN_NAME);
        ePackage.setNsPrefix(SAMPLE_DOMAIN_NAME);
        ePackage.setNsURI("domain://sample"); //$NON-NLS-1$

        EClass sampleClass = EcoreFactory.eINSTANCE.createEClass();
        sampleClass.setName(SAMPLE_ENTITY_NAME);
        ePackage.getEClassifiers().add(sampleClass);
        packageRegistryImpl.put(ePackage.getNsURI(), ePackage);
        resourceSet.setPackageRegistry(packageRegistryImpl);

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(nodeDescription.eClass(), nodeDescription, diagnosticChain, defaultContext);
        assertThat(validationResult).isTrue();

        assertThat(diagnosticChain).isEqualTo(new BasicDiagnostic(Diagnostic.OK, null, 0, null, null));
    }

    @Test
    public void testNodeStyleDescriptionValidQualifiedDomainInPackageRegistry() {
        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        NodeDescription nodeDescription = ViewFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setDomainType(SAMPLE_DOMAIN_NAME + "::" + SAMPLE_ENTITY_NAME); //$NON-NLS-1$

        ResourceSetImpl resourceSet = new ResourceSetImpl();
        XMIResourceImpl viewResource = new XMIResourceImpl();
        viewResource.getContents().add(nodeDescription);
        resourceSet.getResources().add(viewResource);

        EPackageRegistryImpl packageRegistryImpl = new EPackageRegistryImpl();
        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        ePackage.setName(SAMPLE_DOMAIN_NAME);
        ePackage.setNsPrefix(SAMPLE_DOMAIN_NAME);
        ePackage.setNsURI("domain://sample"); //$NON-NLS-1$

        EClass sampleClass = EcoreFactory.eINSTANCE.createEClass();
        sampleClass.setName(SAMPLE_ENTITY_NAME);
        ePackage.getEClassifiers().add(sampleClass);
        packageRegistryImpl.put(ePackage.getNsURI(), ePackage);
        resourceSet.setPackageRegistry(packageRegistryImpl);

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);
        boolean validationResult = new DiagramDescriptionValidator().validate(nodeDescription.eClass(), nodeDescription, diagnosticChain, defaultContext);
        assertThat(validationResult).isTrue();

        assertThat(diagnosticChain).isEqualTo(new BasicDiagnostic(Diagnostic.OK, null, 0, null, null));
    }

}
