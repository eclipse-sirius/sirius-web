/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.components;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.sirius.web.diagrams.Size;
import org.junit.Test;

/**
 * Test cases for {@link NodeSizeProvider}.
 *
 * @author fbarbin
 */
public class NodeSizeProviderTestCases {

    @Test
    public void testNodeSize() {
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider();
        Size size = nodeSizeProvider.getSize(null, List.of());
        assertThat(size).extracting(Size::getHeight).isEqualTo(Double.valueOf(70));
        assertThat(size).extracting(Size::getWidth).isEqualTo(Double.valueOf(150));
    }
}
