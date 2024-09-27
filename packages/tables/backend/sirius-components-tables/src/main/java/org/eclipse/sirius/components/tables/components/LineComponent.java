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
import java.util.function.Function;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Line;
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

            cache.putLine(lineDescription.getId(), lineElement);
            cache.putLine(semanticElement, lineElement);

        }
        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private Element doRender(VariableManager lineVariableManager, String targetObjectId, Optional<Line> optionalPreviousLine) {
        LineDescription lineDescription = this.props.getLineDescription();
        UUID lineId = optionalPreviousLine.map(Line::getId).orElseGet(() -> this.computeLineId(targetObjectId));

        var cells = this.getCells(lineVariableManager, lineId);

        List<Element> children = new ArrayList<>();
        children.addAll(cells);

        LineElementProps lineElementProps = LineElementProps.newLineElementProps(lineId)
                .targetObjectId(targetObjectId)
                .descriptionId(lineDescription.getId())
                .children(children)
                .build();

        return new Element(LineElementProps.TYPE, lineElementProps);
    }

    private List<Element> getCells(VariableManager lineVariableManager, UUID parentLineId) {
        List<Element> elements = new ArrayList<>();
        Map<UUID, Element> columnDescriptionIdToColumn = this.props.getCache().getColumnDescriptionIdToColumn();

        columnDescriptionIdToColumn.forEach((key, value) -> {
            String rawIdentifier = parentLineId.toString() + key.toString();
            UUID cellId = UUID.nameUUIDFromBytes(rawIdentifier.getBytes());
            String featureName = this.props.getCache().getColumnToFeatureName().get(value);
            BiFunction<VariableManager, String, String> cellTypeProvider = this.props.getCache().getColumnToCellTypeProvider().get(value);
            String cellType = cellTypeProvider.apply(lineVariableManager, featureName);
            BiFunction<VariableManager, String, Object> cellValueProvider = this.props.getCache().getColumnToCellValueProvider().get(value);
            UUID columnId = key;

            Element cellElement = null;

            if (SelectCellElementProps.TYPE.equals(cellType)) {
                Function<VariableManager, String> cellOptionIdProvider = this.props.getCache().getColumnToCellOptionIdProvider().get(value);
                Function<VariableManager, String> cellOptionLabelProvider = this.props.getCache().getColumnToCellOptionLabelProvider().get(value);
                BiFunction<VariableManager, String, List<Object>> cellOptionsProvider = this.props.getCache().getColumnToCellOptionsProvider().get(value);
                var cellComponentProps = SelectCellComponentProps.newSelectCellComponentProps()
                        .variableManager(lineVariableManager)
                        .cellValueProvider(cellValueProvider)
                        .featureName(featureName)
                        .cellId(cellId)
                        .parentLineId(parentLineId)
                        .columnId(columnId)
                        .cellOptionIdProvider(cellOptionIdProvider)
                        .cellOptionLabelProvider(cellOptionLabelProvider)
                        .cellOptionsProvider(cellOptionsProvider)
                        .build();
                cellElement = new Element(SelectCellComponent.class, cellComponentProps);
            } else if (MultiSelectCellElementProps.TYPE.equals(cellType)) {
                Function<VariableManager, String> cellOptionIdProvider = this.props.getCache().getColumnToCellOptionIdProvider().get(value);
                Function<VariableManager, String> cellOptionLabelProvider = this.props.getCache().getColumnToCellOptionLabelProvider().get(value);
                BiFunction<VariableManager, String, List<Object>> cellOptionsProvider = this.props.getCache().getColumnToCellOptionsProvider().get(value);
                var cellComponentProps = MultiSelectCellComponentProps.newMultiSelectCellComponentProps()
                        .variableManager(lineVariableManager)
                        .cellValuesProvider(cellValueProvider)
                        .featureName(featureName)
                        .cellId(cellId)
                        .parentLineId(parentLineId)
                        .columnId(columnId)
                        .cellOptionIdProvider(cellOptionIdProvider)
                        .cellOptionLabelProvider(cellOptionLabelProvider)
                        .cellOptionsProvider(cellOptionsProvider)
                        .build();
                cellElement = new Element(MultiSelectCellComponent.class, cellComponentProps);
            } else if (CheckboxCellElementProps.TYPE.equals(cellType)) {
                var cellComponentProps = new CheckboxCellComponentProps(lineVariableManager, cellValueProvider, featureName, cellId, parentLineId, columnId);
                cellElement = new Element(CheckboxCellComponent.class, cellComponentProps);
            } else if (TextfieldCellElementProps.TYPE.equals(cellType)) {
                var cellComponentProps = new TextfieldCellComponentProps(lineVariableManager, cellValueProvider, featureName, cellId, parentLineId, columnId);
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
