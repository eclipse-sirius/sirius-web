/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.elk.core.LayoutConfigurator;
import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.elk.graph.properties.MapPropertyHolder;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;

/**
 * Specialized {@link LayoutConfigurator} that can configure layout options based on the {@code id} and {@code type}
 * attributes of diagram elements.
 *
 * @author hmarchadour
 */
public class SiriusWebLayoutConfigurator extends LayoutConfigurator implements ISiriusWebLayoutConfigurator {

    private final Map<String, MapPropertyHolder> idIndex = new HashMap<>();

    private final Map<String, MapPropertyHolder> typeIndex = new HashMap<>();

    private final Map<Class<? extends ILayoutStrategy>, MapPropertyHolder> childrenLayoutPropertyIndex = new HashMap<>();

    @Override
    public IPropertyHolder configureByType(String type) {
        return this.typeIndex.computeIfAbsent(type, key -> new MapPropertyHolder());
    }

    @Override
    public IPropertyHolder configureByChildrenLayoutStrategy(Class<? extends ILayoutStrategy> layoutStrategyClass) {
        return this.childrenLayoutPropertyIndex.computeIfAbsent(layoutStrategyClass, key -> new MapPropertyHolder());
    }

    @Override
    public IPropertyHolder configureByElementClass(Class<? extends ElkGraphElement> elementClass) {
        return this.configure(elementClass);
    }

    @Override
    public void visit(final ElkGraphElement element) {
        super.visit(element);

        IPropertyHolder typeProperties = this.getPropertiesByType(element.getProperty(ELKDiagramConverter.PROPERTY_TYPE));
        this.applyProperties(element, typeProperties);

        IPropertyHolder childrenLayoutStrategyProperties = this.getPropertiesByLayoutStrategy(element.getProperty(ELKDiagramConverter.PROPERTY_CHILDREN_LAYOUT_STRATEGY));
        this.applyProperties(element, childrenLayoutStrategyProperties);

        IPropertyHolder idProperties = this.getPropertiesById(element.getIdentifier());
        this.applyProperties(element, idProperties);
    }

    @Override
    public LayoutConfigurator overrideWith(LayoutConfigurator other) {
        if (other instanceof SiriusWebLayoutConfigurator siriusWebLayoutConfigurator) {
            return this.overrideWith(siriusWebLayoutConfigurator);
        } else {
            return super.overrideWith(other);
        }
    }

    private IPropertyHolder getPropertiesById(String id) {
        return this.idIndex.get(id);
    }

    private IPropertyHolder getPropertiesByType(String type) {
        return this.typeIndex.get(type);
    }

    private IPropertyHolder getPropertiesByLayoutStrategy(Class<? extends ILayoutStrategy> layoutStrategyClass) {
        return this.childrenLayoutPropertyIndex.get(layoutStrategyClass);
    }

    private SiriusWebLayoutConfigurator overrideWith(SiriusWebLayoutConfigurator layoutConfigurator) {
        super.overrideWith(layoutConfigurator);

        for (Map.Entry<String, MapPropertyHolder> entry : layoutConfigurator.idIndex.entrySet()) {
            MapPropertyHolder thisHolder = this.idIndex.get(entry.getKey());
            if (thisHolder == null) {
                thisHolder = new MapPropertyHolder();
                this.idIndex.put(entry.getKey(), thisHolder);
            }
            thisHolder.copyProperties(entry.getValue());
        }
        for (Map.Entry<String, MapPropertyHolder> entry : layoutConfigurator.typeIndex.entrySet()) {
            MapPropertyHolder thisHolder = this.typeIndex.get(entry.getKey());
            if (thisHolder == null) {
                thisHolder = new MapPropertyHolder();
                this.typeIndex.put(entry.getKey(), thisHolder);
            }
            thisHolder.copyProperties(entry.getValue());
        }
        for (Map.Entry<Class<? extends ILayoutStrategy>, MapPropertyHolder> entry : layoutConfigurator.childrenLayoutPropertyIndex.entrySet()) {
            MapPropertyHolder thisHolder = this.childrenLayoutPropertyIndex.get(entry.getKey());
            if (thisHolder == null) {
                thisHolder = new MapPropertyHolder();
                this.childrenLayoutPropertyIndex.put(entry.getKey(), thisHolder);
            }
            thisHolder.copyProperties(entry.getValue());
        }
        return this;
    }

