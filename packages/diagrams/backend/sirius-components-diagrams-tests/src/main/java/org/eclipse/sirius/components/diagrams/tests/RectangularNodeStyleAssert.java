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
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;

/**
 * Custom assertion class used to perform some tests on a rectangular node style.
 *
 * @author sbegaudeau
 */
public class RectangularNodeStyleAssert extends AbstractAssert<RectangularNodeStyleAssert, RectangularNodeStyle> {
    public RectangularNodeStyleAssert(RectangularNodeStyle rectangularNodeStyle) {
        super(rectangularNodeStyle, RectangularNodeStyleAssert.class);
    }

    public RectangularNodeStyleAssert matches(RectangularNodeStyle rectangularNodeStyle) {
        this.isNotNull();

        assertThat(this.actual.getColor()).isEqualTo(rectangularNodeStyle.getColor());
        assertThat(this.actual.getBorderColor()).isEqualTo(rectangularNodeStyle.getBorderColor());
        assertThat(this.actual.getBorderSize()).isEqualTo(rectangularNodeStyle.getBorderSize());
        assertThat(this.actual.getBorderStyle()).isEqualTo(rectangularNodeStyle.getBorderStyle());

        return this;
    }
}
