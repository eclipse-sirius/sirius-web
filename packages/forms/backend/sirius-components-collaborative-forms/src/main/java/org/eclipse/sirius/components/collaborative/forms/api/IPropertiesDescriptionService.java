/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.api;

import java.util.List;

import org.eclipse.sirius.components.forms.description.PageDescription;

/**
 * This class gives access to all the properties descriptions available.
 *
 * @author hmarchadour
 */
public interface IPropertiesDescriptionService {

    List<PageDescription> getPropertiesDescriptions();

}
