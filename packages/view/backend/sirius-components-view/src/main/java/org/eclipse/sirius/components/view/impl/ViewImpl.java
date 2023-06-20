/**
 * Copyright (c) 2021, 2023 Obeo.
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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>View</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.ViewImpl#getDescriptions <em>Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.ViewImpl#getColorPalettes <em>Color Palettes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ViewImpl extends MinimalEObjectImpl.Container implements View {
    /**
     * The cached value of the '{@link #getDescriptions() <em>Descriptions</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescriptions()
     * @generated
     * @ordered
     */
    protected EList<RepresentationDescription> descriptions;

    /**
     * The cached value of the '{@link #getColorPalettes() <em>Color Palettes</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getColorPalettes()
     * @generated
     * @ordered
     */
    protected EList<ColorPalette> colorPalettes;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ViewImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.VIEW;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<RepresentationDescription> getDescriptions() {
        if (this.descriptions == null) {
            this.descriptions = new EObjectContainmentEList<>(RepresentationDescription.class, this, ViewPackage.VIEW__DESCRIPTIONS);
        }
        return this.descriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ColorPalette> getColorPalettes() {
        if (this.colorPalettes == null) {
            this.colorPalettes = new EObjectContainmentEList<>(ColorPalette.class, this, ViewPackage.VIEW__COLOR_PALETTES);
        }
        return this.colorPalettes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.VIEW__DESCRIPTIONS:
                return ((InternalEList<?>) this.getDescriptions()).basicRemove(otherEnd, msgs);
            case ViewPackage.VIEW__COLOR_PALETTES:
                return ((InternalEList<?>) this.getColorPalettes()).basicRemove(otherEnd, msgs);
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
            case ViewPackage.VIEW__DESCRIPTIONS:
                return this.getDescriptions();
            case ViewPackage.VIEW__COLOR_PALETTES:
                return this.getColorPalettes();
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
            case ViewPackage.VIEW__DESCRIPTIONS:
                this.getDescriptions().clear();
                this.getDescriptions().addAll((Collection<? extends RepresentationDescription>) newValue);
                return;
            case ViewPackage.VIEW__COLOR_PALETTES:
                this.getColorPalettes().clear();
                this.getColorPalettes().addAll((Collection<? extends ColorPalette>) newValue);
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
            case ViewPackage.VIEW__DESCRIPTIONS:
                this.getDescriptions().clear();
                return;
            case ViewPackage.VIEW__COLOR_PALETTES:
                this.getColorPalettes().clear();
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
            case ViewPackage.VIEW__DESCRIPTIONS:
                return this.descriptions != null && !this.descriptions.isEmpty();
            case ViewPackage.VIEW__COLOR_PALETTES:
                return this.colorPalettes != null && !this.colorPalettes.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ViewImpl
