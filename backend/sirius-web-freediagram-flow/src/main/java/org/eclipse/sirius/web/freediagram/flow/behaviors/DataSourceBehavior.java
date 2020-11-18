/***********************************************************************************************
 * Copyright (c) 2019, 2020 Obeo. All Rights Reserved.
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 ***********************************************************************************************/
package org.eclipse.sirius.web.freediagram.flow.behaviors;

import fr.obeo.dsl.designer.sample.flow.DataSource;
import fr.obeo.dsl.designer.sample.flow.FlowElementStatus;
import fr.obeo.dsl.designer.sample.flow.FlowPackage;
import fr.obeo.dsl.designer.sample.flow.System;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.freediagram.behaviors.ICreateInstanceBehavior;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Data source behaviors.
 *
 * Define the create instance behavior.
 *
 * @author hmarchadour
 */
public class DataSourceBehavior implements ICreateInstanceBehavior {

    private final String defaultLabel;

    public DataSourceBehavior() {
        this("DataSource"); //$NON-NLS-1$
    }

    public DataSourceBehavior(String defaultLabel) {
        this.defaultLabel = Objects.requireNonNull(defaultLabel);
    }

    @Override
    public Optional<EObject> getOwner(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, System.class).map(EObject.class::cast);
    }

    @Override
    public Optional<EReference> getContainmentFeature(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, System.class).map(system -> FlowPackage.Literals.SYSTEM__ELEMENTS);
    }

    @Override
    public Optional<EObject> createEObject(VariableManager variableManager) {
        DataSource instance = (DataSource) EcoreUtil.create(FlowPackage.Literals.DATA_SOURCE);
        instance.setStatus(FlowElementStatus.ACTIVE);
        instance.setVolume(6);
        String suffix = ""; //$NON-NLS-1$
        Optional<EObject> optionalContainer = variableManager.get(VariableManager.SELF, System.class).map(EObject::eContainer);
        if (optionalContainer.isPresent()) {
            // @formatter:off
            long nbProcessors = optionalContainer.get().eContents().stream()
                    .filter(System.class::isInstance)
                    .count();
            // @formatter:on
            if (nbProcessors > 0) {
                suffix = "" + nbProcessors; //$NON-NLS-1$
            }
        }
        instance.setName(this.defaultLabel + suffix);
        return Optional.of(instance);
    }

}
