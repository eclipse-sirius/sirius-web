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

import java.util.OptionalDouble;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link TextLayoutEngine}.
 *
 * @author pcdavid
 */
public class TextLayoutEngineTests {

    /**
     * The expected line height when using 16px font size.
     */
    private static final double DEFAULT_LINE_HEIGHT = 18.3984375;

    private TextLayoutEngine engine;

    @BeforeEach
    public void setup() {
        this.engine = new TextLayoutEngine();
    }

    /**
     * Even with an empty text, a label is considered as 1 (empty) line.
     */
    @Test
    public void testEmptyTextNoWrapCountsAsOneEmptyLine() {
        Label label = this.createLabel("");
        TextBox box = this.engine.layout(label, OptionalDouble.empty());
        assertThat(box).isNotNull();
        assertThat(box.lines()).isEqualTo(1);
        assertThat(box.size()).isEqualTo(new Size(0.0, DEFAULT_LINE_HEIGHT));
    }

    @Test
    public void testShortStringNoWrap() {
        Label label = this.createLabel("Short label");
        TextBox box = this.engine.layout(label, OptionalDouble.empty());
        assertThat(box).isNotNull();
        assertThat(box.lines()).isEqualTo(1);
        assertThat(box.size().width()).isEqualTo(76.4921875);
        assertThat(box.size().height()).isEqualTo(DEFAULT_LINE_HEIGHT);
    }

    @Test
    public void testLongStringWrap() {
        Label label = this.createLabel("Some rather long label, long enough to be wrapped into multiple lines");
        TextBox box = this.engine.layout(label, OptionalDouble.of(100.0));
        assertThat(box).isNotNull();
        assertThat(box.lines()).isEqualTo(6);
    }

    /**
     * In "auto-wrap" mode, we should still get a single line if we provide enough width.
     */
    @Test
    @Disabled("not working: resulting height is too high")
    public void testNoWeapIfEnoughWidth() {
        Label label = this.createLabel("Short label");
        // Get the natural text width when no maximum width is given
        TextBox noWrapBox = this.engine.layout(label, OptionalDouble.empty());
        double naturalWidth = noWrapBox.size().width();
        // If we give at least this width, auto-wrap should keep everything in a single line
        TextBox wrapBoxExactWidth = this.engine.layout(label, OptionalDouble.of(naturalWidth));
        assertThat(wrapBoxExactWidth).isEqualTo(noWrapBox);
        // If we give more than this width, auto-wrap should keep everything in a single line
        TextBox wrapBoxMoreWidth = this.engine.layout(label, OptionalDouble.of(naturalWidth + 1));
        assertThat(wrapBoxMoreWidth).isEqualTo(noWrapBox);
    }

    private Label createLabel(String text, int fontSize) {
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .fontSize(fontSize)
                .color("#000000")
                .iconURL("")
                .build();
        Label label = Label.newLabel(UUID.randomUUID().toString())
                .type("labelType")
                .position(Position.UNDEFINED)
                .size(org.eclipse.sirius.components.diagrams.Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .text(text)
                .style(labelStyle)
                .build();
        return label;
    }

    private Label createLabel(String text) {
        return this.createLabel(text, 16);
    }
}
