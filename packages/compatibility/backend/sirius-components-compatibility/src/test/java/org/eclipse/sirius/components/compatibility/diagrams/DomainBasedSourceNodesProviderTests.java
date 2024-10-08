/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the domain-based source nodes provider.
 *
 * @author sbegaudeau
 */
public class DomainBasedSourceNodesProviderTests {

    @Test
    public void testComputeSourceNodes() {
        EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
        edgeMapping.setSourceFinderExpression("aql:self");
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName("3b0c8a37-b19c-4978-8a85-ba283d45d22d");
        edgeMapping.getSourceMapping().add(nodeMapping);

        Object object = new Object();
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, object);

        DiagramRenderingCache cache = new DiagramRenderingCache();
        Element nodeElement = this.createNodeElement(nodeMapping.getName());
        cache.put(object, nodeElement);

        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));

        variableManager.put(DiagramDescription.CACHE, cache);

        IIdentifierProvider identifierProvider = new IIdentifierProvider.NoOp() {
            @Override
            public String getIdentifier(Object element) {
                return nodeMapping.getName();
            }
        };
        List<Element> sourceNodes = new DomainBasedSourceNodesProvider(edgeMapping, interpreter, identifierProvider).apply(variableManager);
        assertThat(sourceNodes).hasSize(1);
        assertThat(sourceNodes).contains(nodeElement);
    }

    private Element createNodeElement(String descriptionId) {
        INodeStyle style = ImageNodeStyle.newImageNodeStyle()
                .imageURL("")
                .scalingFactor(42)
                .build();

        NodeElementProps nodeElementProps = NodeElementProps.newNodeElementProps(UUID.randomUUID().toString())
                .type("type")
                .targetObjectId("targetObjectId")
                .targetObjectKind("targetObjectKind")
                .targetObjectLabel("targetObjectLabel")
                .descriptionId(descriptionId)
                .style(style)
                .children(List.of())
                .state(ViewModifier.Normal)
                .modifiers(Set.of())
                .collapsingState(CollapsingState.EXPANDED)
                .build();
        return new Element(NodeElementProps.TYPE, nodeElementProps);
    }
}
