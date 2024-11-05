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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.elements.TableElementProps;
import org.eclipse.sirius.components.tables.renderer.TableRenderingCache;

/**
 * The component used to render the table representation.
 *
 * @author arichard
 */
public class TableComponent implements IComponent {

    private final TableComponentProps props;

    public TableComponent(TableComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        TableDescription tableDescription = this.props.tableDescription();
        Optional<Table> optionalPreviousTable = this.props.previousTable();

        String id = variableManager.get(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, String.class).orElseGet(() -> UUID.randomUUID().toString());
        String targetObjectId = tableDescription.getTargetObjectIdProvider().apply(variableManager);
        String targetObjectKind = tableDescription.getTargetObjectKindProvider().apply(variableManager);
        boolean stripeRow = tableDescription.getIsStripeRowPredicate().test(variableManager);

        TableRenderingCache cache = new TableRenderingCache();
        ITableElementRequestor tableElementRequestor = new TableElementRequestor();

        var childrenColumns = tableDescription.getColumnDescriptions().stream()
                .map(columnDescription -> {
                    var previousColumns = optionalPreviousTable.map(previousTable -> tableElementRequestor.getColumns(previousTable, columnDescription)).orElse(List.of());
                    var columnComponentProps = new ColumnComponentProps(variableManager, columnDescription, previousColumns, cache, this.props.tableEvents());
                    return new Element(ColumnComponent.class, columnComponentProps);
                }).toList();

        var childrenLines = tableDescription.getLineDescriptions().stream()
                .map(lineDescription -> {
                    var previousLines = optionalPreviousTable.map(previousTable -> tableElementRequestor.getRootLines(previousTable, lineDescription)).orElse(List.of());
                    ILinesRequestor linesRequestor = new LinesRequestor(previousLines);
                    var lineComponentProps = new LineComponentProps(variableManager, lineDescription, tableDescription.getCellDescriptions(), linesRequestor, cache, id);
                    return new Element(LineComponent.class, lineComponentProps);
                }).toList();


        List<Element> children = new ArrayList<>();
        children.addAll(childrenColumns);
        children.addAll(childrenLines);

        TableElementProps tableElementProps = TableElementProps.newTableElementProps(id)
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .descriptionId(tableDescription.getId())
                .stripeRow(stripeRow)
                .children(children)
                .build();

        return new Element(TableElementProps.TYPE, tableElementProps);
    }

}
