/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.services.api.representations;

import java.util.List;

import org.eclipse.sirius.web.representations.IRepresentationDescription;

/**
 * The description service gives access to all the representation descriptions available.
 *
 * @author sbegaudeau
 */
public interface IRepresentationDescriptionService {

    List<IRepresentationDescription> getRepresentationDescriptions(Object clazz);

    List<IRepresentationDescription> getRepresentationDescriptions();
}
