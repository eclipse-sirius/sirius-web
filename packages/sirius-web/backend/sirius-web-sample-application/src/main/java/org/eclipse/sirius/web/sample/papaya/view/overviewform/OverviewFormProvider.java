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
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IRepresentationDescriptionProvider;
import org.eclipse.sirius.components.view.form.FormFactory;

/**
 * Used to create the description of the overview form.
 *
 * @author sbegaudeau
 */
public class OverviewFormProvider implements IRepresentationDescriptionProvider {
    @Override
    public RepresentationDescription create(IColorProvider colorProvider) {
        var formDescription = FormFactory.eINSTANCE.createFormDescription();
        formDescription.setDomainType("papaya_core::Root");
        formDescription.setName("Overview Form");

        var pageDescription = FormFactory.eINSTANCE.createPageDescription();
        pageDescription.setName("Page");
        pageDescription.setSemanticCandidatesExpression("aql:self");

        var groupDescription = FormFactory.eINSTANCE.createGroupDescription();
        groupDescription.setName("Group");
        groupDescription.setSemanticCandidatesExpression("aql:self");
        groupDescription.setLabelExpression("Root");

        var textfieldDescription = FormFactory.eINSTANCE.createTextfieldDescription();
        textfieldDescription.setName("Textfield");
        textfieldDescription.setLabelExpression("Root");
        textfieldDescription.setValueExpression("root element");
        textfieldDescription.setHelpExpression("aql:'This is a ' + self.eClass().name + ' element'");
        groupDescription.getChildren().add(textfieldDescription);

        pageDescription.getGroups().add(groupDescription);
        formDescription.getPages().add(pageDescription);

        return formDescription;
    }
}
