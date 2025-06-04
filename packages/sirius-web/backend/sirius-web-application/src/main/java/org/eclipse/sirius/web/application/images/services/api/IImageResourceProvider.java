/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.sirius.web.application.images.services.api;

import org.springframework.core.io.Resource;

/**
 * Used to return the spring resource for a given image path.
 *
 * @author sbegaudeau
 */
public interface IImageResourceProvider {
    boolean canHandle(String imagePath);

    Resource getResource(String imagePath);
}
