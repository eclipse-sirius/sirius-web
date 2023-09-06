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
package org.eclipse.sirius.components.widget.reference;

/**
 * Represents the metadata needed to identify the EMF feature (EReference) being edited by a Reference widget.
 * Needed for the frontend to subscribe to a Model Browser tree representation configured for this EReference.
 *
 * @author pcdavid
 */
public record Reference(String ownerKind, String referenceKind, boolean containment, boolean manyValued) {

}
