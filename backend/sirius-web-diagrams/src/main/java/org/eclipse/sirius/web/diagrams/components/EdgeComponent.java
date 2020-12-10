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
package org.eclipse.sirius.web.diagrams.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.Fragment;
import org.eclipse.sirius.web.components.FragmentProps;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.diagrams.EdgeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.web.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.web.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The component used to render an edge.
 *
 * @author sbegaudeau
 */
public class EdgeComponent implements IComponent {

    private final EdgeComponentProps props;

    public EdgeComponent(EdgeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        EdgeDescription edgeDescription = this.props.getEdgeDescription();
        IEdgesRequestor edgesRequestor = this.props.getEdgesRequestor();
        DiagramRenderingCache cache = this.props.getCache();

        List<Element> children = new ArrayList<>();

        // @formatter:off
        boolean hasCandidates = this.hasNodeCandidates(edgeDescription.getSourceNodeDescriptions(), cache)
                             && this.hasNodeCandidates(edgeDescription.getTargetNodeDescriptions(), cache);

        // @formatter:on

        if (hasCandidates) {
            VariableManager semanticElementsVariableManager = new VariableManager();
            variableManager.getVariables().forEach((key, value) -> semanticElementsVariableManager.put(key, value));
            semanticElementsVariableManager.put(DiagramDescription.CACHE, cache);

            List<Object> semanticElements = edgeDescription.getSemanticElementsProvider().apply(semanticElementsVariableManager);
            int count = 0;
            for (Object semanticElement : semanticElements) {
                VariableManager edgeVariableManager = variableManager.createChild();
                edgeVariableManager.put(VariableManager.SELF, semanticElement);
                edgeVariableManager.put(DiagramDescription.CACHE, cache);

                String targetObjectId = edgeDescription.getTargetObjectIdProvider().apply(edgeVariableManager);
                String targetObjectKind = edgeDescription.getTargetObjectKindProvider().apply(edgeVariableManager);
                String targetObjectLabel = edgeDescription.getTargetObjectLabelProvider().apply(edgeVariableManager);

                var optionalPreviousEdge = edgesRequestor.getByTargetObjectId(targetObjectId);
                SynchronizationPolicy synchronizationPolicy = edgeDescription.getSynchronizationPolicy();
                boolean shouldRender = synchronizationPolicy == SynchronizationPolicy.SYNCHRONIZED
                        || (synchronizationPolicy == SynchronizationPolicy.UNSYNCHRONIZED && optionalPreviousEdge.isPresent());

                List<Element> sourceNodes = edgeDescription.getSourceNodesProvider().apply(edgeVariableManager);
                if (shouldRender && !sourceNodes.isEmpty()) {
                    List<Element> targetNodes = edgeDescription.getTargetNodesProvider().apply(edgeVariableManager);

                    for (Element sourceNode : sourceNodes) {
                        for (Element targetNode : targetNodes) {
                            VariableManager idVariableManager = edgeVariableManager.createChild();
                            idVariableManager.put(EdgeDescription.SOURCE_NODE, sourceNode);
                            idVariableManager.put(EdgeDescription.TARGET_NODE, targetNode);
                            idVariableManager.put(EdgeDescription.COUNT, count);
                            String id = edgeDescription.getIdProvider().apply(idVariableManager);

                            EdgeStyle style = edgeDescription.getStyleProvider().apply(edgeVariableManager);

                            VariableManager labelVariableManager = edgeVariableManager.createChild();
                            labelVariableManager.put(LabelDescription.OWNER_ID, id);
                            Label beginLabel = edgeDescription.getBeginLabelProvider().apply(labelVariableManager).orElse(null);
                            Label centerLabel = edgeDescription.getCenterLabelProvider().apply(labelVariableManager).orElse(null);
                            Label endLabel = edgeDescription.getEndLabelProvider().apply(labelVariableManager).orElse(null);

                            String sourceId = this.getId(sourceNode);
                            String targetId = this.getId(targetNode);

                            // @formatter:off
                            EdgeElementProps edgeElementProps = EdgeElementProps.newEdgeElementProps(id)
                                    .type("edge:straight") //$NON-NLS-1$
                                    .descriptionId(edgeDescription.getId())
                                    .beginLabel(beginLabel)
                                    .centerLabel(centerLabel)
                                    .endLabel(endLabel)
                                    .targetObjectId(targetObjectId)
                                    .targetObjectKind(targetObjectKind)
                                    .targetObjectLabel(targetObjectLabel)
                                    .sourceId(sourceId)
                                    .targetId(targetId)
                                    .style(style)
                                    .routingPoints(List.of())
                                    .build();
                            // @formatter:on

                            Element edgeElement = new Element(EdgeElementProps.TYPE, edgeElementProps);
                            children.add(edgeElement);
                            count++;
                        }
                    }
                }
            }
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private boolean hasNodeCandidates(List<NodeDescription> nodeDescriptions, DiagramRenderingCache cache) {
        // @formatter:off
        return nodeDescriptions.stream()
                .map(NodeDescription::getId)
                .map(cache.getNodeDescriptionIdToNodes()::get)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .findAny()
                .isPresent();
        // @formatter:on
    }

    private String getId(Element nodeElement) {
        // @formatter:off
        return Optional.of(nodeElement.getProps())
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast)
                .map(NodeElementProps::getId)
                .orElse(""); //$NON-NLS-1$
        // @formatter:on
    }

}
