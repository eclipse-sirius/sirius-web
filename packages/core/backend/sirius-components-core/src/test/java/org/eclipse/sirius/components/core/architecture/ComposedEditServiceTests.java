/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.core.architecture;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IDefaultEditService;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditServiceDelegate;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.services.ComposedEditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the default {@link IEditService} implementation.
 *
 * @author arichard
 */
public class ComposedEditServiceTests {

    @Test
    @DisplayName("Test that a valid delegate edit service is taken into account before the default edit service")
    public void testDelegateEditService() {
        IEditServiceDelegate editServiceDelegate = new IEditServiceDelegate.NoOp() {
            @Override
            public boolean canHandle(Object object) {
                return true;
            }

            @Override
            public boolean canHandle(IEditingContext editingContext) {
                return true;
            }

            @Override
            public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String classId, String referenceName) {
                return List.of(new ChildCreationDescription("delegateId", "", List.of()));
            }
        };

        IDefaultEditService defaultEditService = new IDefaultEditService.NoOp();

        ComposedEditService editService = new ComposedEditService(List.of(editServiceDelegate), defaultEditService);

        List<ChildCreationDescription> result = editService.getChildCreationDescriptions(null, "", "");
        assertEquals(1, result.size());
        assertEquals("delegateId", result.get(0).getId());
    }

    @Test
    @DisplayName("Test that the default edit service is taken into account if no valid delegate edit service is found")
    public void testDefaultEditService() {
        IEditServiceDelegate editServiceDelegate = new IEditServiceDelegate.NoOp() {
            @Override
            public boolean canHandle(Object object) {
                return false;
            }

            @Override
            public boolean canHandle(IEditingContext editingContext) {
                return false;
            }
        };

        IDefaultEditService defaultEditService = new IDefaultEditService.NoOp() {
            @Override
            public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String classId, String referenceName) {
                return List.of(new ChildCreationDescription("defaultId", "", List.of()));
            }
        };

        ComposedEditService editService = new ComposedEditService(List.of(editServiceDelegate), defaultEditService);

        List<ChildCreationDescription> result = editService.getChildCreationDescriptions(null, "", "");
        assertEquals(1, result.size());
        assertEquals("defaultId", result.get(0).getId());
    }
}
