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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Offsets} utility class.
 *
 * @author pcdavid
 */
public class OffsetsTests {

    @Test
    public void testNegativeValuesRejected() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Offsets(-1, 0, 0, 0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Offsets(0, -1, 0, 0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Offsets(0, 0, -1, 0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Offsets(0, 0, 0, -1);
        });
    }

    @Test
    public void testEmptyOffset() {
        var empty = Offsets.empty();
        assertThat(empty).isEqualTo(Offsets.of(0.0));
        assertThat(empty).isEqualTo(new Offsets(0.0, 0.0, 0.0, 0.0));
        assertThat(empty.top()).isEqualTo(0.0);
        assertThat(empty.bottom()).isEqualTo(0.0);
        assertThat(empty.left()).isEqualTo(0.0);
        assertThat(empty.right()).isEqualTo(0.0);
    }

    @Test
    public void testUniformOffsets() {
        double value = 5.0;
        var offsets = Offsets.of(value);
        assertThat(offsets.top()).isEqualTo(value);
        assertThat(offsets.bottom()).isEqualTo(value);
        assertThat(offsets.left()).isEqualTo(value);
        assertThat(offsets.right()).isEqualTo(value);
        assertThat(offsets.width()).isEqualTo(2 * value);
        assertThat(offsets.height()).isEqualTo(2 * value);
    }

    @Test
    public void testNonUniformOffsets() {
        var offsets = new Offsets(1.0, 2.0, 3.0, 4.0);
        assertThat(offsets.top()).isEqualTo(1.0);
        assertThat(offsets.bottom()).isEqualTo(2.0);
        assertThat(offsets.left()).isEqualTo(3.0);
        assertThat(offsets.right()).isEqualTo(4.0);
        assertThat(offsets.width()).isEqualTo(7.0);
        assertThat(offsets.height()).isEqualTo(3.0);
    }

    @Test
    public void testCombineWithEmpty() {
        var offsets = Offsets.of(5.0);
        assertThat(offsets.combine(Offsets.empty())).isEqualTo(offsets);
        assertThat(Offsets.empty().combine(offsets)).isEqualTo(offsets);
    }

    @Test
    public void testCombine() {
        var o1 = Offsets.of(3.0);
        var o2 = Offsets.of(2.0);
        assertThat(o1.combine(o2)).isEqualTo(Offsets.of(5.0));
        assertThat(o2.combine(o1)).isEqualTo(Offsets.of(5.0));
    }
}
