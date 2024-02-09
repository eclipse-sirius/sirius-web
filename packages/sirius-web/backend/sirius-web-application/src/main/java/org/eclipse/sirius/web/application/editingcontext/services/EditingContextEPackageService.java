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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.emf.services.IEditingContextEPackageService;
import org.springframework.stereotype.Service;

/**
 * A service used to retrieve all the EPackages accessible for a given editing context.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextEPackageService implements IEditingContextEPackageService {
    @Override
    public List<EPackage> getEPackages(String editingContextId) {
        return List.of();
    }
}
