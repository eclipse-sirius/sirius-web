/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.components;

import java.util.List;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.web.diagrams.ImageSizeProvider;
import org.eclipse.sirius.web.diagrams.Size;

/**
 * Provides the Size to apply to a new node.
 *
 * @author fbarbin
 */
public class NodeSizeProvider {

    public Size getSize(INodeStyle style, List<Element> childElements) {
        if (style instanceof ImageNodeStyle) {
            return new ImageNodeStyleSizeProvider(new ImageSizeProvider()).getSize((ImageNodeStyle) style);
        }
        // @formatter:off
        return Size.newSize()
                .width(150)
                .height(70)
                .build();
        // @formatter:on
    }
}
