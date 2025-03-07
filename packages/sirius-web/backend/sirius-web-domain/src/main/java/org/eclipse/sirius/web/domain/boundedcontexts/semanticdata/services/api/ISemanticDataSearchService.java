/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;

/**
 * Used to retrieve the semantic data.
 *
 * @author sbegaudeau
 */
public interface ISemanticDataSearchService {

    List<SemanticData> findAllByDomains(List<String> domainUris);

    Optional<SemanticData> findById(UUID id);

    boolean existsById(UUID id);

    List<SemanticData> findAllDependenciesRecursivelyById(UUID id);
}
