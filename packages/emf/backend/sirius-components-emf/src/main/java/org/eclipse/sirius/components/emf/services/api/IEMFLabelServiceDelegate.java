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
package org.eclipse.sirius.components.emf.services.api;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.labels.StyledString;

/**
 * Used to customize the label of an EMF object.
 *
 * @author sbegaudeau
 */
public interface IEMFLabelServiceDelegate {
    boolean canHandle(EObject self);

    StyledString getStyledLabel(EObject self);

    List<String> getImagePaths(EObject self);

}
