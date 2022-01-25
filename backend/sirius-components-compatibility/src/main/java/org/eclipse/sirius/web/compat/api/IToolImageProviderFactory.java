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
package org.eclipse.sirius.web.compat.api;

import java.util.function.Supplier;

import org.eclipse.sirius.viewpoint.description.tool.AbstractToolDescription;

/**
 * Used to provide a function which will give us the image path for a tool.
 *
 * @author sbegaudeau
 */
public interface IToolImageProviderFactory {
    Supplier<String> getToolImageProvider(AbstractToolDescription abstractToolDescription);
}
