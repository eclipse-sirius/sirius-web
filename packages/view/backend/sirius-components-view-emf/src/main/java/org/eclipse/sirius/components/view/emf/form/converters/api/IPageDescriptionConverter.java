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
package org.eclipse.sirius.components.view.emf.form.converters.api;

import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;

/**
 * Used to convert a page description.
 *
 * @author sbegaudeau
 */

public interface IPageDescriptionConverter {

    PageDescription convert(org.eclipse.sirius.components.view.form.PageDescription viewPageDescription, AQLInterpreter interpreter);
}
