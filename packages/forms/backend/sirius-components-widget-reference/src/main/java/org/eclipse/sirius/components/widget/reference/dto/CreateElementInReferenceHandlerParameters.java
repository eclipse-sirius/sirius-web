/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widget.reference.dto;

import java.util.UUID;

import org.eclipse.emf.ecore.EObject;

/**
 * Input object to pass inputs to the createElementInReferenceHandler field of ReferenceWidget.
 *
 * @author Jerome Gout
 */
public record CreateElementInReferenceHandlerParameters(UUID documentId, String domainId, EObject parent, String creationDescriptionId) {

}
