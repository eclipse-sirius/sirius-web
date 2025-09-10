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
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.collaborative.browser.api;

/**
 * Used declare that a given TreeDescription (identified by its technical id) can be used as the model browser (e.g.
 * inside a reference widget modal) using a specific symbolic name/id (modelBrowserId, e.g. {@code "reference"}.
 *
 * @author pcdavid
 */
public record ModelBrowser(String modelBrowserId, String treeDescriptionId) {

}
