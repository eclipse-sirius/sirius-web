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
package org.eclipse.sirius.web.diagrams.layout;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.elk.core.LayoutConfigurator;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.properties.IPropertyHolder;
import org.eclipse.elk.graph.properties.MapPropertyHolder;

/**
 * Specialized {@link LayoutConfigurator} that can configure layout options based on the {@code id} and {@code type}
 * attributes of diagram elements.
 *
 * @author hmarchadour
 */
public class SiriusWebLayoutConfigurator extends LayoutConfigurator {

    private final Map<String, MapPropertyHolder> idIndex = new HashMap<>();

    private final Map<String, MapPropertyHolder> typeIndex = new HashMap<>();

    /**
     * Configure layout options for all model elements with the given type.
     */
    public IPropertyHolder configureByType(String type) {
        MapPropertyHolder result = this.typeIndex.get(type);
        if (result == null) {
            result = new MapPropertyHolder();
            this.typeIndex.put(type, result);
        }
        return result;
    }

    @Override
    public void visit(final ElkGraphElement element) {
        super.visit(element);

        IPropertyHolder typeProperties = this.getPropertiesByType(element.getProperty(ELKDiagramConverter.PROPERTY_TYPE));
        this.applyProperties(element, typeProperties);

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
        for (Map.Entry<String, MapPropertyHolder> entry : layoutConfigurator.idIndex.entrySet()) {
            MapPropertyHolder thisHolder = this.typeIndex.get(entry.getKey());
            if (thisHolder == null) {
                thisHolder = new MapPropertyHolder();
                this.typeIndex.put(entry.getKey(), thisHolder);
            }
            thisHolder.copyProperties(entry.getValue());
        }
        return this;
    }

}