    @Override
    public ElkNode applyAfterLayout(ElkNode elkDiagram, IEditingContext editingContext, Diagram diagram) {
        List<ElkNode> children = this.collectChildrenNode(elkDiagram);
        for (ElkNode elkNode : children) {
            if (this.shouldAdjustChildrenSize(elkNode)) {
                this.adjustChildrenSize(elkNode);
            }
        }

        return ISiriusWebLayoutConfigurator.super.applyAfterLayout(elkDiagram, editingContext, diagram);
    }

    @Override
    public ElkNode applyBeforeLayout(ElkNode elkDiagram, IEditingContext editingContext, Diagram diagram) {
        ElkNode elkNode = ISiriusWebLayoutConfigurator.super.applyBeforeLayout(elkDiagram, editingContext, diagram);

        List<ElkNode> children = this.collectChildrenNode(elkDiagram);
        for (ElkNode child : children) {
            Class<? extends ILayoutStrategy> childLayoutStrategyproperty = child.getProperty(ELKDiagramConverter.PROPERTY_CHILDREN_LAYOUT_STRATEGY);
            if (childLayoutStrategyproperty != null && ListLayoutStrategy.class.isAssignableFrom(childLayoutStrategyproperty)) {
                var optionalNode = this.findNodeById(diagram, child.getIdentifier());
                if (optionalNode.isPresent()) {
                    Node node = optionalNode.get();
                    this.updateElkPadding(child, node);
                }
            }
        }
        return elkNode;
    }

    /**
     * If the node is contained in a rectangular node with a header, adds to the existing padding top the node border
     * size and 5 (We may use, in a near future, a property to define that padding)
     *
     * @param elkNode
     *            The elk node to update
     * @param node
     *            The node that have the header or not
     */
    private void updateElkPadding(ElkNode elkNode, Node node) {
        INodeStyle nodeStyle = node.getStyle();
        if (nodeStyle instanceof RectangularNodeStyle style) {
            if (style.isWithHeader()) {
                // We are supposing that rectangular node label is positioned on top center of the node, which could not
                // be always right
                ElkPadding currentPadding = elkNode.getProperty(CoreOptions.PADDING);
                double updatedPaddingTop = currentPadding.top + style.getBorderSize() + 5;
                elkNode.setProperty(CoreOptions.PADDING, new ElkPadding(updatedPaddingTop, currentPadding.right, currentPadding.bottom, currentPadding.left));
            }
        }

    }

    private List<ElkNode> collectChildrenNode(ElkNode elkDiagram) {
        List<ElkNode> children = new ArrayList<>(elkDiagram.getChildren());
        for (ElkNode child : elkDiagram.getChildren()) {
            children.addAll(this.collectChildrenNode(child));
        }
        return Collections.unmodifiableList(children);
    }

    private boolean shouldAdjustChildrenSize(ElkNode elkNode) {
        return ListLayoutStrategy.class.equals(elkNode.getProperty(ELKDiagramConverter.PROPERTY_CHILDREN_LAYOUT_STRATEGY));
    }

    private void adjustChildrenSize(ElkNode elkNode) {
        double parentWidth = elkNode.getWidth();
        for (ElkNode child : elkNode.getChildren()) {
            child.setWidth(parentWidth);
        }
    }

    private Optional<Node> findNodeById(Diagram diagram, String nodeId) {
        return this.findNode(node -> Objects.equals(node.getId(), nodeId), diagram.getNodes());
    }

    private Optional<Node> findNode(Predicate<Node> condition, List<Node> candidates) {
        Optional<Node> result = Optional.empty();
        for (Node node : candidates) {
            if (condition.test(node)) {
                result = Optional.of(node);
            } else {
                result = this.findNode(condition, node.getBorderNodes()).or(() -> this.findNode(condition, node.getChildNodes()));
            }
            if (result.isPresent()) {
                break;
            }
        }
        return result;
    }

}
