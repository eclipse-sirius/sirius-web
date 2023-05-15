/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.experimental;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.layoutdata.Position;

/**
 * Detailed layout information for an "inside label", including the size and position of all its internal elements.
 *
 * @author pcdavid
 */
public class InsideLabelBox {
    /**
     * The whole area occupied by the label.
     */
    private final Rectangle labelArea;

    /**
     * The area occupied by the icon (if present), relative to the labelArea.
     */
    private final Optional<Rectangle> iconArea;

    /**
     * The area occupied by the text, relative to the labelArea.
     */
    private final Rectangle textArea;

    public InsideLabelBox(Rectangle labelArea, Optional<Rectangle> iconArea, Rectangle textArea) {
        this.labelArea = Objects.requireNonNull(labelArea);
        this.iconArea = Objects.requireNonNull(iconArea);
        this.textArea = Objects.requireNonNull(textArea);
    }

    public Rectangle getLabelArea() {
        return this.labelArea;
    }

    public Rectangle getRelativeContentArea() {
        if (this.iconArea.isPresent()) {
            return this.iconArea.get().union(this.textArea);
        } else {
            return this.textArea;
        }
    }

    public Optional<Rectangle> getRelativeIconArea() {
        return this.iconArea;
    }

    public Rectangle getRelativeTextArea() {
        return this.textArea;
    }

    public InsideLabelBox moveTo(Position newPosition) {
        return new InsideLabelBox(this.labelArea.moveTo(newPosition), this.iconArea, this.textArea);
    }

}
