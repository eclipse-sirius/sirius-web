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
import { IconButton } from 'core/button/Button';
import { Link } from 'core/link/Link';
import { Text } from 'core/text/Text';
import { MoreHorizontal } from 'icons';
import { DeleteProjectModal } from 'modals/delete-project/DeleteProjectModal';
import { RenameProjectModal } from 'modals/rename-project/RenameProjectModal';
import React, { useState } from 'react';
import { ProjectActionsContextMenu } from './ProjectActionsContextMenu';
import styles from './ProjectCard.module.css';

/**
 * Determines where the context menu should open relative to the actual mouse position.
 * These are relative to the bottom-left corner of the "more" icon, and to the size of the
 * caret, so that the caret at the left of the menu points to the middle of the "more" icon.
 */
const menuPositionDelta = {
  dx: 20,
  dy: -8,
};

export const ProjectCard = ({ project, onProjectUpdated }) => {
  const initialState = { modalDisplayed: null, x: 0, y: 0, showContextMenu: false };
  const [state, setState] = useState(initialState);

  const onMore = (event) => {
    const { x, y } = event.target.getBoundingClientRect();
    setState({ modalDisplayed: null, x: x + menuPositionDelta.dx, y: y + menuPositionDelta.dy, showContextMenu: true });
  };

  const { modalDisplayed, x, y, showContextMenu } = state;

  let contextMenu = null;
  if (showContextMenu) {
    const onRename = () => setState({ modalDisplayed: 'RenameProject', x: 0, y: 0, showContextMenu: false });
    const onDelete = () => setState({ modalDisplayed: 'DeleteProject', x: 0, y: 0, showContextMenu: false });
    const onCloseContextMenu = () => setState({ modalDisplayed: null, x: 0, y: 0, showContextMenu: false });
    contextMenu = (
      <ProjectActionsContextMenu
        projectId={project.id}
        x={x}
        y={y}
        onRename={onRename}
        onDelete={onDelete}
        onClose={onCloseContextMenu}
      />
    );
  }

  const onCloseModal = () => {
    setState({ modalDisplayed: null, x: 0, y: 0, showContextMenu: false });
    onProjectUpdated();
  };

  let modal = null;
  if (modalDisplayed === 'RenameProject') {
    modal = (
      <RenameProjectModal
        projectId={project.id}
        initialProjectName={project.name}
        onProjectRenamed={onCloseModal}
        onClose={onCloseModal}
      />
    );
  } else if (modalDisplayed === 'DeleteProject') {
    modal = <DeleteProjectModal projectId={project.id} onProjectDeleted={onCloseModal} onClose={onCloseModal} />;
  }

  return (
    <div className={styles.projectCard} data-testid={project.name}>
      <Link to={`/projects/${project.id}/edit`} data-testid={project.name}>
        <Text className={styles.link}>{project.name}</Text>
      </Link>
      <div className={styles.actions}>
        <IconButton className={styles.moreIcon} onClick={onMore} data-testid="more">
          <MoreHorizontal title="More" />
        </IconButton>
      </div>
      {contextMenu}
      {modal}
    </div>
  );
};
