/*******************************************************************************
 * Copyright (c) 2026 CEA LIST.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA LIST - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.collaborative.forms.api;

import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.description.FormDescription;

/**
 * Service used to create forms from scratch.
 *
 * @author pdeville
 */
public interface IFormCreationService {

    /**
     * Creates a new form using the given parameters.
     *
     * @param targetObject
     *            The object used as the target
     * @param formDescription
     *            The description of the form
     * @return A new form
     */
    Form create(Object targetObject, FormDescription formDescription);

}
