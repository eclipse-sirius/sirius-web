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
package org.eclipse.sirius.web.images.domain.messages.api;

/**
 * Used to compute internationalized messages for the images domain.
 *
 * @author sbegaudeau
 * @since 2026.9.0
 */
public interface IImagesDomainMessageService {

    /**
     * Provides the message used when an image is invalid.
     *
     * @return the invalid image message
     * @since 2026.9.0
     */
    String invalidImage();

    /**
     * Provides the message used when an image cannot be found.
     *
     * @return the not found message
     * @since 2026.9.0
     */
    String imageNotFound();
}
