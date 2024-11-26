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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.descriptions.CheckboxCellDescription;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.tables.descriptions.IconLabelCellDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.descriptions.MultiSelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.SelectCellDescription;
import org.eclipse.sirius.components.tables.descriptions.TextfieldCellDescription;
import org.eclipse.sirius.components.tables.elements.LineElementProps;
import org.eclipse.sirius.components.tables.events.ResizeTableRowEvent;

/**
 * The component used to render lines.
 *
 * @author arichard
 * @author lfasani
 */
public class LineComponent implements IComponent {

    private final LineComponentProps props;

    public LineComponent(LineComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        LineDescription lineDescription = this.props.lineDescription();
        ILinesRequestor linesRequestor = this.props.linesRequestor();

        List<Element> children = new ArrayList<>();
        var index = 0;
        for (Object semanticElement : this.props.semanticRowElements()) {
            VariableManager lineVariableManager = variableManager.createChild();
            lineVariableManager.put(VariableManager.SELF, semanticElement);
            lineVariableManager.put("rowIndex", index++);

            String targetObjectId = lineDescription.getTargetObjectIdProvider().apply(lineVariableManager);
            var optionalPreviousLine = linesRequestor.getByTargetObjectId(targetObjectId);

            if (lineDescription.getShouldRenderPredicate().test(lineVariableManager)) {
                Element lineElement = this.doRender(lineVariableManager, targetObjectId, optionalPreviousLine);
                children.add(lineElement);
            }

        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private Element doRender(VariableManager lineVariableManager, String targetObjectId, Optional<Line> optionalPreviousLine) {
        LineDescription lineDescription = this.props.lineDescription();
        UUID rowId = optionalPreviousLine.map(Line::getId).orElseGet(() -> this.computeLineId(targetObjectId));

        String targetObjectKind = lineDescription.getTargetObjectKindProvider().apply(lineVariableManager);

        String headerLabel = lineDescription.getHeaderLabelProvider().apply(lineVariableManager);
        List<String> headerIconURLs = lineDescription.getHeaderIconURLsProvider().apply(lineVariableManager);
        String headerIndexLabel = lineDescription.getHeaderIndexLabelProvider().apply(lineVariableManager);

        var cells = this.getCells(lineVariableManager, rowId);
        boolean resizable = lineDescription.getIsResizablePredicate().test(lineVariableManager);
        Integer initialHeight = lineDescription.getInitialHeightProvider().apply(lineVariableManager);

        List<Element> children = new ArrayList<>();
        children.addAll(cells);

        var height = this.props.tableEvents().stream()
                .filter(ResizeTableRowEvent.class::isInstance)
                .map(ResizeTableRowEvent.class::cast)
                .filter(resizeTableRowEvent -> resizeTableRowEvent.rowId().equals(rowId.toString()))
                .findFirst()
                .map(ResizeTableRowEvent::height)
                .orElseGet(() -> optionalPreviousLine.stream()
                        .map(Line::getHeight)
                        .findFirst()
                        .orElse(initialHeight));

        var rowElementProps = LineElementProps.newLineElementProps(rowId)
                .descriptionId(lineDescription.getId())
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .headerLabel(headerLabel)
                .headerIconURLs(headerIconURLs)
                .headerIndexLabel(headerIndexLabel)
                .children(children)
                .resizable(resizable)
                .height(height)
                .build();

        return new Element(LineElementProps.TYPE, rowElementProps);
    }

    private List<Element> getCells(VariableManager lineVariableManager, UUID parentLineId) {
        List<Element> elements = new ArrayList<>();
        Map<UUID, Object> columnIdToObject = this.props.cache().getColumnIdToObject();

        columnIdToObject.forEach((columnId, columTargetObject) -> {
            VariableManager variableManager = lineVariableManager.createChild();
            variableManager.put(ColumnDescription.COLUMN_TARGET_OBJECT, columTargetObject);

            String rawIdentifier = parentLineId.toString() + columnId;
            UUID cellId = UUID.nameUUIDFromBytes(rawIdentifier.getBytes());

            ICellDescription cellDescription = this.props.cellDescriptions().stream()
                    .filter(cell -> cell.getCanCreatePredicate().test(variableManager))
                    .findFirst()
                    .orElse(null);

            Element cellElement = null;
            if (cellDescription instanceof SelectCellDescription selectCellDescription) {
                var cellComponentProps = new SelectCellComponentProps(variableManager, selectCellDescription, cellId, columnId, columTargetObject);
                cellElement = new Element(SelectCellComponent.class, cellComponentProps);
            } else if (cellDescription instanceof MultiSelectCellDescription multiSelectCellDescription) {
                var cellComponentProps = new MultiSelectCellComponentProps(variableManager, multiSelectCellDescription, cellId, columnId, columTargetObject);
                cellElement = new Element(MultiSelectCellComponent.class, cellComponentProps);
            } else if (cellDescription instanceof CheckboxCellDescription checkboxCellDescription) {
                var cellComponentProps = new CheckboxCellComponentProps(variableManager, checkboxCellDescription, cellId, columnId, columTargetObject);
                cellElement = new Element(CheckboxCellComponent.class, cellComponentProps);
            } else if (cellDescription instanceof TextfieldCellDescription textfieldCellDescription) {
                var cellComponentProps = new TextfieldCellComponentProps(variableManager, textfieldCellDescription, cellId, columnId, columTargetObject);
                cellElement = new Element(TextfieldCellComponent.class, cellComponentProps);
            } else if (cellDescription instanceof IconLabelCellDescription iconLabelCellDescription) {
                var cellComponentProps = new IconLabelCellComponentProps(variableManager, iconLabelCellDescription, cellId, columnId, columTargetObject);
                cellElement = new Element(IconLabelCellComponent.class, cellComponentProps);
            }
            if (cellElement != null) {
                elements.add(cellElement);
            }
        });

        return elements;

    }

    private UUID computeLineId(String targetObjectId) {
        String parentElementId = this.props.parentElementId();
        LineDescription lineDescription = this.props.lineDescription();

        String rawIdentifier = parentElementId + lineDescription.getId() + targetObjectId;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes());
    }
}
