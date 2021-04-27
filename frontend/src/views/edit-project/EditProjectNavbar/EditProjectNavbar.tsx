/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import {
  useBranding,
  IconButton,
  More,
  DeleteProjectModal,
  NewDocumentModal,
  RenameProjectModal,
  UploadDocumentModal,
  Logo,
  Title
} from '@eclipse-sirius/sirius-components';
import React, { useReducer } from 'react';
import { Redirect } from 'react-router-dom';
import { EditProjectNavbarContextMenu } from 'views/edit-project/EditProjectNavbar/EditProjectNavbarContextMenu';
import { EditProjectNavbarProps } from 'views/edit-project/EditProjectNavbar/EditProjectNavbar.types';
import styles from './EditProjectNavbar.module.css';
import {
  CONTEXTUAL_MENU_DISPLAYED__STATE,
  EMPTY__STATE,
  HANDLE_CLOSE_CONTEXT_MENU__ACTION,
  HANDLE_CLOSE_MODAL__ACTION,
  HANDLE_REDIRECTING__ACTION,
  HANDLE_SHOW_CONTEXT_MENU__ACTION,
  HANDLE_SHOW_MODAL__ACTION,
  REDIRECT__STATE
} from './machine';
import { initialState, reducer } from './reducer';

/**
 * Determines where the context menu should open relative to the actual mouse position.
 */
const menuPositionDelta = {
  dx: -4,
  dy: 44
};

export const EditProjectNavbar = ({ projectId, name }: EditProjectNavbarProps) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { userStatus } = useBranding();
  const onMore = (event: React.SyntheticEvent<HTMLButtonElement>) => {
    if (state.viewState === EMPTY__STATE) {
      const { x, y } = event.currentTarget.getBoundingClientRect();
      const action = {
        type: HANDLE_SHOW_CONTEXT_MENU__ACTION,
        x: x + menuPositionDelta.dx,
        y: y + menuPositionDelta.dy
      };
      dispatch(action);
    }
  };

  const { viewState, to, modalDisplayed, x, y } = state;

  let contextMenu = null;
  if (viewState === CONTEXTUAL_MENU_DISPLAYED__STATE) {
    const onCreateDocument = () => dispatch({ modalDisplayed: 'CreateDocument', type: HANDLE_SHOW_MODAL__ACTION });
    const onUploadDocument = () => dispatch({ modalDisplayed: 'UploadDocument', type: HANDLE_SHOW_MODAL__ACTION });
    const onRename = () => dispatch({ modalDisplayed: 'RenameProject', type: HANDLE_SHOW_MODAL__ACTION });
    const onDelete = () => dispatch({ modalDisplayed: 'DeleteProject', type: HANDLE_SHOW_MODAL__ACTION });
    const onCloseContextMenu = () => dispatch({ type: HANDLE_CLOSE_CONTEXT_MENU__ACTION });
    contextMenu = (
      <EditProjectNavbarContextMenu
        x={x}
        y={y}
        projectId={projectId}
        onCreateDocument={onCreateDocument}
        onUploadDocument={onUploadDocument}
        onRename={onRename}
        onDelete={onDelete}
        onClose={onCloseContextMenu}
      />
    );
  }

  const onCloseModal = () => dispatch({ type: HANDLE_CLOSE_MODAL__ACTION });

  const onProjectDeleted = () => {
    dispatch({
      type: HANDLE_REDIRECTING__ACTION,
      to: '/projects',
      modalDisplayed: null,
      x: 0,
      y: 0
    });
  };

  if (viewState === REDIRECT__STATE) {
    return <Redirect to={to} push />;
  }

  let modal = null;
  if (modalDisplayed === 'CreateDocument') {
    modal = <NewDocumentModal editingContextId={projectId} onDocumentCreated={onCloseModal} onClose={onCloseModal} />;
  } else if (modalDisplayed === 'UploadDocument') {
    modal = <UploadDocumentModal projectId={projectId} onDocumentUploaded={onCloseModal} onClose={onCloseModal} />;
  } else if (modalDisplayed === 'RenameProject') {
    modal = (
      <RenameProjectModal
        projectId={projectId}
        initialProjectName={name}
        onRename={onCloseModal}
        onClose={onCloseModal}
      />
    );
  } else if (modalDisplayed === 'DeleteProject') {
    modal = <DeleteProjectModal projectId={projectId} onDelete={onProjectDeleted} onClose={onCloseModal} />;
  }
  return (
    <>
      <div className={styles.editProjectNavbar}>
        <div className={styles.container}>
          <div className={styles.leftArea}>
            <Logo title="Back to all projects" />
          </div>
          <div className={styles.centerArea}>
            <div>
              <Title label={name} />
              <div className={styles.smallIconContainer}>
                <IconButton className={styles.moreIcon} onClick={onMore} data-testid="more">
                  <More title="More" />
                </IconButton>
              </div>
            </div>
          </div>
          <div className={styles.rightArea}>{userStatus}</div>
        </div>
      </div>
      {contextMenu}
      {modal}
    </>
  );
};
