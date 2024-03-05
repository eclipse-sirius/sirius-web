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
package org.eclipse.sirius.components.emf.forms.api;

import org.eclipse.sirius.components.forms.description.FormDescription;

/**
 * Returns the default description for EMF based models.
 *
 * @author sbegaudeau
 */
public interface IEMFFormDescriptionProvider {

    String DESCRIPTION_ID = "emfFormDescription";

    FormDescription getFormDescription();
}
