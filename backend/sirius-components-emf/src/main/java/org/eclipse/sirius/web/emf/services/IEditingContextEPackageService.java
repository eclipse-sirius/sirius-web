/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;

/**
 * Used to find the EPackages of an editing context.
 *
 * @author sbegaudeau
 */
public interface IEditingContextEPackageService {
    List<EPackage> getEPackages(String editingContextId);
}
