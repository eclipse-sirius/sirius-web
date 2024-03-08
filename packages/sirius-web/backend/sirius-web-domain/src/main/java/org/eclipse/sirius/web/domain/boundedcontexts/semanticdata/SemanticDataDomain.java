/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata;

import java.util.Objects;

import org.springframework.data.relational.core.mapping.Table;

/**
 * A domain used by semantic data.
 *
 * @author sbegaudeau
 */
@Table("semantic_data_domain")
public record SemanticDataDomain(String uri) {
    public SemanticDataDomain {
        Objects.requireNonNull(uri);
    }
}
