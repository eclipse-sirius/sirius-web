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
import java.util.UUID;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.elements.ColumnElementProps;
import org.eclipse.sirius.components.tables.events.ResizeTableColumnEvent;

/**
 * The component used to render lines.
 *
 * @author lfasani
 */
public class ColumnComponent implements IComponent {

    private final ColumnComponentProps props;

    public ColumnComponent(ColumnComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        ColumnDescription columnDescription = this.props.columnDescription();

        List<Element> children = columnDescription.getSemanticElementsProvider().apply(variableManager).stream()
                .map(object -> this.doRender(variableManager, object))
                .toList();
        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private Element doRender(VariableManager variableManager, Object object) {
        ColumnDescription columnDescription = this.props.columnDescription();

        VariableManager columnVariableManager = variableManager.createChild();
        columnVariableManager.put(VariableManager.SELF, object);
        String targetObjectId = columnDescription.getTargetObjectIdProvider().apply(columnVariableManager);
        String targetObjectKind = columnDescription.getTargetObjectKindProvider().apply(columnVariableManager);
        String label = columnDescription.getLabelProvider().apply(columnVariableManager);
        Integer initialWidth = columnDescription.getInitialWidthProvider().apply(columnVariableManager);
        boolean resizable = columnDescription.getIsResizablePredicate().test(columnVariableManager);
        UUID columnId = this.computeColumnId(targetObjectId);
        this.props.cache().putColumnObject(columnId, object);

        ColumnElementProps.Builder columnElementProps = ColumnElementProps.newColumnElementProps(columnId)
                .descriptionId(columnDescription.getId())
                .label(label)
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .resizable(resizable)
                .initialWidth(initialWidth);

        this.props.tableEvents().stream()
                .filter(ResizeTableColumnEvent.class::isInstance)
                .map(ResizeTableColumnEvent.class::cast)
                .filter(resizeTableColumnEvent -> resizeTableColumnEvent.columnId().equals(columnId.toString()))
                .findFirst()
                .ifPresentOrElse(resizeTableColumnEvent -> columnElementProps.width(resizeTableColumnEvent.width()),
                        () -> this.props.previousColumns().stream()
                                .filter(column -> column.getId().equals(columnId))
                                .map(Column::getWidth)
                                .filter(Objects::nonNull)
                                .findFirst()
                                .ifPresent(columnElementProps::width));

        return new Element(ColumnElementProps.TYPE, columnElementProps.build());
    }

    private UUID computeColumnId(String targetObjectId) {
        ColumnDescription columnDescription = this.props.columnDescription();

        String rawIdentifier = columnDescription.getId().toString() + targetObjectId;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes());
    }
}
