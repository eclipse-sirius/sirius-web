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
package org.eclipse.sirius.components.tables.elements;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the cell element.
 *
 * @author Jerome Gout
 */
public record TextareaCellElementProps(
        UUID id,
        String descriptionId,
        String targetObjectId,
        String targetObjectKind,
        UUID columnId,
        String value,
        String tooltipValue) implements IProps {

    public static final String TYPE = "TextareaCell";

    public TextareaCellElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(columnId);
        Objects.requireNonNull(value);
        Objects.requireNonNull(tooltipValue);
    }
}
