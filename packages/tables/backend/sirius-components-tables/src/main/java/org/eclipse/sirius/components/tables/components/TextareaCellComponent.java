/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.TextareaCellDescription;
import org.eclipse.sirius.components.tables.elements.TextareaCellElementProps;

/**
 * The component used to render Textarea-based cells.
 *
 * @author Jerome Gout
 */
public class TextareaCellComponent implements IComponent {

    private final TextareaCellComponentProps props;

    public TextareaCellComponent(TextareaCellComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        TextareaCellDescription cellDescription = this.props.textareaCellDescription();

        String targetObjectId = cellDescription.getTargetObjectIdProvider().apply(variableManager);
        String targetObjectKind = cellDescription.getTargetObjectKindProvider().apply(variableManager);

        String value = cellDescription.getCellValueProvider().apply(variableManager, this.props.columnTargetObject());

        TextareaCellElementProps cellElementProps = new TextareaCellElementProps(this.props.cellId(), targetObjectId, targetObjectKind, this.props.columnId(), value);
        return new Element(TextareaCellElementProps.TYPE, cellElementProps);
    }
}
