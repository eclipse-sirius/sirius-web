/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractObjectAssert;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;

/**
 * Custom assertion class used to perform some tests on a label.
 *
 * @author gdaniel
 */
public class LabelAssert extends AbstractObjectAssert<LabelAssert, Label> {

    public LabelAssert(Label label) {
        super(label, LabelAssert.class);
    }

    public LabelAssert hasId(String id) {
        assertThat(this.actual.getId()).isEqualTo(id);
        return this;
    }

    public LabelAssert hasType(String type) {
        assertThat(this.actual.getType()).isEqualTo(type);
        return this;
    }

    public LabelAssert hasText(String text) {
        assertThat(this.actual.getText()).isEqualTo(text);
        return this;
    }

    public LabelAssert hasPosition(Position position) {
        assertThat(this.actual.getPosition()).isEqualTo(position);
        return this;
    }

    public LabelAssert hasSize(Size size) {
        assertThat(this.actual.getSize()).isEqualTo(size);
        return this;
    }

    public LabelAssert hasAlignment(Position alignment) {
        assertThat(this.actual.getAlignment()).isEqualTo(alignment);
        return this;
    }
}
