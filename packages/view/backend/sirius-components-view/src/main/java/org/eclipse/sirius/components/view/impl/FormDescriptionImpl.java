/**
 * Copyright (c) 2021, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.ButtonDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.WidgetDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Form Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.FormDescriptionImpl#getWidgets <em>Widgets</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FormDescriptionImpl extends RepresentationDescriptionImpl implements FormDescription {
    /**
     * The cached value of the '{@link #getToolbarButtons() <em>Toolbar Buttons</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getToolbarButtons()
     * @generated
     * @ordered
     */
    protected EList<ButtonDescription> toolbarButtons;

    /**
     * The cached value of the '{@link #getWidgets() <em>Widgets</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getWidgets()
     * @generated
     * @ordered
     */
    protected EList<WidgetDescription> widgets;

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
        return ViewPackage.Literals.FORM_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ButtonDescription> getToolbarButtons() {
        if (this.toolbarButtons == null) {
            this.toolbarButtons = new EObjectContainmentEList<>(ButtonDescription.class, this, ViewPackage.FORM_DESCRIPTION__TOOLBAR_BUTTONS);
        }
        return this.toolbarButtons;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<WidgetDescription> getWidgets() {
        if (this.widgets == null) {
            this.widgets = new EObjectContainmentEList<>(WidgetDescription.class, this, ViewPackage.FORM_DESCRIPTION__WIDGETS);
        }
        return this.widgets;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ViewPackage.FORM_DESCRIPTION__TOOLBAR_BUTTONS:
            return ((InternalEList<?>) this.getToolbarButtons()).basicRemove(otherEnd, msgs);
        case ViewPackage.FORM_DESCRIPTION__WIDGETS:
            return ((InternalEList<?>) this.getWidgets()).basicRemove(otherEnd, msgs);
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
        case ViewPackage.FORM_DESCRIPTION__TOOLBAR_BUTTONS:
            return this.getToolbarButtons();
        case ViewPackage.FORM_DESCRIPTION__WIDGETS:
            return this.getWidgets();
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
        case ViewPackage.FORM_DESCRIPTION__TOOLBAR_BUTTONS:
            this.getToolbarButtons().clear();
            this.getToolbarButtons().addAll((Collection<? extends ButtonDescription>) newValue);
            return;
        case ViewPackage.FORM_DESCRIPTION__WIDGETS:
            this.getWidgets().clear();
            this.getWidgets().addAll((Collection<? extends WidgetDescription>) newValue);
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
        case ViewPackage.FORM_DESCRIPTION__TOOLBAR_BUTTONS:
            this.getToolbarButtons().clear();
            return;
        case ViewPackage.FORM_DESCRIPTION__WIDGETS:
            this.getWidgets().clear();
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
        case ViewPackage.FORM_DESCRIPTION__TOOLBAR_BUTTONS:
            return this.toolbarButtons != null && !this.toolbarButtons.isEmpty();
        case ViewPackage.FORM_DESCRIPTION__WIDGETS:
            return this.widgets != null && !this.widgets.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // FormDescriptionImpl
