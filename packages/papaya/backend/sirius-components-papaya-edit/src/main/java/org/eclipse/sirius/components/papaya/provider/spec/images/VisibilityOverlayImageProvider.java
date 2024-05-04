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
package org.eclipse.sirius.components.papaya.provider.spec.images;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.sirius.components.papaya.Visibility;

/**
 * Used to provide the visibility overlay image.
 *
 * @author sbegaudeau
 */
public class VisibilityOverlayImageProvider {
    public Object overlayImage(ResourceLocator resourceLocator, Visibility visibility) {
        return switch (visibility) {
            case PUBLIC -> resourceLocator.getImage("full/ovr16/Visibility_PUBLIC.svg");
            case PROTECTED -> resourceLocator.getImage("full/ovr16/Visibility_PROTECTED.svg");
            case PACKAGE -> resourceLocator.getImage("full/ovr16/Visibility_PACKAGE.svg");
            case PRIVATE -> resourceLocator.getImage("full/ovr16/Visibility_PRIVATE.svg");
        };
    }
}
