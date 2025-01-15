/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import MoreVertIcon from '@mui/icons-material/MoreVert';
import { makeStyles } from 'tss-react/mui';
import { CSSProperties, useEffect, useRef } from 'react';
import { DeckInput } from '../common/DeckInput';
import { DeckLaneHeaderProps } from './DeckLaneHeader.types';

import IconButton from '@mui/material/IconButton';
import { useLaneContextMenu } from './useLaneContextMenu';

const useLaneHeaderStyle = makeStyles<boolean>()((theme, draggable) => ({
  header: {
    marginBottom: ' 10px',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'flex-start',
    padding: '0px 5px',
  },
  title: {
    width: '70%',
    color: theme.palette.text.primary,
    cursor: draggable ? 'grab' : `auto`,
  },
  rightContent: {
    width: '38%',
    textAlign: 'right',
    paddingRight: '10px',
    fontSize: '13px',
  },
  more: {
    hover: {
      backgroundColor: theme.palette.action.hover,
    },
    focus: {
      backgroundColor: theme.palette.action.selected,
    },
  },
}));

const titleFontStyle: CSSProperties = {
  fontWeight: 'bold',
  fontSize: '14px',
  lineHeight: '18px',
};

export const DeckLaneHeader = ({
  updateTitle,
  editLaneTitle,
  label,
  title,
  t: translate,
  laneDraggable,
  cards,
  id,
  titleStyle = titleFontStyle,
}: DeckLaneHeaderProps) => {
  const titleInputRef = useRef<HTMLInputElement | HTMLTextAreaElement | null>(null);
  const headerRef = useRef<HTMLElement | null>(null);
  const { openContextMenu, contextMenu } = useLaneContextMenu(cards, id);

  useEffect(() => {
    if (headerRef.current) {
      if (editLaneTitle) {
        headerRef.current.focus();
      }
    }
  }, [editLaneTitle]);

  const handleKeyDown = (e: React.KeyboardEvent<HTMLDivElement>) => {
    if (titleInputRef.current) {
      if (e.key === 'F2') {
        titleInputRef.current.select();
      }
    }
  };

  const { classes } = useLaneHeaderStyle(laneDraggable);
  return (
    <>
      <header onKeyDown={handleKeyDown} tabIndex={0} ref={headerRef} className={classes.header}>
        <span
          draggable={laneDraggable}
          style={titleStyle}
          data-testid={`lane-${title}-title`}
          className={classes.title}>
          {editLaneTitle ? (
            <DeckInput
              ref={titleInputRef}
              value={title}
              placeholder={translate('placeholder.title')}
              onSave={updateTitle}
              style={titleStyle}
              data-testid={'lane-input-title'}
            />
          ) : (
            title
          )}
        </span>
        {label && <span className={classes.rightContent}>{label}</span>}
        <IconButton className={classes.more} size="small" onClick={openContextMenu} data-testid={`lane-${title}-more`}>
          <MoreVertIcon style={{ fontSize: 14 }} />
        </IconButton>
      </header>
      {contextMenu}
    </>
  );
};
