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
package org.eclipse.sirius.components.compatibility.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the relation-based source nodes provider.
 *
 * @author sbegaudeau
 */
public class RelationBasedSourceNodesProviderTests {

    @Test
    public void testComputeSourceNodes() {
        EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName("54af6e49-9792-4fbd-a40e-dfa90055bcc2"); //$NON-NLS-1$
        edgeMapping.getSourceMapping().add(nodeMapping);

        Object object = new Object();
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, object);

        DiagramRenderingCache cache = new DiagramRenderingCache();
        Element nodeElement = this.createNodeElement(UUID.fromString(nodeMapping.getName()));
        cache.put(object, nodeElement);

        variableManager.put(DiagramDescription.CACHE, cache);

        IIdentifierProvider identifierProvider = element -> nodeMapping.getName();
        List<Element> sourceNodes = new RelationBasedSourceNodesProvider(edgeMapping, identifierProvider).apply(variableManager);
        assertThat(sourceNodes).hasSize(1).contains(nodeElement);
    }

    private Element createNodeElement(UUID descriptionId) {
        // @formatter:off
        INodeStyle style = ImageNodeStyle.newImageNodeStyle()
                .imageURL("") //$NON-NLS-1$
                .build();

        NodeElementProps nodeElementProps = NodeElementProps.newNodeElementProps(UUID.randomUUID().toString())
                .type("type") //$NON-NLS-1$
                .targetObjectId("targetObjectId") //$NON-NLS-1$
                .targetObjectKind("targetObjectKind") //$NON-NLS-1$
                .targetObjectLabel("targetObjectLabel") //$NON-NLS-1$
                .descriptionId(descriptionId)
                .style(style)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .children(List.of())
                .build();
        // @formatter:on
        return new Element(NodeElementProps.TYPE, nodeElementProps);
    }
}
