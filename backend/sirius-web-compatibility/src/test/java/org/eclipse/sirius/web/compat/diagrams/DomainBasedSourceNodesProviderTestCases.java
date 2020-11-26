/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.web.compat.services.representations.IdentifierProvider;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.junit.Test;

/**
 * Unit tests of the domain-based source nodes provider.
 *
 * @author sbegaudeau
 */
public class DomainBasedSourceNodesProviderTestCases {

    private IdentifierProvider identifierProvider = new IdentifierProvider(new NoOpIdMappingRepository(), 1) {
        @Override
        public String getIdentifier(EObject vsmElement) {
            if (vsmElement instanceof NodeMapping) {
                return ((NodeMapping) vsmElement).getName();
            }
            return super.getIdentifier(vsmElement);
        }
    };

    @Test
    public void testComputeSourceNodes() {
        EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
        edgeMapping.setSourceFinderExpression("aql:self"); //$NON-NLS-1$
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName("3b0c8a37-b19c-4978-8a85-ba283d45d22d"); //$NON-NLS-1$
        edgeMapping.getSourceMapping().add(nodeMapping);

        Object object = new Object();
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, object);

        DiagramRenderingCache cache = new DiagramRenderingCache();
        Element nodeElement = this.createNodeElement(UUID.fromString(nodeMapping.getName()));
        cache.put(object, nodeElement);

        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));

        variableManager.put(DiagramDescription.CACHE, cache);

        List<Element> sourceNodes = new DomainBasedSourceNodesProvider(edgeMapping, interpreter, this.identifierProvider).apply(variableManager);
        assertThat(sourceNodes).hasSize(1);
        assertThat(sourceNodes).contains(nodeElement);
    }

    private Element createNodeElement(UUID descriptionId) {
        // @formatter:off
        INodeStyle style = ImageNodeStyle.newImageNodeStyle()
                .imageURL("") //$NON-NLS-1$
                .scalingFactor(42)
                .build();

        NodeElementProps nodeElementProps = NodeElementProps.newNodeElementProps(UUID.randomUUID())
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
