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
import java.util.function.BiFunction;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.descriptions.CellDescription;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;
import org.eclipse.sirius.components.tables.elements.CheckboxCellElementProps;
import org.eclipse.sirius.components.tables.elements.LineElementProps;
import org.eclipse.sirius.components.tables.elements.MultiSelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.SelectCellElementProps;
import org.eclipse.sirius.components.tables.elements.TextfieldCellElementProps;
import org.eclipse.sirius.components.tables.renderer.TableRenderingCache;

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
        VariableManager variableManager = this.props.getVariableManager();
        LineDescription lineDescription = this.props.getLineDescription();
        ILinesRequestor linesRequestor = this.props.getLinesRequestor();
        TableRenderingCache cache = this.props.getCache();

        List<Element> children = new ArrayList<>();
        List<Object> semanticElements = lineDescription.getSemanticElementsProvider().apply(variableManager);

        for (Object semanticElement : semanticElements) {
            VariableManager lineVariableManager = variableManager.createChild();
            lineVariableManager.put(VariableManager.SELF, semanticElement);

            String targetObjectId = lineDescription.getTargetObjectIdProvider().apply(lineVariableManager);
            var optionalPreviousLine = linesRequestor.getByTargetObjectId(targetObjectId);

            Element lineElement = this.doRender(lineVariableManager, targetObjectId, optionalPreviousLine);
            children.add(lineElement);
        }
        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private Element doRender(VariableManager lineVariableManager, String targetObjectId, Optional<Line> optionalPreviousLine) {
        LineDescription lineDescription = this.props.getLineDescription();
        UUID lineId = optionalPreviousLine.map(Line::getId).orElseGet(() -> this.computeLineId(targetObjectId));

        String targetObjectKind = lineDescription.getTargetObjectKindProvider().apply(lineVariableManager);

        var cells = this.getCells(lineVariableManager, lineId);

        List<Element> children = new ArrayList<>();
        children.addAll(cells);

        LineElementProps lineElementProps = LineElementProps.newLineElementProps(lineId)
                .descriptionId(lineDescription.getId())
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .children(children)
                .build();

        return new Element(LineElementProps.TYPE, lineElementProps);
    }

    private List<Element> getCells(VariableManager lineVariableManager, UUID parentLineId) {
        List<Element> elements = new ArrayList<>();
        Map<UUID, Object> columnIdToObject = this.props.getCache().getColumnIdToObject();
        CellDescription cellDescription = this.props.getCellDescription();

        columnIdToObject.forEach((columnId, columTargetObject) -> {
            VariableManager columnVariableManager = lineVariableManager.createChild();
            columnVariableManager.put(ColumnDescription.COLUMN_TARGET_OBJECT, columTargetObject);

            String rawIdentifier = parentLineId.toString() + columnId;
            UUID cellId = UUID.nameUUIDFromBytes(rawIdentifier.getBytes());

            BiFunction<VariableManager, Object, String> cellTypeProvider = cellDescription.getCellTypeProvider();
            String cellType = cellTypeProvider.apply(lineVariableManager, columTargetObject);

            Element cellElement = null;
            if (SelectCellElementProps.TYPE.equals(cellType)) {
                var cellComponentProps = new SelectCellComponentProps(columnVariableManager, cellDescription, cellId, columnId, columTargetObject);
                cellElement = new Element(SelectCellComponent.class, cellComponentProps);
            } else if (MultiSelectCellElementProps.TYPE.equals(cellType)) {
                var cellComponentProps = new MultiSelectCellComponentProps(columnVariableManager, cellDescription, cellId, columnId, columTargetObject);
                cellElement = new Element(MultiSelectCellComponent.class, cellComponentProps);
            } else if (CheckboxCellElementProps.TYPE.equals(cellType)) {
                var cellComponentProps = new CheckboxCellComponentProps(columnVariableManager, cellDescription, cellId, columnId, columTargetObject);
                cellElement = new Element(CheckboxCellComponent.class, cellComponentProps);
            } else if (TextfieldCellElementProps.TYPE.equals(cellType)) {
                var cellComponentProps = new TextfieldCellComponentProps(columnVariableManager, cellDescription, cellId, columnId, columTargetObject);
                cellElement = new Element(TextfieldCellComponent.class, cellComponentProps);
            }
            if (cellElement != null) {
                elements.add(cellElement);
            }
        });

        return elements;

    }

    private UUID computeLineId(String targetObjectId) {
        String parentElementId = this.props.getParentElementId();
        LineDescription lineDescription = this.props.getLineDescription();

        String rawIdentifier = parentElementId.toString() + lineDescription.getId().toString() + targetObjectId;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes());
    }
}
