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
package org.eclipse.sirius.components.view.emf;

import java.util.Optional;

import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;

/**
 * Get a {@link View} from its representation description Id.
 *
 * @author arichard
 */
public interface IViewService {

    Optional<RepresentationDescription> getRepresentationDescription(String representationDescriptionId);
}
