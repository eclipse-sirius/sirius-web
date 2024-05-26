/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.factories.services.api;

import java.util.function.Predicate;

import org.eclipse.sirius.components.papaya.Component;

/**
 * Used to initialize quickly a Sirius Web component.
 *
 * @author sbegaudeau
 */
public interface IComponentInitializer {
    Component initialize(String name, String rootPackageName, Predicate<String> packageNameFilter);
}
