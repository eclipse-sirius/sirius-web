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
package org.eclipse.sirius.components.core.services;

import org.springframework.stereotype.Service;

import org.eclipse.sirius.components.core.api.IDefaultIdentityService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IIdentityServiceDelegate;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IIdentityService} which delegates to {@link IIdentityServiceDelegate} or fallback to
 * {@link IDefaultIdentityService}.
 *
 * @author mcharfadi
 */
@Service
public class ComposedIdentityService implements IIdentityService {

    private final List<IIdentityServiceDelegate> identityServiceDelegate;

    private final IDefaultIdentityService defaultIdentityService;

    public ComposedIdentityService(List<IIdentityServiceDelegate> identityServiceDelegate, IDefaultIdentityService defaultIdentityService) {
        this.identityServiceDelegate = Objects.requireNonNull(identityServiceDelegate);
        this.defaultIdentityService = Objects.requireNonNull(defaultIdentityService);
    }
    @Override
    public String getId(Object object) {
        var optionalDelegate = this.identityServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getId(object);
        }
        return this.defaultIdentityService.getId(object);
    }

    @Override
    public String getKind(Object object) {
        var optionalDelegate = this.identityServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getKind(object);
        }
        return this.defaultIdentityService.getKind(object);
    }
}
