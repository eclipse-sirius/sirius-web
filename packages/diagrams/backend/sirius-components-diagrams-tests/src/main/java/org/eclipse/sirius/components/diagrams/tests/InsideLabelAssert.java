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
package org.eclipse.sirius.components.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramAssertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;

/**
 * Custom assertion class used to perform some tests on an inside label.
 *
 * @author gcoutable
 */
public class InsideLabelAssert extends AbstractAssert<InsideLabelAssert, InsideLabel> {

    public InsideLabelAssert(InsideLabel actual) {
        super(actual, InsideLabelAssert.class);
    }

    public InsideLabelAssert matches(InsideLabel insideLabel, IdPolicy idPolicy, LayoutPolicy layoutPolicy) {
        this.isNotNull();

        if (idPolicy == IdPolicy.WITH_ID) {
            assertThat(this.actual.getId()).isEqualTo(insideLabel.getId());
        }

        // Actual label type can not be equal to label type because of the dummy label type set in NodeComponent.
        assertThat(this.actual.getType()).isNotEqualTo(insideLabel.getType());

        assertThat(this.actual.getText()).isEqualTo(insideLabel.getText());
        assertThat(this.actual.getStyle()).matches(insideLabel.getStyle());

        if (layoutPolicy == LayoutPolicy.WITH_LAYOUT) {
            this.hasBounds(insideLabel.getPosition().getX(), insideLabel.getPosition().getY(), insideLabel.getSize().getWidth(), insideLabel.getSize().getHeight(), insideLabel.getAlignment().getX(), insideLabel.getAlignment().getY());
        }

        return this;
    }

    public InsideLabelAssert hasBounds(double x, double y, double width, double height, double xAlignment, double yAlignment) {
        this.isNotNull();

        Size size = this.actual.getSize();
        if (size == null) {
            this.failWithMessage("Expected label's size to be <'{'width: %.2f, height: %.2f'}'> but was null", width, height);
        } else {
            if (width != size.getWidth()) {
                this.failWithMessage("Expected label's width to be <%.2f> but was <%.2f>", width, size.getWidth());
            }
            if (height != size.getHeight()) {
                this.failWithMessage("Expected label's height to be <%.2f> but was <%.2f>", height, size.getHeight());
            }
        }

        Position position = this.actual.getPosition();
        if (position == null) {
            this.failWithMessage("Expected label's position to be <'{'x: %.2f, y: %.2f'}'> but was null", x, y);
        } else {
            if (x != position.getX()) {
                this.failWithMessage("Expected label's x to be <%.2f> but was <%.2f>", x, position.getX());
            }
            if (y != position.getY()) {
                this.failWithMessage("Expected label's y to be <%.2f> but was <%.2f>", y, position.getY());
            }
        }

        Position alignment = this.actual.getAlignment();
        if (alignment == null) {
            this.failWithMessage("Expected label's alignment to be <'{'x: %.2f, y: %.2f'}'> but was null", xAlignment, yAlignment);
        } else {
            if (x != alignment.getX()) {
                this.failWithMessage("Expected label's x alignment to be <%.2f> but was <%.2f>", x, alignment.getX());
            }
            if (y != alignment.getY()) {
                this.failWithMessage("Expected label's y alignment to be <%.2f> but was <%.2f>", y, alignment.getY());
            }
        }

        return this;
    }
}
