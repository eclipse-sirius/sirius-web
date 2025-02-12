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
package org.eclipse.sirius.web.domain.boundedcontexts.library;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A dependency of a library.
 *
 * @author gdaniel
 */
@Table("library_dependency")
public record LibraryDependency(AggregateReference<Library, UUID> dependencyLibraryId) {

    public LibraryDependency {
        Objects.requireNonNull(dependencyLibraryId);
    }

}
