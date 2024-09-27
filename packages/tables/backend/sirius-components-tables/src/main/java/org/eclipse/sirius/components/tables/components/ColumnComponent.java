/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.tables.components;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.elements.ColumnElementProps;
import org.eclipse.sirius.components.tables.renderer.TableRenderingCache;

/**
 * The component used to render lines.
 *
 * @author arichard
 */
public class ColumnComponent implements IComponent {

    private final ColumnComponentProps props;

    public ColumnComponent(ColumnComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ColumnDescription columnDescription = this.props.getColumnDescription();
        TableRenderingCache cache = this.props.getCache();
        BiFunction<VariableManager, String, String> cellTypeProvider = columnDescription.getCellTypeProvider();
        BiFunction<VariableManager, String, Object> cellValueProvider = columnDescription.getCellValueProvider();
        Function<VariableManager, String> cellOptionsIdProvider = columnDescription.getCellOptionsIdProvider();
        Function<VariableManager, String> cellOptionsLabelProvider = columnDescription.getCellOptionsLabelProvider();
        BiFunction<VariableManager, String, List<Object>> cellOptionsProvider = columnDescription.getCellOptionsProvider();

        String featureName = columnDescription.getFeatureNameProvider().apply(variableManager);
        ColumnElementProps columnElementProps = ColumnElementProps.newColumnElementProps(columnDescription.getId())
                .descriptionId(columnDescription.getId())
                .label(columnDescription.getLabelProvider().apply(variableManager))
                .featureName(featureName)
                .cellTypeProvider(cellTypeProvider)
                .cellValueProvider(cellValueProvider)
                .build();
        Element element = new Element(ColumnElementProps.TYPE, columnElementProps);
        cache.putColumn(columnDescription.getId(), element);
        cache.putColumn(element, featureName, cellTypeProvider, cellValueProvider, cellOptionsIdProvider, cellOptionsLabelProvider, cellOptionsProvider);
        return element;

    }

}
