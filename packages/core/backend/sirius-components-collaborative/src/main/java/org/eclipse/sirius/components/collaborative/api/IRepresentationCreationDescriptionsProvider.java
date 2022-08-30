/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentationDescription;

/**
 * Provide the {@link RepresentationCreationDescription}s for a given object.
 *
 * @author arichard
 */
public interface IRepresentationCreationDescriptionsProvider {

    boolean canHandle(IRepresentationDescription representationDescription);

    List<RepresentationCreationDescription> handle(IEditingContext editingContext, Object object, IRepresentationDescription representationDescription);

}