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

import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * Used to retrieve the semantic data.
 *
 * @author sbegaudeau
 */
public interface ISemanticDataSearchService {

    Optional<SemanticData> findByProject(AggregateReference<Project, String> project);

    List<SemanticData> findAllByDomains(List<String> domainUris);
}
