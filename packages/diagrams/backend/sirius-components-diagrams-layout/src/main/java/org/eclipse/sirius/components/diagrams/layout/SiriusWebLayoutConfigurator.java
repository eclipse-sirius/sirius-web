/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import org.eclipse.elk.core.LayoutConfigurator;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.elk.graph.properties.MapPropertyHolder;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;

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
        if (other instanceof SiriusWebLayoutConfigurator) {
            return this.overrideWith((SiriusWebLayoutConfigurator) other);
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

}
