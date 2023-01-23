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
package org.eclipse.sirius.web.services.diagram;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.api.ParametricSVGImage;
import org.springframework.stereotype.Service;

/**
 * Provide a IParametricSVGImageRegistry.
 *
 * @author lfasani
 */
@Service
public class ParametricSVGImageRegistry implements IParametricSVGImageRegistry {
    @Override
    public List<ParametricSVGImage> getImages() {
        return List.of(new ParametricSVGImage(UUID.nameUUIDFromBytes("Package".getBytes()), "Package", "parametricSVGs/package.svg"),
                new ParametricSVGImage(UUID.nameUUIDFromBytes("Class".getBytes()), "Class", "parametricSVGs/class.svg"));
    }
}
