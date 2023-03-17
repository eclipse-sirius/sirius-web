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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.junit.jupiter.api.Test;

/**
 * Tests for the internal layout of inside labels.
 *
 * @author pcdavid
 */
public class InsideLabelLayoutTests {

    private static final String SHORT_TEXT = "Short";
    private static final String LONG_TEXT = "A much, much longer label which will force a line wrap at most reasonable maximum width";
    private static final int DEFAULT_FONT_SIZE = 16;
    private static final double DEFAULT_LINE_HEIGHT = 18.3984375;
    private static final double SHORT_TEXT_WIDTH = 38.2421875;

    @Test
    public void testShortLabelWithIcon() {
        Label label = this.createLabel(SHORT_TEXT, DEFAULT_FONT_SIZE);
        // Keep the default rules.
        var rules = InsideLabelLayoutConfiguration.newInsideLabelLayoutConfiguration()
                .border(Offsets.empty())
                .padding(Offsets.of(5.0))
                .textStyle(label.getStyle())
                .build();
        InsideLabelBox box = new InsideLabelLayoutEngine().layout(label, rules, OptionalDouble.empty());
        this.assertBasicConsistency(box);
        Rectangle contentArea = box.getRelativeContentArea();
        assertThat(contentArea).isEqualTo(new Rectangle(5.0, 5.0, 59.2421875, DEFAULT_LINE_HEIGHT));
        // Icon present vertically centered as text is taller (18.3984375 > 18)
        assertThat(box.getRelativeIconArea()).isPresent();
        assertThat(box.getRelativeIconArea().get()).isEqualTo(new Rectangle(5, 5 + (DEFAULT_LINE_HEIGHT - 18.0) / 2.0, 16, 18));
        // Text on the right of the icon, after the gap, and starts at the vertical padding.
        assertThat(box.getRelativeTextArea()).isEqualTo(new Rectangle(new Position(26.0, 5.0), new Size(SHORT_TEXT_WIDTH, DEFAULT_LINE_HEIGHT)));
    }

    @Test
    public void testShortLabelWithIconAndMoreWidth() {
        Label label = this.createLabel(SHORT_TEXT, 16);
        // Keep the default rules.
        var rules = InsideLabelLayoutConfiguration.newInsideLabelLayoutConfiguration()
                .border(Offsets.empty())
                .padding(Offsets.of(5.0))
                .textStyle(label.getStyle())
                .build();
        InsideLabelBox box = new InsideLabelLayoutEngine().layout(label, rules, OptionalDouble.of(100.0));
        assertThat(box).isNotNull();
        Rectangle contentArea = box.getRelativeContentArea();
        assertThat(contentArea).isNotNull();
        assertThat(contentArea.topLeft()).isEqualTo(new Position(5.0, 5.0));
        assertThat(contentArea).isEqualTo(new Rectangle(5.0, 5.0, 90.0, 22.600000381469727));
        assertThat(box.getRelativeIconArea()).isPresent();
        assertThat(box.getRelativeIconArea().get().size()).isEqualTo(new Size(16, 18));
        assertThat(box.getRelativeContentArea().includes(box.getRelativeIconArea().get()));
        assertThat(box.getRelativeContentArea().includes(box.getRelativeTextArea()));
        assertThat(box.getRelativeIconArea().get().intersection(box.getRelativeTextArea())).isEqualTo(new Rectangle(0, 0, 0, 0));
    }

    /**
     * Just the text: no icon, border, padding, etc.
     */
    @Test
    public void testShortLabelOnlyText() {
        Label label = this.createLabel(SHORT_TEXT, 16);
        var rules = InsideLabelLayoutConfiguration.newInsideLabelLayoutConfiguration()
                .iconSize(Optional.empty())
                .border(Offsets.empty())
                .padding(Offsets.empty())
                .margin(Offsets.empty())
                .textStyle(label.getStyle())
                .build();
        InsideLabelBox box = new InsideLabelLayoutEngine().layout(label, rules, OptionalDouble.empty());
        this.assertBasicConsistency(box);
        // No icon
        assertThat(box.getRelativeIconArea()).isEmpty();
        Rectangle contentArea = box.getRelativeContentArea();
        assertThat(contentArea).isNotNull();
        assertThat(contentArea.topLeft()).isEqualTo(new Position(0.0, 0.0));
        // All the content is taken by the text itself
        assertThat(box.getRelativeTextArea()).isEqualTo(contentArea);
        assertThat(box.getLabelArea()).isEqualTo(new Rectangle(0.0, 0.0, SHORT_TEXT_WIDTH, DEFAULT_LINE_HEIGHT));
    }

