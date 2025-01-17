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
import java.util.UUID;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.TextareaCellDescription;

/**
 * The props of the Textarea-based cell component.
 *
 * @author Jerome Gout
 */
public record TextareaCellComponentProps(VariableManager variableManager, TextareaCellDescription textareaCellDescription, UUID cellId, UUID columnId, Object columnTargetObject) implements IProps {

    public TextareaCellComponentProps {
        Objects.requireNonNull(variableManager);
        Objects.requireNonNull(textareaCellDescription);
        Objects.requireNonNull(cellId);
        Objects.requireNonNull(columnId);
        Objects.requireNonNull(columnTargetObject);
    }
}
