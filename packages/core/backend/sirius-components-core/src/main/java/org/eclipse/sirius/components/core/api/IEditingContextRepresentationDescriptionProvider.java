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
package org.eclipse.sirius.components.core.api;

import java.util.List;

import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * Used to provide the representation description of the editing context.
 *
 * Note: This interface will soon be moved into sirius-web-application
 *
 * @author sbegaudeau
 */
public interface IEditingContextRepresentationDescriptionProvider {
    List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext);
}
