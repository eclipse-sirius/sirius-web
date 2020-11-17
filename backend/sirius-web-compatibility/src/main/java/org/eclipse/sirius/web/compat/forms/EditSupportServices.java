/*******************************************************************************
 * Copyright (c) 2016, 2017, 2022 Obeo.
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
package org.eclipse.sirius.web.compat.forms;

import org.eclipse.eef.common.api.utils.Util;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.ext.emf.edit.EditingDomainServices;

/**
 * Contains the actual implementation of the EditSupport EOperations.
 *
 * @author pcdavid
 */
public class EditSupportServices {

    private static final String JAVA_LANG_STRING = "java.lang.String"; //$NON-NLS-1$

    private static final String INT = "int"; //$NON-NLS-1$

    private static final String JAVA_LANG_INTEGER = "java.lang.Integer"; //$NON-NLS-1$

    private static final String DOUBLE = "double"; //$NON-NLS-1$

    private static final String JAVA_LANG_DOUBLE = "java.lang.Double"; //$NON-NLS-1$

    private static final String CHAR = "char"; //$NON-NLS-1$

    private static final String JAVA_LANG_CHARACTER = "java.lang.Character"; //$NON-NLS-1$

    private static final String SHORT = "short"; //$NON-NLS-1$

    private static final String JAVA_LANG_SHORT = "java.lang.Short"; //$NON-NLS-1$

    private static final String LONG = "long"; //$NON-NLS-1$

    private static final String JAVA_LANG_LONG = "java.lang.Long"; //$NON-NLS-1$

    private static final String FLOAT = "float"; //$NON-NLS-1$

    private static final String JAVA_LANG_FLOAT = "java.lang.Float"; //$NON-NLS-1$

    private static final String JAVA_UTIL_DATE = "java.util.Date"; //$NON-NLS-1$

    private static final String BOOLEAN = "boolean"; //$NON-NLS-1$

    private static final String JAVA_LANG_BOOLEAN = "java.lang.Boolean"; //$NON-NLS-1$

    private final EditingDomainServices editServices = new EditingDomainServices();

    public Object getImage(EditSupportInput input) {
        EObject target = input.getTarget();
        if (target != null) {
            return this.editServices.getLabelProviderImage(target);
        } else {
            return null;
        }
    }

    public String getText(EditSupportInput input) {
        EObject target = input.getTarget();
        if (target != null) {
            return this.editServices.getLabelProviderText(target);
        } else {
            return String.valueOf(target);
        }
    }

    public Object getText(EditSupportInput input, EStructuralFeature feature) {
        EObject target = input.getTarget();
        if (target != null) {
            String result = this.editServices.getPropertyDescriptorDisplayName(target, feature.getName());
            if (Util.isBlank(result)) {
                result = this.editServices.getLabelProviderText(feature);
            }
            if (Util.isBlank(result)) {
                result = feature.getName();
            }
            return result;
        } else {
            return ""; //$NON-NLS-1$
        }
    }

    public String getTabName(EditSupportInput input) {
        return "Main"; //$NON-NLS-1$
    }

    public EList<Object> getChoiceOfValues(EditSupportInput input, EStructuralFeature feature) {
        BasicEList<Object> result = new BasicEList<>();
        EObject target = input.getTarget();
        if (target != null) {
            result.addAll(this.editServices.getPropertyDescriptorChoiceOfValues(target, feature.getName()));
        }
        return result;

    }

    public boolean isMultiline(EditSupportInput input, EStructuralFeature eStructuralFeature) {
        EObject target = input.getTarget();
        if (target != null) {
            return this.editServices.isPropertyDescriptorMultiLine(target, eStructuralFeature.getName());
        } else {
            return false;
        }
    }

    public String getDescription(EditSupportInput input, EStructuralFeature eStructuralFeature) {
        EObject target = input.getTarget();
        if (target != null) {
            return this.editServices.getPropertyDescriptorDescription(target, eStructuralFeature.getName());
        } else {
            return ""; //$NON-NLS-1$
        }
    }

    public boolean needsTextWidget(EditSupportInput input, EStructuralFeature eStructuralFeature) {
        boolean needsTextWidget = false;

        needsTextWidget = needsTextWidget || JAVA_LANG_STRING.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || INT.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || JAVA_LANG_INTEGER.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || DOUBLE.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || JAVA_LANG_DOUBLE.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || CHAR.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || JAVA_LANG_CHARACTER.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || SHORT.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || JAVA_LANG_SHORT.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || LONG.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || JAVA_LANG_LONG.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || FLOAT.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || JAVA_LANG_FLOAT.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsTextWidget = needsTextWidget || JAVA_UTIL_DATE.equals(eStructuralFeature.getEType().getInstanceTypeName());

        return needsTextWidget && !eStructuralFeature.isMany();
    }

    public boolean needsCheckboxWidget(EditSupportInput input, EStructuralFeature eStructuralFeature) {
        boolean needsCheckboxWidget = false;

        needsCheckboxWidget = needsCheckboxWidget || BOOLEAN.equals(eStructuralFeature.getEType().getInstanceTypeName());
        needsCheckboxWidget = needsCheckboxWidget || JAVA_LANG_BOOLEAN.equals(eStructuralFeature.getEType().getInstanceTypeName());

        return needsCheckboxWidget && !eStructuralFeature.isMany();
    }

    public EList<EStructuralFeature> getEStructuralFeatures(EditSupportInput input) {
        EList<EStructuralFeature> visibleFeatures = new BasicEList<>();
        for (EStructuralFeature eStructuralFeature : input.getTarget().eClass().getEAllStructuralFeatures()) {
            if (this.shouldAppearInPropertySheet(eStructuralFeature)) {
                visibleFeatures.add(eStructuralFeature);
            }
        }
        return visibleFeatures;
    }

    /**
     * Helper to check if a given feature should (by default) appear in the property sheet of an element.
     *
     * @param eStructuralFeature
     *            the feature to test.
     * @return <code>true</code> if the feature should appear in the property sheet by default.
     */
    public boolean shouldAppearInPropertySheet(EStructuralFeature eStructuralFeature) {
        return !eStructuralFeature.isDerived() && !eStructuralFeature.isTransient() && !(eStructuralFeature instanceof EReference && ((EReference) eStructuralFeature).isContainment());
    }

    public Object setValue(EditSupportInput input, EStructuralFeature feature, Object newValue) {
        EObject target = input.getTarget();
        if (target != null) {
            Object finalValue = newValue;
            if (feature instanceof EAttribute && newValue instanceof String) {
                finalValue = EcoreUtil.createFromString(((EAttribute) feature).getEAttributeType(), (String) newValue);
            }
            target.eSet(feature, finalValue);
        }
        return target;
    }

}
