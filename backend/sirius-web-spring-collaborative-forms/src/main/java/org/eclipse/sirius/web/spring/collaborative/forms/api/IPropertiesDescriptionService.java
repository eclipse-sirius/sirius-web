/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms.api;

import java.util.List;

import org.eclipse.sirius.web.forms.description.FormDescription;

/**
 * This class gives access to all the properties descriptions available.
 *
 * @author hmarchadour
 */
public interface IPropertiesDescriptionService {

    List<FormDescription> getPropertiesDescriptions();

}
