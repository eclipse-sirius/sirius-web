/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
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
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color("black")
                .fontSize(14)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL(List.of())
                .build();

        return Label.newLabel(UUID.randomUUID().toString())
                .type(Objects.requireNonNull(labelType).getValue())
                .text(Objects.requireNonNull(text))
                .alignment(Position.UNDEFINED)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .style(labelStyle)
                .build();
    }

    public InsideLabel basicInsideLabel(String text, LabelType labelType, boolean isHeader) {
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color("black")
                .fontSize(14)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL(List.of())
                .build();

        return InsideLabel.newLabel(UUID.randomUUID().toString())
                .text(Objects.requireNonNull(text))
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .style(labelStyle)
                .isHeader(isHeader)
                .displayHeaderSeparator(isHeader)
                .build();
    }

}
