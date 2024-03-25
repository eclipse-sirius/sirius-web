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
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;

/**
 * Custom assertion class used to perform some tests on an inside label.
 *
 * @author gdaniel
 */
public class InsideLabelAssert extends AbstractObjectAssert<InsideLabelAssert, InsideLabel> {

    public InsideLabelAssert(InsideLabel label) {
        super(label, InsideLabelAssert.class);
    }

    public InsideLabelAssert hasId(String id) {
        assertThat(this.actual.getId()).isEqualTo(id);
        return this;
    }

    public InsideLabelAssert hasText(String text) {
        assertThat(this.actual.getText()).isEqualTo(text);
        return this;
    }

    public InsideLabelAssert hasInsideLabelLocation(InsideLabelLocation location) {
        assertThat(this.actual.getInsideLabelLocation()).isEqualTo(location);
        return this;
    }

    public InsideLabelAssert isHeader() {
        assertThat(this.actual.isIsHeader()).isTrue();
        return this;
    }

    public InsideLabelAssert isNotHeader() {
        assertThat(this.actual.isIsHeader()).isFalse();
        return this;
    }

    public InsideLabelAssert isDisplayHeaderSeparator() {
        assertThat(this.actual.isDisplayHeaderSeparator()).isTrue();
        return this;
    }

    public InsideLabelAssert isNotDisplayHeaderSeparator() {
        assertThat(this.actual.isDisplayHeaderSeparator()).isFalse();
        return this;
    }
}
