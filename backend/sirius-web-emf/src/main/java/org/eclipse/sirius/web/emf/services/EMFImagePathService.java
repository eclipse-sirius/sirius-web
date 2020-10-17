/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import java.util.List;

import org.eclipse.sirius.web.api.services.IImagePathService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IImagePathService} for the default EMF icon location.
 *
 * @author hmarchadour
 */
@Service
public class EMFImagePathService implements IImagePathService {

    private static final List<String> IMAGES_PATHS = List.of("/icons/full/obj16"); //$NON-NLS-1$

    @Override
    public List<String> getPaths() {
        return IMAGES_PATHS;
    }

}
