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
package org.eclipse.sirius.components.view.emf;

import java.util.List;

import org.eclipse.sirius.components.view.View;

/**
 * Provides Java service classes for a particular {@link View} definition.
 *
 * @author pcdavid
 */
public interface IJavaServiceProvider {
    List<Class<?>> getServiceClasses(View view);
}
