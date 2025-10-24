/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import org.eclipse.sirius.components.diagrams.Label;

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
            assertThat(this.actual.id()).isEqualTo(label.id());
        }

        // Actual label type can not be equal to label type because of the dummy label type set in NodeComponent.
        assertThat(this.actual.type()).isNotEqualTo(label.type());
        assertThat(this.actual.text()).isEqualTo(label.text());
        assertThat(this.actual.style()).matches(label.style());

        return this;
    }
}
