/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf.compatibility;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Customizes the properties view for some of the View DSL elements.
 *
 * @author mcharfadi
 */
public interface IPropertiesWidgetCreationService {

    PageDescription createSimplePageDescription(String id, GroupDescription groupDescription, Predicate<VariableManager> canCreatePredicate);

    GroupDescription createSimpleGroupDescription(List<AbstractControlDescription> controls);

    CheckboxDescription createCheckbox(String id, String title, Function<Object, Boolean> reader, BiConsumer<Object, Boolean> writer, Object feature);
}
