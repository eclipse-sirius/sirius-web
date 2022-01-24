/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.compat.forms;

import org.eclipse.emf.ecore.EObject;

/**
 * Standard services available when evaluating form-related expressions.
 *
 * @author pcdavid
 */
public class FormStandardServices {
    public EditSupportInput emfEditServices(EObject input, EObject self) {
        return new EditSupportInput(self);
    }
}
