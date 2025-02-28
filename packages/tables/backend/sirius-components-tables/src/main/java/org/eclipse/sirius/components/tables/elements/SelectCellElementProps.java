/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.tables.SelectCellOption;

/**
 * Properties of the Select-based cell element.
 *
 * @author lfasani
 */
public record SelectCellElementProps(UUID id, String descriptionId, String targetObjectId, String targetObjectKind, UUID columnId, List<SelectCellOption> options, String value) implements IProps {

    public static final String TYPE = "SelectCell";

    public SelectCellElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(columnId);
        Objects.requireNonNull(options);
        Objects.requireNonNull(value);
    }
}
