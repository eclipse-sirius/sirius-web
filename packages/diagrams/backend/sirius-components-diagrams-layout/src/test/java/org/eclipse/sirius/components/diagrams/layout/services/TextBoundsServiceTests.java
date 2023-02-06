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
package org.eclipse.sirius.components.diagrams.layout.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.layout.TextBoundsService;
import org.junit.jupiter.api.Test;

/**
 * Tests of the TextBoundsService.
 *
 * @author arichard
 */
public class TextBoundsServiceTests {

    private static final String BLACK_COLOR = "black";

    private static final String LABEL_ID = "label";

    private static final String LABEL_TYPE = "labelType";

    private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
            + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
            + "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
            + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private static final String LOREM_IPSUM_WITH_NEW_LINE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. \n"
            + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
            + "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
            + "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private final TextBoundsService textBoundsService = new TextBoundsService();

    @Test
    public void smallLabelWithSmallFont() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(BLACK_COLOR)
                .fontSize(8)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("")
                .build();

        Label label = Label.newLabel(LABEL_ID)
                .alignment(Position.at(0, 0))
                .position(Position.at(0, 0))
                .size(Size.of(0, 0))
                .style(labelStyle)
                .text("small font")
                .type(LABEL_TYPE)
                .build();
        // @formatter:on
        TextBounds labelBounds = this.textBoundsService.getAutoWrapBounds(label, 200);
        assertThat(labelBounds.getSize().getWidth()).isEqualTo(200);
        assertThat(labelBounds.getSize().getHeight()).isCloseTo(11.0, Offset.offset(1.0));
    }

    @Test
    public void smallLabelWithNormalFont() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(BLACK_COLOR)
                .fontSize(14)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("")
                .build();

        Label label = Label.newLabel(LABEL_ID)
                .alignment(Position.at(0, 0))
                .position(Position.at(0, 0))
                .size(Size.of(0, 0))
                .style(labelStyle)
                .text("default font")
                .type(LABEL_TYPE)
                .build();
        // @formatter:on
        TextBounds labelBounds = this.textBoundsService.getAutoWrapBounds(label, 200);
        assertThat(labelBounds.getSize().getWidth()).isEqualTo(200);
        assertThat(labelBounds.getSize().getHeight()).isCloseTo(19.0, Offset.offset(1.0));
    }

    @Test
    public void smallLabelWithBigFont() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(BLACK_COLOR)
                .fontSize(26)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("big font")
                .build();

        Label label = Label.newLabel(LABEL_ID)
                .alignment(Position.at(0, 0))
                .position(Position.at(0, 0))
                .size(Size.of(0, 0))
                .style(labelStyle)
                .text("Lorem ipsum")
                .type(LABEL_TYPE)
                .build();
        // @formatter:on
        TextBounds labelBounds = this.textBoundsService.getAutoWrapBounds(label, 200);
        assertThat(labelBounds.getSize().getWidth()).isEqualTo(200);
        assertThat(labelBounds.getSize().getHeight()).isCloseTo(36.0, Offset.offset(1.0));
    }

    @Test
    public void labelGreaterThanContainerWithSmallFont() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(BLACK_COLOR)
                .fontSize(8)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("")
                .build();

        Label label = Label.newLabel(LABEL_ID)
                .alignment(Position.at(0, 0))
                .position(Position.at(0, 0))
                .size(Size.of(0, 0))
                .style(labelStyle)
                .text(LOREM_IPSUM)
                .type(LABEL_TYPE)
                .build();
        // @formatter:on
        TextBounds labelBounds = this.textBoundsService.getAutoWrapBounds(label, 400);
        assertThat(labelBounds.getSize().getWidth()).isEqualTo(400);
        assertThat(labelBounds.getSize().getHeight()).isCloseTo(59.0, Offset.offset(1.0));
    }

    @Test
    public void labelGreaterThanContainerWithNormalFont() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(BLACK_COLOR)
                .fontSize(14)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("")
                .build();

        Label label = Label.newLabel(LABEL_ID)
                .alignment(Position.at(0, 0))
                .position(Position.at(0, 0))
                .size(Size.of(0, 0))
                .style(labelStyle)
                .text(LOREM_IPSUM)
                .type(LABEL_TYPE)
                .build();
        // @formatter:on
        TextBounds labelBounds = this.textBoundsService.getAutoWrapBounds(label, 400);
        assertThat(labelBounds.getSize().getWidth()).isEqualTo(400);
        assertThat(labelBounds.getSize().getHeight()).isCloseTo(155.0, Offset.offset(1.0));
    }

    @Test
    public void labelGreaterThanContainerWithBigFont() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(BLACK_COLOR)
                .fontSize(26)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("")
                .build();

        Label label = Label.newLabel(LABEL_ID)
                .alignment(Position.at(0, 0))
                .position(Position.at(0, 0))
                .size(Size.of(0, 0))
                .style(labelStyle)
                .text(LOREM_IPSUM)
                .type(LABEL_TYPE)
                .build();
        // @formatter:on
        TextBounds labelBounds = this.textBoundsService.getAutoWrapBounds(label, 400);
        assertThat(labelBounds.getSize().getWidth()).isEqualTo(400);
        assertThat(labelBounds.getSize().getHeight()).isCloseTo(570.0, Offset.offset(1.0));
    }

    @Test
    public void labelWithNewLineGreaterThanContainerWithSmallFont() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(BLACK_COLOR)
                .fontSize(8)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("")
                .build();

        Label label = Label.newLabel(LABEL_ID)
                .alignment(Position.at(0, 0))
                .position(Position.at(0, 0))
                .size(Size.of(0, 0))
                .style(labelStyle)
                .text(LOREM_IPSUM_WITH_NEW_LINE)
                .type(LABEL_TYPE)
                .build();
        // @formatter:on
        TextBounds labelBounds = this.textBoundsService.getAutoWrapBounds(label, 400);
        assertThat(labelBounds.getSize().getWidth()).isEqualTo(400);
        assertThat(labelBounds.getSize().getHeight()).isCloseTo(71.0, Offset.offset(1.0));
    }

    @Test
    public void labelWithNewLineGreaterThanContainerWithNormalFont() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(BLACK_COLOR)
                .fontSize(14)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("")
                .build();

        Label label = Label.newLabel(LABEL_ID)
                .alignment(Position.at(0, 0))
                .position(Position.at(0, 0))
                .size(Size.of(0, 0))
                .style(labelStyle)
                .text(LOREM_IPSUM_WITH_NEW_LINE)
                .type(LABEL_TYPE)
                .build();
        // @formatter:on
        TextBounds labelBounds = this.textBoundsService.getAutoWrapBounds(label, 400);
        assertThat(labelBounds.getSize().getWidth()).isEqualTo(400);
        assertThat(labelBounds.getSize().getHeight()).isCloseTo(175.0, Offset.offset(1.0));
    }

    @Test
    public void labelWithNewLineGreaterThanContainerWithBigFont() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color(BLACK_COLOR)
                .fontSize(26)
                .bold(false)
                .italic(false)
                .underline(false)
                .strikeThrough(false)
                .iconURL("")
                .build();

        Label label = Label.newLabel(LABEL_ID)
                .alignment(Position.at(0, 0))
                .position(Position.at(0, 0))
                .size(Size.of(0, 0))
                .style(labelStyle)
                .text(LOREM_IPSUM_WITH_NEW_LINE)
                .type(LABEL_TYPE)
                .build();
        // @formatter:on
        TextBounds labelBounds = this.textBoundsService.getAutoWrapBounds(label, 400);
        assertThat(labelBounds.getSize().getWidth()).isEqualTo(400);
        assertThat(labelBounds.getSize().getHeight()).isCloseTo(570.0, Offset.offset(1.0));
    }
}
