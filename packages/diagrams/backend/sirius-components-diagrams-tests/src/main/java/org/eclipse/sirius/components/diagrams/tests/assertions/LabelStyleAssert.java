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

import java.util.List;

import org.assertj.core.api.AbstractObjectAssert;
import org.eclipse.sirius.components.diagrams.LabelStyle;

/**
 * Custom assertion class used to perform some tests on a label style.
 *
 * @author gdaniel
 */
public class LabelStyleAssert extends AbstractObjectAssert<LabelStyleAssert, LabelStyle> {

    public LabelStyleAssert(LabelStyle labelStyle) {
        super(labelStyle, LabelStyleAssert.class);
    }

    public LabelStyleAssert hasColor(String color) {
        assertThat(this.actual.getColor().equals(color));
        return this;
    }

    public LabelStyleAssert hasFontSize(int fontSize) {
        assertThat(this.actual.getFontSize()).isEqualTo(fontSize);
        return this;
    }

    public LabelStyleAssert isBold() {
        assertThat(this.actual.isBold()).isTrue();
        return this;
    }

    public LabelStyleAssert isNotBold() {
        assertThat(this.actual.isBold()).isFalse();
        return this;
    }

    public LabelStyleAssert isItalic() {
        assertThat(this.actual.isItalic()).isTrue();
        return this;
    }

    public LabelStyleAssert isNotItalic() {
        assertThat(this.actual.isItalic()).isFalse();
        return this;
    }

    public LabelStyleAssert isUnderline() {
        assertThat(this.actual.isUnderline()).isTrue();
        return this;
    }

    public LabelStyleAssert isNotUnderline() {
        assertThat(this.actual.isUnderline()).isFalse();
        return this;
    }

    public LabelStyleAssert isStrikeThrough() {
        assertThat(this.actual.isStrikeThrough()).isTrue();
        return this;
    }

    public LabelStyleAssert isNotStrikeThrough() {
        assertThat(this.actual.isStrikeThrough()).isFalse();
        return this;
    }

    public LabelStyleAssert hasIconURL(List<String> iconURLs) {
        assertThat(this.actual.getIconURL()).isEqualTo(iconURLs);
        return this;
    }
}
