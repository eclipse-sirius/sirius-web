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
package org.eclipse.sirius.web.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.web.diagrams.tests.DiagramAssertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;

/**
 * Custom assertion class used to perform some tests on a label.
 *
 * @author sbegaudeau
 */
public class LabelAssert extends AbstractAssert<LabelAssert, Label> {

    public LabelAssert(Label actual) {
        super(actual, LabelAssert.class);
    }

    public LabelAssert matches(Label label, IdPolicy idPolicy, LayoutPolicy layoutPolicy) {
        this.isNotNull();

        if (idPolicy == IdPolicy.WITH_ID) {
            assertThat(this.actual.getId()).isEqualTo(label.getId());
        }

        assertThat(this.actual.getType()).isEqualTo(label.getType());
        assertThat(this.actual.getText()).isEqualTo(label.getText());
        assertThat(this.actual.getStyle()).matches(label.getStyle());

        if (layoutPolicy == LayoutPolicy.WITH_LAYOUT) {
            this.hasBounds(label.getPosition().getX(), label.getPosition().getY(), label.getSize().getWidth(), label.getSize().getHeight(), label.getAlignment().getX(), label.getAlignment().getY());
        }

        return this;
    }

    public LabelAssert hasBounds(double x, double y, double width, double height, double xAlignment, double yAlignment) {
        this.isNotNull();

        Size size = this.actual.getSize();
        if (size == null) {
            this.failWithMessage("Expected label's size to be <'{'width: %.2f, height: %.2f'}'> but was null", width, height); //$NON-NLS-1$
        } else {
            if (width != size.getWidth()) {
                this.failWithMessage("Expected label's width to be <%.2f> but was <%.2f>", width, size.getWidth()); //$NON-NLS-1$
            }
            if (height != size.getHeight()) {
                this.failWithMessage("Expected label's height to be <%.2f> but was <%.2f>", height, size.getHeight()); //$NON-NLS-1$
            }
        }

        Position position = this.actual.getPosition();
        if (position == null) {
            this.failWithMessage("Expected label's position to be <'{'x: %.2f, y: %.2f'}'> but was null", x, y); //$NON-NLS-1$
        } else {
            if (x != position.getX()) {
                this.failWithMessage("Expected label's x to be <%.2f> but was <%.2f>", x, position.getX()); //$NON-NLS-1$
            }
            if (y != position.getY()) {
                this.failWithMessage("Expected label's y to be <%.2f> but was <%.2f>", y, position.getY()); //$NON-NLS-1$
            }
        }

        Position alignment = this.actual.getAlignment();
        if (alignment == null) {
            this.failWithMessage("Expected label's alignment to be <'{'x: %.2f, y: %.2f'}'> but was null", xAlignment, yAlignment); //$NON-NLS-1$
        } else {
            if (x != alignment.getX()) {
                this.failWithMessage("Expected label's x alignment to be <%.2f> but was <%.2f>", x, alignment.getX()); //$NON-NLS-1$
            }
            if (y != alignment.getY()) {
                this.failWithMessage("Expected label's y alignment to be <%.2f> but was <%.2f>", y, alignment.getY()); //$NON-NLS-1$
            }
        }

        return this;
    }
}
