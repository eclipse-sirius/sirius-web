/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

/**
 * Properties of the icon label cell element.
 *
 * @author frouene
 */
public record IconLabelCellElementProps(UUID id, String targetObjectId, String targetObjectKind, UUID columnId, String value, List<String> iconURLs) implements IProps {

    public static final String TYPE = "IconLabelCell";

    public IconLabelCellElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(columnId);
        Objects.requireNonNull(value);
        Objects.requireNonNull(iconURLs);
    }
}
