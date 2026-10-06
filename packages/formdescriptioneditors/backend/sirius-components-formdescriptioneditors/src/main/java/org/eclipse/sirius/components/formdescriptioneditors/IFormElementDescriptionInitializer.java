/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors;

import org.eclipse.sirius.components.view.form.FormElementDescription;

/**
 * Services initializing a form element description for the form description editors.
 *
 * @author tgiraudet
 * @since v2026.11.0
 */
public interface IFormElementDescriptionInitializer {

    void initialize(FormElementDescription formElementDescription);
}
