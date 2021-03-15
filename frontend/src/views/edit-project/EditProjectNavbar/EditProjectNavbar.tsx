/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { useBranding } from 'common/BrandingContext';
import { IconButton } from 'core/button/Button';
import { More } from 'icons';
import { DeleteProjectModal } from 'modals/delete-project/DeleteProjectModal';
import { NewDocumentModal } from 'modals/new-document/NewDocumentModal';
import { RenameProjectModal } from 'modals/rename-project/RenameProjectModal';
import { UploadDocumentModal } from 'modals/upload-document/UploadDocumentModal';
import { Logo } from 'navbar/Logo';
import { Title } from 'navbar/Title';
import PropTypes from 'prop-types';
import React, { useReducer } from 'react';
import { Redirect } from 'react-router-dom';
import { EditProjectNavbarContextMenu } from 'views/edit-project/EditProjectNavbar/EditProjectNavbarContextMenu';
import styles from './EditProjectNavbar.module.css';
import {
  CONTEXTUAL_MENU_DISPLAYED__STATE,
  EMPTY__STATE,
  HANDLE_CLOSE_CONTEXT_MENU__ACTION,
  HANDLE_CLOSE_MODAL__ACTION,
  HANDLE_REDIRECTING__ACTION,
  HANDLE_SHOW_CONTEXT_MENU__ACTION,
  HANDLE_SHOW_MODAL__ACTION,
  REDIRECT__STATE,
} from './machine';
import { initialState, reducer } from './reducer';

/**
 * Determines where the context menu should open relative to the actual mouse position.
 */
const menuPositionDelta = {
  dx: -4,
  dy: 44,
};

const propTypes = {
  subscribers: PropTypes.array.isRequired,
};
export const EditProjectNavbar = ({ projectId, name, subscribers }) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { userStatus } = useBranding();
  const onMore = (event) => {
    if (state.viewState === EMPTY__STATE) {
      const { x, y } = event.target.getBoundingClientRect();
      const action = {
        type: HANDLE_SHOW_CONTEXT_MENU__ACTION,
        x: x + menuPositionDelta.dx,
        y: y + menuPositionDelta.dy,
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
      y: 0,
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
EditProjectNavbar.propTypes = propTypes;
