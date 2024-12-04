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
import java.util.stream.IntStream;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.elements.ColumnElementProps;

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
        VariableManager variableManager = this.props.getVariableManager();
        ColumnDescription columnDescription = this.props.getColumnDescription();

        List<Object> elements = columnDescription.getSemanticElementsProvider().apply(variableManager);

        List<Element> children = IntStream.range(0, elements.size())
                .mapToObj(index -> this.doRender(variableManager, elements.get(index), index))
                .toList();

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private Element doRender(VariableManager variableManager, Object object, int index) {
        ColumnDescription columnDescription = this.props.getColumnDescription();

        VariableManager columnVariableManager = variableManager.createChild();
        columnVariableManager.put(VariableManager.SELF, object);
        columnVariableManager.put("columnIndex", index);
        String targetObjectId = columnDescription.getTargetObjectIdProvider().apply(columnVariableManager);
        String targetObjectKind = columnDescription.getTargetObjectKindProvider().apply(columnVariableManager);

        String headerLabel = columnDescription.getHeaderLabelProvider().apply(columnVariableManager);

        List<String> headerIconURLs = columnDescription.getHeaderIconURLsProvider().apply(columnVariableManager);
        String headerIndexLabel = columnDescription.getHeaderIndexLabelProvider().apply(columnVariableManager);

        UUID columnId = this.computeColumnId(targetObjectId);
        this.props.getCache().putColumnObject(columnId, object);

        var columnElementProps = ColumnElementProps.newColumnElementProps(columnId)
                .descriptionId(columnDescription.getId())
                .headerLabel(headerLabel)
                .headerIconURLs(headerIconURLs)
                .headerIndexLabel(headerIndexLabel)
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .build();

        return new Element(ColumnElementProps.TYPE, columnElementProps);
    }

    private UUID computeColumnId(String targetObjectId) {
        ColumnDescription columnDescription = this.props.getColumnDescription();

        String rawIdentifier = columnDescription.getId().toString() + targetObjectId;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes());
    }
}
