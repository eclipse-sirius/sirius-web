/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

        assertThat(this.actual.getText()).isEqualTo(insideLabel.getText());
        assertThat(this.actual.getStyle()).matches(insideLabel.getStyle());

        return this;
    }

}
