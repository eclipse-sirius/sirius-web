/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.images.infrastructure.messages;

import java.util.Objects;

import org.eclipse.sirius.web.images.domain.messages.api.IImagesDomainMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

/**
 * Provides internationalized messages for the images domain.
 *
 * @author sbegaudeau
 */
@Service
public class ImagesDomainMessageService implements IImagesDomainMessageService {

    private final MessageSourceAccessor messageSourceAccessor;

    public ImagesDomainMessageService(@Qualifier("imagesDomainMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = Objects.requireNonNull(messageSourceAccessor);
    }

    @Override
    public String invalidImage() {
        return this.messageSourceAccessor.getMessage("INVALID_IMAGE");
    }

    @Override
    public String imageNotFound() {
        return this.messageSourceAccessor.getMessage("IMAGE_NOT_FOUND");
    }
}
