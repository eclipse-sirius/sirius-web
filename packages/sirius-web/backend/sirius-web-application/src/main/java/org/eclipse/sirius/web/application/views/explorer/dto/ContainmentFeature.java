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

package org.eclipse.sirius.web.application.views.explorer.dto;

/**
 * Representation of a containment feature that can be used to attach a child to a parent.
 *
 * @param id
 *         the id of the containment feature
 * @param label
 *         label displayed to the user
 * @author Arthur Daussy
 */
public record ContainmentFeature(String id, String label) {
}