    /**
     * With an icon taller than the text itself, the text is vertically centered.
     */
    @Test
    public void testShortLabelTallIcon() {
        Label label = this.createLabel(SHORT_TEXT, 16);
        Size tallIconSize = new Size(16, 30);
        var rules = InsideLabelLayoutConfiguration.newInsideLabelLayoutConfiguration()
                .iconSize(Optional.of(tallIconSize))
                .border(Offsets.empty())
                .padding(Offsets.empty())
                .margin(Offsets.empty())
                .textStyle(label.getStyle())
                .build();
        InsideLabelBox box = new InsideLabelLayoutEngine().layout(label, rules, OptionalDouble.empty());
        this.assertBasicConsistency(box);
        // Icon present and in the top-left corner
        assertThat(box.getRelativeIconArea()).isPresent();
        assertThat(box.getRelativeIconArea().get().topLeft()).isEqualTo(new Position(0, 0));
        assertThat(box.getRelativeIconArea().get().size()).isEqualTo(tallIconSize);
        // Text to the right after the gap and vertically centered
        assertThat(box.getRelativeTextArea()).isEqualTo(new Rectangle(21, (30 - DEFAULT_LINE_HEIGHT) / 2, SHORT_TEXT_WIDTH, DEFAULT_LINE_HEIGHT));
    }

    @Test
    public void testLongLabelNoIconNoOffsets() {
        Label label = this.createLabel(LONG_TEXT, 16);
        var rules = InsideLabelLayoutConfiguration.newInsideLabelLayoutConfiguration()
                .iconSize(Optional.empty())
                .border(Offsets.empty())
                .padding(Offsets.empty())
                .margin(Offsets.empty())
                .textStyle(label.getStyle())
                .build();
        double maxWidth = 100.0;
        InsideLabelBox box = new InsideLabelLayoutEngine().layout(label, rules, OptionalDouble.of(maxWidth));
        this.assertBasicConsistency(box);
        assertThat(box.getRelativeTextArea()).isEqualTo(new Rectangle(0.0, 0.0, maxWidth, 203.4000244140625));
    }

    @Test
    public void testLongLabelNoIconOffsets() {
        Label label = this.createLabel(LONG_TEXT, 16);
        var rules = InsideLabelLayoutConfiguration.newInsideLabelLayoutConfiguration()
                .iconSize(Optional.empty())
                .border(Offsets.of(1.0))
                .padding(Offsets.of(5.0))
                .margin(Offsets.empty())
                .textStyle(label.getStyle())
                .anchor(Anchor.MIDDLE_CENTER)
                .build();
        double maxWidth = 100.0;
        InsideLabelBox box = new InsideLabelLayoutEngine().layout(label, rules, OptionalDouble.of(maxWidth));
        this.assertBasicConsistency(box);
        assertThat(box.getRelativeIconArea()).isEmpty();
        // With the same maxWidth but additional padding & margin, there is less horizontal room for the text than in
        // testLongLabelNoIconNoOffsets, so the text area is taller
        assertThat(box.getRelativeTextArea()).isEqualTo(new Rectangle(6.0, 6.0, maxWidth - 12.0, 226.00003051757812));
        assertThat(box.getLabelArea().width()).isEqualTo(maxWidth);
        assertThat(box.getLabelArea().height()).isEqualTo(226.00003051757812 + 12.0);
    }

    private void assertBasicConsistency(InsideLabelBox box) {
        assertThat(box).isNotNull();
        Rectangle contentArea = box.getRelativeContentArea();
        assertThat(contentArea).isNotNull();
        assertThat(contentArea.isEmpty()).isFalse();
        if (box.getRelativeIconArea().isPresent()) {
            assertThat(contentArea.includes(box.getRelativeIconArea().get()));
            assertThat(contentArea.includes(box.getRelativeTextArea()));
            assertThat(box.getRelativeTextArea().overlaps(box.getRelativeIconArea().get())).isFalse();
        } else {
            assertThat(contentArea).isEqualTo(box.getRelativeTextArea());
        }
    }

    private Label createLabel(String text, int fontSize) {
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .fontSize(fontSize)
                .color("#000000")
                .iconURL("")
                .build();
        Label label = Label.newLabel(UUID.randomUUID().toString())
                .type("labelType")
                .position(org.eclipse.sirius.components.diagrams.Position.UNDEFINED)
                .size(org.eclipse.sirius.components.diagrams.Size.UNDEFINED)
                .alignment(org.eclipse.sirius.components.diagrams.Position.UNDEFINED)
                .text(text)
                .style(labelStyle)
                .build();
        return label;
    }
}
