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
package org.eclipse.sirius.web.diagrams.layout.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.TextBoundsService;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests of the text bounds service used mostly to ensure that the behavior will not change without anybody
 * noticing it.
 *
 * @author sbegaudeau
 */
public class TextBoundsServiceTestCases {

    private static final int FONT_SIZE = 16;

    private static final String LABEL_COLOR = "#000000"; //$NON-NLS-1$

    private static final String LABEL_TYPE = "labelType"; //$NON-NLS-1$

    private static final String ICON_URL = ""; //$NON-NLS-1$

    private static final String ID = "ID"; //$NON-NLS-1$

    @Test
    @Ignore
    public void testHelloWorldSize() {
        //@formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(LABEL_COLOR)
                .fontSize(FONT_SIZE)
                .iconURL(ICON_URL)
                .build();

        Label label = Label.newLabel(ID)
                .type(LABEL_TYPE)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .text("Hello World") //$NON-NLS-1$
                .style(labelStyle)
                .build();
        //@formatter:on
        Size size = new TextBoundsService().getBounds(label).getSize();
        assertThat(size.getWidth()).isCloseTo(82.6875, Offset.offset(0.0001));
        assertThat(size.getHeight()).isCloseTo(18.3984, Offset.offset(0.0001));
    }

    @Test
    @Ignore
    public void testAlphabetSize() {
        //@formatter:off

        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(LABEL_COLOR)
                .fontSize(FONT_SIZE)
                .iconURL(ICON_URL)
                .build();
        Label label = Label.newLabel(ID)
                .type(LABEL_TYPE)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .text("abcdefghijklmopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ") //$NON-NLS-1$
                .style(labelStyle)
                .build();
        //@formatter:on
        Size size = new TextBoundsService().getBounds(label).getSize();
        assertThat(size.getWidth()).isCloseTo(476.55468, Offset.offset(0.0001));
        assertThat(size.getHeight()).isCloseTo(18.3984, Offset.offset(0.0001));
    }

    @Test
    @Ignore
    public void testSpecialCharactersSize() {
        //@formatter:off

        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(LABEL_COLOR)
                .fontSize(FONT_SIZE)
                .iconURL(ICON_URL)
                .build();
        Label label = Label.newLabel(ID)
                .type(LABEL_TYPE)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .text(" &;$({[]})*@#=:;,?./+-_! ") //$NON-NLS-1$
                .style(labelStyle)
                .build();
        //@formatter:on
        Size size = new TextBoundsService().getBounds(label).getSize();
        assertThat(size.getWidth()).isCloseTo(162.99218, Offset.offset(0.0001));
        assertThat(size.getHeight()).isCloseTo(18.3984, Offset.offset(0.0001));
    }
}
