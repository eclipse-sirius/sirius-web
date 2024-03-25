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
import org.eclipse.sirius.components.diagrams.OutsideLabel;
import org.eclipse.sirius.components.diagrams.OutsideLabelLocation;

/**
 * Custom assertion class used to perform some tests on an outside label.
 *
 * @author gdaniel
 */
public class OutsideLabelAssert extends AbstractObjectAssert<OutsideLabelAssert, OutsideLabel> {

    public OutsideLabelAssert(OutsideLabel outsideLabel) {
        super(outsideLabel, OutsideLabelAssert.class);
    }

    public OutsideLabelAssert hasId(String id) {
        assertThat(this.actual.id()).isEqualTo(id);
        return this;
    }

    public OutsideLabelAssert hasText(String text) {
        assertThat(this.actual.text()).isEqualTo(text);
        return this;
    }

    public OutsideLabelAssert hasOutsideLabelLocation(OutsideLabelLocation outsideLabelLocation) {
        assertThat(this.actual.outsideLabelLocation()).isEqualTo(outsideLabelLocation);
        return this;
    }
}
