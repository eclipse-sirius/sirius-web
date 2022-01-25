/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.components.collaborative.forms.api;

import org.eclipse.sirius.components.forms.description.FormDescription;

/**
 * Provides the default form description for the properties.
 *
 * @author fbarbin
 */
public interface IPropertiesDefaultDescriptionProvider {
    FormDescription getFormDescription();
}
