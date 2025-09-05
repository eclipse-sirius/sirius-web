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
package org.eclipse.sirius.components.diagrams;

import java.util.Objects;
import java.util.Set;

/**
 * The outside label.
 *
 * @author gcoutable
 */
public record OutsideLabel(String id, String text, OutsideLabelLocation outsideLabelLocation, LabelStyle style, LabelOverflowStrategy overflowStrategy, LabelTextAlign textAlign, Set<String> customizedStyleProperties) {

    public OutsideLabel {
        Objects.requireNonNull(id);
        Objects.requireNonNull(text);
        Objects.requireNonNull(outsideLabelLocation);
        Objects.requireNonNull(style);
        Objects.requireNonNull(overflowStrategy);
        Objects.requireNonNull(textAlign);
    }

}
