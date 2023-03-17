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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Represents a rectangular area.
 *
 * @author pcdavid
 */
public record Rectangle(double x, double y, double width, double height) {

    public Rectangle(double x, double y, double width, double height) {
        if (width < 0) {
            throw new IllegalArgumentException("width can not be negative");
        }
        if (height < 0) {
            throw new IllegalArgumentException("height can not be negative");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Position position, Size size) {
        this(position.x(), position.y(), size.width(), size.height());
    }

    public Rectangle translate(double dx, double dy) {
        return new Rectangle(this.x + dx, this.y + dy, this.width, this.height);
    }

    public Rectangle moveTo(Position newPosition) {
        return new Rectangle(newPosition.x(), newPosition.y(), this.width, this.height);
    }

    public Rectangle moveTo(double newX, double newY) {
        return new Rectangle(newX, newY, this.width, this.height);
    }

    public Rectangle resize(double newWidth, double newHeight) {
        return new Rectangle(this.x, this.y, newWidth, newHeight);
    }

    public Rectangle expand(Offsets offsets) {
        return new Rectangle(this.x - offsets.left(), this.y - offsets.top(), this.width + offsets.width(), this.height + offsets.height());
    }

    public Rectangle shrink(Offsets offsets) {
        return new Rectangle(this.x + offsets.left(), this.y + offsets.top(), this.width - offsets.width(), this.height - offsets.height());
    }

    public Rectangle union(Rectangle... others) {
        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(this);
        rectangles.addAll(Arrays.asList(others));

        double minX = rectangles.stream().map(Rectangle::x).min(Comparator.naturalOrder()).orElse(0.0);
        double minY = rectangles.stream().map(Rectangle::y).min(Comparator.naturalOrder()).orElse(0.0);
        double maxX = rectangles.stream().map(r -> r.x + r.width).max(Comparator.naturalOrder()).orElse(0.0);
        double maxY = rectangles.stream().map(r -> r.y + r.height).max(Comparator.naturalOrder()).orElse(0.0);

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public Rectangle intersection(Rectangle other) {
        double topleftX = Math.max(this.x, other.x);
        double topleftY = Math.max(this.y, other.y);
        double bottomrightX = Math.min(this.bottomRight().x(), other.bottomRight().x());
        double bottomrightY = Math.min(this.bottomRight().y(), other.bottomRight().y());
        if (topleftX > bottomrightX || topleftY > bottomrightY) {
            return new Rectangle(0, 0, 0, 0);
        } else {
            return new Rectangle(topleftX, topleftY, bottomrightX - topleftX, bottomrightY - topleftY);
        }
    }

    public boolean isEmpty() {
        return this.width == 0.0 || this.height == 0.0;
    }

    public boolean overlaps(Rectangle other) {
        return !this.intersection(other).isEmpty();
    }

    public boolean includes(Rectangle other) {
        return this.intersection(other).equals(other);
    }

    public Size size() {
        return new Size(this.width, this.height);
    }

    public Position topLeft() {
        return new Position(this.x, this.y);
    }

    public Position topCenter() {
        return this.topLeft().midPoint(this.topRight());
    }

    public Position topRight() {
        return new Position(this.x + this.width, this.y);
    }

    public Position middleLeft() {
        return this.topLeft().midPoint(this.bottomLeft());
    }

    public Position middleCenter() {
        return this.topLeft().midPoint(this.bottomRight());
    }

    public Position middleRight() {
        return this.topRight().midPoint(this.bottomRight());
    }

    public Position bottomLeft() {
        return new Position(this.x, this.y + this.height);
    }

    public Position bottomCenter() {
        return this.bottomLeft().midPoint(this.bottomRight());
    }

    public Position bottomRight() {
        return new Position(this.x + this.width, this.y + this.height);
    }

}
