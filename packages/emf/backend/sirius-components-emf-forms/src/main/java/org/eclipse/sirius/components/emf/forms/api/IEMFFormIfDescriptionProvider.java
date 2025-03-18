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
package org.eclipse.sirius.components.emf.forms.api;

import java.util.List;

import org.eclipse.sirius.components.forms.description.IfDescription;

/**
 * Used to provide if descriptions to reuse in the description of the EMF form.
 *
 * @author sbegaudeau
 */
public interface IEMFFormIfDescriptionProvider {
    List<IfDescription> getIfDescriptions();
}
