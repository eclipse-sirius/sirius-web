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
package org.eclipse.sirius.components.core.architecture;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchServiceDelegate;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.services.ComposedObjectSearchService;

import java.util.List;
import java.util.Optional;

/**
 * Tests for the default {@link IObjectService} implementation.
 *
 * @author arichard
 */
public class ComposedObjectServiceTests {

    @Test
    @DisplayName("Test that a valid delegate object service is taken into account before the default object service")
    public void testDelegateObjectService() {
        IObjectSearchServiceDelegate objectServicesDelegate = new IObjectSearchServiceDelegate.NoOp() {

            @Override
            public boolean canHandle(IEditingContext editingContext, String objectId) {
                return true;
            }
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        IDefaultObjectSearchService defaultObjectService = new IDefaultObjectSearchService.NoOp();

        ComposedObjectSearchService objectService = new ComposedObjectSearchService(List.of(objectServicesDelegate), defaultObjectService);

        assertTrue(objectService.getObject(new IEditingContext.NoOp(), "").isPresent());
    }

    @Test
    @DisplayName("Test that the default object service is taken into account if no valid delegate objet service is found")
    public void testDefaultObjectService() {
        IObjectSearchServiceDelegate objectServicesDelegate = new IObjectSearchServiceDelegate.NoOp() {

            @Override
            public boolean canHandle(IEditingContext editingContext, String objectId) {
                return false;
            }
        };

        IDefaultObjectSearchService defaultObjectService = new IDefaultObjectSearchService.NoOp() {

        };

        ComposedObjectSearchService objectService = new ComposedObjectSearchService(List.of(objectServicesDelegate), defaultObjectService);

        assertFalse(objectService.getObject(new IEditingContext.NoOp(), "").isPresent());
    }
}
