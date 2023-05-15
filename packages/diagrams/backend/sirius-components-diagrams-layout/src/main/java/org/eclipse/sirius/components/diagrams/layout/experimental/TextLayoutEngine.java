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

import java.util.OptionalDouble;

import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.layout.TextBoundsService;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Produces a {@link TextBox} from a text, font styling and optional maximum width.
 *
 * @author pcdavid
 */
public class TextLayoutEngine {
    private final TextBoundsService textBoundsService = new TextBoundsService();

    public TextBox layout(Label label, OptionalDouble maxWidth)  {
        TextBounds bounds;
        long lines;
        if (maxWidth.isPresent()) {
            bounds = this.textBoundsService.getAutoWrapBounds(label, maxWidth.getAsDouble());
            lines =  this.textBoundsService.getAutoWrapLines(label, maxWidth.getAsDouble());
        } else {
            bounds = this.textBoundsService.getBounds(label);
            lines =  Math.max(1, label.getText().lines().count());

        }
        Size size = new Size(bounds.getSize().getWidth(), bounds.getSize().getHeight());
        return new TextBox(size, lines);
    }

}