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
import { useAuth } from 'auth/useAuth';
import { UserStatusContextMenu } from 'navbar/UserStatusContextMenu';
import React, { useState } from 'react';
import { Profile } from 'icons/Profile';
import styles from './UserStatus.module.css';

export const UserStatus = () => {

  /**
 * Determines where the context menu should open relative to the actual mouse position.
 * These are relative to the bottom-left corner of the "more" icon, and to the size of the
 * caret, so that the caret at the left of the menu points to the middle of the "more" icon.
 */
  const menuPositionDelta = {
    dx: -168,
    dy: 49,
  };
  const initialState = { x: 0, y: 0, showContextMenu: false };
  const [state, setState] = useState(initialState);

  const { x, y, showContextMenu } = state;
  const auth = useAuth() as any;
  const userName = auth.username + ' (You)';

  let contextMenu = null;
  if (showContextMenu) {
    const onLoggedOutContextMenu = () => auth.logout();
    const onCloseContextMenu = () =>
      setState({
        x: 0,
        y: 0,
        showContextMenu: false,
      });
    contextMenu = (
      <UserStatusContextMenu
        x={x}
        y={y}
        userName={userName}
        onLoggedOut={onLoggedOutContextMenu}
        onClose={onCloseContextMenu}
      />
    );
  }

  const onMore = (event) => {
    if (!showContextMenu) {
      const { x, y } = event.target.getBoundingClientRect();
      setState({ x: x + menuPositionDelta.dx, y: y + menuPositionDelta.dy, showContextMenu: true });
    }
  };

  if (auth.username) {
    return (
      <>
        <div className={styles.userstatus} data-testid="userstatus" title={userName}>
          <Profile onClick={onMore} className={styles.userIcon} title="" />
        </div>
        {contextMenu}
      </>
    );
  }
  return null;
};
