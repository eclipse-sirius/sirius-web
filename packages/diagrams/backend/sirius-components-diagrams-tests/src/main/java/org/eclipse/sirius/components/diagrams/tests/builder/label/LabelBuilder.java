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
package org.eclipse.sirius.components.diagrams.tests.builder.label;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.components.LabelType;

/**
 * The builder used to create a label.
 *
 * @author gcoutable
 */
public final class LabelBuilder {

    public Label basicLabel(String text, LabelType labelType) {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color("black") //$NON-NLS-1$
                .fontSize(14)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("") //$NON-NLS-1$
                .build();
        // @formatter:on

        // @formatter:off
        return Label.newLabel(UUID.randomUUID().toString())
                .type(Objects.requireNonNull(labelType).getValue())
                .text(Objects.requireNonNull(text))
                .alignment(Position.UNDEFINED)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .style(labelStyle)
                .build();
        // @formatter:on
    }

}
