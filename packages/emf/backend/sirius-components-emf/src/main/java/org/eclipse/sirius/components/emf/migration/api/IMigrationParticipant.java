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
package org.eclipse.sirius.components.emf.migration.api;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.emfjson.resource.JsonResource;

import com.google.gson.JsonObject;

/**
 * Interface of MigrationParticipant.
 *
 * @author mcharfadi
 */
public interface IMigrationParticipant {
    String getVersion();

    /**
     * Called before a JsonResource is serialized.
     *
     * @param resource
     *            The JsonResource that is serialized
     * @param jsonObject
     *            The root jsonObject
     */
    default void preDeserialization(JsonResource resource, JsonObject jsonObject) {

    }

    /**
     * Called after a JsonResource is serialized.
     *
     * @param resource
     *            The JsonResource that is serialized
     * @param jsonObject
     *            The root jsonObject
     */
    default void postSerialization(JsonResource resource, JsonObject jsonObject) {

    }

    /**
     * Called during the parsing of JsonResources after loading an eObject. As such the eObject will have all his
     * features set. The jsonObject is the one that was used to set the features and can be used to retrieve values
     * of unknown features.
     *
     * @param eObject
     *            the eObject that have been loaded.
     * @param jsonObject
     *            the jsonObject used to load the eObject.
     */
    default void postObjectLoading(EObject eObject, JsonObject jsonObject) {

    }

    /**
     * Called during the parsing of JsonResources (at loading time). If a feature value has changed since a previous
     * version, use this method to return the correct expected value or null if it did not change.
     *
     * @param eObject
     *            the object containing the feature.
     * @param feature
     *            the feature to set value.
     * @param value
     *            the initial serialized value.
     * @return the new value, or null otherwise.
     */
    default Object getValue(EObject eObject, EStructuralFeature feature, Object value)  {
        return null;
    }

    /**
     * Called during the parsing of JsonResources (at loading time). It allows to
     * retrieve a renamed EStructuralFeature from its old name. For example, if a
     * feature 'aaa' has been renamed to 'bbb', then your MigrationParticipant
     * should return the 'bbb' feature when given the 'aaa' name.
     *
     * @param eClass
     *            the given eClass.
     * @param eStructuralFeatureName
     *            the feature name before migration.
     * @return the new structural feature or null if not found. The attribute
     *         corresponding to given old name into given eClass.
     */
    default EStructuralFeature getEStructuralFeature(EClass eClass, String eStructuralFeatureName) {
        return eClass.getEStructuralFeature(eStructuralFeatureName);
    }

    /**
     * Called during the parsing of JsonResources (at loading time). If an
     * EClassifier name has changed, then you should return the correct one.
     *
     * @param ePackage
     *            the package where looking for classifier.
     * @param typeName
     *            the old classifier name before migration.
     * @return the new classifier corresponding to the old given name into given
     *         ePackage or null if not found.
     */
    default EClassifier getEClassifier(EPackage ePackage, String typeName) {
        return ePackage.getEClassifier(typeName);
    }

    /**
     * Return the EPackage to use for the given namespace.
     *
     * @param nsURI
     *            the nsURI of the package we are looking for.
     * @return an EPackage if some new mapping exists, null otherwise.
     */
    default EPackage getPackage(String nsURI) {
        return null;
    }
}
