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
package org.eclipse.sirius.components.view.tree.impl;

import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.TextStyleDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Item Label Fragment Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeItemLabelFragmentDescriptionImpl#getLabelExpression
 * <em>Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeItemLabelFragmentDescriptionImpl#getStyle
 * <em>Style</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TreeItemLabelFragmentDescriptionImpl extends TreeItemLabelElementDescriptionImpl implements TreeItemLabelFragmentDescription {

    /**
     * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelExpression()
     */
    protected static final String LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getLabelExpression()
     */
    protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     * @see #getStyle()
     */
    protected TextStyleDescription style;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TreeItemLabelFragmentDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TreePackage.Literals.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelExpression() {
        return this.labelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelExpression(String newLabelExpression) {
        String oldLabelExpression = this.labelExpression;
        this.labelExpression = newLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, this.labelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TextStyleDescription getStyle() {
        if (this.style != null && this.style.eIsProxy()) {
            InternalEObject oldStyle = (InternalEObject) this.style;
            this.style = (TextStyleDescription) this.eResolveProxy(oldStyle);
            if (this.style != oldStyle) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE, oldStyle, this.style));
            }
        }
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStyle(TextStyleDescription newStyle) {
        TextStyleDescription oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE, oldStyle, this.style));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TextStyleDescription basicGetStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__LABEL_EXPRESSION:
                return this.getLabelExpression();
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE:
                if (resolve)
                    return this.getStyle();
                return this.basicGetStyle();
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
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression((String) newValue);
                return;
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE:
                this.setStyle((TextStyleDescription) newValue);
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
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__LABEL_EXPRESSION:
                this.setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE:
                this.setStyle(null);
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
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__LABEL_EXPRESSION:
                return !Objects.equals(LABEL_EXPRESSION_EDEFAULT, this.labelExpression);
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE:
                return this.style != null;
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

        String result = super.toString() + " (labelExpression: " +
                this.labelExpression +
                ')';
        return result;
    }

} // TreeItemLabelFragmentDescriptionImpl
