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

import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;

/**
 * Custom assertion class used to perform some tests on a list layout strategy.
 *
 * @author gdaniel
 */
public class ListLayoutStrategyAssert extends LayoutStrategyAssert<ListLayoutStrategyAssert, ListLayoutStrategy> {

    public ListLayoutStrategyAssert(ListLayoutStrategy listLayoutStrategy) {
        super(listLayoutStrategy, ListLayoutStrategyAssert.class);
    }

    public ListLayoutStrategyAssert isAreChildNodesDraggable() {
        assertThat(this.actual.isAreChildNodesDraggable()).isTrue();
        return this;
    }

    public ListLayoutStrategyAssert isNotAreChildNodesDraggable() {
        assertThat(this.actual.isAreChildNodesDraggable()).isFalse();
        return this;
    }

    public ListLayoutStrategyAssert hasTopGap(int topGap) {
        assertThat(this.actual.getTopGap()).isEqualTo(topGap);
        return this;
    }

    public ListLayoutStrategyAssert hasBottomGap(int bottomGap) {
        assertThat(this.actual.getBottomGap()).isEqualTo(bottomGap);
        return this;
    }

    public ListLayoutStrategyAssert hasGrowableNodeIds(List<String> growableNodeIds) {
        assertThat(this.actual.getGrowableNodeIds()).hasSameElementsAs(growableNodeIds);
        return this;
    }
}
