/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf.widget.reference.architecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.widget.reference.ReferenceWidgetDescriptionConverterSwitch;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.junit.jupiter.api.Test;

/**
 * Converts a View-based ReferenceWidgetDescription into its API equivalent.
 *
 * @author pcdavid
 */
public class ReferenceWidgetDescriptionConverterSwitchTest {

    @Test
    public void testReferenceOptions() {
        var interpreter = new AQLInterpreter(List.of(), List.of(EcorePackage.eINSTANCE));
        var objectService = new IObjectService.NoOp();
        var editService = new IEditService.NoOp();
        var emfKindService = new IEMFKindService.NoOp();
        var feedbackMessageService = new IFeedbackMessageService.NoOp();
        var composedAdapterFactory = new ComposedAdapterFactory();
        composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
        var widgetIdProvider = new IFormIdProvider.NoOp();
        var converterSwitch = new ReferenceWidgetDescriptionConverterSwitch(interpreter, objectService, editService, emfKindService, feedbackMessageService, composedAdapterFactory, widgetIdProvider);

        EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
        EClass eClass = EcoreFactory.eINSTANCE.createEClass();
        EOperation eOperation = EcoreFactory.eINSTANCE.createEOperation();
        ePackage.getEClassifiers().add(eClass);
        eClass.getEOperations().add(eOperation);

        VariableManager variableManager = new VariableManager();
        variableManager.put("self", ePackage);

        ReferenceWidgetDescription referenceWidgetDescription = ReferenceFactory.eINSTANCE.createReferenceWidgetDescription();
        referenceWidgetDescription.setName("TEST");
        referenceWidgetDescription.setLabelExpression("TEST");
        referenceWidgetDescription.setReferenceNameExpression("eOperations");
        referenceWidgetDescription.setReferenceOwnerExpression("aql:self.eClassifiers->first()");
        Optional<AbstractWidgetDescription> widgetDescription = converterSwitch.caseReferenceWidgetDescription(referenceWidgetDescription);
        assertTrue(widgetDescription.isPresent());
        AbstractWidgetDescription abstractWidgetDescription = widgetDescription.get();
        assertInstanceOf(org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription.class, abstractWidgetDescription);
        List<?> options = ((org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription) abstractWidgetDescription).getOptionsProvider().apply(variableManager);
        assertNotNull(options);
        assertFalse(options.isEmpty());
        assertEquals(eOperation, options.get(0));
    }
}
