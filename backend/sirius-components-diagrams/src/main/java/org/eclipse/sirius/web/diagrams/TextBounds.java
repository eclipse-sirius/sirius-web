/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams;

import java.util.Objects;

/**
 * Used to hold both the size of the text and its alignment.
 *
 * @author sbegaudeau
 */
public class TextBounds {
    private final Size size;

    private final Position alignment;

    public TextBounds(Size size, Position alignment) {
        this.size = Objects.requireNonNull(size);
        this.alignment = Objects.requireNonNull(alignment);
    }

    public Size getSize() {
        return this.size;
    }

    public Position getAlignment() {
        return this.alignment;
    }
}
