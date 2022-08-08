/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams;

/**
 * The layout strategy that position children where their position is putting them.
 *
 * @author gcoutable
 */
public final class FreeFormLayoutStrategy implements ILayoutStrategy {

    public static final String KIND = "FreeForm"; //$NON-NLS-1$

    @Override
    public String getKind() {
        return KIND;
    }

}
