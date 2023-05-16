/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api.experimental;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the box anchoring logic.
 *
 * @author pcdavid
 */
public class AnchorTests {

    private Rectangle container;
    private Size boxSize;

    @BeforeEach
    public void setup() {
        this.container = new Rectangle(0, 0, 100, 100);
        this.boxSize = new Size(10, 10);
    }

    @Test
    public void testTopLeft() {
        assertThat(Anchor.TOP_LEFT.apply(this.container, this.boxSize)).isEqualTo(new Position(0, 0));
    }

    @Test
    public void testTopCenter() {
        assertThat(Anchor.TOP_CENTER.apply(this.container, this.boxSize)).isEqualTo(new Position(45.0, 0));
    }

    @Test
    public void testTopRight() {
        assertThat(Anchor.TOP_RIGHT.apply(this.container, this.boxSize)).isEqualTo(new Position(90.0, 0));
    }

    @Test
    public void testMiddleLeft() {
        assertThat(Anchor.MIDDLE_LEFT.apply(this.container, this.boxSize)).isEqualTo(new Position(0.0, 45.0));
    }

    @Test
    public void testMiddleCenter() {
        assertThat(Anchor.MIDDLE_CENTER.apply(this.container, this.boxSize)).isEqualTo(new Position(45.0, 45.0));
    }

    @Test
    public void testMiddleRight() {
        assertThat(Anchor.MIDDLE_RIGHT.apply(this.container, this.boxSize)).isEqualTo(new Position(90.0, 45.0));
    }

    @Test
    public void testBottomLeft() {
        assertThat(Anchor.BOTTOM_LEFT.apply(this.container, this.boxSize)).isEqualTo(new Position(0.0, 90.0));
    }

    @Test
    public void testBottomCenter() {
        assertThat(Anchor.BOTTOM_CENTER.apply(this.container, this.boxSize)).isEqualTo(new Position(45.0, 90.0));
    }

    @Test
    public void testBottomRight() {
        assertThat(Anchor.BOTTOM_RIGHT.apply(this.container, this.boxSize)).isEqualTo(new Position(90.0, 90.0));
    }
}
