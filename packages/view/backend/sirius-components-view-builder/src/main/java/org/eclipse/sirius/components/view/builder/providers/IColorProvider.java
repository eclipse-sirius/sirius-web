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
package org.eclipse.sirius.components.view.builder.providers;

import org.eclipse.sirius.components.view.UserColor;

/**
 * Used to find colors.
 *
 * @author sbegaudeau
 */
public interface IColorProvider {
    UserColor getColor(String colorName);
}
