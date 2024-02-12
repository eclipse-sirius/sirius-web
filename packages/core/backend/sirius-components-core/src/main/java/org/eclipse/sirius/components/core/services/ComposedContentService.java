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

import org.eclipse.sirius.components.core.api.IContentService;
import org.eclipse.sirius.components.core.api.IContentServiceDelegate;
import org.eclipse.sirius.components.core.api.IDefaultContentService;
import org.eclipse.sirius.components.core.api.IIdentityService;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IIdentityService} which delegates to {@link IContentServiceDelegate} or fallback to
 * {@link IDefaultContentService}.
 *
 * @author mcharfadi
 */
@Service
public class ComposedContentService implements IContentService {

    private final List<IContentServiceDelegate> contentServiceDelegate;

    private final IDefaultContentService defaultContentService;

    public ComposedContentService(List<IContentServiceDelegate> contentServiceDelegate, IDefaultContentService defaultContentService) {
        this.contentServiceDelegate = Objects.requireNonNull(contentServiceDelegate);
        this.defaultContentService = Objects.requireNonNull(defaultContentService);
    }

    @Override
    public List<Object> getContents(Object object) {
        var optionalDelegate = this.contentServiceDelegate.stream()
                .filter(delegate -> delegate.canHandle(object))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getContents(object);
        }
        return this.defaultContentService.getContents(object);
    }
}
