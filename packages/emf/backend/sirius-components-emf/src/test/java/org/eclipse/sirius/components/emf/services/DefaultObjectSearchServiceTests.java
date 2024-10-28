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
package org.eclipse.sirius.components.emf.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.junit.jupiter.api.Test;

/**
 * Test for the default implementation if {@link IDefaultObjectSearchService}.
 *
 * @author pcdavid
 */
public class DefaultObjectSearchServiceTests {

    @Test
    public void testFindsEditingContextFromItsId() {
        var editingContextId = "editingContextId";
        IEditingContext editingContext = () -> editingContextId;

        DefaultObjectSearchService defaultObjectSearchService = new DefaultObjectSearchService();

        var optionalObject = defaultObjectSearchService.getObject(editingContext, editingContextId);
        assertThat(optionalObject).containsSame(editingContext);
    }

}
