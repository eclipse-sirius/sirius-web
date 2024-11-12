/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata;

import java.util.Objects;

import org.springframework.data.relational.core.mapping.Table;

/**
 * Representation icon table.
 *
 * @author Jerome Gout
 */
@Table("representation_metadata_icon_url")
public record RepresentationIconURL(String url) {
    public RepresentationIconURL {
        Objects.requireNonNull(url);
    }
}
