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
package org.eclipse.sirius.web.application.studio.services.library.api;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;

/**
 * Creates the semantic data containing the studio libraries of a given dependency graph.
 *
 * @author gdaniel
 */
public interface IStudioLibrarySemanticDataCreationService {

    Collection<SemanticData> createSemanticData(PublishLibrariesInput input, DependencyGraph<EObject> dependencyGraph, ResourceSet resourceSet);

}
