/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.view.emf.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramCreationService;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.layout.api.ILayoutService;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.emf.ViewConverter;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Tests for dynamically defined diagrams.
 *
 * @author pcdavid
 */
public class DynamicDiagramsTests {

    private static final String NAME_EXPRESSION = "aql:self.name"; //$NON-NLS-1$

    private EPackage fixture;

    @BeforeEach
    void buildFixture() {
        this.fixture = EcoreFactory.eINSTANCE.createEPackage();
        this.fixture.setName("fixture"); //$NON-NLS-1$
        EClass klass1 = EcoreFactory.eINSTANCE.createEClass();
        klass1.setName("Class1"); //$NON-NLS-1$
        this.fixture.getEClassifiers().add(klass1);
        EClass klass2 = EcoreFactory.eINSTANCE.createEClass();
        klass2.setName("Class2"); //$NON-NLS-1$
        this.fixture.getEClassifiers().add(klass2);
    }

    private void setBasicNodeStyle(NodeDescription nodeDescription, String color) {
        NodeStyleDescription eClassNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        eClassNodeStyle.setColor(color);
        nodeDescription.setStyle(eClassNodeStyle);
    }

    @Test
    void testRenderSynchronizedEcoreDiagram() throws Exception {
        DiagramDescription diagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("Simple Ecore Diagram"); //$NON-NLS-1$
        diagramDescription.setTitleExpression(NAME_EXPRESSION);
        diagramDescription.setAutoLayout(false);
        diagramDescription.setDomainType("ecore::EPackage"); //$NON-NLS-1$

        NodeDescription eClassNode = ViewFactory.eINSTANCE.createNodeDescription();
        eClassNode.setName("EClass Node"); //$NON-NLS-1$
        eClassNode.setDomainType("ecore::EClass"); //$NON-NLS-1$
        eClassNode.setLabelExpression(NAME_EXPRESSION);
        eClassNode.setSemanticCandidatesExpression("aql:self.eClassifiers"); //$NON-NLS-1$
        eClassNode.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        this.setBasicNodeStyle(eClassNode, "red"); //$NON-NLS-1$

        diagramDescription.getNodeDescriptions().add(eClassNode);

        Diagram result = this.render(diagramDescription, this.fixture);

        assertThat(result).isNotNull();
        assertThat(result.getEdges()).isEmpty();
        assertThat(result.getNodes()).hasSize(2);
        assertThat(result.getNodes()).extracting(node -> node.getLabel().getText()).containsExactlyInAnyOrder("Class1", "Class2"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Test
    void testRenderUnsynchronizedEcoreDiagram() throws Exception {
        DiagramDescription diagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("Simple Ecore Diagram"); //$NON-NLS-1$
        diagramDescription.setTitleExpression(NAME_EXPRESSION);
        diagramDescription.setAutoLayout(false);
        diagramDescription.setDomainType("ecore::EPackage"); //$NON-NLS-1$

        NodeDescription eClassNode = ViewFactory.eINSTANCE.createNodeDescription();
        eClassNode.setName("EClass Node"); //$NON-NLS-1$
        eClassNode.setDomainType("ecore::EClass"); //$NON-NLS-1$
        eClassNode.setLabelExpression(NAME_EXPRESSION);
        eClassNode.setSemanticCandidatesExpression("aql:self.eClassifiers"); //$NON-NLS-1$
        eClassNode.setSynchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED);
        this.setBasicNodeStyle(eClassNode, "red"); //$NON-NLS-1$

        diagramDescription.getNodeDescriptions().add(eClassNode);

        Diagram result = this.render(diagramDescription, this.fixture);

        assertThat(result).isNotNull();
        assertThat(result.getEdges()).isEmpty();
        assertThat(result.getNodes()).isEmpty();
    }

    private Diagram render(DiagramDescription diagramDescription, Object target) {
        // Wrap into a View, as expected by ViewConverter
        View view = ViewFactory.eINSTANCE.createView();
        view.getDescriptions().add(diagramDescription);

        ViewDiagramDescriptionConverter diagramDescriptionConverter = new ViewDiagramDescriptionConverter(new IObjectService.NoOp(), new IEditService.NoOp());
        var viewConverter = new ViewConverter(List.of(), List.of(diagramDescriptionConverter), new StaticApplicationContext());
        List<IRepresentationDescription> conversionResult = viewConverter.convert(view, List.of(EcorePackage.eINSTANCE));
        assertThat(conversionResult).hasSize(1);
        assertThat(conversionResult.get(0)).isInstanceOf(org.eclipse.sirius.components.diagrams.description.DiagramDescription.class);
        org.eclipse.sirius.components.diagrams.description.DiagramDescription convertedDiagramDescription = (org.eclipse.sirius.components.diagrams.description.DiagramDescription) conversionResult
                .get(0);

        IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                return Optional.of(convertedDiagramDescription);
            }
        };
        IObjectService objectService = new IObjectService.NoOp();
        ILayoutService layoutService = new ILayoutService.NoOp();
        MeterRegistry meterRegistry = new SimpleMeterRegistry();
        var diagramCreationService = new DiagramCreationService(representationDescriptionSearchService, new IRepresentationPersistenceService.NoOp(), objectService, layoutService, meterRegistry);

        IEditingContext editinContext = new IEditingContext.NoOp();

        return diagramCreationService.create("Test Diagram", target, convertedDiagramDescription, editinContext); //$NON-NLS-1$
    }
}
