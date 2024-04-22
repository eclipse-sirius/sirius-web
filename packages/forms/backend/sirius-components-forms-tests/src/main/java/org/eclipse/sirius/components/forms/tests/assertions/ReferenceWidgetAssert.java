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
package org.eclipse.sirius.components.forms.tests.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;

/**
 * Custom assertion class used to perform some tests on a reference widget.
 *
 * @author sbegaudeau
 */
public class ReferenceWidgetAssert extends AbstractAssert<ReferenceWidgetAssert, ReferenceWidget> {

    public ReferenceWidgetAssert(ReferenceWidget referenceWidget) {
        super(referenceWidget, ReferenceWidgetAssert.class);
    }

    public ReferenceWidgetAssert hasLabel(String label) {
        assertThat(this.actual.getLabel()).isEqualTo(label);

        return this;
    }

    public ReferenceWidgetAssert hasValueWithLabel(String valueLabel) {
        assertThat(this.actual.getReferenceValues())
                .isNotEmpty()
                .anySatisfy(referenceValue -> assertThat(referenceValue.getLabel()).isEqualTo(valueLabel));
        return this;
    }
}
