/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Set;

/**
 * A label.
 *
 * @author hmarchadour
 * @author sbegaudeau
 */
public record Label(String id, String type, String text, LabelStyle style, Set<String> customizedStyleProperties) {

    public Label {
        Objects.requireNonNull(id);
        Objects.requireNonNull(text);
        Objects.requireNonNull(style);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, type: {2}, text: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.type, this.text);
    }

}
