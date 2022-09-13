/*******************************************************************************
 * Copyright (c) 2022 CEA.
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
package org.eclipse.sirius.components.emf.services;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * A {@link ECrossReferenceAdapter}@ that is able to clean dangling references when an object is deleted or when a
 * resource is removed from the ResourceSet.
 *
 * @author lfasani
 */
public class EditingContextCrossReferenceAdapter extends ECrossReferenceAdapter {

    @Override
    protected void handleContainment(Notification notification) {
        Object oldValue = notification.getOldValue();
        int eventType = notification.getEventType();

        if (eventType == Notification.REMOVE) {
            this.handleRemoveObject(oldValue);

        } else if (eventType == Notification.REMOVE_MANY) {
            for (Object object : (Collection<?>) oldValue) {
                this.handleRemoveObject(object);
            }
        } else {
            super.handleContainment(notification);
        }
    }

    private void handleRemoveObject(Object object) {
        if (object instanceof Resource) {
            this.handleRemoveResource((Resource) object);
        } else if (object instanceof EObject) {
            this.handleRemoveEObject((EObject) object);
        }
    }

    private void handleRemoveEObject(EObject eObject) {
        // Remove all inverse references
        this.clearReferencesTo(eObject);
        TreeIterator<Object> allProperContents = EcoreUtil.getAllProperContents(eObject, false);
        while (allProperContents.hasNext()) {
            Object object = allProperContents.next();
            if (object instanceof EObject) {
                this.clearReferencesTo((EObject) object);
            }
        }

        // clean the crossRefererenceAdapter
        this.unsetTarget(eObject);
    }

    private void handleRemoveResource(Resource resource) {
        // Remove all inverse references
        TreeIterator<Object> allProperContents = EcoreUtil.getAllProperContents(resource, false);
        while (allProperContents.hasNext()) {
            Object object = allProperContents.next();
            if (object instanceof EObject) {
                this.clearReferencesTo((EObject) object);
            }
        }

        // clean the crossRefererenceAdapter
        this.unsetTarget(resource);
    }

    private void clearReferencesTo(EObject referencedObject) {
        Collection<Setting> settings = this.getInverseReferences(referencedObject);

        if (settings != null) {
            for (Setting setting : this.getNonContainmentReferences(settings)) {
                try {
                    EcoreUtil.remove(setting, referencedObject);
                } catch (NullPointerException e) {
                    // thrown when trying to remove from an object that is present in the being removed Resource.
                    // But we do not care about this resource.
                }
            }
        }
    }

    private Collection<Setting> getNonContainmentReferences(Collection<Setting> inverseReferences) {
        Collection<Setting> containmentReferences = new ArrayList<>();
        for (EStructuralFeature.Setting setting : inverseReferences) {
            EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();
            if (eStructuralFeature instanceof EReference) {
                EReference eReference = (EReference) eStructuralFeature;
                if (!eReference.isContainment()) {
                    containmentReferences.add(setting);
                }
            }
        }
        return containmentReferences;
    }
}
