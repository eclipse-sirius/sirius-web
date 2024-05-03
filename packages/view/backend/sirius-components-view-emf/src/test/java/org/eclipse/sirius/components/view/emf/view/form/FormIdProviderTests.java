/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf.view.form;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.emf.form.FormIdProvider;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescription;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link FormIdProvider}.
 *
 * @author Arthur Daussy
 */
public class FormIdProviderTests {

    private final FormIdProvider idProvider = new FormIdProvider(new MockedObjectService());

    @Test
    public void formDescriptionContainedInResource() {
        FormDescription formDescription = this.createFormInsideResource();
        assertEquals("siriusComponents://representationDescription?kind=formDescription&sourceKind=view&sourceId=test-resource&sourceElementId=form1", this.idProvider.getId(formDescription));
    }

    @Test
    public void formElementDescriptionContainedInResource() {
        GroupDescription group = this.createFormWithOneGroup();

        TextfieldDescription textField = FormFactory.eINSTANCE.createTextfieldDescription();
        group.getChildren().add(textField);
        textField.setName("textField1");

        assertEquals("siriusComponents://formElementDescription?kind=TextfieldDescription&sourceKind=view&sourceId=test-resource&sourceElementId=textField1", this.idProvider.getFormElementDescriptionId(textField));
    }

    private GroupDescription createFormWithOneGroup() {
        FormDescription description = this.createFormInsideResource();

        PageDescription pageDescription = FormFactory.eINSTANCE.createPageDescription();
        pageDescription.setName("page1");
        description.getPages().add(pageDescription);

        GroupDescription group = FormFactory.eINSTANCE.createGroupDescription();
        group.setName("group1");
        pageDescription.getGroups().add(group);

        return group;
    }

    private FormDescription createFormInsideResource() {
        URI uri = URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///test-resource");

        XMIResource resource = new XMIResourceImpl(uri);
        FormDescription description = FormFactory.eINSTANCE.createFormDescription();
        description.setName("form1");
        resource.getContents().add(description);
        return description;
    }

    /**
     * Mocked {@link IObjectService} that use the name of an object as its id.
     *
     * @author Arthur Daussy
     */
    private static final class MockedObjectService extends IObjectService.NoOp {

        @Override
        public String getId(Object object) {
            final String id;
            if (object instanceof FormElementDescription desc) {
                id = desc.getName();
            } else if (object instanceof RepresentationDescription desc) {
                id = desc.getName();
            } else {
                id = "";
            }
            return id;
        }

    }

}
