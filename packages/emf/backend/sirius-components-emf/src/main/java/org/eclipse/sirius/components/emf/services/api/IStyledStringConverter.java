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
package org.eclipse.sirius.components.emf.services.api;

import org.eclipse.sirius.components.core.api.labels.StyledString;

/**
 * Used to convert EMF styled strings to Sirius Components ones.
 *
 * @author sbegaudeau
 */
public interface IStyledStringConverter {
    StyledString convert(org.eclipse.emf.edit.provider.StyledString styledString);
}
