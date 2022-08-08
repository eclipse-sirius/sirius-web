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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.elk.core.LayoutConfigurator;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.elk.graph.properties.MapPropertyHolder;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;

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

}
