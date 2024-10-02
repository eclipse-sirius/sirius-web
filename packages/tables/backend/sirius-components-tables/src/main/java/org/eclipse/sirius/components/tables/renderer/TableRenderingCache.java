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
package org.eclipse.sirius.components.tables.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Cache used during the rendering of a table.
 *
 * @author arichard
 */
public class TableRenderingCache {

    private final Map<UUID, List<Element>> lineDescriptionIdToLines = new HashMap<>();

    private final Map<UUID, Element> columnDescriptionIdToColumn = new LinkedHashMap<>();

    private final Map<Element, Object> lineToObject = new LinkedHashMap<>();

    private final Map<Element, String> columnToFeatureName = new LinkedHashMap<>();

    private final Map<Element, BiFunction<VariableManager, String, String>> columnToCellTypeProvider = new LinkedHashMap<>();

    private final Map<Element, BiFunction<VariableManager, String, Object>> columnToCellValueProvider = new LinkedHashMap<>();

    private final Map<Object, List<Element>> objectToLines = new LinkedHashMap<>();

    private final Map<Element, Function<VariableManager, String>> columnToCellOptionIdProvider = new LinkedHashMap<>();

    private final Map<Element, Function<VariableManager, String>> columnToCellOptionLabelProvider = new LinkedHashMap<>();

    private final Map<Element, BiFunction<VariableManager, String, List<Object>>> columnToCellOptionsProvider = new LinkedHashMap<>();

    public void putLine(UUID lineDescriptionId, Element lineElement) {
        this.lineDescriptionIdToLines.computeIfAbsent(lineDescriptionId, id -> new ArrayList<>()).add(lineElement);
    }

    public void putLine(Object object, Element lineElement) {
        this.lineToObject.put(lineElement, object);
        this.objectToLines.computeIfAbsent(object, obj -> new ArrayList<>()).add(lineElement);
    }

    public void putColumn(UUID columnDescriptionId, Element columnElement) {
        this.columnDescriptionIdToColumn.computeIfAbsent(columnDescriptionId, id -> columnElement);
    }

    public void putColumn(Element columnElement, String featureName, BiFunction<VariableManager, String, String> cellTypeProvider, BiFunction<VariableManager, String, Object> cellValueProvider,
            Function<VariableManager, String> cellOptionIdProvider, Function<VariableManager, String> cellOptionLabelProvider, BiFunction<VariableManager, String, List<Object>> cellOptionsProvider) {
        this.columnToFeatureName.put(columnElement, featureName);
        this.columnToCellTypeProvider.put(columnElement, cellTypeProvider);
        this.columnToCellValueProvider.put(columnElement, cellValueProvider);
        this.columnToCellOptionIdProvider.put(columnElement, cellOptionIdProvider);
        this.columnToCellOptionLabelProvider.put(columnElement, cellOptionLabelProvider);
        this.columnToCellOptionsProvider.put(columnElement, cellOptionsProvider);
    }

    public Map<UUID, List<Element>> getLineDescriptionIdToLines() {
        return this.lineDescriptionIdToLines;
    }

    public Map<UUID, Element> getColumnDescriptionIdToColumn() {
        return this.columnDescriptionIdToColumn;
    }

    public Map<Element, Object> getLineToObject() {
        return this.lineToObject;
    }

    public Map<Element, String> getColumnToFeatureName() {
        return this.columnToFeatureName;
    }

    public Map<Element, BiFunction<VariableManager, String, String>> getColumnToCellTypeProvider() {
        return this.columnToCellTypeProvider;
    }

    public Map<Element, BiFunction<VariableManager, String, Object>> getColumnToCellValueProvider() {
        return this.columnToCellValueProvider;
    }

    public Map<Element, Function<VariableManager, String>> getColumnToCellOptionIdProvider() {
        return this.columnToCellOptionIdProvider;
    }

    public Map<Element, Function<VariableManager, String>> getColumnToCellOptionLabelProvider() {
        return this.columnToCellOptionLabelProvider;
    }

    public Map<Element, BiFunction<VariableManager, String, List<Object>>> getColumnToCellOptionsProvider() {
        return this.columnToCellOptionsProvider;
    }

    public List<Element> getElementsRepresenting(Object semanticObject) {
        return this.objectToLines.getOrDefault(semanticObject, Collections.emptyList());
    }

    public Map<Object, List<Element>> getObjectToLines() {
        return this.objectToLines;
    }
}
