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
package org.eclipse.sirius.components.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.diagrams.LabelStyle;

/**
 * Custom assertion class used to perform some tests on a label.
 *
 * @author sbegaudeau
 */
public class LabelStyleAssert extends AbstractAssert<LabelStyleAssert, LabelStyle> {

    public LabelStyleAssert(LabelStyle labelStyle) {
        super(labelStyle, LabelStyleAssert.class);
    }

    public LabelStyleAssert matches(LabelStyle labelStyle) {
        this.isNotNull();

        assertThat(this.actual.getColor()).isEqualTo(labelStyle.getColor());
        assertThat(this.actual.getFontSize()).isEqualTo(labelStyle.getFontSize());
        assertThat(this.actual.isBold()).isEqualTo(labelStyle.isBold());
        assertThat(this.actual.isItalic()).isEqualTo(labelStyle.isItalic());
        assertThat(this.actual.isUnderline()).isEqualTo(labelStyle.isUnderline());
        assertThat(this.actual.isStrikeThrough()).isEqualTo(labelStyle.isStrikeThrough());

        return this;
    }
}
