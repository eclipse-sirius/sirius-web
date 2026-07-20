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
package org.eclipse.sirius.components.view.diagram.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Selection Dialog Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionDialogTreeDescription <em>Selection Dialog Tree Description</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#isMultiple <em>Multiple</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#isOptional <em>Optional</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getDefaultTitleExpression <em>Default Title Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionTitleExpression <em>No Selection Title Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getWithSelectionTitleExpression <em>With Selection Title Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getDescriptionExpression <em>Description Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionActionLabelExpression <em>No Selection Action Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionActionDescriptionExpression <em>No Selection Action Description Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getWithSelectionActionLabelExpression <em>With Selection Action Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getWithSelectionActionDescriptionExpression <em>With Selection Action Description Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionActionStatusMessageExpression <em>No Selection Action Status Message Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionRequiredWithoutSelectionStatusMessageExpression <em>Selection Required Without Selection Status Message Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionRequiredWithSelectionStatusMessageExpression <em>Selection Required With Selection Status Message Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionConfirmButtonLabelExpression <em>No Selection Confirm Button Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression <em>Selection Required Without Selection Confirm Button Label Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionRequiredWithSelectionConfirmButtonLabelExpression <em>Selection Required With Selection Confirm Button Label Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SelectionDialogDescriptionImpl extends DialogDescriptionImpl implements SelectionDialogDescription {
    /**
	 * The cached value of the '{@link #getSelectionDialogTreeDescription() <em>Selection Dialog Tree Description</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSelectionDialogTreeDescription()
	 * @generated
	 * @ordered
	 */
    protected SelectionDialogTreeDescription selectionDialogTreeDescription;

    /**
	 * The default value of the '{@link #isMultiple() <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isMultiple()
	 * @generated
	 * @ordered
	 */
    protected static final boolean MULTIPLE_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isMultiple() <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isMultiple()
	 * @generated
	 * @ordered
	 */
    protected boolean multiple = MULTIPLE_EDEFAULT;

    /**
	 * The default value of the '{@link #isOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isOptional()
	 * @generated
	 * @ordered
	 */
    protected static final boolean OPTIONAL_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #isOptional()
	 * @generated
	 * @ordered
	 */
    protected boolean optional = OPTIONAL_EDEFAULT;

    /**
     * The default value of the '{@link #getDefaultTitleExpression() <em>Default Title Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDefaultTitleExpression()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_TITLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultTitleExpression() <em>Default Title Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDefaultTitleExpression()
     * @generated
     * @ordered
     */
    protected String defaultTitleExpression = DEFAULT_TITLE_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getNoSelectionTitleExpression() <em>No Selection Title Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionTitleExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String NO_SELECTION_TITLE_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getNoSelectionTitleExpression() <em>No Selection Title Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionTitleExpression()
	 * @generated
	 * @ordered
	 */
    protected String noSelectionTitleExpression = NO_SELECTION_TITLE_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getWithSelectionTitleExpression() <em>With Selection Title Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getWithSelectionTitleExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getWithSelectionTitleExpression() <em>With Selection Title Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getWithSelectionTitleExpression()
	 * @generated
	 * @ordered
	 */
    protected String withSelectionTitleExpression = WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getDescriptionExpression() <em>Description Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescriptionExpression()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EXPRESSION_EDEFAULT = null;

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
	 * The default value of the '{@link #getNoSelectionActionLabelExpression() <em>No Selection Action Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionActionLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getNoSelectionActionLabelExpression() <em>No Selection Action Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionActionLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String noSelectionActionLabelExpression = NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getNoSelectionActionDescriptionExpression() <em>No Selection Action Description Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionActionDescriptionExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getNoSelectionActionDescriptionExpression() <em>No Selection Action Description Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionActionDescriptionExpression()
	 * @generated
	 * @ordered
	 */
    protected String noSelectionActionDescriptionExpression = NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getWithSelectionActionLabelExpression() <em>With Selection Action Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getWithSelectionActionLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getWithSelectionActionLabelExpression() <em>With Selection Action Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getWithSelectionActionLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String withSelectionActionLabelExpression = WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getWithSelectionActionDescriptionExpression() <em>With Selection Action Description Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getWithSelectionActionDescriptionExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getWithSelectionActionDescriptionExpression() <em>With Selection Action Description Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getWithSelectionActionDescriptionExpression()
	 * @generated
	 * @ordered
	 */
    protected String withSelectionActionDescriptionExpression = WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getNoSelectionActionStatusMessageExpression() <em>No Selection Action Status Message Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionActionStatusMessageExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getNoSelectionActionStatusMessageExpression() <em>No Selection Action Status Message Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionActionStatusMessageExpression()
	 * @generated
	 * @ordered
	 */
    protected String noSelectionActionStatusMessageExpression = NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getSelectionRequiredWithoutSelectionStatusMessageExpression() <em>Selection
     * Required Without Selection Status Message Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getSelectionRequiredWithoutSelectionStatusMessageExpression()
     * @generated
     * @ordered
     */
    protected static final String SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSelectionRequiredWithoutSelectionStatusMessageExpression() <em>Selection
     * Required Without Selection Status Message Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getSelectionRequiredWithoutSelectionStatusMessageExpression()
     * @generated
     * @ordered
     */
    protected String selectionRequiredWithoutSelectionStatusMessageExpression = SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getSelectionRequiredWithSelectionStatusMessageExpression() <em>Selection Required With Selection Status Message Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSelectionRequiredWithSelectionStatusMessageExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getSelectionRequiredWithSelectionStatusMessageExpression() <em>Selection Required With Selection Status Message Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSelectionRequiredWithSelectionStatusMessageExpression()
	 * @generated
	 * @ordered
	 */
    protected String selectionRequiredWithSelectionStatusMessageExpression = SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getNoSelectionConfirmButtonLabelExpression() <em>No Selection Confirm Button Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionConfirmButtonLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getNoSelectionConfirmButtonLabelExpression() <em>No Selection Confirm Button Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNoSelectionConfirmButtonLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String noSelectionConfirmButtonLabelExpression = NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression() <em>Selection Required Without Selection Confirm Button Label Expression</em>}' attribute.
	 * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
	 * @see #getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression() <em>Selection Required Without Selection Confirm Button Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String selectionRequiredWithoutSelectionConfirmButtonLabelExpression = SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT;

    /**
	 * The default value of the '{@link #getSelectionRequiredWithSelectionConfirmButtonLabelExpression() <em>Selection Required With Selection Confirm Button Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getSelectionRequiredWithSelectionConfirmButtonLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected static final String SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getSelectionRequiredWithSelectionConfirmButtonLabelExpression() <em>Selection Required With Selection Confirm Button Label Expression</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getSelectionRequiredWithSelectionConfirmButtonLabelExpression()
	 * @generated
	 * @ordered
	 */
    protected String selectionRequiredWithSelectionConfirmButtonLabelExpression = SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected SelectionDialogDescriptionImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public SelectionDialogTreeDescription getSelectionDialogTreeDescription() {
		return selectionDialogTreeDescription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetSelectionDialogTreeDescription(SelectionDialogTreeDescription newSelectionDialogTreeDescription, NotificationChain msgs) {
		SelectionDialogTreeDescription oldSelectionDialogTreeDescription = selectionDialogTreeDescription;
		selectionDialogTreeDescription = newSelectionDialogTreeDescription;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION, oldSelectionDialogTreeDescription, newSelectionDialogTreeDescription);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSelectionDialogTreeDescription(SelectionDialogTreeDescription newSelectionDialogTreeDescription) {
		if (newSelectionDialogTreeDescription != selectionDialogTreeDescription)
		{
			NotificationChain msgs = null;
			if (selectionDialogTreeDescription != null)
				msgs = ((InternalEObject)selectionDialogTreeDescription).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION, null, msgs);
			if (newSelectionDialogTreeDescription != null)
				msgs = ((InternalEObject)newSelectionDialogTreeDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION, null, msgs);
			msgs = basicSetSelectionDialogTreeDescription(newSelectionDialogTreeDescription, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION, newSelectionDialogTreeDescription, newSelectionDialogTreeDescription));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isMultiple() {
		return multiple;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setMultiple(boolean newMultiple) {
		boolean oldMultiple = multiple;
		multiple = newMultiple;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE, oldMultiple, multiple));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isOptional() {
		return optional;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setOptional(boolean newOptional) {
		boolean oldOptional = optional;
		optional = newOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL, oldOptional, optional));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDefaultTitleExpression() {
		return defaultTitleExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDefaultTitleExpression(String newDefaultTitleExpression) {
		String oldDefaultTitleExpression = defaultTitleExpression;
		defaultTitleExpression = newDefaultTitleExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION, oldDefaultTitleExpression, defaultTitleExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getNoSelectionTitleExpression() {
		return noSelectionTitleExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setNoSelectionTitleExpression(String newNoSelectionTitleExpression) {
		String oldNoSelectionTitleExpression = noSelectionTitleExpression;
		noSelectionTitleExpression = newNoSelectionTitleExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION, oldNoSelectionTitleExpression, noSelectionTitleExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getWithSelectionTitleExpression() {
		return withSelectionTitleExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setWithSelectionTitleExpression(String newWithSelectionTitleExpression) {
		String oldWithSelectionTitleExpression = withSelectionTitleExpression;
		withSelectionTitleExpression = newWithSelectionTitleExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION, oldWithSelectionTitleExpression, withSelectionTitleExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getDescriptionExpression() {
		return descriptionExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setDescriptionExpression(String newDescriptionExpression) {
		String oldDescriptionExpression = descriptionExpression;
		descriptionExpression = newDescriptionExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION, oldDescriptionExpression, descriptionExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getNoSelectionActionLabelExpression() {
		return noSelectionActionLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setNoSelectionActionLabelExpression(String newNoSelectionActionLabelExpression) {
		String oldNoSelectionActionLabelExpression = noSelectionActionLabelExpression;
		noSelectionActionLabelExpression = newNoSelectionActionLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION, oldNoSelectionActionLabelExpression, noSelectionActionLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getNoSelectionActionDescriptionExpression() {
		return noSelectionActionDescriptionExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setNoSelectionActionDescriptionExpression(String newNoSelectionActionDescriptionExpression) {
		String oldNoSelectionActionDescriptionExpression = noSelectionActionDescriptionExpression;
		noSelectionActionDescriptionExpression = newNoSelectionActionDescriptionExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION, oldNoSelectionActionDescriptionExpression, noSelectionActionDescriptionExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getWithSelectionActionLabelExpression() {
		return withSelectionActionLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setWithSelectionActionLabelExpression(String newWithSelectionActionLabelExpression) {
		String oldWithSelectionActionLabelExpression = withSelectionActionLabelExpression;
		withSelectionActionLabelExpression = newWithSelectionActionLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION, oldWithSelectionActionLabelExpression, withSelectionActionLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getWithSelectionActionDescriptionExpression() {
		return withSelectionActionDescriptionExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setWithSelectionActionDescriptionExpression(String newWithSelectionActionDescriptionExpression) {
		String oldWithSelectionActionDescriptionExpression = withSelectionActionDescriptionExpression;
		withSelectionActionDescriptionExpression = newWithSelectionActionDescriptionExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION, oldWithSelectionActionDescriptionExpression, withSelectionActionDescriptionExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getNoSelectionActionStatusMessageExpression() {
		return noSelectionActionStatusMessageExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setNoSelectionActionStatusMessageExpression(String newNoSelectionActionStatusMessageExpression) {
		String oldNoSelectionActionStatusMessageExpression = noSelectionActionStatusMessageExpression;
		noSelectionActionStatusMessageExpression = newNoSelectionActionStatusMessageExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION, oldNoSelectionActionStatusMessageExpression, noSelectionActionStatusMessageExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getSelectionRequiredWithoutSelectionStatusMessageExpression() {
		return selectionRequiredWithoutSelectionStatusMessageExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSelectionRequiredWithoutSelectionStatusMessageExpression(String newSelectionRequiredWithoutSelectionStatusMessageExpression) {
		String oldSelectionRequiredWithoutSelectionStatusMessageExpression = selectionRequiredWithoutSelectionStatusMessageExpression;
		selectionRequiredWithoutSelectionStatusMessageExpression = newSelectionRequiredWithoutSelectionStatusMessageExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION, oldSelectionRequiredWithoutSelectionStatusMessageExpression, selectionRequiredWithoutSelectionStatusMessageExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getSelectionRequiredWithSelectionStatusMessageExpression() {
		return selectionRequiredWithSelectionStatusMessageExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSelectionRequiredWithSelectionStatusMessageExpression(String newSelectionRequiredWithSelectionStatusMessageExpression) {
		String oldSelectionRequiredWithSelectionStatusMessageExpression = selectionRequiredWithSelectionStatusMessageExpression;
		selectionRequiredWithSelectionStatusMessageExpression = newSelectionRequiredWithSelectionStatusMessageExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION, oldSelectionRequiredWithSelectionStatusMessageExpression, selectionRequiredWithSelectionStatusMessageExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getNoSelectionConfirmButtonLabelExpression() {
		return noSelectionConfirmButtonLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setNoSelectionConfirmButtonLabelExpression(String newNoSelectionConfirmButtonLabelExpression) {
		String oldNoSelectionConfirmButtonLabelExpression = noSelectionConfirmButtonLabelExpression;
		noSelectionConfirmButtonLabelExpression = newNoSelectionConfirmButtonLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION, oldNoSelectionConfirmButtonLabelExpression, noSelectionConfirmButtonLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression() {
		return selectionRequiredWithoutSelectionConfirmButtonLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSelectionRequiredWithoutSelectionConfirmButtonLabelExpression(String newSelectionRequiredWithoutSelectionConfirmButtonLabelExpression) {
		String oldSelectionRequiredWithoutSelectionConfirmButtonLabelExpression = selectionRequiredWithoutSelectionConfirmButtonLabelExpression;
		selectionRequiredWithoutSelectionConfirmButtonLabelExpression = newSelectionRequiredWithoutSelectionConfirmButtonLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION, oldSelectionRequiredWithoutSelectionConfirmButtonLabelExpression, selectionRequiredWithoutSelectionConfirmButtonLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getSelectionRequiredWithSelectionConfirmButtonLabelExpression() {
		return selectionRequiredWithSelectionConfirmButtonLabelExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setSelectionRequiredWithSelectionConfirmButtonLabelExpression(String newSelectionRequiredWithSelectionConfirmButtonLabelExpression) {
		String oldSelectionRequiredWithSelectionConfirmButtonLabelExpression = selectionRequiredWithSelectionConfirmButtonLabelExpression;
		selectionRequiredWithSelectionConfirmButtonLabelExpression = newSelectionRequiredWithSelectionConfirmButtonLabelExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION, oldSelectionRequiredWithSelectionConfirmButtonLabelExpression, selectionRequiredWithSelectionConfirmButtonLabelExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
				return basicSetSelectionDialogTreeDescription(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
				return getSelectionDialogTreeDescription();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE:
				return isMultiple();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL:
				return isOptional();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION:
				return getDefaultTitleExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION:
				return getNoSelectionTitleExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION:
				return getWithSelectionTitleExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
				return getDescriptionExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION:
				return getNoSelectionActionLabelExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
				return getNoSelectionActionDescriptionExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION:
				return getWithSelectionActionLabelExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
				return getWithSelectionActionDescriptionExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION:
				return getNoSelectionActionStatusMessageExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION:
				return getSelectionRequiredWithoutSelectionStatusMessageExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION:
				return getSelectionRequiredWithSelectionStatusMessageExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				return getNoSelectionConfirmButtonLabelExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				return getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression();
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				return getSelectionRequiredWithSelectionConfirmButtonLabelExpression();
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
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
				setSelectionDialogTreeDescription((SelectionDialogTreeDescription)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE:
				setMultiple((Boolean)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL:
				setOptional((Boolean)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION:
				setDefaultTitleExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION:
				setNoSelectionTitleExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION:
				setWithSelectionTitleExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
				setDescriptionExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION:
				setNoSelectionActionLabelExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
				setNoSelectionActionDescriptionExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION:
				setWithSelectionActionLabelExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
				setWithSelectionActionDescriptionExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION:
				setNoSelectionActionStatusMessageExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION:
				setSelectionRequiredWithoutSelectionStatusMessageExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION:
				setSelectionRequiredWithSelectionStatusMessageExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				setNoSelectionConfirmButtonLabelExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				setSelectionRequiredWithoutSelectionConfirmButtonLabelExpression((String)newValue);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				setSelectionRequiredWithSelectionConfirmButtonLabelExpression((String)newValue);
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
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
				setSelectionDialogTreeDescription((SelectionDialogTreeDescription)null);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE:
				setMultiple(MULTIPLE_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL:
				setOptional(OPTIONAL_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION:
				setDefaultTitleExpression(DEFAULT_TITLE_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION:
				setNoSelectionTitleExpression(NO_SELECTION_TITLE_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION:
				setWithSelectionTitleExpression(WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
				setDescriptionExpression(DESCRIPTION_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION:
				setNoSelectionActionLabelExpression(NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
				setNoSelectionActionDescriptionExpression(NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION:
				setWithSelectionActionLabelExpression(WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
				setWithSelectionActionDescriptionExpression(WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION:
				setNoSelectionActionStatusMessageExpression(NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION:
				setSelectionRequiredWithoutSelectionStatusMessageExpression(SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION:
				setSelectionRequiredWithSelectionStatusMessageExpression(SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				setNoSelectionConfirmButtonLabelExpression(NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				setSelectionRequiredWithoutSelectionConfirmButtonLabelExpression(SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT);
				return;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				setSelectionRequiredWithSelectionConfirmButtonLabelExpression(SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT);
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
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
				return selectionDialogTreeDescription != null;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE:
				return multiple != MULTIPLE_EDEFAULT;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL:
				return optional != OPTIONAL_EDEFAULT;
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION:
				return DEFAULT_TITLE_EXPRESSION_EDEFAULT == null ? defaultTitleExpression != null : !DEFAULT_TITLE_EXPRESSION_EDEFAULT.equals(defaultTitleExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION:
				return NO_SELECTION_TITLE_EXPRESSION_EDEFAULT == null ? noSelectionTitleExpression != null : !NO_SELECTION_TITLE_EXPRESSION_EDEFAULT.equals(noSelectionTitleExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION:
				return WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT == null ? withSelectionTitleExpression != null : !WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT.equals(withSelectionTitleExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
				return DESCRIPTION_EXPRESSION_EDEFAULT == null ? descriptionExpression != null : !DESCRIPTION_EXPRESSION_EDEFAULT.equals(descriptionExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION:
				return NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT == null ? noSelectionActionLabelExpression != null : !NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT.equals(noSelectionActionLabelExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
				return NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT == null ? noSelectionActionDescriptionExpression != null : !NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT.equals(noSelectionActionDescriptionExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION:
				return WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT == null ? withSelectionActionLabelExpression != null : !WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT.equals(withSelectionActionLabelExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
				return WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT == null ? withSelectionActionDescriptionExpression != null : !WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT.equals(withSelectionActionDescriptionExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION:
				return NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT == null ? noSelectionActionStatusMessageExpression != null : !NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT.equals(noSelectionActionStatusMessageExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION:
				return SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT == null ? selectionRequiredWithoutSelectionStatusMessageExpression != null : !SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT.equals(selectionRequiredWithoutSelectionStatusMessageExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION:
				return SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT == null ? selectionRequiredWithSelectionStatusMessageExpression != null : !SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT.equals(selectionRequiredWithSelectionStatusMessageExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				return NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT == null ? noSelectionConfirmButtonLabelExpression != null : !NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT.equals(noSelectionConfirmButtonLabelExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				return SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT == null ? selectionRequiredWithoutSelectionConfirmButtonLabelExpression != null : !SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT.equals(selectionRequiredWithoutSelectionConfirmButtonLabelExpression);
			case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
				return SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT == null ? selectionRequiredWithSelectionConfirmButtonLabelExpression != null : !SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT.equals(selectionRequiredWithSelectionConfirmButtonLabelExpression);
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
		result.append(" (multiple: ");
		result.append(multiple);
		result.append(", optional: ");
		result.append(optional);
		result.append(", defaultTitleExpression: ");
		result.append(defaultTitleExpression);
		result.append(", noSelectionTitleExpression: ");
		result.append(noSelectionTitleExpression);
		result.append(", withSelectionTitleExpression: ");
		result.append(withSelectionTitleExpression);
		result.append(", descriptionExpression: ");
		result.append(descriptionExpression);
		result.append(", noSelectionActionLabelExpression: ");
		result.append(noSelectionActionLabelExpression);
		result.append(", noSelectionActionDescriptionExpression: ");
		result.append(noSelectionActionDescriptionExpression);
		result.append(", withSelectionActionLabelExpression: ");
		result.append(withSelectionActionLabelExpression);
		result.append(", withSelectionActionDescriptionExpression: ");
		result.append(withSelectionActionDescriptionExpression);
		result.append(", noSelectionActionStatusMessageExpression: ");
		result.append(noSelectionActionStatusMessageExpression);
		result.append(", selectionRequiredWithoutSelectionStatusMessageExpression: ");
		result.append(selectionRequiredWithoutSelectionStatusMessageExpression);
		result.append(", selectionRequiredWithSelectionStatusMessageExpression: ");
		result.append(selectionRequiredWithSelectionStatusMessageExpression);
		result.append(", noSelectionConfirmButtonLabelExpression: ");
		result.append(noSelectionConfirmButtonLabelExpression);
		result.append(", selectionRequiredWithoutSelectionConfirmButtonLabelExpression: ");
		result.append(selectionRequiredWithoutSelectionConfirmButtonLabelExpression);
		result.append(", selectionRequiredWithSelectionConfirmButtonLabelExpression: ");
		result.append(selectionRequiredWithSelectionConfirmButtonLabelExpression);
		result.append(')');
		return result.toString();
	}

} // SelectionDialogDescriptionImpl
