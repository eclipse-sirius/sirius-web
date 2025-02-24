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

import org.eclipse.sirius.components.collaborative.api.RepresentationDescriptionMetadata;

/**
 * Used to sort the compatible representation description (most common/highest priority first) before presenting them to
 * the user or representation creation.
 *
 * @author pcdavid
 */
public interface IRepresentationDescriptionMetadataSorter {
    List<RepresentationDescriptionMetadata> sort(List<RepresentationDescriptionMetadata> representationDescriptions);
}
