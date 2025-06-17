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
package org.eclipse.sirius.components.core.api;

import java.util.function.Predicate;

/**
 * Used to test if an object is read-only.
 *
 * @author gdaniel
 */
public interface IReadOnlyObjectPredicateDelegate extends Predicate<Object> {

    boolean canHandle(Object object);

}
