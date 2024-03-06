/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.view.form.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.FormVariable;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FormDescriptionImpl#getPages <em>Pages</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.FormDescriptionImpl#getFormVariables <em>Form
 * Variables</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FormDescriptionImpl extends RepresentationDescriptionImpl implements FormDescription {
    /**
     * The cached value of the '{@link #getPages() <em>Pages</em>}' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPages()
     * @generated
     * @ordered
     */
    protected EList<PageDescription> pages;

    /**
     * The cached value of the '{@link #getFormVariables() <em>Form Variables</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getFormVariables()
     * @generated
     * @ordered
     */
    protected EList<FormVariable> formVariables;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FormDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.FORM_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<PageDescription> getPages() {
        if (this.pages == null) {
            this.pages = new EObjectContainmentEList<>(PageDescription.class, this, FormPackage.FORM_DESCRIPTION__PAGES);
        }
        return this.pages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<FormVariable> getFormVariables() {
        if (this.formVariables == null) {
            this.formVariables = new EObjectContainmentEList<>(FormVariable.class, this, FormPackage.FORM_DESCRIPTION__FORM_VARIABLES);
        }
        return this.formVariables;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case FormPackage.FORM_DESCRIPTION__PAGES:
                return ((InternalEList<?>) this.getPages()).basicRemove(otherEnd, msgs);
            case FormPackage.FORM_DESCRIPTION__FORM_VARIABLES:
                return ((InternalEList<?>) this.getFormVariables()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.FORM_DESCRIPTION__PAGES:
                return this.getPages();
            case FormPackage.FORM_DESCRIPTION__FORM_VARIABLES:
                return this.getFormVariables();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case FormPackage.FORM_DESCRIPTION__PAGES:
                this.getPages().clear();
                this.getPages().addAll((Collection<? extends PageDescription>) newValue);
                return;
            case FormPackage.FORM_DESCRIPTION__FORM_VARIABLES:
                this.getFormVariables().clear();
                this.getFormVariables().addAll((Collection<? extends FormVariable>) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case FormPackage.FORM_DESCRIPTION__PAGES:
                this.getPages().clear();
                return;
            case FormPackage.FORM_DESCRIPTION__FORM_VARIABLES:
                this.getFormVariables().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case FormPackage.FORM_DESCRIPTION__PAGES:
                return this.pages != null && !this.pages.isEmpty();
            case FormPackage.FORM_DESCRIPTION__FORM_VARIABLES:
                return this.formVariables != null && !this.formVariables.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // FormDescriptionImpl
