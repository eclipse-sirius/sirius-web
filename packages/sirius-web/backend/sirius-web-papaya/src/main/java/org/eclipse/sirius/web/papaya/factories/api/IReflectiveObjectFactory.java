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
package org.eclipse.sirius.web.papaya.factories.api;

import java.util.function.Predicate;

import org.eclipse.sirius.components.papaya.Package;

/**
 * Used to scan the classloader and create the relevant objects.
 *
 * @author sbegaudeau
 */
public interface IReflectiveObjectFactory {

    void createTypes(Package papayaPackage, Predicate<String> packageFilter);
}
