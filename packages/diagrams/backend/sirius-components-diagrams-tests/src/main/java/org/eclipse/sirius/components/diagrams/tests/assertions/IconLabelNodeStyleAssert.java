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

import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;

/**
 * Custom assertion class used to perform some tests on a icon label node style.

 * @author gdaniel
 */
public class IconLabelNodeStyleAssert extends NodeStyleAssert<IconLabelNodeStyleAssert, IconLabelNodeStyle> {

    public IconLabelNodeStyleAssert(IconLabelNodeStyle iconLabelNodeStyle) {
        super(iconLabelNodeStyle, IconLabelNodeStyleAssert.class);
    }

    public IconLabelNodeStyleAssert hasBackgroundColor(String backgroundColor) {
        assertThat(this.actual.getBackground()).isEqualTo(backgroundColor);
        return this;
    }
}
