/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

/**
 * POJO representing a parametric svg image.
 *
 * @author lfasani
 */
public class ParametricSVGImage {
    private final UUID id;

    private final String label;

    private final String path;

    public ParametricSVGImage(UUID id, String label, String path) {
        this.id = Objects.requireNonNull(id);
        this.label = Objects.requireNonNull(label);
        this.path = Objects.requireNonNull(path);
    }

    public UUID getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{' id: {1}, label: {2}, path: {3} '}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.path);
    }
}
