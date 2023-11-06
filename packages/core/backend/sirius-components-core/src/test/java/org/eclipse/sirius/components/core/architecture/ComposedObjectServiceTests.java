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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IDefaultObjectService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IObjectServiceDelegate;
import org.eclipse.sirius.components.core.services.ComposedObjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the default {@link IObjectService} implementation.
 *
 * @author arichard
 */
public class ComposedObjectServiceTests {

    @Test
    @DisplayName("Test that a valid delegate object service is taken into account before the default object service")
    public void testDelegateObjectService() {
        IObjectServiceDelegate objectServiceDelegate = new IObjectServiceDelegate.NoOp() {
            @Override
            public boolean canHandle(Object object) {
                return true;
            }

            @Override
            public boolean canHandle(IEditingContext editingContext) {
                return true;
            }

            @Override
            public String getId(Object object) {
                return "delegateId";
            }

            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        IDefaultObjectService defaultObjectService = new IDefaultObjectService.NoOp();

        ComposedObjectService objectService = new ComposedObjectService(List.of(objectServiceDelegate), defaultObjectService);

        assertEquals("delegateId", objectService.getId(new Object()));
        assertTrue(objectService.getObject(new IEditingContext.NoOp(), "").isPresent());
    }

    @Test
    @DisplayName("Test that the default object service is taken into account if no valid delegate objet service is found")
    public void testDefaultObjectService() {
        IObjectServiceDelegate objectServiceDelegate = new IObjectServiceDelegate.NoOp() {
            @Override
            public boolean canHandle(Object object) {
                return false;
            }

            @Override
            public boolean canHandle(IEditingContext editingContext) {
                return false;
            }
        };

        IDefaultObjectService defaultObjectService = new IDefaultObjectService.NoOp() {
            @Override
            public String getId(Object object) {
                return "defaultId";
            }
        };

        ComposedObjectService objectService = new ComposedObjectService(List.of(objectServiceDelegate), defaultObjectService);

        assertEquals("defaultId", objectService.getId(new Object()));
        assertFalse(objectService.getObject(new IEditingContext.NoOp(), "").isPresent());
    }
}
