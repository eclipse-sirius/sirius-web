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
package org.eclipse.sirius.web.services.projects.api;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.services.api.projects.Nature;

/**
 * The metadata of a project.
 *
 * @author frouene
 */
public record EditingContextMetadata(List<Nature> natures) {
    public EditingContextMetadata {
        Objects.requireNonNull(natures);
    }
}
