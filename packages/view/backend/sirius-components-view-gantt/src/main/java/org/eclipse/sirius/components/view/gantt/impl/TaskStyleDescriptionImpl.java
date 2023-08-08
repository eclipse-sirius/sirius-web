/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.gantt.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskStyleDescription;
import org.eclipse.sirius.components.view.impl.LabelStyleImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Task Style Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.TaskStyleDescriptionImpl#getLabelColorExpression <em>Label
 * Color Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.TaskStyleDescriptionImpl#getBackgroundColorExpression
 * <em>Background Color Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.TaskStyleDescriptionImpl#getProgressColorExpression
 * <em>Progress Color Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TaskStyleDescriptionImpl extends LabelStyleImpl implements TaskStyleDescription {
    /**
     * The default value of the '{@link #getLabelColorExpression() <em>Label Color Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getLabelColorExpression()
     * @generated
     * @ordered
     */
    protected static final String LABEL_COLOR_EXPRESSION_EDEFAULT = "aql:self";

    /**
     * The cached value of the '{@link #getLabelColorExpression() <em>Label Color Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getLabelColorExpression()
     * @generated
     * @ordered
     */
    protected String labelColorExpression = LABEL_COLOR_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getBackgroundColorExpression() <em>Background Color Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBackgroundColorExpression()
     * @generated
     * @ordered
     */
    protected static final String BACKGROUND_COLOR_EXPRESSION_EDEFAULT = "aql:self";

    /**
     * The cached value of the '{@link #getBackgroundColorExpression() <em>Background Color Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBackgroundColorExpression()
     * @generated
     * @ordered
     */
    protected String backgroundColorExpression = BACKGROUND_COLOR_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getProgressColorExpression() <em>Progress Color Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getProgressColorExpression()
     * @generated
     * @ordered
     */
    protected static final String PROGRESS_COLOR_EXPRESSION_EDEFAULT = "aql:self";

    /**
     * The cached value of the '{@link #getProgressColorExpression() <em>Progress Color Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getProgressColorExpression()
     * @generated
     * @ordered
     */
    protected String progressColorExpression = PROGRESS_COLOR_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TaskStyleDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return GanttPackage.Literals.TASK_STYLE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLabelColorExpression() {
        return this.labelColorExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelColorExpression(String newLabelColorExpression) {
        String oldLabelColorExpression = this.labelColorExpression;
        this.labelColorExpression = newLabelColorExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_STYLE_DESCRIPTION__LABEL_COLOR_EXPRESSION, oldLabelColorExpression, this.labelColorExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getBackgroundColorExpression() {
        return this.backgroundColorExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBackgroundColorExpression(String newBackgroundColorExpression) {
        String oldBackgroundColorExpression = this.backgroundColorExpression;
        this.backgroundColorExpression = newBackgroundColorExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION, oldBackgroundColorExpression, this.backgroundColorExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getProgressColorExpression() {
        return this.progressColorExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setProgressColorExpression(String newProgressColorExpression) {
        String oldProgressColorExpression = this.progressColorExpression;
        this.progressColorExpression = newProgressColorExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_STYLE_DESCRIPTION__PROGRESS_COLOR_EXPRESSION, oldProgressColorExpression, this.progressColorExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case GanttPackage.TASK_STYLE_DESCRIPTION__LABEL_COLOR_EXPRESSION:
                return this.getLabelColorExpression();
            case GanttPackage.TASK_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION:
                return this.getBackgroundColorExpression();
            case GanttPackage.TASK_STYLE_DESCRIPTION__PROGRESS_COLOR_EXPRESSION:
                return this.getProgressColorExpression();
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
            case GanttPackage.TASK_STYLE_DESCRIPTION__LABEL_COLOR_EXPRESSION:
                this.setLabelColorExpression((String) newValue);
                return;
            case GanttPackage.TASK_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION:
                this.setBackgroundColorExpression((String) newValue);
                return;
            case GanttPackage.TASK_STYLE_DESCRIPTION__PROGRESS_COLOR_EXPRESSION:
                this.setProgressColorExpression((String) newValue);
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
            case GanttPackage.TASK_STYLE_DESCRIPTION__LABEL_COLOR_EXPRESSION:
                this.setLabelColorExpression(LABEL_COLOR_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION:
                this.setBackgroundColorExpression(BACKGROUND_COLOR_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_STYLE_DESCRIPTION__PROGRESS_COLOR_EXPRESSION:
                this.setProgressColorExpression(PROGRESS_COLOR_EXPRESSION_EDEFAULT);
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
            case GanttPackage.TASK_STYLE_DESCRIPTION__LABEL_COLOR_EXPRESSION:
                return LABEL_COLOR_EXPRESSION_EDEFAULT == null ? this.labelColorExpression != null : !LABEL_COLOR_EXPRESSION_EDEFAULT.equals(this.labelColorExpression);
            case GanttPackage.TASK_STYLE_DESCRIPTION__BACKGROUND_COLOR_EXPRESSION:
                return BACKGROUND_COLOR_EXPRESSION_EDEFAULT == null ? this.backgroundColorExpression != null : !BACKGROUND_COLOR_EXPRESSION_EDEFAULT.equals(this.backgroundColorExpression);
            case GanttPackage.TASK_STYLE_DESCRIPTION__PROGRESS_COLOR_EXPRESSION:
                return PROGRESS_COLOR_EXPRESSION_EDEFAULT == null ? this.progressColorExpression != null : !PROGRESS_COLOR_EXPRESSION_EDEFAULT.equals(this.progressColorExpression);
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (labelColorExpression: ");
        result.append(this.labelColorExpression);
        result.append(", backgroundColorExpression: ");
        result.append(this.backgroundColorExpression);
        result.append(", progressColorExpression: ");
        result.append(this.progressColorExpression);
        result.append(')');
        return result.toString();
    }

} // TaskStyleDescriptionImpl
