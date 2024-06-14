/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.components.view.gantt.TaskDescription;

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
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDomainType()
     * @generated
     * @ordered
     */
    protected static final String DOMAIN_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getDomainType()
     * @generated
     * @ordered
     */
    protected String domainType = DOMAIN_TYPE_EDEFAULT;

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
     * The default value of the '{@link #getNameExpression() <em>Name Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getNameExpression()
     * @generated
     * @ordered
     */
    protected static final String NAME_EXPRESSION_EDEFAULT = "aql:self.name";

    /**
     * The cached value of the '{@link #getNameExpression() <em>Name Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getNameExpression()
     * @generated
     * @ordered
     */
    protected String nameExpression = NAME_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getDescriptionExpression() <em>Description Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescriptionExpression()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EXPRESSION_EDEFAULT = "aql:self.description";

    /**
     * The cached value of the '{@link #getDescriptionExpression() <em>Description Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescriptionExpression()
     * @generated
     * @ordered
     */
    protected String descriptionExpression = DESCRIPTION_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getStartTimeExpression() <em>Start Time Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getStartTimeExpression()
     * @generated
     * @ordered
     */
    protected static final String START_TIME_EXPRESSION_EDEFAULT = "aql:self.startTime";

    /**
     * The cached value of the '{@link #getStartTimeExpression() <em>Start Time Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getStartTimeExpression()
     * @generated
     * @ordered
     */
    protected String startTimeExpression = START_TIME_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getEndTimeExpression() <em>End Time Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEndTimeExpression()
     * @generated
     * @ordered
     */
    protected static final String END_TIME_EXPRESSION_EDEFAULT = "aql:self.endTime";

    /**
     * The cached value of the '{@link #getEndTimeExpression() <em>End Time Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEndTimeExpression()
     * @generated
     * @ordered
     */
    protected String endTimeExpression = END_TIME_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getProgressExpression() <em>Progress Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getProgressExpression()
     * @generated
     * @ordered
     */
    protected static final String PROGRESS_EXPRESSION_EDEFAULT = "aql:self.progress";

    /**
     * The cached value of the '{@link #getProgressExpression() <em>Progress Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getProgressExpression()
     * @generated
     * @ordered
     */
    protected String progressExpression = PROGRESS_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getComputeStartEndDynamicallyExpression() <em>Compute Start End Dynamically
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getComputeStartEndDynamicallyExpression()
     * @generated
     * @ordered
     */
    protected static final String COMPUTE_START_END_DYNAMICALLY_EXPRESSION_EDEFAULT = "aql:self.computeStartEndDynamically";

    /**
     * The cached value of the '{@link #getComputeStartEndDynamicallyExpression() <em>Compute Start End Dynamically
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getComputeStartEndDynamicallyExpression()
     * @generated
     * @ordered
     */
    protected String computeStartEndDynamicallyExpression = COMPUTE_START_END_DYNAMICALLY_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTaskDependenciesExpression() <em>Task Dependencies Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTaskDependenciesExpression()
     * @generated
     * @ordered
     */
    protected static final String TASK_DEPENDENCIES_EXPRESSION_EDEFAULT = "aql:self.dependencies";

    /**
     * The cached value of the '{@link #getTaskDependenciesExpression() <em>Task Dependencies Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTaskDependenciesExpression()
     * @generated
     * @ordered
     */
    protected String taskDependenciesExpression = TASK_DEPENDENCIES_EXPRESSION_EDEFAULT;

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
    public String getName() {
        return this.name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = this.name;
        this.name = newName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDomainType() {
        return this.domainType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDomainType(String newDomainType) {
        String oldDomainType = this.domainType;
        this.domainType = newDomainType;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__DOMAIN_TYPE, oldDomainType, this.domainType));
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
    public String getNameExpression() {
        return this.nameExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setNameExpression(String newNameExpression) {
        String oldNameExpression = this.nameExpression;
        this.nameExpression = newNameExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__NAME_EXPRESSION, oldNameExpression, this.nameExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDescriptionExpression() {
        return this.descriptionExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDescriptionExpression(String newDescriptionExpression) {
        String oldDescriptionExpression = this.descriptionExpression;
        this.descriptionExpression = newDescriptionExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__DESCRIPTION_EXPRESSION, oldDescriptionExpression, this.descriptionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getStartTimeExpression() {
        return this.startTimeExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setStartTimeExpression(String newStartTimeExpression) {
        String oldStartTimeExpression = this.startTimeExpression;
        this.startTimeExpression = newStartTimeExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__START_TIME_EXPRESSION, oldStartTimeExpression, this.startTimeExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getEndTimeExpression() {
        return this.endTimeExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEndTimeExpression(String newEndTimeExpression) {
        String oldEndTimeExpression = this.endTimeExpression;
        this.endTimeExpression = newEndTimeExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__END_TIME_EXPRESSION, oldEndTimeExpression, this.endTimeExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getProgressExpression() {
        return this.progressExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setProgressExpression(String newProgressExpression) {
        String oldProgressExpression = this.progressExpression;
        this.progressExpression = newProgressExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__PROGRESS_EXPRESSION, oldProgressExpression, this.progressExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getComputeStartEndDynamicallyExpression() {
        return this.computeStartEndDynamicallyExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setComputeStartEndDynamicallyExpression(String newComputeStartEndDynamicallyExpression) {
        String oldComputeStartEndDynamicallyExpression = this.computeStartEndDynamicallyExpression;
        this.computeStartEndDynamicallyExpression = newComputeStartEndDynamicallyExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION, oldComputeStartEndDynamicallyExpression,
                    this.computeStartEndDynamicallyExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTaskDependenciesExpression() {
        return this.taskDependenciesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTaskDependenciesExpression(String newTaskDependenciesExpression) {
        String oldTaskDependenciesExpression = this.taskDependenciesExpression;
        this.taskDependenciesExpression = newTaskDependenciesExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, GanttPackage.TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION, oldTaskDependenciesExpression, this.taskDependenciesExpression));
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
            case GanttPackage.TASK_DESCRIPTION__NAME:
                return this.getName();
            case GanttPackage.TASK_DESCRIPTION__DOMAIN_TYPE:
                return this.getDomainType();
            case GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return this.getSemanticCandidatesExpression();
            case GanttPackage.TASK_DESCRIPTION__NAME_EXPRESSION:
                return this.getNameExpression();
            case GanttPackage.TASK_DESCRIPTION__DESCRIPTION_EXPRESSION:
                return this.getDescriptionExpression();
            case GanttPackage.TASK_DESCRIPTION__START_TIME_EXPRESSION:
                return this.getStartTimeExpression();
            case GanttPackage.TASK_DESCRIPTION__END_TIME_EXPRESSION:
                return this.getEndTimeExpression();
            case GanttPackage.TASK_DESCRIPTION__PROGRESS_EXPRESSION:
                return this.getProgressExpression();
            case GanttPackage.TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION:
                return this.getComputeStartEndDynamicallyExpression();
            case GanttPackage.TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION:
                return this.getTaskDependenciesExpression();
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
            case GanttPackage.TASK_DESCRIPTION__NAME:
                this.setName((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__DOMAIN_TYPE:
                this.setDomainType((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__NAME_EXPRESSION:
                this.setNameExpression((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__DESCRIPTION_EXPRESSION:
                this.setDescriptionExpression((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__START_TIME_EXPRESSION:
                this.setStartTimeExpression((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__END_TIME_EXPRESSION:
                this.setEndTimeExpression((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__PROGRESS_EXPRESSION:
                this.setProgressExpression((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION:
                this.setComputeStartEndDynamicallyExpression((String) newValue);
                return;
            case GanttPackage.TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION:
                this.setTaskDependenciesExpression((String) newValue);
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
            case GanttPackage.TASK_DESCRIPTION__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__DOMAIN_TYPE:
                this.setDomainType(DOMAIN_TYPE_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.setSemanticCandidatesExpression(SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__NAME_EXPRESSION:
                this.setNameExpression(NAME_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__DESCRIPTION_EXPRESSION:
                this.setDescriptionExpression(DESCRIPTION_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__START_TIME_EXPRESSION:
                this.setStartTimeExpression(START_TIME_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__END_TIME_EXPRESSION:
                this.setEndTimeExpression(END_TIME_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__PROGRESS_EXPRESSION:
                this.setProgressExpression(PROGRESS_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION:
                this.setComputeStartEndDynamicallyExpression(COMPUTE_START_END_DYNAMICALLY_EXPRESSION_EDEFAULT);
                return;
            case GanttPackage.TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION:
                this.setTaskDependenciesExpression(TASK_DEPENDENCIES_EXPRESSION_EDEFAULT);
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
            case GanttPackage.TASK_DESCRIPTION__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case GanttPackage.TASK_DESCRIPTION__DOMAIN_TYPE:
                return DOMAIN_TYPE_EDEFAULT == null ? this.domainType != null : !DOMAIN_TYPE_EDEFAULT.equals(this.domainType);
            case GanttPackage.TASK_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                return SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT == null ? this.semanticCandidatesExpression != null : !SEMANTIC_CANDIDATES_EXPRESSION_EDEFAULT.equals(this.semanticCandidatesExpression);
            case GanttPackage.TASK_DESCRIPTION__NAME_EXPRESSION:
                return NAME_EXPRESSION_EDEFAULT == null ? this.nameExpression != null : !NAME_EXPRESSION_EDEFAULT.equals(this.nameExpression);
            case GanttPackage.TASK_DESCRIPTION__DESCRIPTION_EXPRESSION:
                return DESCRIPTION_EXPRESSION_EDEFAULT == null ? this.descriptionExpression != null : !DESCRIPTION_EXPRESSION_EDEFAULT.equals(this.descriptionExpression);
            case GanttPackage.TASK_DESCRIPTION__START_TIME_EXPRESSION:
                return START_TIME_EXPRESSION_EDEFAULT == null ? this.startTimeExpression != null : !START_TIME_EXPRESSION_EDEFAULT.equals(this.startTimeExpression);
            case GanttPackage.TASK_DESCRIPTION__END_TIME_EXPRESSION:
                return END_TIME_EXPRESSION_EDEFAULT == null ? this.endTimeExpression != null : !END_TIME_EXPRESSION_EDEFAULT.equals(this.endTimeExpression);
            case GanttPackage.TASK_DESCRIPTION__PROGRESS_EXPRESSION:
                return PROGRESS_EXPRESSION_EDEFAULT == null ? this.progressExpression != null : !PROGRESS_EXPRESSION_EDEFAULT.equals(this.progressExpression);
            case GanttPackage.TASK_DESCRIPTION__COMPUTE_START_END_DYNAMICALLY_EXPRESSION:
                return COMPUTE_START_END_DYNAMICALLY_EXPRESSION_EDEFAULT == null ? this.computeStartEndDynamicallyExpression != null
                        : !COMPUTE_START_END_DYNAMICALLY_EXPRESSION_EDEFAULT.equals(this.computeStartEndDynamicallyExpression);
            case GanttPackage.TASK_DESCRIPTION__TASK_DEPENDENCIES_EXPRESSION:
                return TASK_DEPENDENCIES_EXPRESSION_EDEFAULT == null ? this.taskDependenciesExpression != null : !TASK_DEPENDENCIES_EXPRESSION_EDEFAULT.equals(this.taskDependenciesExpression);
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
        result.append(" (name: ");
        result.append(this.name);
        result.append(", domainType: ");
        result.append(this.domainType);
        result.append(", semanticCandidatesExpression: ");
        result.append(this.semanticCandidatesExpression);
        result.append(", nameExpression: ");
        result.append(this.nameExpression);
        result.append(", descriptionExpression: ");
        result.append(this.descriptionExpression);
        result.append(", startTimeExpression: ");
        result.append(this.startTimeExpression);
        result.append(", endTimeExpression: ");
        result.append(this.endTimeExpression);
        result.append(", progressExpression: ");
        result.append(this.progressExpression);
        result.append(", computeStartEndDynamicallyExpression: ");
        result.append(this.computeStartEndDynamicallyExpression);
        result.append(", taskDependenciesExpression: ");
        result.append(this.taskDependenciesExpression);
        result.append(')');
        return result.toString();
    }

} // TaskDescriptionImpl
