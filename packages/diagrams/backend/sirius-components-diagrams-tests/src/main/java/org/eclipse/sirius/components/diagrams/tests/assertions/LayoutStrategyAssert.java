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

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;

/**
 * Custom assertion class used to perform some tests on a layout strategy.
 *
 * @param <SELF> the type of the assertion
 * @param <ACTUAL> the type of the element
 *
 * @author gdaniel
 */
public class LayoutStrategyAssert<SELF extends LayoutStrategyAssert<SELF, ACTUAL>, ACTUAL extends ILayoutStrategy> extends AbstractAssert<SELF, ACTUAL> {

    public LayoutStrategyAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    public SELF hasKind(String kind) {
        assertThat(this.actual.getKind()).isEqualTo(kind);
        return this.myself;
    }
}
