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

import org.assertj.core.api.AbstractObjectAssert;
import org.eclipse.sirius.components.diagrams.INodeStyle;

/**
 * Custom assertion class used to perform some tests on a node style.
 *
 * @param <SELF> the type of the assertion
 * @param <ACTUAL> the type of the element
 *
 * @author gdaniel
 */
public class NodeStyleAssert<SELF extends NodeStyleAssert<SELF, ACTUAL>, ACTUAL extends INodeStyle> extends AbstractObjectAssert<SELF, ACTUAL> {

    public NodeStyleAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

}
