/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.representations;

import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * Common interface for all the representation descriptions.
 *
 * @author sbegaudeau
 */
@PublicApi
public interface IRepresentationDescription {

    String CLASS = "class"; //$NON-NLS-1$

    String getId();

    String getLabel();

    Predicate<VariableManager> getCanCreatePredicate();
}
