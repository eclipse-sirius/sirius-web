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

import java.util.Set;

import org.eclipse.sirius.components.diagrams.layout.api.Offsets;
import org.eclipse.sirius.components.diagrams.layout.api.Rectangle;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link OverlapHandler} utility.
 *
 * @author pcdavid
 */
public class OverlapHandlerTests {

    @Test
    public void testReturnsInitialBoundsOnEmptyCanvas() {
        Rectangle requested = new Rectangle(20, 20, 150, 70);
        Rectangle adapted = new OverlapHandler().findNearestFreeLocation(requested, Set.of());
        assertThat(adapted).isNotNull().isEqualTo(requested);
    }

    @Test
    public void testReturnsInitialBoundsIfNoActualOverlap() {
        Rectangle obstacle = new Rectangle(20, 20, 150, 70);
        Rectangle adapted = new OverlapHandler().findNearestFreeLocation(obstacle, Set.of(new Rectangle(200, 200, 100, 100)));
        assertThat(adapted).isNotNull().isEqualTo(obstacle);
    }

    @Test
    public void testMoveLeftOnSimpleOverlapFromRight() {
        Rectangle obstacle = new Rectangle(0, 0, 50, 50);
        Rectangle requested = new Rectangle(25, 0, 50, 50);
        Rectangle adapted = new OverlapHandler().findNearestFreeLocation(requested, Set.of(obstacle));
        assertThat(adapted).isNotNull().isEqualTo(new Rectangle(50, 0, 50, 50));
    }

    @Test
    public void testInsideObstacle() {
        // Default node size, with margin
        Rectangle obstacle = new Rectangle(0, 0, 150, 70).expand(Offsets.of(50));
        assertThat(obstacle).isEqualTo(new Rectangle(-50, -50, 250, 170));
        // Try the default bounds
        Rectangle requested = new Rectangle(0.0, 0.0, 150.0, 70.0);
        Rectangle adapted = new OverlapHandler().findNearestFreeLocation(requested, Set.of(obstacle));
        // The obstacle is more wide than tall, so the smallest move to void it is to move up (or down)
        assertThat(adapted).isNotNull().isEqualTo(new Rectangle(0, 120, 150, 70));
    }
}
