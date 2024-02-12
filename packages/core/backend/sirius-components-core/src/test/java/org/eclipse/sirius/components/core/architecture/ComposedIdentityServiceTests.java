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

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.eclipse.sirius.components.core.api.IDefaultIdentityService;
import org.eclipse.sirius.components.core.api.IIdentityServiceDelegate;
import org.eclipse.sirius.components.core.services.ComposedIdentityService;

import java.util.List;

/**
 * Tests for the default {@link org.eclipse.sirius.components.core.api.IIdentityService} implementation.
 *
 * @author mcharfadi
 */
public class ComposedIdentityServiceTests {
    @Test
    @DisplayName("Test that a valid delegate object service is taken into account before the default object service")
    public void testDelegateObjectService() {
        IIdentityServiceDelegate identityServicesDelegate = new IIdentityServiceDelegate.NoOp() {
            @Override
            public boolean canHandle(Object object) {
                return true;
            }

            @Override
            public String getId(Object object) {
                return "delegateId";
            }
        };

        IDefaultIdentityService defaultObjectService = new IDefaultIdentityService.NoOp();

        ComposedIdentityService objectService = new ComposedIdentityService(List.of(identityServicesDelegate), defaultObjectService);

        assertEquals("delegateId", objectService.getId(new Object()));

    }

    @Test
    @DisplayName("Test that the default object service is taken into account if no valid delegate objet service is found")
    public void testDefaultObjectService() {
        IIdentityServiceDelegate objectServicesDelegate = new IIdentityServiceDelegate.NoOp() {
            @Override
            public boolean canHandle(Object object) {
                return false;
            }

        };

        IDefaultIdentityService defaultObjectService = new IDefaultIdentityService.NoOp() {
            @Override
            public String getId(Object object) {
                return "defaultId";
            }
        };

        ComposedIdentityService objectService = new ComposedIdentityService(List.of(objectServicesDelegate), defaultObjectService);

        assertEquals("defaultId", objectService.getId(new Object()));
    }
}
