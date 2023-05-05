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
package org.eclipse.sirius.web.sample.papaya.view.overviewform;

import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.IRepresentationDescriptionProvider;

/**
 * Used to create the description of the overview form.
 *
 * @author sbegaudeau
 */
public class OverviewFormProvider implements IRepresentationDescriptionProvider {
    @Override
    public RepresentationDescription create(IColorProvider colorProvider) {
        var formDescription = ViewFactory.eINSTANCE.createFormDescription();
        formDescription.setDomainType("papaya_core::Root");
        formDescription.setName("Overview Form");

        var groupDescription = ViewFactory.eINSTANCE.createGroupDescription();
        groupDescription.setName("Group");
        groupDescription.setSemanticCandidatesExpression("aql:self");
        groupDescription.setLabelExpression("Root");

        formDescription.getGroups().add(groupDescription);

        return formDescription;
    }
}
