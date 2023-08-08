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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskDescription;
import org.eclipse.sirius.components.view.gantt.TaskStyleDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Task Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.TaskDescriptionImpl#getSemanticCandidatesExpression
 * <em>Semantic Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.TaskDescriptionImpl#getTaskInfoExpression <em>Task Info
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.TaskDescriptionImpl#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.TaskDescriptionImpl#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.gantt.impl.TaskDescriptionImpl#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TaskDescriptionImpl extends MinimalEObjectImpl.Container implements TaskDescription {
    /**
     * The default value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSemanticCandidatesExpression()
     * @generated
     * @ordered
     */
    protected static final String SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT = "aql:self";

    /**
     * The cached value of the '{@link #getSemanticCandidatesExpression() <em>Semantic Candidates Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSemanticCandidatesExpression()
     * @generated
     * @ordered
     */
    protected String semanticCandidatesExpression = SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTaskDetailExpression() <em>Task Detail Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTaskDetailExpression()
     * @generated
     * @ordered
     */
    protected static final String TASK_DETAIL_EXPRESSION_EDEFAULT = "aql:self";

    /**
     * The cached value of the '{@link #getTaskDetailExpression() <em>Task Detail Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTaskDetailExpression()
     * @generated
     * @ordered
     */
    protected String taskDetailExpression = TASK_DETAIL_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected TaskStyleDescription style;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalTaskStyle> conditionalStyles;

    /**
     * The cached value of the '{@link #getSubTaskElementDescriptions() <em>Sub Task Element Descriptions</em>}'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSubTaskElementDescriptions()
     * @generated
     * @ordered
     */
    protected EList<TaskDescription> subTaskElementDescriptions;

    /**
     * The cached value of the '{@link #getReusedTaskElementDescriptions() <em>Reused Task Element Descriptions</em>}'
     * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getReusedTaskElementDescriptions()
     * @generated
     * @ordered
     */
    protected EList<TaskDescription> reusedTaskElementDescriptions;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TaskDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return GanttPackage.Literals.TASK_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSemanticCandidatesExpression() {
        return this.semanticCandidatesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSemanticCandidatesExpression(String newSemanticCandidatesExpression) {
        String oldSemanticCandidatesExpression = this.semanticCandidatesExpression;
        this.semanticCandidatesExpression = newSemanticCandidatesExpression;
        if (this.eNotificationRequired())
            this.eNotify(
                    new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, oldSemanticCandidatesExpression, this.semanticCandidatesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTaskDetailExpression() {
        return this.taskDetailExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTaskDetailExpression(String newTaskDetailExpression) {
        String oldTaskDetailExpression = this.taskDetailExpression;
        this.taskDetailExpression = newTaskDetailExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__TASK_DETAIL_EXPRESSION, oldTaskDetailExpression, this.taskDetailExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TaskStyleDescription getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(TaskStyleDescription newStyle, NotificationChain msgs) {
        TaskStyleDescription oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__STYLE, oldStyle, newStyle);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStyle(TaskStyleDescription newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GanttPackage.TASK_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GanttPackage.TASK_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalTaskStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalTaskStyle.class, this, GanttPackage.TASK_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TaskDescription> getSubTaskElementDescriptions() {
        if (this.subTaskElementDescriptions == null) {
            this.subTaskElementDescriptions = new EObjectContainmentEList<>(TaskDescription.class, this, GanttPackage.TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS);
        }
        return this.subTaskElementDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TaskDescription> getReusedTaskElementDescriptions() {
        if (this.reusedTaskElementDescriptions == null) {
            this.reusedTaskElementDescriptions = new EObjectResolvingEList<>(TaskDescription.class, this, GanttPackage.TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS);
        }
        return this.reusedTaskElementDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case GanttPackage.TASK_DESCRIPTION__STYLE:
                return this.basicSetStyle(null, msgs);
            case GanttPackage.TASK_DESCRIPTION__CONDITIONAL_STYLES:
                return ((InternalEList<?>) this.getConditionalStyles()).basicRemove(otherEnd, msgs);
            case GanttPackage.TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS:
                return ((InternalEList<?>) this.getSubTaskElementDescriptions()).basicRemove(otherEnd, msgs);
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
            case GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return this.getSemanticCandidatesExpression();
            case GanttPackage.TASK_DESCRIPTION__TASK_DETAIL_EXPRESSION:
                return this.getTaskDetailExpression();
            case GanttPackage.TASK_DESCRIPTION__STYLE:
                return this.getStyle();
            case GanttPackage.TASK_DESCRIPTION__CONDITIONAL_STYLES:
                return this.getConditionalStyles();
            case GanttPackage.TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS:
                return this.getSubTaskElementDescriptions();
            case GanttPackage.TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS:
                return this.getReusedTaskElementDescriptions();
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
            case GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__TASK_DETAIL_EXPRESSION:
                this.setTaskDetailExpression((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__STYLE:
                this.setStyle((TaskStyleDescription) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                this.getConditionalStyles().addAll((Collection<? extends ConditionalTaskStyle>) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS:
                this.getSubTaskElementDescriptions().clear();
                this.getSubTaskElementDescriptions().addAll((Collection<? extends TaskDescription>) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS:
                this.getReusedTaskElementDescriptions().clear();
                this.getReusedTaskElementDescriptions().addAll((Collection<? extends TaskDescription>) newValue);
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
            case GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__TASK_DETAIL_EXPRESSION:
                this.setTaskDetailExpression(TASK_DETAIL_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__STYLE:
                this.setStyle((TaskStyleDescription) null);
                return;
            case GanttPackage.TASK_DESCRIPTION__CONDITIONAL_STYLES:
                this.getConditionalStyles().clear();
                return;
            case GanttPackage.TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS:
                this.getSubTaskElementDescriptions().clear();
                return;
            case GanttPackage.TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS:
                this.getReusedTaskElementDescriptions().clear();
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
            case GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.semanticCandidatesExpression);
            case GanttPackage.TASK_DESCRIPTION__TASK_DETAIL_EXPRESSION:
                return TASK_DETAIL_EXPRESSION_EDEFAULT == null ? this.taskDetailExpression != null : !TASK_DETAIL_EXPRESSION_EDEFAULT.equals(this.taskDetailExpression);
            case GanttPackage.TASK_DESCRIPTION__STYLE:
                return this.style != null;
            case GanttPackage.TASK_DESCRIPTION__CONDITIONAL_STYLES:
                return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
            case GanttPackage.TASK_DESCRIPTION__SUB_TASK_ELEMENT_DESCRIPTIONS:
                return this.subTaskElementDescriptions != null && !this.subTaskElementDescriptions.isEmpty();
            case GanttPackage.TASK_DESCRIPTION__REUSED_TASK_ELEMENT_DESCRIPTIONS:
                return this.reusedTaskElementDescriptions != null && !this.reusedTaskElementDescriptions.isEmpty();
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
        result.append(" (semanticCandidatesExpression: ");
        result.append(this.semanticCandidatesExpression);
        result.append(", taskDetailExpression: ");
        result.append(this.taskDetailExpression);
        result.append(')');
        return result.toString();
    }

} // TaskDescriptionImpl
