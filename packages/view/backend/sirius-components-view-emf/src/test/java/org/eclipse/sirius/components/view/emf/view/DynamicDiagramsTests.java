/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramCreationService;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.emf.ViewConverter;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter;
import org.eclipse.sirius.components.view.emf.diagram.providers.api.IViewToolImageProvider;
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

    private static final String NAME_EXPRESSION = "aql:self.name";

    private EPackage fixture;

    @BeforeEach
    void buildFixture() {
        this.fixture = EcoreFactory.eINSTANCE.createEPackage();
        this.fixture.setName("fixture");
        EClass klass1 = EcoreFactory.eINSTANCE.createEClass();
        klass1.setName("Class1");
        this.fixture.getEClassifiers().add(klass1);
        EClass klass2 = EcoreFactory.eINSTANCE.createEClass();
        klass2.setName("Class2");
        this.fixture.getEClassifiers().add(klass2);
    }

    private void setBasicNodeStyle(NodeDescription nodeDescription, String color) {
        NodeStyleDescription eClassNodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        var userColor = ViewFactory.eINSTANCE.createFixedColor();
        userColor.setName("color");
        userColor.setValue(color);

        eClassNodeStyle.setColor(userColor);
        nodeDescription.setStyle(eClassNodeStyle);
    }

    @Test
    void testRenderSynchronizedEcoreDiagram() throws Exception {
        DiagramDescription diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("Simple Ecore Diagram");
        diagramDescription.setTitleExpression(NAME_EXPRESSION);
        diagramDescription.setAutoLayout(false);
        diagramDescription.setDomainType("ecore::EPackage");

        InsideLabelDescription insideLabelDescription = DiagramFactory.eINSTANCE.createInsideLabelDescription();
        insideLabelDescription.setLabelExpression(NAME_EXPRESSION);
        insideLabelDescription.setStyle(DiagramFactory.eINSTANCE.createInsideLabelStyle());
        insideLabelDescription.setPosition(InsideLabelPosition.TOP_CENTER);

        NodeDescription eClassNode = DiagramFactory.eINSTANCE.createNodeDescription();
        eClassNode.setName("EClass Node");
        eClassNode.setDomainType("ecore::EClass");
        eClassNode.setInsideLabel(insideLabelDescription);
        eClassNode.setSemanticCandidatesExpression("aql:self.eClassifiers");
        eClassNode.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        this.setBasicNodeStyle(eClassNode, "red");

        diagramDescription.getNodeDescriptions().add(eClassNode);

        Diagram result = this.render(diagramDescription, this.fixture);

        assertThat(result).isNotNull();
        assertThat(result.getEdges()).isEmpty();
        assertThat(result.getNodes()).hasSize(2);
        assertThat(result.getNodes()).extracting(node -> node.getInsideLabel().getText()).containsExactlyInAnyOrder("Class1", "Class2");
    }

    @Test
    void testRenderUnsynchronizedEcoreDiagram() throws Exception {
        DiagramDescription diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("Simple Ecore Diagram");
        diagramDescription.setTitleExpression(NAME_EXPRESSION);
        diagramDescription.setAutoLayout(false);
        diagramDescription.setDomainType("ecore::EPackage");

        NodeDescription eClassNode = DiagramFactory.eINSTANCE.createNodeDescription();
        eClassNode.setName("EClass Node");
        eClassNode.setDomainType("ecore::EClass");
        eClassNode.setSemanticCandidatesExpression("aql:self.eClassifiers");
        eClassNode.setSynchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED);
        this.setBasicNodeStyle(eClassNode, "red");

        diagramDescription.getNodeDescriptions().add(eClassNode);

        Diagram result = this.render(diagramDescription, this.fixture);

        assertThat(result).isNotNull();
        assertThat(result.getEdges()).isEmpty();
        assertThat(result.getNodes()).isEmpty();
    }

    private Diagram render(DiagramDescription diagramDescription, Object target) {
        // Wrap into a View and put it inside a proper Resource(Set), as expected by ViewConverter
        View view = ViewFactory.eINSTANCE.createView();
        view.getDescriptions().add(diagramDescription);
        Resource res = new JSONResourceFactory().createResource(URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///fixture"));
        res.getContents().add(view);
        new ResourceSetImpl().getResources().add(res);

        ViewDiagramDescriptionConverter diagramDescriptionConverter = new ViewDiagramDescriptionConverter(new IObjectService.NoOp(), new IEditService.NoOp(), List.of(),
                new IDiagramIdProvider.NoOp(), new IViewToolImageProvider.NoOp(), new IFeedbackMessageService.NoOp());
        var viewConverter = new ViewConverter(List.of(), List.of(diagramDescriptionConverter), new StaticApplicationContext(), new IObjectService.NoOp());
        List<IRepresentationDescription> conversionResult = viewConverter.convert(List.of(view), List.of(EcorePackage.eINSTANCE));
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
        MeterRegistry meterRegistry = new SimpleMeterRegistry();
        var diagramCreationService = new DiagramCreationService(representationDescriptionSearchService, objectService, new IOperationValidator.NoOp(), meterRegistry);

        IEditingContext editinContext = new IEditingContext.NoOp();

        return diagramCreationService.create("Test Diagram", target, convertedDiagramDescription, editinContext);
    }
}
