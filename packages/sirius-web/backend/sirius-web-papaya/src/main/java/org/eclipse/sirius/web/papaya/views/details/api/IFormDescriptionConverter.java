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
package org.eclipse.sirius.web.papaya.views.details.api;

import java.util.List;

import org.eclipse.sirius.components.forms.description.FormDescription;

/**
 * Used to convert the view form description.
 *
 * @author sbegaudeau
 */
public interface IFormDescriptionConverter {
    List<FormDescription> convert(org.eclipse.sirius.components.view.View view);
}
