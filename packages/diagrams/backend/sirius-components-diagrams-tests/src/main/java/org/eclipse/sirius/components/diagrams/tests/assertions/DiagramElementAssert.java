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
import org.eclipse.sirius.components.diagrams.IDiagramElement;

/**
 * Custom assertion class used to perform some tests on a diagram element.
 *
 * @param <SELF> the type of the assertion
 * @param <ACTUAL> the type of the element
 *
 * @author gdaniel
 */
public class DiagramElementAssert<SELF extends DiagramElementAssert<SELF, ACTUAL>, ACTUAL extends IDiagramElement> extends AbstractObjectAssert<SELF, ACTUAL> {

    protected DiagramElementAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    public SELF hasId(String id) {
        assertThat(this.actual.getId()).isEqualTo(id);
        return this.myself;
    }

    public SELF hasDescriptionId(String descriptionId) {
        assertThat(this.actual.getDescriptionId()).isEqualTo(descriptionId);
        return this.myself;
    }
}
