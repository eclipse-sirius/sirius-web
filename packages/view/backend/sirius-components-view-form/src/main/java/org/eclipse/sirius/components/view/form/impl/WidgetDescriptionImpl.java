/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Widget Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.impl.WidgetDescriptionImpl#getHelpExpression <em>Help
 * Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class WidgetDescriptionImpl extends FormElementDescriptionImpl implements WidgetDescription {
    /**
	 * The default value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getLabelExpression() <em>Label Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String labelExpression = LABEL_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getHelpExpression() <em>Help Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getHelpExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String HELP_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getHelpExpression() <em>Help Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getHelpExpression()
	 * @generated
	 * @ordered
	 */
    protected String helpExpression = HELP_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getDiagnosticsExpression() <em>Diagnostics Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDiagnosticsExpression()
     * @generated
     * @ordered
     */
    protected static final String DIAGNOSTICS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDiagnosticsExpression() <em>Diagnostics Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDiagnosticsExpression()
     * @generated
     * @ordered
     */
    protected String diagnosticsExpression = DIAGNOSTICS_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected WidgetDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FormPackage.Literals.WIDGET_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getLabelExpression() {
		return labelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setLabelExpression(String newLabelExpression) {
		String oldLabelExpression = labelExpression;
		labelExpression = newLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION, oldLabelExpression, labelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getHelpExpression() {
		return helpExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setHelpExpression(String newHelpExpression) {
		String oldHelpExpression = helpExpression;
		helpExpression = newHelpExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION, oldHelpExpression, helpExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDiagnosticsExpression() {
		return diagnosticsExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDiagnosticsExpression(String newDiagnosticsExpression) {
		String oldDiagnosticsExpression = diagnosticsExpression;
		diagnosticsExpression = newDiagnosticsExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FormPackage.WIDGET_DESCRIPTION__DIAGNOSTICS_EXPRESSION, oldDiagnosticsExpression, diagnosticsExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION:
				return getLabelExpression();
			case FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION:
				return getHelpExpression();
			case FormPackage.WIDGET_DESCRIPTION__DIAGNOSTICS_EXPRESSION:
				return getDiagnosticsExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression((String)newValue);
				return;
			case FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION:
				setHelpExpression((String)newValue);
				return;
			case FormPackage.WIDGET_DESCRIPTION__DIAGNOSTICS_EXPRESSION:
				setDiagnosticsExpression((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION:
				setLabelExpression(LABEL_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION:
				setHelpExpression(HELP_EXPRESSION_EDEFAULT);
				return;
			case FormPackage.WIDGET_DESCRIPTION__DIAGNOSTICS_EXPRESSION:
				setDiagnosticsExpression(DIAGNOSTICS_EXPRESSION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case FormPackage.WIDGET_DESCRIPTION__LABEL_EXPRESSION:
				return LABEL_EXPRESSION_EDEFAULT == null ? labelExpression != null : !LABEL_EXPRESSION_EDEFAULT.equals(labelExpression);
			case FormPackage.WIDGET_DESCRIPTION__HELP_EXPRESSION:
				return HELP_EXPRESSION_EDEFAULT == null ? helpExpression != null : !HELP_EXPRESSION_EDEFAULT.equals(helpExpression);
			case FormPackage.WIDGET_DESCRIPTION__DIAGNOSTICS_EXPRESSION:
				return DIAGNOSTICS_EXPRESSION_EDEFAULT == null ? diagnosticsExpression != null : !DIAGNOSTICS_EXPRESSION_EDEFAULT.equals(diagnosticsExpression);
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (labelExpression: ");
		result.append(labelExpression);
		result.append(", helpExpression: ");
		result.append(helpExpression);
		result.append(", diagnosticsExpression: ");
		result.append(diagnosticsExpression);
		result.append(')');
		return result.toString();
	}

} // WidgetDescriptionImpl
