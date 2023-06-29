/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.LabelPlacement;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Checkbox Description Style</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl#getColor <em>Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.CheckboxDescriptionStyleImpl#getLabelPlacement <em>Label
 * Placement</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CheckboxDescriptionStyleImpl extends WidgetDescriptionStyleImpl implements CheckboxDescriptionStyle {

    /**
     * The default value of the '{@link #getLabelPlacement() <em>Label Placement</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelPlacement()
     */
    protected static final LabelPlacement LABEL_PLACEMENT_EDEFAULT = LabelPlacement.END;
    /**
     * The cached value of the '{@link #getColor() <em>Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getColor()
     */
    protected UserColor color;
    /**
     * The cached value of the '{@link #getLabelPlacement() <em>Label Placement</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelPlacement()
     */
    protected LabelPlacement labelPlacement = LABEL_PLACEMENT_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CheckboxDescriptionStyleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return FormPackage.Literals.CHECKBOX_DESCRIPTION_STYLE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public UserColor getColor() {
        if (this.color != null && this.color.eIsProxy()) {
            InternalEObject oldColor = (InternalEObject) this.color;
            this.color = (UserColor) this.eResolveProxy(oldColor);
            if (this.color != oldColor) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR, oldColor, this.color));
            }
        }
        return this.color;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UserColor basicGetColor() {
        return this.color;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setColor(UserColor newColor) {
        UserColor oldColor = this.color;
        this.color = newColor;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR, oldColor, this.color));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelPlacement getLabelPlacement() {
        return this.labelPlacement;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelPlacement(LabelPlacement newLabelPlacement) {
        LabelPlacement oldLabelPlacement = this.labelPlacement;
        this.labelPlacement = newLabelPlacement == null ? LABEL_PLACEMENT_EDEFAULT : newLabelPlacement;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_PLACEMENT, oldLabelPlacement, this.labelPlacement));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR:
                if (resolve)
                    return this.getColor();
                return this.basicGetColor();
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_PLACEMENT:
                return this.getLabelPlacement();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR:
                this.setColor((UserColor) newValue);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_PLACEMENT:
                this.setLabelPlacement((LabelPlacement) newValue);
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
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR:
                this.setColor((UserColor) null);
                return;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_PLACEMENT:
                this.setLabelPlacement(LABEL_PLACEMENT_EDEFAULT);
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
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__COLOR:
                return this.color != null;
            case FormPackage.CHECKBOX_DESCRIPTION_STYLE__LABEL_PLACEMENT:
                return this.labelPlacement != LABEL_PLACEMENT_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        String result = super.toString() + " (labelPlacement: " +
                this.labelPlacement +
                ')';
        return result;
    }

} // CheckboxDescriptionStyleImpl
