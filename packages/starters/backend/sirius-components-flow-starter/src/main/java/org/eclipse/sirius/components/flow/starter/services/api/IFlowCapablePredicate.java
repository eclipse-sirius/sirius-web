/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.flow.starter.services.api;

import java.util.function.Predicate;

/**
 * Used to test if a project or an editing context is capable of supporting flow.
 *
 * @author gcoutable
 * @since 2026.11.0
 */
public interface IFlowCapablePredicate extends Predicate<String> {
}
