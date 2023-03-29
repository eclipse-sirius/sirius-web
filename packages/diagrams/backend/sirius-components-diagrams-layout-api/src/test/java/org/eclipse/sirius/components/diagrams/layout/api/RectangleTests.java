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
package org.eclipse.sirius.components.diagrams.layout.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Rectangle} utility class.
 *
 * @author pcdavid
 */
public class RectangleTests {

    @Test
    public void testNegativeWidthRejected() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Rectangle(0, 0, -1, 10);
        });
    }

    @Test
    public void testZeroWidthAccepted() {
        var rect = new Rectangle(0, 0, 0, 10);
        assertThat(rect.width()).isEqualTo(0.0);
    }

    @Test
    public void testNegativeHeightRejected() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Rectangle(0, 0, 10, -1);
        });
    }

    @Test
    public void testZeroHeightAccepted() {
        var rect = new Rectangle(0, 0, 10, 0);
        assertThat(rect.height()).isEqualTo(0.0);
    }

    @Test
    public void testTranslateKeepsSize() {
        var rect = new Rectangle(0, 0, 10, 10);
        assertThat(rect.translate(10, 10)).isEqualTo(new Rectangle(10, 10, 10, 10));
    }

    @Test
    public void testMoveToKeepsSize() {
        var rect = new Rectangle(0, 0, 10, 10);
        assertThat(rect.moveTo(10, 10)).isEqualTo(new Rectangle(10, 10, 10, 10));
    }

    @Test
    public void testResizeKeepsPosition() {
        var rect = new Rectangle(0, 0, 10, 10);
        assertThat(rect.resize(20, 20)).isEqualTo(new Rectangle(0, 0, 20, 20));
    }

    @Test
    public void testIsEmpty() {
        assertThat(new Rectangle(0, 0, 0, 0)).matches(Rectangle::isEmpty);
        // Even with a non-zero position, a rectangle can be empty
        assertThat(new Rectangle(5, 5, 0, 0)).matches(Rectangle::isEmpty);
        // A rectangle can be empty even with a non-zero width *or* height, what matters is that its area is 0.
        assertThat(new Rectangle(5, 5, 10, 0)).matches(Rectangle::isEmpty);
        assertThat(new Rectangle(5, 5, 0, 10)).matches(Rectangle::isEmpty);
    }

    @Test
    public void testUnionWithSelf() {
        var r1 = new Rectangle(0, 0, 30, 30);
        assertThat(r1.unionWith(r1)).isEqualTo(r1);
    }

    @Test
    public void testUnionWithIncludedRectangle() {
        var r1 = new Rectangle(0, 0, 30, 30);
        var r2 = new Rectangle(10, 10, 10, 10);
        assertThat(r1.unionWith(r2)).isEqualTo(r1);
    }

    @Test
    public void testUnionWithEnclosingRectangle() {
        var r1 = new Rectangle(0, 0, 30, 30);
        var r2 = new Rectangle(10, 10, 10, 10);
        assertThat(r2.unionWith(r1)).isEqualTo(r1);
    }

    @Test
    public void testUnionNonOverlapping() {
        var r1 = new Rectangle(0, 0, 10, 10);
        var r2 = new Rectangle(20, -50, 10, 10);
        var expected = new Rectangle(0, -50, 30, 60);
        assertThat(r1.unionWith(r2)).isEqualTo(expected);
    }

    @Test
    public void testUnionOfMultipleRectangles() {
        var rectangles = List.of(new Rectangle(0, 0, 10, 10), new Rectangle(10, 0, 10, 10), new Rectangle(20, 0, 10, 10), new Rectangle(30, 0, 10, 10), new Rectangle(40, 30, 10, 10));

        assertThat(rectangles.get(0).unionWith(rectangles.toArray(new Rectangle[0]))).isEqualTo(new Rectangle(0, 0, 50, 40));
    }

    @Test
    public void testExpandByEmptyOffsetsIsNoOp() {
        var rectangle = new Rectangle(10, 10, 10, 10);
        assertThat(rectangle.expand(Offsets.empty())).isEqualTo(rectangle);
    }

    @Test
    public void testShrinkByEmptyOffsetsIsNoOp() {
        var rectangle = new Rectangle(10, 10, 10, 10);
        assertThat(rectangle.shrink(Offsets.empty())).isEqualTo(rectangle);
    }

    @Test
    public void testExpandThenShrinkIsNoOp() {
        var rectangle = new Rectangle(10, 10, 10, 10);
        Offsets offsets = Offsets.of(2.0);
        assertThat(rectangle.expand(offsets).shrink(offsets)).isEqualTo(rectangle);
    }

    @Test
    public void testShrinkThenEpxandIsNoOp() {
        var rectangle = new Rectangle(10, 10, 10, 10);
        Offsets offsets = Offsets.of(2.0);
        assertThat(rectangle.shrink(offsets).expand(offsets)).isEqualTo(rectangle);
    }

    @Test
    public void testIntersectionWithEmptyIsEmpty() {
        Rectangle empty = new Rectangle(0, 0, 0, 0);
        assertThat(new Rectangle(10, 10, 20, 20).intersection(empty)).isEqualTo(empty);
        assertThat(empty.intersection(new Rectangle(10, 10, 20, 20))).isEqualTo(empty);
    }

    @Test
    public void testIntersectionWithSelfIsSelf() {
        var rectangle = new Rectangle(10, 10, 10, 10);
        assertThat(rectangle.intersection(rectangle)).isEqualTo(rectangle);
    }

    @Test
    public void testPartialIntersection() {
        var r1 = new Rectangle(-5, -5, 10, 10);
        var r2 = new Rectangle(0, 0, 30, 30);
        var r3 = new Rectangle(0, 0, 5, 5);
        assertThat(r1.intersection(r2)).isEqualTo(r3);
        assertThat(r2.intersection(r1)).isEqualTo(r3);
    }

    @Test
    public void testEmptyIntersection() {
        var r1 = new Rectangle(-5, -5, 10, 10);
        var r2 = new Rectangle(20, 20, 30, 30);
        Rectangle empty = new Rectangle(0, 0, 0, 0);
        assertThat(r1.intersection(r2)).isEqualTo(empty);
        assertThat(r2.intersection(r1)).isEqualTo(empty);
    }

    @Test
    public void testOverlapWithSelf() {
        var rect = new Rectangle(20, 20, 30, 30);
        assertThat(rect.overlaps(rect)).isTrue();
    }

    /**
     * Even though the empty rectangle has a position inside another, they are not considered to overlap.
     */
    @Test
    public void testOverlapWithEmpty() {
        var rect = new Rectangle(20, 20, 30, 30);
        assertThat(rect.overlaps(new Rectangle(25, 25, 0, 0))).isFalse();
        assertThat(new Rectangle(25, 25, 0, 0).overlaps(rect)).isFalse();
    }

    @Test
    public void testPartialOverlap() {
        var rect = new Rectangle(20, 20, 30, 30);
        assertThat(rect.overlaps(new Rectangle(30, 30, 30, 30))).isTrue();
    }

    /**
     * Two adjacent rectangles which share a border are not considered to overlap.
     */
    @Test
    public void testTouchingRectangleDoNotOverlap() {
        var rect = new Rectangle(20, 20, 30, 30);
        assertThat(rect.overlaps(rect.moveTo(rect.topRight()))).isFalse();
        assertThat(rect.overlaps(rect.moveTo(rect.bottomLeft()))).isFalse();
        assertThat(rect.overlaps(rect.moveTo(rect.bottomRight()))).isFalse();
    }

    @Test
    public void testIncludeSelf() {
        var rect = new Rectangle(20, 20, 30, 30);
        assertThat(rect.includes(rect)).isTrue();
    }

    @Test
    public void testIncludeShrinked() {
        var rect = new Rectangle(20, 20, 30, 30);
        assertThat(rect.includes(rect.shrink(Offsets.of(1)))).isTrue();
        assertThat(rect.includes(rect.resize(20, 20))).isTrue();
    }

    @Test
    public void testDoesNotIncludeExpanded() {
        var rect = new Rectangle(20, 20, 30, 30);
        assertThat(rect.includes(rect.expand(Offsets.of(1)))).isFalse();
        assertThat(rect.includes(rect.resize(40, 40))).isFalse();
    }

}
