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
package org.eclipse.sirius.web.compat.services.forms.api;

import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.eclipse.sirius.web.forms.description.FormDescription;

/**
 * Used to convert a Sirius view extension description into an Sirius Web one.
 *
 * @author sbegaudeau
 */
public interface IViewExtensionDescriptionConverter {
    FormDescription convert(ViewExtensionDescription viewExtensionDescription);
}
